import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
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
            browse.setText(Picture.getName());
            }


        }


    @Override
    public void initialize(java.net.URL arg0, ResourceBundle arg1)
    {
        propertyGrid.prefWidthProperty().bind(propertyScroll.widthProperty());
        //SplitPane.Divider divider = mainLayout.getDividers().get(0);
        //divider.positionProperty().addListener((observable, oldvalue, newvalue) -> divider.setPosition((Double) newvalue));
    }

}