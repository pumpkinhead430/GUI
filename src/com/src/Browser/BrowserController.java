package com.src.Browser;

import com.src.main.Main;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.util.ResourceBundle;


public class BrowserController implements Initializable {
    @FXML
    private TreeView<String> browseTree;
    private File file;
    public String type = "";
    @Override
    public void initialize(java.net.URL arg0, ResourceBundle arg1)
    {
        file = null;
        browseTree.setOnMouseClicked(this::handleClicked);
        TreeItem<String> root = new TreeItem<>();
        root.setExpanded(true);
        browseTree.setRoot(root);
        for(File path : File.listRoots())
        {
            TreeItem<String> parentItem = new TreeItem<>(path.getPath());
            parentItem.expandedProperty().addListener((observableValue, aBoolean, t1) -> AddExpansionProperty(observableValue, t1));
            browseTree.getRoot().getChildren().add(parentItem);
            File dir = new File(path.getPath());
            if(dir.isDirectory()) {
                handleExpanded(parentItem);
            }
        }
    }

    public void handleClicked(MouseEvent mouseEvent){
        if(mouseEvent.getClickCount() == 2){
            TreeItem<String> treeItem = browseTree.getSelectionModel().getSelectedItem();
            if(treeItem != null) {
                File treeFile = GetFileFromNode(treeItem);

                if(type.equals("pic")) {
                    if (treeFile.isFile() && Main.IsPicture(treeFile.getName())) {
                        Stage thisWindow = (Stage) browseTree.getScene().getWindow();
                        thisWindow.close();
                        file = treeFile;
                    }
                }
                else {

                        if (type.equals("dir")) {
                            System.out.println(treeFile);
                            if (treeFile.isDirectory()) {
                                Stage thisWindow = (Stage) browseTree.getScene().getWindow();
                                thisWindow.close();
                                file = treeFile;
                            }
                        }
                    }
            }
        }

    }

    public void handleExpanded(TreeItem<String> newValue)
    {
        File treeFile = GetFileFromNode(newValue);
        LoadLevels(2, newValue);
        if(type.equals("pic"))
            if(treeFile.isFile() && Main.IsPicture(treeFile.getName()))
            {
                Stage thisWindow = (Stage)browseTree.getScene().getWindow();
                thisWindow.close();
                file =  treeFile;
            }
        else
            if(type.equals("dir")){
                if (treeFile.isDirectory()) {
                    Stage thisWindow = (Stage) browseTree.getScene().getWindow();
                    thisWindow.close();
                    file = treeFile;
                }
            }
    }
    private void AddExpansionProperty(ObservableValue<? extends Boolean> observableValue, Boolean t1)
    {
        BooleanProperty bb = (BooleanProperty) observableValue;
        TreeItem t = (TreeItem) bb.getBean();
        handleExpanded(t);
    }

    public void LoadBranch(TreeItem<String> parent, File dir) {
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                TreeItem<String> childItem = new TreeItem<>(child.getName());
                childItem.expandedProperty().addListener((observableValue, aBoolean, t1) -> AddExpansionProperty(observableValue, t1));
                parent.getChildren().add(childItem);
            }
        }


    }
    public void LoadLevels(int amount, TreeItem<String> currentItem){
        if(amount != 0) {
            if(GetFileFromNode(currentItem).isDirectory())
                if (currentItem.isLeaf()) {
                    LoadBranch(currentItem, GetFileFromNode(currentItem));
                }
            for (TreeItem<String> child : currentItem.getChildren()) {
                LoadLevels(amount - 1, child);
            }
        }
    }

    public File GetFileFromNode(TreeItem<String> newValue){
        TreeItem<String> tempValue = newValue;
        String path = newValue.getValue();
        while(!browseTree.getRoot().getChildren().contains(tempValue))
        {
            path = tempValue.getParent().getValue() + "\\" + path;
            tempValue = tempValue.getParent();
        }
        return new File(path);
    }
    public File GetFile(){
        return file;
    }
}