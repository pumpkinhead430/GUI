import javafx.fxml.FXMLLoader;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;

public class FullBrowser {

    public static String display(String title) throws IOException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinHeight(300);
        window.setMinWidth(300);
        FXMLLoader loader = new FXMLLoader(FullBrowser.class.getResource("Browser.fxml"));
        Parent root = loader.load();
        BrowserController browserController = loader.getController();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("Viper.css");
        window.setScene(scene);
        window.showAndWait();
        File selectedFile = browserController.GetFile();
        if(selectedFile != null) {
            File dest = new File(Main.directory + "\\assets" + selectedFile.getName());
            if (!dest.exists()) {
                Main.CopyFile(selectedFile ,new File(Main.directory + "\\assets"));
            }
            return selectedFile.getName();
        }
        return "";

    }
}
