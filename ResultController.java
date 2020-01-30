import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

public class ResultController implements Initializable {

    @FXML public Label scoreLabel;

    @FXML public Button closeButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("イニシャライザ");
        scoreLabel.setText("こんちわ");
    }

    public void closeButtonAction(ActionEvent event) {
        System.exit(0);
    }
}
