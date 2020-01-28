import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class MapGameBuilder extends Application {
    Stage stage;
    Scene builder, game;

    private BuilderController   builderController;
    private BuiltGameController builtGameController;

    private static MapGameBuilder singleton;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        singleton = this;
        primaryStage.setTitle("Map Game Builder");

        builder = setPage("BuilderController.fxml");
        primaryStage.setScene(builder);
        primaryStage.show();
    }

    public void toGame() {
        game = setPage("BuiltGameController.fxml");
        game.setOnKeyPressed(e -> {
            builtGameController.keyAction(e);
        });
        game.getStylesheets().add("BuiltGameController.css");
        stage.setScene(game);
        builtGameController.setDataFile(builderController.titleField.getText());
    }

    //fxmlファイルの読み込み
    private Scene setPage(String fxml) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
            Pane newPane = (Pane)fxmlLoader.load();
            Scene newScene = new Scene(newPane);

            if (fxml.equals("BuilderController.fxml")) {
                System.out.println("get: builderController");
                builderController = (BuilderController)fxmlLoader.getController();
            }

            if (fxml.equals("BuiltGameController.fxml")) {
                System.out.println("get: builtGameController");
                builtGameController = (BuiltGameController)fxmlLoader.getController();
            }

            return newScene;
        } catch(Exception e) {
            System.err.println(e);
        }

        return null;
    }

    public static MapGameBuilder getInstance() {
        return singleton;
    }
}
