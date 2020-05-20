package com.src.main;

import com.src.Browser.FullBrowser;
import com.src.JsonHandler;
import com.src.Movable.Movable;
import com.src.ObjectDisplay.ObjectDisplay;
import com.src.PathTaker.PathTaker;
import com.src.Stationery.Stationery;
import com.src.Win.Win;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class MainLayoutController implements Initializable
{
    @FXML
    private Canvas mainCanvas;
    @FXML
    private MenuBar menuBar;
    @FXML
    private ScrollPane objectsScroll;
    @FXML
    private ListView<JSONObject> objectsView;
    public ObservableList<JSONObject> objectsList;
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
    private Pane canvasPane;
    @FXML
    private Button deleteObject;

    private ArrayList<Boolean> idList = new ArrayList<>();
    @FXML
    private CheckBox fullScreenBox;
    @FXML
    private ScrollPane importScroll;
    @FXML
    private TilePane importFiles;
    private JsonHandler worldSettings;
    private JsonHandler objectData;

    @Override
    public void initialize(java.net.URL arg0, ResourceBundle arg1)
    {
        objectsList =  FXCollections.observableArrayList();
        mainCanvas.heightProperty().bind(canvasPane.heightProperty());
        mainCanvas.widthProperty().bind(canvasPane.widthProperty());
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


        mainCanvas.heightProperty().addListener(e -> DrawInCanvas());
        mainCanvas.widthProperty().addListener(e -> DrawInCanvas());

        backGroundButton.setOnDragOver(this::AcceptFiles);
        backGroundButton.setOnDragDropped(this::HandleBackGround);


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
        backGroundButton.setText(worldSettings.GetObject().get("background").toString().replace("assets\\", ""));
        fullScreenBox.setOnAction(e ->{
            worldSettings.GetObject().put("fullscreen", fullScreenBox.isSelected());
            worldSettings.Write();
        });
        fullScreenBox.setSelected(Boolean.parseBoolean(worldSettings.GetObject().get("fullscreen").toString()));
        titleField.textProperty().addListener((observable, oldValue, newValue) -> {
            worldSettings.GetObject().put("title", newValue);
            worldSettings.Write();
        });
        titleField.setText(worldSettings.GetObject().get("title").toString());
        Main.NumberFilter(gravityField);
        gravityField.textProperty().addListener((observable, oldValue, newValue) -> {
            worldSettings.GetObject().put("gravity", Integer.parseInt(newValue));
            worldSettings.Write();
        });
        gravityField.setText(worldSettings.GetObject().get("gravity").toString());
        Main.NumberFilter(windowWidthField);
        windowWidthField.textProperty().addListener((observable, oldValue, newValue) ->{
            worldSettings.GetObject().put("width", Integer.parseInt(newValue));
            worldSettings.Write();
        });
        windowWidthField.setText(worldSettings.GetObject().get("width").toString());
        Main.NumberFilter(windowHeightField);
        windowHeightField.textProperty().addListener((observable, oldValue, newValue) ->{
            worldSettings.GetObject().put("height", Integer.parseInt(newValue));
            worldSettings.Write();
        });
        windowHeightField.setText(worldSettings.GetObject().get("height").toString());

        DrawInCanvas();
    }

    @FXML
    private void handleBackGroundButton() {
        File Picture = FullBrowser.display("Background", "pic");
        if(Picture != null){
            if(!Main.files.contains(Picture)) {
                Main.CopyFile(Picture, new File(Main.directory + "\\assets"));
            }
            backGroundButton.setText(Picture.getName());
            worldSettings.GetObject().put("background", "assets\\" + Picture.getName());
            worldSettings.Write();
        }

    }

    private JSONObject FindDefaultAnimation(JSONObject movable) {

        for(Object o : (JSONArray) movable.get("animations")){
            if(Boolean.parseBoolean(((JSONObject) o).get("default").toString())){
                return (JSONObject) o;
            }
        }
        return null;
    }
    private void HandleBackGround(DragEvent event){
        File dropFile = event.getDragboard().getFiles().get(0);
        File check = new File( Main.directory + "assets\\" + dropFile.getName());
        if(!check.exists())
            Main.CopyFile(dropFile, new File(Main.directory + "assets"));
        backGroundButton.setText(dropFile.getName());
        worldSettings.GetObject().put("background", "assets\\" + dropFile.getName());
        worldSettings.Write();
    }
    //-----------------------------------------------------------------------------------------------------------------
    // canvas related functions
    public void DrawInCanvas() {
        GraphicsContext gc = mainCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());
        gc.drawImage(new Image(worldSettings.GetObject().get("background").toString()),
                0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());
        for (JSONObject object : objectsList) {
            Pair<Integer, Integer> tempSize, tempPosition;
            switch (object.get("type").toString()) {
                case "Stationery":
                    tempSize = SizeOnCanvas(object.get("path").toString());

                    tempPosition = PositionOnCanvas(Integer.parseInt(object.get("x").toString()),
                            Integer.parseInt(object.get("y").toString()));

                    gc.drawImage(new Image(object.get("path").toString()), tempPosition.getKey(), tempPosition.getValue(),
                            tempSize.getKey(), tempSize.getValue());
                    break;
                case "Movable":
                    JSONObject defaultAnimation = FindDefaultAnimation(object);
                    if (defaultAnimation != null) {
                        if(((JSONArray)defaultAnimation.get("frames")).size() > 0) {
                            String startingFrame = ((JSONArray) defaultAnimation.get("frames")).get(0).toString();
                            tempSize = SizeOnCanvas(startingFrame);

                            tempPosition = PositionOnCanvas(Integer.parseInt(object.get("x").toString()),
                                    Integer.parseInt(object.get("y").toString()));

                            gc.drawImage(new Image(startingFrame), tempPosition.getKey(), tempPosition.getValue(),
                                    tempSize.getKey(), tempSize.getValue());
                        }
                    }
                    break;
            }
        }
    }

    private Pair<Integer, Integer> SizeOnCanvas(String path){
        Image objectImage = new Image(path);
        int canvasHeight = (int) (objectImage.getHeight() * mainCanvas.getHeight())
                / Integer.parseInt(windowHeightField.getText());
        int canvasWidth = (int) (objectImage.getWidth() * mainCanvas.getWidth())
                / Integer.parseInt(windowWidthField.getText());
        return new Pair<>(canvasWidth, canvasHeight);
    }

    private Pair<Integer, Integer> PositionOnCanvas(int x, int y){

        int positionX = (int) ( x * mainCanvas.getWidth())
                / Integer.parseInt(windowWidthField.getText());
        int positionY = (int) (y * mainCanvas.getHeight())
                / Integer.parseInt(windowHeightField.getText());
        return new Pair<>(positionX, positionY);
    }

    //-----------------------------------------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------------------------
    //list view related functions
    private void BringObjects(JsonHandler objectData, ObservableList<JSONObject> objectsList) {
        JSONObject main = objectData.GetObject();
        JSONArray movables = (JSONArray) main.get("Movables");
        for (Object movable : movables) {
            LockId((JSONObject) movable);
            objectsList.add((JSONObject) movable);

        }
        JSONArray stationers = (JSONArray) main.get("Stationers");
        for (Object stationery : stationers) {
            LockId((JSONObject) stationery);
            objectsList.add((JSONObject) stationery);

        }
        JSONArray wins = (JSONArray) main.get("Wins");
        for (Object win : wins) {
            LockId((JSONObject) win);
            objectsList.add((JSONObject) win);
        }

        JSONArray losses = (JSONArray) main.get("Losses");
        for (Object loss : losses) {
            LockId((JSONObject) loss);
            objectsList.add((JSONObject) loss);
        }

    }

    @FXML
    private void NewObject(){
        JSONObject temp = ObjectDisplay.display("new object");
        if(temp != null) {
            switch (temp.get("type").toString()) {
                case ("Movable"):
                    ((JSONArray) objectData.GetObject().get("Movables")).add(temp);
                    break;
                case ("Stationery"):
                    ((JSONArray) objectData.GetObject().get("Stationers")).add(temp);
                    break;
                case("Win"):
                    ((JSONArray) objectData.GetObject().get("Wins")).add(temp);
                    break;
                case("Loss"):
                    ((JSONArray) objectData.GetObject().get("Losses")).add(temp);
                    break;
            }
            GiveId(temp);
            objectsList.add(temp);
            objectData.Write();
            DrawInCanvas();
        }


    }

    @FXML
    private void DeleteObject(){
        JSONObject data = objectData.GetObject();
        JSONObject object = objectsView.getSelectionModel().getSelectedItem();
        objectsList.remove(object);
        switch (object.get("type").toString()){
            case "Movable":
                ((JSONArray)data.get("Movables")).remove(object);
            case "Stationery":
                ((JSONArray)data.get("Stationers")).remove(object);
            case "Win":
                ((JSONArray)data.get("Wins")).remove(object);
            case "Loss":
                ((JSONArray)data.get("Losses")).remove(object);
        }
        objectData.Write();
        DrawInCanvas();
    }

    public void HandleObjectClick(MouseEvent mouseEvent) {
        if(mouseEvent.getClickCount() == 2){
            JSONObject object = objectsView.getSelectionModel().getSelectedItem();
            if(object != null) {
                switch (object.get("type").toString()) {
                    case ("Stationery"):
                        Stationery.display("Stationery Edit", object);
                        break;
                    case ("Movable"):
                        Movable.display("Movable Edit", object);
                        break;
                    case ("Win"):
                        Win.display("Win Edit", object, objectsList);
                        break;
                    case ("Loss"):
                        Win.display("Loss Edit", object, objectsList);
                        break;
                }
                objectsView.refresh();
                objectData.Write();
                DrawInCanvas();
            }
        }
    }
    //-----------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------
    //id related functions

    private void GiveId(JSONObject object){
        for(int i = 0; i < idList.size(); i++){
            if(!idList.get(i)) {
                object.put("id", i);
                idList.set(i, true);
                return;
            }
        }
        idList.add(true);
        object.put("id", idList.size() - 1);
    }

    private void LockId(JSONObject object){
        while (idList.size() <= Integer.parseInt(object.get("id").toString())){
            idList.add(false);
        }
        idList.set(Integer.parseInt(object.get("id").toString()), true);
    }
    //-----------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------
    // picture import related functions
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

    @FXML
    private void HandleImport() {
        File file = FullBrowser.display("Import Picture", "pic");
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
    //-----------------------------------------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------------------------
    // menu related functions
    @FXML
    public void Export(){
        File path = PathTaker.display("where to export?");
        File copiedPath = null;
        ArrayList<String> files = new ArrayList<>();
        if(path != null) {
            for (File file : Objects.requireNonNull(path.listFiles())) {
                if(file.isDirectory())
                    files.add(file.getName());
            }
            if (!files.contains(Main.export.getName())) {
                Main.CopyFile(Main.export, path);
                copiedPath = new File(path.getPath() + "\\" + "assets");
            }
            if(copiedPath != null) {
                for (File file : Objects.requireNonNull(Main.assets.listFiles())) {
                    if (file.isFile())
                        Main.CopyFile(file, copiedPath);
                }
            }
        }
    }

    @FXML
    public void Exit(){
        ((Stage)mainLayout.getScene().getWindow()).close();
    }

    @FXML
    public void Run(){
        File path = new File(Main.export.getPath() + "\\assets");
        for (File file : Objects.requireNonNull(Main.assets.listFiles())) {
            if (file.isFile())
                Main.CopyFile(file, path);
        }
        try {
            Runtime.getRuntime().exec(Main.export.getPath() + "\\game_engine.exe", null, Main.export);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}