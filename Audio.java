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
    MediaPlayer bgm_play = new MediaPlayer(bgm);
    // 無限ループ
    bgm_play.setCycleCount(MediaPlayer.INDEFINITE);
    bgm_play.setAutoPlay(true);
  }
}
