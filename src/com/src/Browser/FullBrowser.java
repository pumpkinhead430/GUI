package com.src.Browser;

import com.src.main.Main;
import javafx.fxml.FXMLLoader;
import javafx.stage.*;
import javafx.scene.*;

import java.io.File;
import java.io.IOException;

public class FullBrowser {

    public static File display(String title, String type){
        try {
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle(title);
            window.setMinHeight(300);
            window.setMinWidth(300);
            FXMLLoader loader = new FXMLLoader(FullBrowser.class.getResource("Browser.fxml"));
            Parent root = loader.load();
            BrowserController browserController = loader.getController();
            browserController.type = type;
            Scene scene = new Scene(root);
            scene.getStylesheets().add("Viper.css");
            window.setScene(scene);
            window.setAlwaysOnTop(true);
            window.showAndWait();
            File selectedFile = browserController.GetFile();
            if (selectedFile != null) {
                if (selectedFile.isFile()) {
                    File assetsDir = new File(Main.directory + "\\assets");
                    File dest = new File(assetsDir.getPath() + "\\" + selectedFile.getName());
                    if (!dest.exists()) {
                        Main.CopyFile(selectedFile, assetsDir);
                    }
                    return selectedFile;
                }
                if (selectedFile.isDirectory())
                    return selectedFile;
            }
            return null;

        }
        catch (IOException e){
            e.printStackTrace();
            return null;
        }

    }
}