import java.io.*;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import jfxtras.styles.jmetro8.JMetro;
import netscape.javascript.JSObject;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


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
        new JMetro(JMetro.Style.DARK).applyTheme(scene);
        stage.setScene(scene);

        stage.show();
    }

    public static void NumberFilter(TextField field) {
        field.getProperties().put("vkType", "numeric");
        field.setTextFormatter(new TextFormatter<>(c -> {
            if (c.isContentChange()) {
                if (c.getControlNewText().length() == 0) {
                    return c;
                }
                try {
                    Integer.parseInt(c.getControlNewText());
                    return c;
                } catch (NumberFormatException ignored) {
                }
                return null;

            }
            return c;
        }));
    }


}