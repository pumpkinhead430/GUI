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

import java.io.IOException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MovableController implements Initializable {

    private JSONObject movableObject;
    @FXML
    private TextField nameField;
    @FXML
    private TextField PYField;
    @FXML
    private TextField PXField;
    @FXML
    private TextField healthField;
    @FXML
    private ScrollPane propertyScroll;
    @FXML
    private VBox propertyBox;
    @FXML
    private HBox mainBox;
    @FXML
    private GridPane propertyGrid;
    @FXML
    private TabPane animationPane;
    private ArrayList<animationTab> animations;

    @Override
    public void initialize(java.net.URL arg0, ResourceBundle arg1)
    {
        animations = new ArrayList<>();
        Main.NumberFilter(healthField);
        Main.NumberFilter(PYField);
        Main.NumberFilter(PXField);
    }
    @FXML
    public void CreateAnimation()  {
        animationTab animation = new animationTab();
        animations.add(animation);
        animationPane.getTabs().add(animation.tab);
    }
    public void LoadJSON(){
        nameField.setText(movableObject.get("name").toString());
        healthField.setText(movableObject.get("health").toString());
        PYField.setText(movableObject.get("x").toString());
        PXField.setText(movableObject.get("y").toString());
        for(Object o : ((JSONArray)movableObject.get("animations"))){
            animationTab animation = new animationTab( (JSONObject) o);
            animations.add(animation);
            animationPane.getTabs().add(animation.tab);
        }
    }
    public void SetJson(JSONObject object){
        movableObject = object;
    }


    private animationTab FindController(Tab tab){
        for(animationTab animation : animations){
            if(animation.tab == tab){
                return animation;
            }
        }
        return null;
    }
    @FXML
    public void DeleteAnimation(){
        Tab tab = animationPane.getSelectionModel().getSelectedItem();
        animationTab animation = FindController(tab);
        animations.remove(animation);
        animationPane.getTabs().remove(tab);
    }

    public void WriteInfo(){
        movableObject.put("name", nameField.getText());
        movableObject.put("health", healthField.getText());
        movableObject.put("x", PXField.getText());
        movableObject.put("y", PYField.getText());
        JSONArray jsonAnimations = new JSONArray();
        for(animationTab animation : animations){
            jsonAnimations.add(animation.controller.GetJsonData());
        }
        movableObject.put("animations", jsonAnimations);
    }


}