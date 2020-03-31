package com.src.Movable;

import com.src.Movable.Movable;
import com.src.Movable.MovableController;
import com.src.main.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.simple.JSONObject;

import java.io.IOException;

public class animationTab {

    public AnimationController controller;
    public Tab tab;
    public animationTab(JSONObject object){
        FXMLLoader loader = new FXMLLoader(animationTab.class.getResource("animationLayout.fxml"));
        try {
            tab = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        controller = loader.getController();
        controller.PutJson(object);
    }

    public animationTab(){
        FXMLLoader loader = new FXMLLoader(animationTab.class.getResource("animationLayout.fxml"));
        try {
            tab = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        controller = loader.getController();
    }

}
