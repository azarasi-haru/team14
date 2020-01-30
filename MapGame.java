import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class MapGame extends Application {

  Stage stage;
  Scene start;
  Scene game;
  Scene builder;
  Scene builtGame;
  Scene result;

  private BuilderController   builderController;
  private BuiltGameController builtGameController;

  //画面遷移用に外部クラスに渡す自身のインスタンス
  private static MapGame singleton;

  @Override
  public void start(Stage primaryStage) throws Exception {
    stage = primaryStage;
    singleton = this;
    primaryStage.setTitle("MAP GAME");

    start = setPage("Start.fxml");
    primaryStage.setScene(start);
    primaryStage.show();

    // play BGM
    Audio.playBgm();
  }

  public static void main(String[] args) {
    launch(args);
  }

  //ゲーム画面への画面遷移
  public void toGame() {
    game = setPage("MapGame.fxml");
    stage.setScene(game);
  }

  public void toBuilder() {
    if (builder == null) {
        builder = setPage("BuilderController.fxml");
    }
    stage.setScene(builder);
  }

  public void toBuiltGame() {
    if (builtGame == null) {
        builtGame = setPage("BuiltGameController.fxml");
        builtGame.setOnKeyPressed(e -> {
            builtGameController.keyAction(e);
        });
        builtGame.getStylesheets().add("BuiltGameController.css");
    }
    stage.setScene(builtGame);
    builtGameController.setDataFile(builderController.titleField.getText());
  }

  public void toResult() {
    
    result = setPage("Result.fxml");
    stage.setScene(result);
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
    } catch (Exception e) {
      System.err.println(e);
    }
    return null;
  }

  //外部に自身のインスタンスを渡す
  public static MapGame getInstance() {
    return singleton;
  }
}
