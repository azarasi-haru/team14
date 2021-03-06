import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.animation.AnimationTimer;

public class MoveChara {
  public static final int TYPE_DOWN  = 0;
  public static final int TYPE_LEFT  = 1;
  public static final int TYPE_RIGHT = 2;
  public static final int TYPE_UP    = 3;

  private final String[] dirStrings  = { "d", "l", "r", "u" };
  private final String[] kindStrings = { "1", "2", "3" };
  static int animalNumber=0;
  private final String[] pngPathBefore= {"png/neko/neko","png/neko2/neko","png/neko3/neko"};
  private final String pngPathAfter  = ".png";

  private int posX;
  private int posY;

  private MapData mapData;
  private Score score;

  private Image[][][] charaImages;
  private ImageView[][] charaImageViews;
  private ImageAnimation[][] charaImageAnimations;

  private int count   = 0;
  private int diffx   = 1;
  private int charaDir;

  MoveChara(int startX, int startY, MapData mapData, Score score){
    this.mapData = mapData;
    int animals = 3;
    charaImages = new Image[animals][4][3];
    charaImageViews = new ImageView[animals][4];
    charaImageAnimations = new ImageAnimation[animals][4];

    for (int i=0; i<4; i++) {
      charaImages[0][i] = new Image[3];
      charaImages[1][i] = new Image[3];
      charaImages[2][i] = new Image[3];
      for (int j=0; j<3; j++) {
        charaImages[0][i][j] = new Image(pngPathBefore[0] + dirStrings[i] + kindStrings[j] + pngPathAfter);
        charaImages[1][i][j] = new Image(pngPathBefore[1] + dirStrings[i] + kindStrings[j] + pngPathAfter);
        charaImages[2][i][j] = new Image(pngPathBefore[2] + dirStrings[i] + kindStrings[j] + pngPathAfter);
      }
      charaImageViews[0][i] = new ImageView(charaImages[0][i][0]);
      charaImageViews[1][i] = new ImageView(charaImages[1][i][0]);
      charaImageViews[2][i] = new ImageView(charaImages[2][i][0]);
      charaImageAnimations[0][i] = new ImageAnimation( charaImageViews[0][i], charaImages[0][i] );
      charaImageAnimations[1][i] = new ImageAnimation( charaImageViews[1][i], charaImages[1][i] );
      charaImageAnimations[2][i] = new ImageAnimation( charaImageViews[2][i], charaImages[2][i] );
    }

    posX = startX;
    posY = startY;

    setCharaDir(TYPE_DOWN);
  }

  public void changeCount(){
    count = count + diffx;
    if (count > 2) {
      count = 1;
      diffx = -1;
    } else if (count < 0){
      count = 1;
      diffx = 1;
    }
  }

  public int getPosX(){
    return posX;
  }

  public int getPosY(){
    return posY;
  }

  public void setCharaDir(int cd){
    charaDir = cd;
    for (int i=0; i<4; i++) {
      if (i == charaDir) {
        charaImageAnimations[animalNumber][i].start();
      } else {
        charaImageAnimations[animalNumber][i].stop();
      }
    }
  }

  public boolean canMove(int dx, int dy){
    if (mapData.getMap(posX+dx, posY+dy) == MapData.TYPE_WALL){
      return false;
    } else if (mapData.getMap(posX+dx, posY+dy) == MapData.TYPE_NONE){
      return true;
    }
    return false;
  }

  public boolean move(int dx, int dy){
    // Score
    Score.getScore();
    if (canMove(dx,dy)){
      posX += dx;
      posY += dy;
      Score.minusScore(1);
      return true;
    } else if(catchSomething(dx, dy, MapData.TYPE_GOAL)){
      // catch TYPE_GOAL
      posX += dx;
      posY += dy;
      Score.minusScore(1);
      return true;
    } else if(catchSomething(dx, dy, MapData.TYPE_ITEM)){
      // catch TYPE_ITEM
      posX += dx;
      posY += dy;
      Score.minusScore(1);
      Audio.playItem();
      return true;
    } else if(catchSomething(dx, dy, MapData.TYPE_HUMAN)){
      // catch TYPE_ITEM
      posX += dx;
      posY += dy;
      Audio.playDamage();
      if(mapData.getSomethingNumber(MapData.TYPE_ITEM) == 0){
        //
        Score.plusScore(200);
      } else if(mapData.getSomethingNumber(MapData.TYPE_ITEM) == 1){
        Score.minusScore(1);
      } else {
        Score.minusScore(100);
      }
      return true;
    } else {
      return false;
    }
  }

  public boolean goal(){
    if(catchSomething(MapData.TYPE_GOAL)){
      if(mapData.getSomethingNumber(MapData.TYPE_ITEM) == 0){
        // If All Items Nothing
        return true;
      }
    }
    return false;
  }

  public boolean item(){
    if(catchSomething(MapData.TYPE_ITEM)){
      return true;
    }
    return false;
  }

  public boolean human_die(){
    if(catchSomething(MapData.TYPE_HUMAN)){
      if(mapData.getSomethingNumber(MapData.TYPE_ITEM) == 0){
        // If All Items Nothing
        return true;
      }
    }
    return false;
  }

  // catchSomething(x, y, int MapData.TYPE) by KUBOTA
  public boolean catchSomething(int dx, int dy, int type){
    if (mapData.getMap(posX+dx, posY+dy) == type){
      return true;
    }
    return false;
  }

  // キャラの移動した位置に,指定したアイテムがあればtrue返す by KUBOTA
  public boolean catchSomething(int type){
    if (mapData.getMap(posX, posY) == type){
      return true;
    }
    return false;
  }

  // キャラの移動した位置の画像をNONEにする. BY KUBOTA
  public void removeSomething(){
    mapData.removeSomething(posX , posY);
  }
  // by KUBOTA HARUKI

  public ImageView getCharaImageView(){
    return charaImageViews[animalNumber][charaDir];
  }

  private class ImageAnimation extends AnimationTimer {
    // アニメーション対象ノード
    private ImageView   charaView     = null;
    private Image[]     charaImages   = null;
    private int         index       = 0;

    private long        duration    = 500 * 1000000L;   // 500[ms]
    private long        startTime   = 0;

    private long count = 0L;
    private long preCount;
    private boolean isPlus = true;

    public ImageAnimation( ImageView charaView , Image[] images ) {
      this.charaView   = charaView;
      this.charaImages = images;
      this.index      = 0;
    }

    @Override
    public void handle( long now ) {
      if( startTime == 0 ){ startTime = now; }

      preCount = count;
      count  = ( now - startTime ) / duration;
      if (preCount != count) {
        if (isPlus) {
          index++;
        } else {
          index--;
        }
        if ( index < 0 || 2 < index ) {
          index = 1;
          isPlus = !isPlus; // true == !false, false == !true
        }
        charaView.setImage(charaImages[index]);
      }
    }
  }
}
