package com.src.Movable;

import com.src.Movable.Movable;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
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
