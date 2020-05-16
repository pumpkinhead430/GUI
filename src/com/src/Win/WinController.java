package com.src.Win;

import com.src.Browser.FullBrowser;
import com.src.main.Main;
import com.src.main.MainLayoutController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import netscape.javascript.JSObject;
import org.json.simple.JSONObject;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class WinController implements Initializable {

    @FXML
    private ComboBox<String> typeBox;
    @FXML
    private Button characterButton;
    @FXML
    private ListView<JSONObject> objectsView;
    @FXML
    private TextField nameField;
    @FXML
    private TextField startXField;
    @FXML
    private TextField endXField;
    @FXML
    private TextField startYField;
    @FXML
    private TextField endYField;
    @FXML
    private Button screenButton;
    @FXML
    private GridPane positionGrid;
    private ObservableList<JSONObject> objectsList;
    private JSONObject winObject;
    private int characterId;
    @Override
    public void initialize(java.net.URL arg0, ResourceBundle arg1) {
        objectsList = FXCollections.observableArrayList();
        typeBox.getItems().addAll("Place", "Death");
        typeBox.setOnAction(e -> HandleSelected());
        characterButton.setOnDragOver(this::AcceptFiles);
        characterButton.setOnDragDropped(this::HandleDrop);


        objectsView.setOnDragDetected(e ->{
            Dragboard db = objectsView.startDragAndDrop(TransferMode.COPY);
            ClipboardContent content = new ClipboardContent();
            content.put(Main.jsonObjectFormat, objectsView.getSelectionModel().getSelectedItem());
            db.setContent(content);
            e.consume();
        });


        objectsView.setItems(objectsList);
        objectsView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(JSONObject item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.get("name").toString() == null) {
                    setText(null);
                } else {
                    setText(item.get("name").toString());
                }
            }
        });


        startYField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.matches("-*[0-9]*")){
                startYField.setText(oldValue);
            }
        });

        startXField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.matches("-*[0-9]*")){
                startXField.setText(oldValue);
            }
        });

        endYField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.matches("-*[0-9]*")){
                endYField.setText(oldValue);
            }
        });

        endXField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.matches("-*[0-9]*")){
                endXField.setText(oldValue);
            }
        });
    }

    @FXML
    private void handlePictureButton() {
        File Picture = FullBrowser.display("Background", "pic");
        if(Picture != null){
            if(!Main.files.contains(Picture)) {
                Main.CopyFile(Picture, new File(Main.directory + "\\assets"));
            }
            SwitchPicture(Picture.getName());
        }
    }

    public void SwitchPicture(String name){
        File asset_directory = new File(Main.directory + "\\assets");
        File picture = new File(asset_directory + "\\" + name);
        if(picture.exists()) {
            screenButton.setText(picture.getName());
        }
    }


    public void SetObjects(ObservableList<JSONObject> objects){
        objectsList = objects;
        objectsView.setItems(objectsList);
    }

    private void HandleSelected(){
        switch (typeBox.getSelectionModel().getSelectedItem()){
            case "Place":
                positionGrid.setDisable(false);
                break;
            case "Death":
                positionGrid.setDisable(true);
                break;
        }
    }

    public void AcceptFiles(DragEvent event){
        if (event.getDragboard().hasContent(Main.jsonObjectFormat)) {
            JSONObject character = (JSONObject) event.getDragboard().getContent(Main.jsonObjectFormat);

            if (!event.isConsumed()) {
                if (character.get("type").toString().equals("Movable")){
                    event.acceptTransferModes(TransferMode.COPY);
                }
            }


        }
        else
            event.consume();
    }

    public void HandleDrop(DragEvent event){
        if (event.getDragboard().hasContent(Main.jsonObjectFormat)) {
            JSONObject character = (JSONObject) event.getDragboard().getContent(Main.jsonObjectFormat);
            characterButton.setText(character.get("name").toString());
            characterId = Integer.parseInt(character.get("id").toString());
        }
        else{
            event.consume();
        }

    }

    public void SetJson(JSONObject object) {
        winObject = object;
    }

    public void LoadJSON() {
        nameField.setText(winObject.get("name").toString());
        startXField.setText(winObject.get("startX").toString());
        endXField.setText(winObject.get("endX").toString());
        startYField.setText(winObject.get("startY").toString());
        endYField.setText(winObject.get("endY").toString());
        typeBox.getSelectionModel().select(winObject.get("action").toString());
        screenButton.setText(winObject.get("screen").toString().replace("assets\\", ""));

        HandleSelected();
    }

    public void WriteInfo() {
        winObject.put("name", nameField.getText());
        winObject.put("startX", Integer.parseInt(startXField.getText()));
        winObject.put("startY", Integer.parseInt(startYField.getText()));
        winObject.put("endX", Integer.parseInt(endXField.getText()));
        winObject.put("endY", Integer.parseInt(endYField.getText()));
        winObject.put("action", typeBox.getSelectionModel().getSelectedItem());
        winObject.put("characterId", characterId);
        if(!screenButton.getText().equals("Screen"))
            winObject.put("screen", "assets\\" + screenButton.getText());
        else
            winObject.put("screen", "assets\\default.png");
    }
}
