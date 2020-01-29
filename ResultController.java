import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

public class ResultController {

    @FXML public Label scoreLabel;

    @FXML public Button closeButton;

    public void closeButtonAction(ActionEvent event) {
        System.exit(0);
    }
}
