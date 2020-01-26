import java.io.File;

import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class MapGame extends Application {

  Stage stage;
  Scene start, game, result;

  //画面遷移用に外部クラスに渡す自身のインスタンス
  public static MapGame singleton;

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

  public void toResult() {
    Score.readfile();
    result = setPage("Result.fxml");
    stage.setScene(result);
  }

  //fxmlファイルの読み込み
  private Scene setPage(String fxml) {
    try {
      Pane newPane = (Pane)FXMLLoader.load(getClass().getResource(fxml));
      Scene newScene = new Scene(newPane);
      return newScene;
    } catch (Exception e) {
      System.err.println(e);
      System.out.println("そのようなfxmlファイルはありません.");
    }
    return null;
  }

  //外部に自身のインスタンスを渡す
  public static MapGame getInstance() {
    return singleton;
  }
}
