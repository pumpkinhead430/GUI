import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;


public class MainLayoutController implements Initializable
{
    @FXML
    private MenuBar menuBar;
    @FXML
    private ScrollPane objectsScroll;
    @FXML
    private ListView<JSONObject> objectsView;
    private ObservableList<JSONObject> objectsList;
    @FXML
    private  TextField gravityField;
    @FXML
    private Button importButton;
    @FXML
    private ScrollPane objectsScrollAdd;
    @FXML
    private BorderPane mainLayout;
    @FXML
    private VBox objectsLayout;
    @FXML
    private  TextField titleField;
    @FXML
    private  TextField windowWidthField;
    @FXML
    private  TextField windowHeightField;
    @FXML
    private  Button backGroundButton;
    @FXML
    private CheckBox fullScreenBox;
    @FXML
    private ScrollPane importScroll;
    @FXML
    private TilePane importFiles;
    private JsonHandler worldSettings;
    private JsonHandler objectData;
    @FXML
    private void handleAboutAction(final ActionEvent event)
    {
        provideAboutFunctionality();
    }

    @FXML
    private void handleBackGroundButton() {
        File Picture = FullBrowser.display("Background");
        if(Picture != null){
            if(!Main.files.contains(Picture)) {
               Main.CopyFile(Picture, new File(Main.directory + "\\assets"));
            }
            backGroundButton.setText(Picture.getName());
            worldSettings.GetObject().put("background", "assets\\" + Picture.getName());
            worldSettings.Write();
        }

    }
    @FXML
    private void HandleImport() {
        File file = FullBrowser.display("Import Picture");
        if (!Main.files.contains(file))
            Main.CopyFile(file, new File(Main.directory + "\\assets"));
    }
    public void AddToImportBox(File file){
        if(file != null) {
            ImageView temp = new ImageView();
            temp.setImage(new Image(file.toURI().toString()));
            temp.setPickOnBounds(true);
            temp.setOnDragDetected(e ->{
                Dragboard db = temp.startDragAndDrop(TransferMode.COPY);
                ClipboardContent content = new ClipboardContent();
                content.putFiles(Collections.singletonList(file));
                db.setContent(content);
                e.consume();
            });

            Main.ReSizePicture(175, 175, temp);
            VBox tempLayout = new VBox(5);
            tempLayout.setMaxWidth(175);
            Label tempName = new Label(file.getName());
            tempLayout.setAlignment(Pos.CENTER);
            tempLayout.getChildren().addAll(temp, tempName);
            importFiles.getChildren().add(tempLayout);
        }
    }

    public void RemoveFile(File file){
        for(Node layout :importFiles.getChildren()){
            VBox fullLayout = (VBox)layout;
            for(Node layoutFile : fullLayout.getChildren()){
                if(layoutFile instanceof Label)
                    if(((Label) layoutFile).getText().equals(file.getName())){
                        importFiles.getChildren().remove(fullLayout);
                        return;
                    }

            }
        }
    }





    @FXML
    private void handleKeyInput(final InputEvent event)
    {
        if (event instanceof KeyEvent)
        {
            final KeyEvent keyEvent = (KeyEvent) event;
            if (keyEvent.isControlDown() && keyEvent.getCode() == KeyCode.A)
            {
                provideAboutFunctionality();
            }
        }
    }


    private void provideAboutFunctionality()
    {
        System.out.println("You clicked on About!");
    }





    @Override
    public void initialize(java.net.URL arg0, ResourceBundle arg1)
    {
        objectsList =  FXCollections.observableArrayList();
        objectsLayout.prefHeightProperty().bind(objectsScrollAdd.heightProperty());
        objectsLayout.prefWidthProperty().bind(objectsScrollAdd.widthProperty());

        importFiles.prefHeightProperty().bind(importScroll.heightProperty());
        importFiles.prefWidthProperty().bind(importScroll.widthProperty());
        for(Node node: objectsLayout.getChildren()){
            VBox.setVgrow(node, Priority.ALWAYS);
        }
        worldSettings = new JsonHandler("src\\assets\\GameData.json");
        objectData = new JsonHandler("src\\assets\\objects.json");
        BringObjects(objectData, objectsList);
        fullScreenBox.setOnAction(e ->{
            worldSettings.GetObject().put("fullscreen", fullScreenBox.isSelected());
            worldSettings.Write();
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


        objectsView.setOnMouseClicked(this::HandleObjectClick);
        objectsView.prefWidthProperty().bind(objectsScroll.widthProperty());
        objectsView.prefHeightProperty().bind(objectsScroll.heightProperty());
        importFiles.setOnDragOver(this::AcceptFiles);
        importFiles.setOnDragDropped(this::HandleDrop);

        menuBar.setFocusTraversable(true);
        // world settings fields
        titleField.textProperty().addListener((observable, oldValue, newValue) -> {
            worldSettings.GetObject().put("title", newValue);
            worldSettings.Write();
        });
        Main.NumberFilter(gravityField);
        gravityField.textProperty().addListener((observable, oldValue, newValue) -> {
            worldSettings.GetObject().put("gravity", Integer.parseInt(newValue));
            worldSettings.Write();
        });
        Main.NumberFilter(windowWidthField);
        windowWidthField.textProperty().addListener((observable, oldValue, newValue) ->{
            worldSettings.GetObject().put("width", Integer.parseInt(newValue));
            worldSettings.Write();
        });
        Main.NumberFilter(windowHeightField);
        windowHeightField.textProperty().addListener((observable, oldValue, newValue) ->{
            worldSettings.GetObject().put("height", Integer.parseInt(newValue));
            worldSettings.Write();
        });

    }

    private void BringObjects(JsonHandler objectData, ObservableList<JSONObject> objectsList) {
        JSONObject main = objectData.GetObject();
        JSONArray movables = (JSONArray) main.get("Movables");
        for (Object movable : movables) {
            objectsList.add((JSONObject) movable);
        }
        JSONArray stationers = (JSONArray) main.get("Stationers");
        for (Object stationery : stationers) {
            objectsList.add((JSONObject) stationery);
        }
    }



    @FXML
    private void NewMovableObject(){
        JSONObject temp = new JSONObject();
        temp.put("name", "temp(Movable)");
        temp.put("type", "Movable");
        temp.put("health", 0);
        temp.put("x", 0);
        temp.put("y", 0);
        JSONArray animations = new JSONArray();
        JSONObject animation = new JSONObject();
        animation.put("default", "default");
        animations.add(animation);
        temp.put("animations", animations);
        ((JSONArray)objectData.GetObject().get("Movables")).add(temp);
        objectsList.add(temp);
    }

    @FXML
    private void NewStationaryObject(){
        JSONObject temp = new JSONObject();
        temp.put("name", "temp(Stationery)");
        temp.put("type", "Stationery");
        temp.put("damage", 0);
        temp.put("x", 0);
        temp.put("y", 0);
        temp.put("path", "");
        JSONArray ani_start = new JSONArray();
        temp.put("ani_start",ani_start);
        ((JSONArray)objectData.GetObject().get("Stationers")).add(temp);
        objectsList.add(temp);
    }

    public void HandleObjectClick(MouseEvent mouseEvent) {
        if(mouseEvent.getClickCount() == 2){
            JSONObject object = objectsView.getSelectionModel().getSelectedItem();
            if(object != null) {
                    Stationery.display("nn", object);
                    objectsView.refresh();
                    objectData.Write();
                }
            }
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
        File asset_directory = new File(Main.directory + "\\assets");
        for(File file : dropFiles){
            if(!Main.files.contains(file)){
                Main.CopyFile(file, asset_directory);
            }
        }
    }


}