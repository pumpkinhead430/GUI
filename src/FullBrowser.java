import javafx.fxml.FXMLLoader;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

import java.io.IOException;

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
        return browserController.GetFileName();

    }
}
