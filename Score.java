import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.Group;
import javafx.fxml.FXML;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Collections;

public class Score {

    private static int score;

    // initial score
    Score(int n){
      score = n;
    }

    // minusScore(int 引数)
    // スコアを引数分マイナスする.

    // plusScore(int 引数)
    // スコアを引数分プラスする.

    // getScore()
    // スコアの値を返す.

    // setScore()
    // スコアの値をセットする.


    // printScore()
    // スコアの値をGUIに出力する.

    public static void minusScore(int n){
      score = score - n;
    }

    public static void plusScore(int n){
      score = score + n;
    }

    public static void setScore(int n){
      score = n;
      System.out.println("set score: " + score);
    }

    public static int getScore(){
      System.out.println("get score: " + score);
      return score;
    }
    public static void readfile(){
    try{
      File file = new File("score.txt");
      FileReader fileReader = new FileReader(file);
      BufferedReader bufferedReader = new BufferedReader(fileReader);

      String data;
      ArrayList<Integer> score = new ArrayList<>();

      while((data = bufferedReader.readLine()) != null){
        score.add(Integer.parseInt(data));
      }
      Collections.sort(score, Collections.reverseOrder());
      for(int i = 0; i < 3; i++){
        int rank = score.get(i);
        System.out.println(rank);
      }
      bufferedReader.close();
    }catch(IOException e){
      e.printStackTrace();
    }
  }
  public static void writefile(){
    try{
      FileWriter file = new FileWriter("score.txt", true);
      PrintWriter pw = new PrintWriter(new BufferedWriter(file));

      pw.println(getScore());

      pw.close();
    }catch(IOException e){
      e.printStackTrace();
    }
  }
}
