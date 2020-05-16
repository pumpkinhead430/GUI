package com.src.PathTaker;

import com.src.Browser.FullBrowser;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javafx.scene.control.TextField;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class PathTakerController {
    @FXML
    private AnchorPane mainPane;
    @FXML
    private TextField pathField;


    @FXML
    private void HandleBrowsePath(){
        File path = FullBrowser.display("path taker", "dir");
        if(path != null)
            pathField.setText(path.getPath());
    }
    @FXML
    private void Finish(){
        ((Stage) mainPane.getScene().getWindow()).close();
    }
    public String GetPath(){
        return pathField.getText();
    }
}
