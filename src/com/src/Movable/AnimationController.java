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
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.TilePane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.util.*;


public class AnimationController implements Initializable {
    @FXML
    private Tab mainTab;
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
    private Button swapUp;
    @FXML
    private Button swapDown;
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


        damageField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.matches("-*[0-9]+")){
                damageField.setText(oldValue);
            }
        });
        YForceField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.matches("-*[0-9]+")){
                YForceField.setText(oldValue);
            }
        });
        XForceField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.matches("-*[0-9]+")){
                XForceField.setText(oldValue);
            }
        });
        triggerField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.equals(""))
                triggerField.setText(newValue.substring(newValue.length() -1));
        });
        nameField.textProperty().addListener((observable, oldValue, newValue) -> {
            mainTab.setText(newValue);
        });
        Main.NumberFilter(timeField);


        mainTile.prefHeightProperty().bind(mainScroll.heightProperty());
        mainTile.prefWidthProperty().bind(mainScroll.widthProperty());

    }
    public JSONObject GetJsonData(){
        JSONObject animation = new JSONObject();

        animation.put("name", nameField.getText());
        animation.put("forcey", Integer.parseInt(YForceField.getText()));
        animation.put("forcex", Integer.parseInt(XForceField.getText()));
        animation.put("damage", Integer.parseInt(damageField.getText()));
        if(triggerField.getText().equals(" "))
            animation.put("trigger", "Space");
        else
            animation.put("trigger", triggerField.getText());
        animation.put("time", Integer.parseInt(timeField.getText()));

        JSONArray frames = new JSONArray();
        if(frameObsList.size() == 0){
            frameObsList.add("default.png");
        }
        for(String frame : frameObsList){
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
        XForceField.setText(object.get("forcex").toString());
        YForceField.setText(object.get("forcey").toString());
        damageField.setText(object.get("damage").toString());
        if(object.get("trigger").toString().equals("Space"))
            triggerField.setText(" ");
        else
            triggerField.setText(object.get("trigger").toString());
        timeField.setText(object.get("time").toString());

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
        File frame = FullBrowser.display("choose Frame", "pic");
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

    public void AcceptFiles(DragEvent event){
        if (event.getDragboard().hasFiles()) {
            List<File> draggedFiles = event.getDragboard().getFiles();
            for(File file : draggedFiles){
                if(!Main.IsPicture(file.getName())){
                    event.consume();
                    break;
                }
            }
            if(!event.isConsumed())
                event.acceptTransferModes(TransferMode.COPY);
        }
        else
            event.consume();
    }

    public void HandleDrop(DragEvent event){
        List<File> dropFiles = event.getDragboard().getFiles();
        for(File file : dropFiles){
            if(!Main.ExsitsInAssets(file.getName())){
                Main.CopyFile(file, Main.assets);
            }
            frameObsList.add(file.getName());
        }
    }

    @FXML
    public void SwapUp(){
        String frame =frameList.getSelectionModel().getSelectedItem();
        if(frameObsList.indexOf(frame) - 1 >= 0)
            Collections.swap(frameObsList, frameObsList.indexOf(frame), frameObsList.indexOf(frame) - 1);
    }

    @FXML
    public void SwapDown(){
        String frame =frameList.getSelectionModel().getSelectedItem();
        if(frameObsList.indexOf(frame) + 1 < frameObsList.size())
            Collections.swap(frameObsList, frameObsList.indexOf(frame), frameObsList.indexOf(frame) + 1);
    }

}
