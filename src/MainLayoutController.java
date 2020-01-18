import java.io.*;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.skin.ChoiceBoxSkin;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class MainLayoutController implements Initializable
{
    @FXML
    private MenuBar menuBar;
    @FXML
    private  TextField gravityField;
    @FXML
    private Button importButton;
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

    @FXML private HBox importFiles;

    private JsonHandler worldSettings;
    @FXML
    private void handleAboutAction(final ActionEvent event)
    {
        provideAboutFunctionality();
    }

    @FXML
    private void handleBackGroundButton() throws IOException {
        String Picture = FullBrowser.display("Background");
        if(!Picture.equals(""))
            backGroundButton.setText(Picture);
    }
    @FXML
    private void HandleImport() throws IOException {
        String fileName = FullBrowser.display("Import Picture");
        importFiles.getChildren().add(new Label(fileName));
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
        worldSettings = new JsonHandler("src\\assets\\GameData.json");
        fullScreenBox.setOnAction(e ->{
            System.out.println("wad");
            worldSettings.GetObject().put("fullscreen", fullScreenBox.isSelected());
            worldSettings.Write();
        });
        importFiles.setOnDragOver(this::AcceptFiles);
        importFiles.setOnDragDropped(this::HandleDrop);

        menuBar.setFocusTraversable(true);
        // world settings fields
        titleField.textProperty().addListener((observable, oldValue, newValue) -> HandleWorldSettingsField(titleField, oldValue, newValue));
        Main.NumberFilter(gravityField);
        gravityField.textProperty().addListener((observable, oldValue, newValue) -> HandleWorldSettingsField(gravityField, oldValue, newValue));
        Main.NumberFilter(windowWidthField);
        windowWidthField.textProperty().addListener((observable, oldValue, newValue) ->HandleWorldSettingsField(windowWidthField, oldValue, newValue));
        Main.NumberFilter(windowHeightField);
        windowHeightField.textProperty().addListener((observable, oldValue, newValue) ->HandleWorldSettingsField(windowHeightField, oldValue, newValue));

    }

    public void HandleWorldSettingsField(TextField field, String oldValue, String newValue) {
        if(field == gravityField)
            worldSettings.GetObject().put("gravity", Integer.parseInt(newValue));

        if(field == windowWidthField)
            worldSettings.GetObject().put("width", Integer.parseInt(newValue));

        if(field == windowHeightField)
            worldSettings.GetObject().put("height", Integer.parseInt(newValue));

        if(field == titleField)
            worldSettings.GetObject().put("title", newValue);
        worldSettings.Write();
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
            if(!Main.ExistsInDir(file, new File(Main.directory + "\\assets"))){
                Main.CopyFile(file, new File(Main.directory + "\\assets"));
                importFiles.getChildren().add(new Label(file.getName()));
            }
        }
    }


}
