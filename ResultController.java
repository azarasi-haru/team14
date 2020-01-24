import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ResultController {

    @FXML private Button closeButton;

    public void closeButtonAction(ActionEvent event) {
        System.exit(0);
    }
}
