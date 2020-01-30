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

public class ResultController implements Initializable {

    @FXML public Label scoreLabel;

    @FXML public Button closeButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{
          File file = new File("score.txt");
          FileReader fileReader = new FileReader(file);
          BufferedReader bufferedReader = new BufferedReader(fileReader);

          String data;

          while((data = bufferedReader.readLine()) != null){
            scoreLabel.setText(data);
          }
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
