package com.src.Movable;

import com.src.Movable.Movable;
import com.src.Movable.MovableController;
import com.src.main.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.stage.Stage;
import org.json.simple.JSONObject;

import java.io.IOException;

public class animationTab {
    public static Tab newAnimation() throws IOException {
        return FXMLLoader.load(animationTab.class.getResource("animationLayout.fxml"));
    }
}
