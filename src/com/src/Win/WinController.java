package com.src.Win;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.util.ResourceBundle;

public class WinController implements Initializable {

    @FXML
    private ComboBox<String> typeBox;
    @Override
    public void initialize(java.net.URL arg0, ResourceBundle arg1) {
        typeBox.getItems().addAll("Place", "Death");
    }





}
