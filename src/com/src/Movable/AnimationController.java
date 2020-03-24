package com.src.Movable;

import com.src.main.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.TilePane;
import org.json.simple.JSONObject;

import java.util.ResourceBundle;


public class AnimationController implements Initializable {
    @FXML
    private TextField nameField;
    @FXML
    private TextField damageField;
    @FXML
    private TextField YForceField;
    @FXML
    private TextField XForceField;
    @FXML
    private TextField triggerField;
    @FXML
    ScrollPane mainScroll;
    @FXML
    TilePane mainTile;
    @Override
    public void initialize(java.net.URL arg0, ResourceBundle arg1) {
        Main.NumberFilter(damageField);
        Main.NumberFilter(YForceField);
        Main.NumberFilter(XForceField);
        mainTile.prefHeightProperty().bind(mainScroll.heightProperty());
        mainTile.prefWidthProperty().bind(mainScroll.widthProperty());

    }
    public JSONObject GetJsonData(){
        //TODO need to take data of animation and change format to a JSON object
        return new JSONObject();
    }
    public void PutJson(JSONObject object){
        //TODO need to load JSON animation data to textFields and listViews
    }

}
