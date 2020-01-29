import java.net.URL;
import java.util.ResourceBundle;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.Initializable; 
import javafx.scene.layout.GridPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;

public class BuiltGameController implements Initializable {

    @FXML public GridPane gridPane;

    @FXML public Button   button_down;
    @FXML public Button   button_left;
    @FXML public Button   button_right;
    @FXML public Button   button_up;
    @FXML public Button   button_A;
    @FXML public Button   button_B;

    private BuiltGameChara player = new BuiltGameChara(new ImageView(), 1, 1, CharaType.Player);

    private AnimationItem[][] mapData = new AnimationItem[21][15];

    private String dataFile;

    //イニシャライザ
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        player.setPos(1,1);

        gridPane.getStyleClass().add("display");
        button_down.getStyleClass().add("button");
        button_left.getStyleClass().add("button");
        button_right.getStyleClass().add("button");
        button_up.getStyleClass().add("button");
        button_A.getStyleClass().add("button");
        button_B.getStyleClass().add("button");
    }

    public void setDataFile(String file) {
        dataFile = file;
        loadMap();
        printMap();
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

        printMap();
    }

    //左移動
    private void leftAction() {
        final int[] newPos = player.leftPos();
        final int   x      = newPos[0];
        final int   y      = newPos[1];

        if (canMoveTo(x, y)) {
            player.goLeft();
        }

        printMap();
    }

    //右移動
    private void rightAction() {
        final int[] newPos = player.rightPos();
        final int   x      = newPos[0];
        final int   y      = newPos[1];

        if (canMoveTo(x, y)) {
            player.goRight();
        }

        printMap();
    }
    
    //上移動
    private void upAction() {
        final int[] newPos = player.upPos();
        final int   x      = newPos[0];
        final int   y      = newPos[1];

        if (canMoveTo(x, y)) {
            player.goUp();
        }

        printMap();
    }

    //動けるかどうかの判定
    public boolean canMoveTo(int x, int y) {
        return !(mapData[x][y].attribute == AnimationItem.Attribute.Wall);
    }

    //マップのロード (キャラ位置の初期化もするよ)
    private void loadMap() {

        try {
            File file = new File("Maps/" + dataFile + ".csv");

            if (!file.exists()) {
                System.err.println("ファイルが存在しません");
                return;
            }

            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            for (int x = 0; x < mapData.length; x++) {
                for (int y = 0; y < mapData[0].length; y++) {
                    //１行読み込んでインスタンスを作成
                    String line;
                    if ((line = bufferedReader.readLine()) != null) {
                        System.out.println("アイテムを読み込みます (" + x + ", " + y + ")");

                        ImageView     view    = new ImageView();
                        AnimationItem newItem = new AnimationItem(view, line, false);

                        switch (newItem.attribute) {
                            case Start:
                                player.setPos(x, y);
                                mapData[x][y] = new AnimationItem(view, "Space:SPACE", false);
                                break;
                            case Goal:
                                mapData[x][y] = newItem;
                                break;
                            case Wall:
                                mapData[x][y] = newItem;
                                break;
                            case Space:
                                mapData[x][y] = newItem;
                                break;
                            case Item:
                                mapData[x][y] = newItem;
                                break;
                            case Enemy:
                                System.out.println("敵をロードしたよ");
                                mapData[x][y] = new AnimationItem(view, "Space:SPACE", false);
                                break;
                            default:
                                System.out.println("無効なアイテムです　(x: " + x + ", y: " + y + ")");
                        }
                    } else {
                        System.err.println("ファイルが終了しました。");
                        bufferedReader.close();
                    }
                }
            }

            bufferedReader.close();
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    //マップを描画する
    private void printMap() {
        gridPane.getChildren().clear();

        for (int x = 0; x < mapData.length; x++) {
            for (int y = 0; y < mapData[0].length; y++) {

                if (player.x == x && player.y == y) {
                    printChara(player, x, y);
                    continue;
                }

                ImageView view = mapData[x][y].getImageView();
                mapData[x][y].start();
                gridPane.add(view, x, y);
            }
        }
    }

    //キャラクターをプリントする
    private void printChara(BuiltGameChara chara, int x, int y) {
        ImageView view = chara.getImageView();
        gridPane.add(view, x, y);
    }

    //ゴールしたときの処理
    private void goalAction() {
    }

    //次のステージへ
    private void nextStage() {
    }
}
