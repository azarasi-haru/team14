import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Collections;

public class ResultController implements Initializable {

    @FXML public Label scoreLabel;
    @FXML public Label rankLabel1;
    @FXML public Label rankLabel2;
    @FXML public Label rankLabel3;

    @FXML public Button closeButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{
          File file = new File("score.txt");
          FileReader fileReader = new FileReader(file);
          BufferedReader bufferedReader = new BufferedReader(fileReader);

          String data;
          ArrayList<Integer> score = new ArrayList<>();
          while((data = bufferedReader.readLine()) != null){
            score.add(Integer.parseInt(data));
            scoreLabel.setText(data);
          }
          Collections.sort(score, Collections.reverseOrder());
          rankLabel1.setText("1位." + score.get(0));
          rankLabel2.setText("2位." + score.get(1));
          rankLabel3.setText("3位." + score.get(2));
          bufferedReader.close();
        }catch(IOException e){
          e.printStackTrace();
        }
    }
    public void startButtonAction(ActionEvent event) {
      MapGame.getInstance().toGame();
    }

    public void buildButtonAction(ActionEvent event) {
      MapGame.getInstance().toBuilder();
    }
}
