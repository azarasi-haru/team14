import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class StartController {

  @FXML private Button startButton;

  public void startAction() {
    MapGame.getInstance().toGame();
  }

  public void builderAction() {
    MapGame.getInstance().toBuilder();
  }

  public void startButtonAction(ActionEvent event) {
    startAction();
  }

  public void buildButtonAction(ActionEvent event) {
    builderAction();
  }
}
