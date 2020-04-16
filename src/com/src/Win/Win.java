package com.src.Win;

import com.src.Movable.Movable;
import com.src.Movable.MovableController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.json.simple.JSONObject;

import java.io.IOException;

public class Win {
    public static void display(String title, JSONObject object){
        try {
            Stage window = new Stage();
            window.setTitle(title);
            window.setMinHeight(300);
            window.setMinWidth(300);
            FXMLLoader loader = new FXMLLoader(Win.class.getResource("WinLayout.fxml"));
            Parent root = loader.load();
            WinController controller = loader.getController();
            //controller.SetJson(object);
            //controller.LoadJSON();
            Scene scene = new Scene(root);
            scene.getStylesheets().add("Viper.css");
            window.setScene(scene);
            window.setOnCloseRequest((WindowEvent e) -> System.out.println("hi"));
            //controller.WriteInfo()
            window.setAlwaysOnTop(true);
            window.showAndWait();


        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

}
