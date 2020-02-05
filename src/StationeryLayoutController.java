import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

public class StationeryLayoutController implements Initializable {

    @FXML
    private ScrollPane propertyScroll;
    @FXML
    private SplitPane mainLayout;
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
            System.out.println(Picture.getName());
            browse.setText(Picture.getName());
            objectImage.setImage(new Image(Picture.toURI().toString()));
            }


        }


    @Override
    public void initialize(java.net.URL arg0, ResourceBundle arg1)
    {
        objectImage.fitHeightProperty();
        objectImage.fitWidthProperty();
        objectImage.setOnDragOver(this::AcceptFiles);
        objectImage.setOnDragDropped(this::HandleDrop);
        propertyGrid.prefWidthProperty().bind(propertyScroll.widthProperty());
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
        File dropFile = event.getDragboard().getFiles().get(0);
        File asset_directory = new File(Main.directory + "\\assets");
            if(!Main.files.contains(dropFile)){
                Main.CopyFile(dropFile, asset_directory);
        }
        objectImage.setImage(new Image(dropFile.toURI().toString()));
    }

}