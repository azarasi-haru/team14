import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;

import javafx.fxml.FXML;

public class MapGameController implements Initializable {
  public MapData mapData;
  public MoveChara chara;
  public GridPane mapGrid;
  public ImageView[] mapImageViews;
  //    public Group[] mapGroups;

  @FXML private Label score_result;
  // Score
  public Score score;

  /* Mapの初期化 */

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    mapData = new MapData(21,15);
    chara = new MoveChara(1,1,mapData, score);
    //        mapGroups = new Group[mapData.getHeight()*mapData.getWidth()];
    score = new Score(1000);
    score_result.setText("score: " + score.getScore());
    mapGrid.setStyle("-fx-background-color: #FFFFFF;");

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

  public void func1ButtonAction(ActionEvent event) {
    goalButtonAction();
  }
  public void func2ButtonAction(ActionEvent event) {
    changeAnimal();
  }
  public void func3ButtonAction(ActionEvent event) { }
  public void func4ButtonAction(ActionEvent event) {
    Audio.playGoal();
    MapGame.getInstance().toResult();
  }

  /* keyActionメソッド: 各キーが押された時の処理 */

  public void keyAction(KeyEvent event){
    KeyCode key = event.getCode();

    switch (key) {
      case DOWN:
      case J:
      case S:
      downButtonAction();
      break;
      case UP:
      case K:
      case W:
      upButtonAction();
      break;
      case LEFT:
      case H:
      case A:
      leftButtonAction();
      break;
      case RIGHT:
      case L:
      case D:
      rightButtonAction();
      break;
      default:
      System.out.println("無効なキーです");
    }

  }

  // If There is something on Player Posision.
  public void somethingAction(){
    if(chara.item()){
      outputAction("ITEM");
      chara.removeSomething();
      setMapImageViews();
      changeAnimal();
    }
    if(chara.human_die()){
      outputAction("HUMAN_DIE");
      chara.removeSomething();
      setMapImageViews();
    }
    // Are You GOALED??
    if(chara.goal()){
      // If All Items Nothing
      // GOAL!! and Generate New Map!!
      outputAction("GOAL");
      MapGame.getInstance().toResult();
      Audio.playGoal();
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
    score_result.setText("score: " + score.getScore());
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
    score_result.setText("score: " + score.getScore());
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
    score_result.setText("score: " + score.getScore());
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
    score_result.setText("score: " + score.getScore());
    somethingAction();
    mapPrint(chara, mapData);
  }
  public void downButtonAction(ActionEvent event) {
    downButtonAction();
  }

  /* GOAL CHEAT */
  public void goalButtonAction(){
    outputAction("GOAL");
    score_result.setText("score: " + score.getScore());
    score.writefile();
    NewMapAction();
    mapPrint(chara, mapData);
    Audio.playGoal();
    MapGame.getInstance().toResult();
  }
  public void goalButtonAction(ActionEvent event) {
    goalButtonAction();
  }

  /* 新規Ｍapを作成 */
  public void NewMapAction(){
    MoveChara.animalNumber=0;
    mapData = new MapData(21,15);
    chara = new MoveChara(1,1,mapData, score);
    score = new Score(100); // initialize Score
    score_result.setText("score: " + score.getScore());

    setMapImageViews();

    mapPrint(chara, mapData);
  }
  public void NewMapAction(ActionEvent event){
    NewMapAction();
  }

  /*動物の変更*/
  public void changeAnimal(){
    System.out.println("change animal");
  MoveChara.animalNumber++;
  if(MoveChara.animalNumber==3){
    MoveChara.animalNumber=0;
  }
      chara = new MoveChara(chara.getPosX(),chara.getPosY(),mapData, score);
      mapPrint(chara, mapData);
      System.out.println(MoveChara.animalNumber);
  }

  public void changeAnimal(ActionEvent event){
    changeAnimal();
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
