package com.src.ObjectDisplay;

import com.src.Win.Win;
import com.src.Win.WinController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import org.json.simple.JSONObject;

import java.io.IOException;

public class ObjectDisplay {
    public static JSONObject display(String title){
        try {
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.initStyle(StageStyle.UNDECORATED);
            window.setTitle(title);
            window.setMinHeight(300);
            window.setMinWidth(300);
            FXMLLoader loader = new FXMLLoader(ObjectDisplay.class.getResource("ObjectLayout.fxml"));
            Parent root = loader.load();
            ObjectController controller = loader.getController();
            Scene scene = new Scene(root);
            scene.getStylesheets().add("Viper.css");
            window.setScene(scene);
            window.setAlwaysOnTop(true);
            window.showAndWait();
            return controller.ChosenObject;
        }
        catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
