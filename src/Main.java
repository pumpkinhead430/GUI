import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


public class Main extends Application
{
    public static final String[] pictureFormat = {"jpg", "png", "bmp", "jpeg"};
    public static final String directory = Paths.get("src").toAbsolutePath().toString();
    public static ArrayList<File> files = new ArrayList<>();

    public static void main(String[] arguments)
    {
        Application.launch(Main.class, arguments);
    }


    @Override
    public void start(final Stage stage) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("MainLayout.fxml"));
        Parent root = loader.load();
        MainLayoutController controller = loader.getController();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("Viper.css");
        stage.setScene(scene);
        stage.show();
        Thread thread =  new Thread(() -> {
            while (stage.isShowing()) {

                Platform.runLater(() -> {
                    File dir = new File(directory + "\\assets");
                    if (dir.listFiles() != null) {
                        for (File file : Objects.requireNonNull(dir.listFiles())) {
                            if (!files.contains(file)) {
                                if (Main.IsPicture(file.getName())) {
                                    files.add(file);
                                    controller.AddToImportBox(file);
                                }
                            }
                        }
                        ArrayList<File>filesToRemove = new ArrayList<>();
                        for(File file : files){
                            if(!file.exists()){
                                filesToRemove.add(file);
                                controller.RemoveFile(file);
                            }
                        }
                        files.removeAll(filesToRemove);
                    }

                });
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
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
        if(src != null && dest != null) {
            if (src.exists() && dest.exists()) {
                try {
                    System.out.println(src + " " + dest);
                    FileUtils.copyFileToDirectory(src, dest);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void ReSizePicture(double maxWidth, double maxHeight, ImageView image){
        double ratio = image.getImage().getWidth()/image.getImage().getHeight();
        image.setFitHeight(1);
        image.setFitWidth(ratio);
        double previousWidth = image.getFitWidth();
        double previousHeight = image.getFitHeight();
        while(maxWidth > image.getFitWidth() && maxHeight > image.getFitHeight()) {
            previousWidth = image.getFitWidth();
            previousHeight = image.getFitHeight();
            image.setFitHeight(image.getFitHeight() + 1);
            image.setFitWidth(image.getFitHeight() * ratio);

        }
        image.setFitWidth(previousWidth);
        image.setFitHeight(previousHeight);
    }


}