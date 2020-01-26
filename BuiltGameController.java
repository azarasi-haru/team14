import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable; 
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;

public class BuiltGameController implements Initializable {

    private BuiltGameChara player = new BuiltGameChara(0, 0, CharaType.Player);
    private BuiltGameItem[] items;

    private AnimationItem[][] mapData = new AnimationItem[21][15];

    //イニシャライザ
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    //キーボードが押されたときの処理
    public void keyAction(KeyEvent event) {
        KeyCode key = event.getCode();

        switch (key) {
            case DOWN:
            case J:
            case S:
                downAction();
                break;
            case UP:
            case K:
            case W:
                upAction();
                break;
            case LEFT:
            case H:
            case A:
                leftAction();
                break;
            case RIGHT:
            case L:
            case D:
                rightAction();
                break;
            default:
                System.out.println("無効なキーです");
        }
    }

    //下移動
    private void downAction() {
        final int[] newPos = player.downPos();
        final int   x      = newPos[0];
        final int   y      = newPos[1];

        if (canMoveTo(x, y)) {
            player.goDown();
        }
    }

    //左移動
    private void leftAction() {
        final int[] newPos = player.leftPos();
        final int   x      = newPos[0];
        final int   y      = newPos[1];

        if (canMoveTo(x, y)) {
            player.goLeft();
        }
    }

    //右移動
    private void rightAction() {
        final int[] newPos = player.rightPos();
        final int   x      = newPos[0];
        final int   y      = newPos[1];

        if (canMoveTo(x, y)) {
            player.goRight();
        }
    }
    
    //上移動
    private void upAction() {
        final int[] newPos = player.upPos();
        final int   x      = newPos[0];
        final int   y      = newPos[1];

        if (canMoveTo(x, y)) {
            player.goUp();
        }
    }

    //動けるかどうかの判定
    public boolean canMoveTo(int x, int y) {
        return !(mapData[x][y].attribute == AnimationItem.Attribute.Wall);
    }

    //マップのロード
    private void loadMap() {
    }

    //マップを描画する
    private void printMap() {
    }

    //ゴールしたときの処理
    private void goalAction() {
    }

    //次のステージへ
    private void nextStage() {
    }
}
