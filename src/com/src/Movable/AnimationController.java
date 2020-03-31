package com.src.Movable;

import com.src.Browser.FullBrowser;
import com.src.main.Main;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.TilePane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.util.*;


public class AnimationController implements Initializable {
    @FXML
    private ListView<String> frameList;
    @FXML
    private ListView<TextField> aniStartList;
    @FXML
    private TextField nameField;
    @FXML
    private TextField damageField;
    @FXML
    private TextField timeField;
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
    ObservableList<String> frameObsList;
    ObservableList<TextField> aniStartObsList;
    @Override
    public void initialize(java.net.URL arg0, ResourceBundle arg1) {
        frameObsList = FXCollections.observableArrayList();
        frameList.setItems(frameObsList);
        aniStartObsList = FXCollections.observableArrayList();
        aniStartList.setItems(aniStartObsList);
        damageField.setTextFormatter(Main.allNumberFilter);
        YForceField.setTextFormatter(Main.allNumberFilter);
        XForceField.setTextFormatter(Main.allNumberFilter);
        Main.NumberFilter(timeField);
        mainTile.prefHeightProperty().bind(mainScroll.heightProperty());
        mainTile.prefWidthProperty().bind(mainScroll.widthProperty());

    }
    public JSONObject GetJsonData(){
        JSONObject animation = new JSONObject();

        animation.put("name", nameField.getText());
        animation.put("forcey", YForceField.getText());
        animation.put("forcex", XForceField.getText());
        animation.put("damage", damageField.getText());
        animation.put("trigger", triggerField.getText());
        animation.put("time", timeField.getText());

        JSONArray frames = new JSONArray();
        for(String frame : frameObsList){
            System.out.println(frame);
            System.out.println("assets//" + frame);
            System.out.println("assets/" + frame);
            frames.add("assets/" + frame);
        }
        animation.put("frames", frames);

        JSONArray starters = new JSONArray();
        for(TextField starter : aniStartObsList){
            starters.add(starter.getText());
        }
        animation.put("ani_starter", starters);
        return animation;
    }
    public void PutJson(JSONObject object){
        nameField.setText(object.get("name").toString());
        XForceField.setText(object.get("forcey").toString());
        YForceField.setText(object.get("forcex").toString());
        damageField.setText(object.get("damage").toString());
        triggerField.setText(object.get("trigger").toString());

        for(Object o : ((JSONArray)object.get("ani_starter"))){
            aniStartObsList.add(new TextField(o.toString()));
        }

        for(Object o : ((JSONArray)object.get("frames"))){
            if(Main.ExsitsInAssets(o.toString().replace("assets/", "")))
                frameObsList.add(o.toString().replace("assets/", ""));
        }
    }
    @FXML
    public void AddAniStarter(){
        aniStartObsList.add(new TextField());
    }
    @FXML
    public void AddFrame(){
        File frame = FullBrowser.display("choose Frame");
        if(frame != null) {
            if(!Main.ExsitsInAssets(frame.getName()))
                Main.CopyFile(frame, Main.assets);
            frameObsList.add(frame.getName());
        }
    }
    @FXML
    public void DeleteFrame(){
        String frame = frameList.getSelectionModel().getSelectedItem();
        frameObsList.remove(frame);
    }
    @FXML
    public void DeleteAni_Starter(){
        TextField starter = aniStartList.getSelectionModel().getSelectedItem();
        aniStartObsList.remove(starter);
    }

}
