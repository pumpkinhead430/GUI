package com.src.Movable;
import com.src.main.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ResourceBundle;

public class MovableController implements Initializable {

    private JSONObject movableObject;
    @FXML
    private TextField nameField;
    @FXML
    private TextField damageField;
    @FXML
    private TextField PYField;
    @FXML
    private TextField PXField;
    @FXML
    private ScrollPane propertyScroll;
    @FXML
    private VBox propertyBox;
    @FXML
    private HBox mainBox;
    @FXML
    private GridPane propertyGrid;

    @Override
    public void initialize(java.net.URL arg0, ResourceBundle arg1)
    {
        //propertyScroll.prefHeightProperty().bind(mainBox.heightProperty());
        //prefWidthProperty().bind(propertyBox.widthProperty());
        Main.NumberFilter(damageField);
        Main.NumberFilter(PYField);
        Main.NumberFilter(PXField);
    }

    public void LoadJSON(){
        System.out.println("nice");
    }
    public void SetJson(JSONObject object){
        System.out.println("what what");
    }


    public void WriteInfo(){
        JSONArray text = new JSONArray();
    }


}