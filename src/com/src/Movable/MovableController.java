package com.src.Movable;
import com.src.main.Main;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.event.ChangeListener;
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
    private CheckBox deafultSelect;
    @FXML
    private TabPane animationPane;
    private ArrayList<animationTab> animations;
    public animationTab deafultAnimation = null;

    @Override
    public void initialize(java.net.URL arg0, ResourceBundle arg1)
    {
        animations = new ArrayList<>();
        Main.NumberFilter(healthField);
        Main.NumberFilter(PYField);
        Main.NumberFilter(PXField);
        animationPane.getSelectionModel().selectedItemProperty().addListener(e -> CheckDefault());

        deafultSelect.selectedProperty().addListener(e ->{
            if(deafultSelect.isSelected())
                deafultAnimation = FindController(animationPane.getSelectionModel().getSelectedItem());
        });
    }
    public void CheckDefault(){
        if (deafultAnimation != null) {
            if (animationPane.getSelectionModel().getSelectedItem() == deafultAnimation.tab) {
                deafultSelect.setSelected(true);
                deafultSelect.setDisable(true);
            } else {
                deafultSelect.setSelected(false);
                deafultSelect.setDisable(false);
            }
        }
    }
    @FXML
    public void CreateAnimation()  {
        JSONObject animationJ = new JSONObject();
        animationJ.put("forcey", 0);
        animationJ.put("forcex", 0);
        animationJ.put("default", true);
        animationJ.put("damage", 0);
        animationJ.put("name", "temp(animation)");
        animationJ.put("trigger", " ");
        animationJ.put("time", 1);
        JSONArray frames = new JSONArray();
        frames.add("assets\\default.png");
        animationJ.put("frames",  frames);
        animationJ.put("ani_starter",  new JSONArray());
        animationTab animation = new animationTab(animationJ);
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
            if(Boolean.parseBoolean(((JSONObject) o).get("default").toString())){
                deafultAnimation = animation;
            }
        }
        if (animationPane.getSelectionModel().getSelectedItem() == deafultAnimation.tab) {
            deafultSelect.setSelected(true);
            deafultSelect.setDisable(true);
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
        if(animationPane.getTabs().size() > 1) {
            Tab tab = animationPane.getSelectionModel().getSelectedItem();
            animationTab animation = FindController(tab);
            animations.remove(animation);
            animationPane.getTabs().remove(tab);
            if (animation == deafultAnimation) {
                deafultAnimation = FindController(animationPane.getTabs().get(0));
                CheckDefault();
            }
        }
    }

    public void WriteInfo(){
        movableObject.put("name", nameField.getText());
        movableObject.put("health", Integer.parseInt(healthField.getText()));
        movableObject.put("x", Integer.parseInt(PXField.getText()));
        movableObject.put("y", Integer.parseInt(PYField.getText()));
        JSONArray jsonAnimations = new JSONArray();
        for(animationTab animation : animations){
            JSONObject animationJson = animation.controller.GetJsonData();
            animationJson.put("default",(animation == deafultAnimation));
            jsonAnimations.add(animationJson);
        }
        movableObject.put("animations", jsonAnimations);
    }


}