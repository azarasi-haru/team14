import java.io.File;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.Group;

public class MapGameController implements Initializable {
  public MapData mapData;
  public MoveChara chara;
  public GridPane mapGrid;
  public ImageView[] mapImageViews;
  //    public Group[] mapGroups;

  // Score
  public Score score;

  /* Mapの初期化 */

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    mapData = new MapData(21,15);
    chara = new MoveChara(1,1,mapData, score);
    //        mapGroups = new Group[mapData.getHeight()*mapData.getWidth()];
    score = new Score(100);

    setMapImageViews();
    mapPrint(chara, mapData);
  }

  public void mapPrint(MoveChara c, MapData m){
    int cx = c.getPosX();
    int cy = c.getPosY();
    mapGrid.getChildren().clear();
    for(int y=0; y<mapData.getHeight(); y++){
      for(int x=0; x<mapData.getWidth(); x++){
        int index = y*mapData.getWidth() + x;
        if (x==cx && y==cy) {
          mapGrid.add(c.getCharaImageView(), x, y);
        } else {
          mapGrid.add(mapImageViews[index], x, y);
        }
      }
    }
  }

  public void func1ButtonAction(ActionEvent event) { }
  public void func2ButtonAction(ActionEvent event) { }
  public void func3ButtonAction(ActionEvent event) { }
  public void func4ButtonAction(ActionEvent event) { }

  /* keyActionメソッド: 各キーが押された時の処理 */

  public void keyAction(KeyEvent event){
    KeyCode key = event.getCode();
    if (key == KeyCode.UP){
      upButtonAction();
    } else if (key == KeyCode.LEFT){
      leftButtonAction();
    } else if (key == KeyCode.RIGHT){
      rightButtonAction();
    } else if (key == KeyCode.DOWN){
      downButtonAction();
    } else if (key == KeyCode.G){
      // Gkey GOAL CHEAT
      goalButtonAction();
    }
  }

  // If There is something on Player Posision.
  public void somethingAction(){
    if(chara.item()){
      outputAction("ITEM");
      chara.removeSomething();
      setMapImageViews();

    }
    // Are You GOALED??
    if(chara.goal()){
      // If All Items Nothing
      // GOAL!! and Generate New Map!!
      outputAction("GOAL");
      goalButtonAction();
    }
  }

  public void outputAction(String actionString) {
    System.out.println("Select Action: " + actionString);
  }

  /* keyActionメソッドの各キーに対応する、各キャラの挙動 */

  public void upButtonAction(){
    outputAction("UP");
    chara.setCharaDir(MoveChara.TYPE_UP);
    chara.move(0, -1);  // 進めたか否かをtrueかfalseの値で返す
    somethingAction();

    mapPrint(chara, mapData);
  }
  public void upButtonAction(ActionEvent event) {
    upButtonAction();
  }

  public void leftButtonAction(){
    outputAction("LEFT");

    chara.setCharaDir(MoveChara.TYPE_LEFT);
    chara.move(-1, 0);
    somethingAction();
    mapPrint(chara, mapData);
  }
  public void leftButtonAction(ActionEvent event) {
    leftButtonAction();
  }

  public void rightButtonAction(){
    outputAction("RIGHT");

    chara.setCharaDir(MoveChara.TYPE_RIGHT);
    chara.move( 1, 0);
    somethingAction();
    mapPrint(chara, mapData);
  }
  public void rightButtonAction(ActionEvent event) {
    rightButtonAction();
  }

  public void downButtonAction(){
    outputAction("DOWN");

    chara.setCharaDir(MoveChara.TYPE_DOWN);
    chara.move(0, 1);
    somethingAction();
    mapPrint(chara, mapData);
  }
  public void downButtonAction(ActionEvent event) {
    downButtonAction();
  }

  /* GOAL CHEAT */
  public void goalButtonAction(){
    outputAction("GOAL");
    NewMapAction();
    mapPrint(chara, mapData);
  }
  public void goalButtonAction(ActionEvent event) {
    goalButtonAction();
  }

  /* 新規Ｍapを作成 */
  public void NewMapAction(){
    mapData = new MapData(21,15);
    chara = new MoveChara(1,1,mapData, score);
    score = new Score(100); // initialize Score

    setMapImageViews();

    mapPrint(chara, mapData);
  }
  public void NewMapAction(ActionEvent event){
    NewMapAction();
  }

  /* Viewに画像を反映する */
  public void setMapImageViews(){
    mapImageViews = new ImageView[mapData.getHeight()*mapData.getWidth()];
    for(int y=0; y<mapData.getHeight(); y++){
      for(int x=0; x<mapData.getWidth(); x++){
        int index = y*mapData.getWidth() + x;
        mapImageViews[index] = mapData.getImageView(x,y);
      }
    }
  }

}
