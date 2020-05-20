package com.src.ObjectDisplay;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Collections;
import java.util.ResourceBundle;

public class ObjectController implements  Initializable {
    private ObservableList<String> objects;
    public JSONObject ChosenObject;
    @FXML
    private ListView<String> objectsView;
    @FXML
    private Pane mainPane;
    @Override
    public void initialize(java.net.URL arg0, ResourceBundle arg1) {
        objects = FXCollections.observableArrayList();
        objects.addAll("Movable", "Stationery", "Win", "Loss");
        objectsView.setItems(objects);
        objectsView.prefHeightProperty().bind(mainPane.heightProperty());
        objectsView.prefWidthProperty().bind(mainPane.widthProperty());
        objectsView.setOnMouseClicked(this::HandleClick);
    }

    private void HandleClick(MouseEvent mouseEvent) {
        if(mouseEvent.getClickCount() == 2) {
            String objectType = objectsView.getSelectionModel().getSelectedItem();
            switch (objectType){
                case "Movable":
                    ChosenObject = NewMovableObject();
                    break;
                case "Stationery":
                    ChosenObject = NewStationaryObject();
                    break;
                case "Win":
                    ChosenObject = NewWinObject();
                    break;
                case "Loss":
                    ChosenObject = NewLossObject();
                    break;

            }
            ((Stage)objectsView.getScene().getWindow()).close();
        }


    }

    @FXML
    private JSONObject NewMovableObject(){
        JSONObject temp = new JSONObject();
        temp.put("name", "temp(Movable)");
        temp.put("type", "Movable");
        temp.put("health", 0);
        temp.put("x", 0);
        temp.put("y", 0);
        JSONArray animations = new JSONArray();
        JSONObject animation = new JSONObject();
        animation.put("forcey", 0);
        animation.put("forcex", 0);
        animation.put("default", true);
        animation.put("damage", 0);
        animation.put("name", "temp(animation)");
        animation.put("trigger", " ");
        animation.put("time", 1);
        JSONArray frames = new JSONArray();
        frames.add("assets\\default.png");
        animation.put("frames",  frames);
        animation.put("ani_starter",  new JSONArray());
        animations.add(animation);
        temp.put("animations", animations);
        return temp;
    }
    private JSONObject NewWinObject(){
        JSONObject temp = new JSONObject();
        temp.put("name", "temp(Win)");
        temp.put("type", "Win");
        temp.put("action", "place");
        temp.put("startX", 0);
        temp.put("endX", 0);
        temp.put("startY", 0);
        temp.put("endY", 0);
        temp.put("screen", "asset\\default.png");
        temp.put("characterId", -1);
        return temp;
    }

    private JSONObject NewLossObject(){
        JSONObject temp = new JSONObject();
        temp.put("name", "temp(Loss)");
        temp.put("type", "Loss");
        temp.put("action", "place");
        temp.put("startX", 0);
        temp.put("endX", 0);
        temp.put("startY", 0);
        temp.put("screen", "asset\\default.png");
        temp.put("endY", 0);
        temp.put("characterId", -1);
        return temp;
    }


    @FXML
    private JSONObject NewStationaryObject(){
        JSONObject temp = new JSONObject();
        temp.put("name", "temp(Stationery)");
        temp.put("type", "Stationery");
        temp.put("damage", 0);
        temp.put("x", 0);
        temp.put("y", 0);
        temp.put("path", "assets/default.png");
        JSONArray ani_start = new JSONArray();
        temp.put("ani_start",ani_start);
        return temp;
    }


}
