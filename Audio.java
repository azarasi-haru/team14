import java.io.File;

import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.net.MalformedURLException;

import javafx.scene.media.AudioClip;

public class Audio {
  // set BGM
  public static Media bgm = new Media(new File("bgm.wav").toURI().toString());

  public static void playBgm(){
    //音声の再生等の操作を実行できるオブジェクト
    MediaPlayer bgm_play = new MediaPlayer(bgm);
    //BGM無限ループ
    bgm_play.setCycleCount(MediaPlayer.INDEFINITE);
    bgm_play.setAutoPlay(true);
  }
}
