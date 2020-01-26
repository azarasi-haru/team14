import javafx.scene.image.ImageView;

public class BuiltGameChara {

    final static int DOWN  = 0;
    final static int LEFT  = 1;
    final static int RIGHT = 2;
    final static int UP    = 3;

    public int x;
    public int y;

    private ImageView view;

    private int currentDir  = 0;
    private int currentForm = 0;

    CharaType type;
    AnimationItem[][]  forms;

    BuiltGameChara(ImageView view, int x, int y, CharaType type) {
        this.view = view;
        this.x = x;
        this.y = y;
        this.type = type;
    }

    //攻撃
    public void attack() {
    }

    //変身
    public void transform() {
    }

    //下移動したときの座標
    public int[] downPos() {
        final int[] pos = {x, y + 1};
        return pos;
    }

    //左移動したときの座標
    public int[] leftPos() {
        final int[] pos = {x - 1, y};
        return pos;
    }

    //右移動したときの座標
    public int[] rightPos() {
        final int[] pos = {x + 1, y};
        return pos;
    }

    //上移動したときの座標
    public int[] upPos() {
        final int[] pos = {x, y - 1};
        return pos;
    }

    //下移動
    public void goDown() {
        forms[currentForm][currentDir].stop();
        currentDir = DOWN;
        forms[currentForm][currentDir].start();
        y += 1;
    }

    //左移動
    public void goLeft() {
        forms[currentForm][currentDir].stop();
        currentDir = LEFT;
        forms[currentForm][currentDir].start();
        x -= 1;
    }

    //右移動
    public void goRight() {
        forms[currentForm][currentDir].stop();
        currentDir = RIGHT;
        forms[currentForm][currentDir].start();
        x += 1;
    }

    //上移動
    public void goUp() {
        forms[currentForm][currentDir].stop();
        currentDir = UP;
        forms[currentForm][currentDir].start();
        y -= 1;
    }

    //指定した座標に移動
    public void setPos(int x, int y) {
        forms[currentForm][currentDir].stop();
        currentDir = DOWN;
        forms[currentForm][currentDir].start();
        this.x = x;
        this.y = y;
    }

    public ImageView getImageView() {
        return view;
    }
}
