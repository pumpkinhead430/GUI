package com.src.main;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class ProgressBarController implements Initializable {
    @FXML
    javafx.scene.control.ProgressBar bar;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bar.setProgress(-0.1);
    }
}
