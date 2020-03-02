package com.src;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.simple.JSONObject;

import java.io.IOException;

public class Stationery {
    public static void display(String title, JSONObject object){
        try {
            Stage window = new Stage();
            window.setTitle(title);
            window.setMinHeight(300);
            window.setMinWidth(300);
            FXMLLoader loader = new FXMLLoader(Stationery.class.getResource("StationeryLayout.fxml"));
            Parent root = loader.load();
            StationeryLayoutController controller = loader.getController();
            controller.SetJson(object);
            Scene scene = new Scene(root);
            scene.getStylesheets().add("Viper.css");
            window.setScene(scene);
            window.setAlwaysOnTop(true);
            window.showAndWait();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}