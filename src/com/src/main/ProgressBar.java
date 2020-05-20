package com.src.main;

import com.src.PathTaker.PathTaker;
import com.src.PathTaker.PathTakerController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ProgressBar {
    ProgressBarController controller;
    Stage window;
    public ProgressBar(){
        try {
            window = new Stage();
            window.setTitle("Progress Bar");
            window.setMinHeight(100);
            window.setMinWidth(100);
            FXMLLoader loader = new FXMLLoader(ProgressBar.class.getResource("ProgressBarLayout.fxml"));
            Parent root = loader.load();
            controller = loader.getController();
            Scene scene = new Scene(root);
            scene.getStylesheets().add("Viper.css");
            window.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void Start(){
        window.show();

    }
    public void Stop(){
        window.hide();
    }
}
