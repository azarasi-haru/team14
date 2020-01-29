import java.io.File;

import javafx.scene.image.ImageView;

public class BuiltGameChara {

    final static int DOWN  = 0;
    final static int LEFT  = 1;
    final static int RIGHT = 2;
    final static int UP    = 3;

    public int x;
    public int y;

    private ImageView view;

    private int direction   = 0;
    private int currentForm = 0;

    CharaType type;
    AnimationItem[][]  forms;

    BuiltGameChara(ImageView view, int x, int y, CharaType type) {
        this.view = view;
        this.x = x;
        this.y = y;
        this.type = type;
        loadItems();
    }

    //AnimationItemを作成する
    private void loadItems() {
        try {
            String dir   = "png/Chara/";
            File[] files = sortThreeFiles(new File(dir).listFiles());
            forms = new AnimationItem[files.length][4];

            for (int i = 0; i < files.length; i++) {
                String          formName  = files[i].getName();
                System.out.println(formName);
                AnimationItem   downItem  = new AnimationItem(view, AnimationItem.Attribute.Player, formName + "/down",  false);
                AnimationItem   leftItem  = new AnimationItem(view, AnimationItem.Attribute.Player, formName + "/left",  false);
                AnimationItem   rightItem = new AnimationItem(view, AnimationItem.Attribute.Player, formName + "/right", false);
                AnimationItem   upItem    = new AnimationItem(view, AnimationItem.Attribute.Player, formName + "/up",    false);
                AnimationItem[] form      = {downItem, leftItem, rightItem, upItem};

                forms[i] = form;
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    //ファイル名でソート
    private File[] sortThreeFiles(File[] files) {
        File[] ans = new File[3];

        if (files[0].getName().compareTo(files[1].getName()) < 0 && files[0].getName().compareTo(files[2].getName()) < 0) {
            ans[0] = files[0];
            if (files[1].getName().compareTo(files[2].getName()) < 0) {
                ans[1] = files[1];
                ans[2] = files[2];
            } else {
                ans[1] = files[2];
                ans[2] = files[1];
            }
        } else if (files[0].getName().compareTo(files[1].getName()) > 0 && files[0].getName().compareTo(files[2].getName()) < 0) {
            ans[0] = files[1];
            ans[1] = files[0];
            ans[2] = files[2];
        } else if (files[0].getName().compareTo(files[1].getName()) < 0 && files[0].getName().compareTo(files[2].getName()) > 0) {
            ans[0] = files[2];
            ans[1] = files[0];
            ans[2] = files[1];
        } else {
            ans[2] = files[0];
            if (files[1].getName().compareTo(files[2].getName()) < 0) {
                ans[0] = files[1];
                ans[1] = files[2];
            } else {
                ans[0] = files[2];
                ans[1] = files[1];
            }
        }

        return ans;
    }

    //攻撃
    public void attack() {
    }

    //変身
    public void transform() {
        if (currentForm == forms.length) {
            return;
        }

        forms[currentForm][direction].stop();
        currentForm++;
        forms[currentForm][direction].start();
    }

    //下移動したときの座標 & 方向転換
    public int[] downPos() {
        final int[] pos = {x, y + 1};
        forms[currentForm][direction].stop();
        direction = DOWN;
        forms[currentForm][direction].start();
        return pos;
    }

    //左移動したときの座標 & 方向転換
    public int[] leftPos() {
        final int[] pos = {x - 1, y};
        forms[currentForm][direction].stop();
        direction = LEFT;
        forms[currentForm][direction].start();
        return pos;
    }

    //右移動したときの座標 & 方向転換
    public int[] rightPos() {
        final int[] pos = {x + 1, y};
        forms[currentForm][direction].stop();
        direction = RIGHT;
        forms[currentForm][direction].start();
        return pos;
    }

    //上移動したときの座標 & 方向転換
    public int[] upPos() {
        final int[] pos = {x, y - 1};
        forms[currentForm][direction].stop();
        direction = UP;
        forms[currentForm][direction].start();
        return pos;
    }

    //下移動
    public void goDown() {
        y += 1;
    }

    //左移動
    public void goLeft() {
        x -= 1;
    }

    //右移動
    public void goRight() {
        x += 1;
    }

    //上移動
    public void goUp() {
        y -= 1;
    }

    //指定した座標に移動
    public void setPos(int x, int y) {
        forms[currentForm][direction].stop();
        direction = DOWN;
        forms[currentForm][direction].start();
        this.x = x;
        this.y = y;
    }

    public ImageView getImageView() {
        return view;
    }
}
