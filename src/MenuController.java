import java.io.FileReader;
import java.io.Reader;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.skin.ChoiceBoxSkin;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileWriter;
import java.io.IOException;


public class MenuController implements Initializable
{
    @FXML
    private MenuBar menuBar;
    @FXML
    private  TextField gravityField;
    @FXML
    private  TextField titleField;
    @FXML
    private  TextField windowWidthField;
    @FXML
    private  TextField windowHeightField;
    @FXML
    private  TextField backGroundField;

    @FXML
    private CheckBox fullScreenBox;

    private JsonHandler worldSettings;
    @FXML
    private void handleAboutAction(final ActionEvent event)
    {
        provideAboutFunctionality();
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

        menuBar.setFocusTraversable(true);
        // world settings fields
        titleField.textProperty().addListener((observable, oldValue, newValue) -> HandleWorldSettingsField(titleField, oldValue, newValue));
        Main.NumberFilter(gravityField);
        gravityField.textProperty().addListener((observable, oldValue, newValue) -> HandleWorldSettingsField(gravityField, oldValue, newValue));
        Main.NumberFilter(windowWidthField);
        windowWidthField.textProperty().addListener((observable, oldValue, newValue) ->HandleWorldSettingsField(windowWidthField, oldValue, newValue));
        Main.NumberFilter(windowHeightField);
        windowHeightField.textProperty().addListener((observable, oldValue, newValue) ->HandleWorldSettingsField(windowHeightField, oldValue, newValue));
        backGroundField.textProperty().addListener((observable, oldValue, newValue) ->HandleWorldSettingsField(backGroundField, oldValue, newValue));

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


}