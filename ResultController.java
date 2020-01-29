import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

public class ResultController {

    @FXML private Label firstLabel;
    @FXML private Label secondLabel;
    @FXML private Label thirdLabel;
    @FXML private Label fourthLabel;
    @FXML private Label fifthLabel;

    @FXML private Button closeButton;

    public void closeButtonAction(ActionEvent event) {
        System.exit(0);
    }
}
