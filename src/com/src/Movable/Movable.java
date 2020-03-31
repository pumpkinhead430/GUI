package com.src.Movable;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.json.simple.JSONObject;

import java.io.IOException;

public class Movable {
    public static void display(String title, JSONObject object){
        try {
            Stage window = new Stage();
            window.setTitle(title);
            window.setMinHeight(300);
            window.setMinWidth(300);
            FXMLLoader loader = new FXMLLoader(Movable.class.getResource("MovableLayout.fxml"));
            Parent root = loader.load();
            MovableController controller = loader.getController();
            controller.SetJson(object);
            controller.LoadJSON();
            Scene scene = new Scene(root);
            scene.getStylesheets().add("Viper.css");
            window.setScene(scene);
            window.setOnCloseRequest((WindowEvent e) -> controller.WriteInfo());
            window.setAlwaysOnTop(true);
            window.showAndWait();


        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
