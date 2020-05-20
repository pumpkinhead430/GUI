package com.src.PathTaker;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class PathTaker {
    public static File display(String title) {
        try {
            Stage window = new Stage();
            window.setTitle(title);
            window.setMinHeight(300);
            window.setMinWidth(300);
            FXMLLoader loader = new FXMLLoader(PathTaker.class.getResource("PathTakerLayout.fxml"));
            Parent root = loader.load();
            PathTakerController controller = loader.getController();
            Scene scene = new Scene(root);
            scene.getStylesheets().add("Viper.css");
            window.setScene(scene);
            window.setAlwaysOnTop(true);
            window.showAndWait();
            return new File(controller.GetPath());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
