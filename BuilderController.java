import java.io.BufferedWriter;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class BuilderController implements SelectorDelegate, Initializable {

    @FXML public TextField titleField;
    @FXML public ImageView selectorView;
    @FXML public GridPane  gridPane;

    //影を落とすエフェクトのインスタンス
    private ColorAdjust shadow = new ColorAdjust();
    private ColorAdjust normal = new ColorAdjust();

    private Selector selector = new Selector();

    //マップデータ
    private AnimationItem[][] data = new AnimationItem[21][15];

    //ドラッグ用
    private int dragX = 0;
    private int dragY = 0;

    //イニシャライザ
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        selector.setDelegate(this);

        gridPane.getChildren().clear();
        initMap();
        printMap();

        shadow.setBrightness(-0.2);
        normal.setBrightness(0.0);
    }

    //セレクタにデータを入れるメソッド
    @Override
    public void setData(Selector sender) {
        final int allCount = Goal.length() + Space.length() + Wall.length();
        int count = 0;
        sender.items = new AnimationItem[allCount];

        for (Goal goalItem : Goal.values()) {
            sender.items[count] = new AnimationItem(selectorView, AnimationItem.Attribute.Goal, goalItem.getValue(), false);
            count++;
        }

        //空白を追加してるとこ
        for (Space spaceItem : Space.values()) {
            sender.items[count] = new AnimationItem(selectorView, AnimationItem.Attribute.Space, spaceItem.getValue(), false);
            count++;
        }

        //壁を追加してるとこ
        for (Wall wallItem : Wall.values()) {
            sender.items[count] = new AnimationItem(selectorView, AnimationItem.Attribute.Wall, wallItem.getValue(), false);
            count++;
        }
/*
        //敵キャラを追加してるとこ
        for (Enemy enemyItem : Enemy.values()) {
            sender.items[count] = new AnimationItem(selectorView, AnimationItem.Attribute.Enemy, enemyItem.getValue(), false);
            count++;
        }
*/
    }

    //ゴールを追加するならココへ
    protected enum Goal {
        goal("goal");

        private String name;

        private Goal(String name) {
            this.name = name;
        }

        public String getValue() {
            return name;
        }

        public static int length() {
            return Goal.values().length;
        }

    }

    //壁を増やすならココへ
    protected enum Wall {
        brown("brown"),
        blue("blue"),
        normal("normal"),
        metal("metal"),
        link1("link1"),
        link2("link2"),
        link3("link3"),
        link4("link4"),
        link5("link5"),
        link6("link6"),
        link7("link7"),
        link8("link8"),
        link9("link9"),
        link10("link10"),
        link11("link11"),
        link12("link12"),
        link13("link13"),
        link14("link14"),
        link15("link15"),
        link16("link16");

        private String name;

        private Wall(String name) {
            this.name = name;
        }

        public String getValue() {
            return name;
        }

        public static int length() {
            return Wall.values().length;
        }
    }

    //空白を増やすならココへ
    protected enum Space {
        white("SPACE");

        private String name;

        private Space(String name) {
            this.name = name;
        }

        public String getValue() {
            return name;
        }

        public static int length() {
            return Space.values().length;
        }
    }

    //敵キャラを増やすならココへ
    protected enum Enemy {
        def("def");

        private String name;

        private Enemy(String name) {
            this.name = name;
        }

        public String getValue() {
            return name;
        }

        public static int length() {
            return Enemy.values().length;
        }
    }

    //右ボタン
    public void nextAction(ActionEvent event) {
        selector.nextItem();
    }

    //左ボタン
    public void prevAction(ActionEvent event) {
        selector.prevItem();
    }

    //作成ボタン
    public void decideAction(ActionEvent event) {
        //一致していないタイトルがあるかどうかの確認
        final String title = titleField.getText();
        final File   file  = new File("Maps/" + title + ".csv");
/*
        if (title.equals("") || file.exists()) {
            System.out.println("タイトルが不正です");
            return;
        }
*/
        //壁に穴がないかの確認
        for (int x = 0; x < data.length; x++) {
            for (int y = 0; y < data[0].length; y++) {
                if (shouldWall(x, y) && data[x][y].attribute != AnimationItem.Attribute.Wall)  {
                    System.out.println("壁に穴が空いています");
                    return;
                }
            }
        }

        //書き出し
        try {
            FileWriter fileWriter = new FileWriter(file);
            PrintWriter printWriter = new PrintWriter(new BufferedWriter(fileWriter));

            file.createNewFile();
            System.out.println("Maps/"+ file.getName() + " を作成しました");

            for (AnimationItem[] array : data) {
                for (AnimationItem item : array) {
                    printWriter.println(item.getID());
                }
            }

            System.out.println("Maps/"+ file.getName() + " への書き込みが完了しました");
            printWriter.close();
        } catch (IOException e) {
            System.err.println(e);
        }

        MapGame.getInstance().toBuiltGame();
    }

    //リロードボタン
    public void reloadAction(ActionEvent event) {
        try {
            File file = new File("Maps/" + titleField.getText() + ".csv");

            if (!file.exists()) {
                System.err.println("ファイルが存在しません");
                return;
            }

            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            for (int x = 0; x < data.length; x++) {
                for (int y = 0; y < data[0].length; y++) {
                    data[x][y].stop();
                    //１行読み込んでインスタンスを作成
                    String line;
                    if ((line = bufferedReader.readLine()) != null) {
                        System.out.println("アイテムを読み込みます (" + x + ", " + y + ")");

                        ImageView     view    = data[x][y].getImageView();
                        AnimationItem newItem = new AnimationItem(view, line, true);

                        switch (newItem.attribute) {
                            case Start:
                                data[x][y] = new AnimationItem(view, "Space:SPACE", true);
                                break;
                            case Goal:
                                data[x][y] = newItem;
                                break;
                            case Wall:
                                data[x][y] = newItem;
                                break;
                            case Space:
                                data[x][y] = newItem;
                                break;
                            case Item:
                                data[x][y] = newItem;
                                break;
                            //case Enemy:
                            //    mapData[x][y] = new AnimationItem(view, "Space:SPACE", false);
                            //    break;
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

    //キャンセルボタン
    public void exitAction(ActionEvent event) {
        Platform.exit();
    }

    //ドラッグ開始時の処理
    public void dragDetected(MouseEvent event) {
        dragX = (int)(event.getX() / 32);
        dragY = (int)(event.getY() / 32);
        System.out.println("Drag Started");
    }

    //ドラッグ時の処理
    public void dragOnGrid(MouseEvent event) {
        final int cursorX = (int)(event.getX() / 32);
        final int cursorY = (int)(event.getY() / 32);

        final int[] rangeX = makeRange(dragX, cursorX);
        final int[] rangeY = makeRange(dragY, cursorY);

        for (int x = 0; x < data.length; x++) {
            for (int y = 0; y < data[0].length; y++) {
                if (included(x, rangeX) && included(y, rangeY)) {
                    final ImageView view = data[x][y].getImageView();
                    view.setEffect(shadow);
                } else {
                    final ImageView view = data[x][y].getImageView();
                    view.setEffect(normal);
                }
            }
        }
    }

    //マウスのボタンを離したときの処理
    public void mouseReleased(MouseEvent event) {
        for (int x = 0; x < data.length; x++) {
            for (int y = 0; y < data[0].length; y++) {
                final AnimationItem oldItem = data[x][y];
                final ImageView     view    = oldItem.getImageView();

                if (view.getEffect() == shadow) {
                    final AnimationItem newItem = new AnimationItem(view, selector.getItem().attribute, selector.getItem().identifier, false);
                    data[x][y] = newItem;

                    oldItem.stop();
                    newItem.start();
                    view.setEffect(normal);
                }
            }
        }
    }

    //範囲を示す配列を作成する
    private int[] makeRange(int num1, int num2) {
        if (num1 < num2) {
            int[] range = new int[num2 - num1 + 1];
            for (int i = 0; i < range.length; i++) {
                range[i] = num1 + i;
            }

            return range;
        } else {
            int[] range = new int[num1 - num2 + 1];
            for (int i = 0; i < range.length; i++) {
                range[i] = num2 + i;
            }

            return range;
        }
    }

    //配列が値を含んでいるか確認
    private boolean included(int num, int[] array) {
        boolean ans = false;

        for (int i : array) {
            if (i == num) {
                ans = true;
            }
        }

        return ans;
    }

    //空マップデータを作成
    private void initMap() {
        for (int x = 0; x < 21; x++) {
            for (int y = 0; y < 15; y++) {
                System.out.println("x: " + x + " y: " + y);
                ImageView view   = new ImageView();

                //viewにカーソルを合わせたときの処理
                view.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        view.setEffect(shadow);
                    }
                });

                //viewからカーソルを出したときの処理
                view.setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        view.setEffect(normal);
                    }
                });

                if (shouldWall(x, y)) {
                    //壁にする
                    data[x][y] = new AnimationItem(view, AnimationItem.Attribute.Wall, Wall.brown.getValue(), true);
                } else {
                    //空白にする
                    data[x][y] = new AnimationItem(view, AnimationItem.Attribute.Space, Space.white.getValue(), true);
                }
            }
        }
    }

    //座標の地点が壁であるべきかどうかの判定
    private boolean shouldWall(int x, int y) {
        if (x == 0 || x == 20 || y == 0 || y == 14) {
            return true;
        } else {
            return false;
        }
    }

    //マップを描画
    private void printMap() {
        gridPane.getChildren().clear();

        for (int x = 0; x < 21; x++) {
            for (int y = 0; y < 15; y++) {
                ImageView view = data[x][y].getImageView();
                gridPane.add(view, x, y);
            }
        }
    }
}
