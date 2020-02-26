import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import netscape.javascript.JSObject;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

public class StationeryLayoutController implements Initializable {

    private JSONObject stationeryObject;
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
    private StackPane imageCont;
    @FXML
    private ImageView objectImage;
    @FXML
    private GridPane propertyGrid;
    @FXML
    private Button browse;
    @FXML
    private void handlePictureButton() {
        File Picture = FullBrowser.display("Background");
        if(Picture != null){
            if(!Main.files.contains(Picture)) {
                Main.CopyFile(Picture, new File(Main.directory + "\\assets"));
            }
            SwitchPicture(Picture.getName());
        }
    }

    @Override
    public void initialize(java.net.URL arg0, ResourceBundle arg1)
    {
        propertyScroll.prefHeightProperty().bind(mainBox.heightProperty());
        imageCont.prefHeightProperty().bind(mainBox.heightProperty());
        imageCont.prefWidthProperty().bind(mainBox.widthProperty());
        objectImage.pickOnBoundsProperty();
        objectImage.setOnDragOver(this::AcceptFiles);
        objectImage.setOnDragDropped(this::HandleDrop);
        propertyGrid.prefWidthProperty().bind(propertyBox.widthProperty());
        nameField.textProperty().addListener((observable, oldValue, newValue) -> {
            stationeryObject.put("name", newValue);
        });
        Main.NumberFilter(damageField);
        Main.NumberFilter(PYField);
        Main.NumberFilter(PXField);
    }
    public void SetJson(JSONObject object){
        this.stationeryObject = object;
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
    public void SwitchPicture(String name){
        File asset_directory = new File(Main.directory + "\\assets");
        File picture = new File(asset_directory + "\\" + name);
        if(picture.exists()) {
            objectImage.setImage(new Image(picture.toURI().toString()));
            browse.setText(picture.getName());
            stationeryObject.put("path", "assets\\" + picture.getName());
        }
    }

    public void HandleDrop(DragEvent event){
        File dropFile = event.getDragboard().getFiles().get(0);
        File asset_directory = new File(Main.directory + "\\assets");
        File copiedFile = new File(asset_directory.getPath() + "\\" + dropFile.getName());
        if(!Main.files.contains(copiedFile)){
            Main.CopyFile(dropFile, asset_directory);
        }
        SwitchPicture(copiedFile.getName());
    }

}