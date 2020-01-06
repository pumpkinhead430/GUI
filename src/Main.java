import java.io.File;
import java.io.FileInputStream;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;



public class Main extends Application
{

    public static void main(String[] arguments)
    {
        Application.launch(Main.class, arguments);
    }


    @Override
    public void start(final Stage stage) throws Exception
    {
        Parent fxmlRoot = FXMLLoader.load(getClass().getResource("JavaFx2Menus.fxml"));
        Scene scene = new Scene(fxmlRoot);
        scene.getStylesheets().add("Viper.css");
        stage.setScene(scene);

        stage.show();
    }
}