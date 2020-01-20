import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;


public class Main extends Application
{
    public static final String[] pictureFormat = {"jpg", "png", "bmp", "jpeg"};
    public static final String directory = Paths.get("src").toAbsolutePath().toString();

    public static void main(String[] arguments)
    {
        Application.launch(Main.class, arguments);
    }


    @Override
    public void start(final Stage stage) throws Exception
    {
        System.out.println(directory);
        Parent fxmlRoot = FXMLLoader.load(getClass().getResource("MainLayout.fxml"));
        Scene scene = new Scene(fxmlRoot);
        scene.getStylesheets().add("Viper.css");
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

    public static Boolean IsPicture(String fileName){
        String extension = "";

        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i+1);
        }
        for(String format : pictureFormat)
            if(extension.equals(format))
                return true;
        return false;
    }
    public static void CopyFile(File src, File dest){
        if (src.exists() && dest.exists()) {
            try {
                FileUtils.copyFileToDirectory(src, dest);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static Boolean ExistsInDir(File file, File dir){
        File checkFile = new File(dir.getPath() + "\\" + file.getName());
        return checkFile.exists();
    }


}