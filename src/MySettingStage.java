import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


/**
 * @Author: jiaxing liu
 * @Date: 2019/3/8 12:36
 */
public class MySettingStage extends Stage {
    private TextField tfDimension;
    private Button btOK;
    private Label lbWarning;
    MySettingStage(Main app){
        VBox vBox = new VBox(20);
        btOK = new Button("OK");
        lbWarning = new Label();
        lbWarning.setTextFill(Color.color(255,195,14));
        addListener(app);
        vBox.getChildren().addAll(UI_GetDimension(),UI_GetColor(),btOK);
        BorderPane pane = new BorderPane();
        pane.setCenter(vBox);
        Scene scene = new Scene(pane,600,600);
        this.setScene(scene);
    }
    private HBox UI_GetDimension(){
        HBox hBox = new HBox(20);
        Label lbDimension = new Label(Constant.DIMENSION);
        tfDimension = new TextField();
        hBox.getChildren().addAll(lbDimension,tfDimension);
        return hBox;
    }
    private HBox UI_GetColor(){
        HBox hBox = new HBox(20);
        Label lbColor = new Label(Constant.ENTER_COLOR);
        ToggleGroup group = new ToggleGroup();
        RadioButton btBlack = new RadioButton("BLACK");
        btBlack.setToggleGroup(group);
        btBlack.setSelected(true);
        RadioButton btWhite = new RadioButton("WHITE");
        btWhite.setToggleGroup(group);
        hBox.getChildren().addAll(lbColor,btBlack,btWhite);
        return hBox;
    }
    public void toShow(){
        this.show();
    }
    private void addListener(Main app){
        btOK.setOnAction(event -> {
            String dimension = tfDimension.getText();
            if(Util.isNumeric(dimension)){
                int dim = Integer.parseInt(dimension);
                if(!(dim % 2 == 0 && dim >= 4 && dim <= 26)){
                    setWarning();
                }
            }else {
                setWarning();
            }
        });
    }
    private void setWarning(){
        lbWarning.setText(Constant.DIMENSION_NOT_LEGAL);
    }
}
