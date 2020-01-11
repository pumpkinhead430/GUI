import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.util.ResourceBundle;


public class BrowserController implements Initializable {
    @FXML
    private TreeView<String> browseTree;
    @Override
    public void initialize(java.net.URL arg0, ResourceBundle arg1)
    {
        browseTree.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> handleExpanded(newValue));
        TreeItem<String> root = new TreeItem<>();
        root.setExpanded(true);
        browseTree.setRoot(root);
        for(File path:File.listRoots())
        {
           TreeItem<String> parentItem = new TreeItem<>(path.getPath());
           browseTree.getRoot().getChildren().add(parentItem);
            File dir = new File(path.getPath());
            if(dir.isDirectory()) {
                handleExpanded(parentItem);
            }
        }
    }

    public void handleExpanded(TreeItem<String> newValue)
    {
        System.out.println(newValue.getValue());
        File treeFile = GetFileFromNode(newValue);
       LoadLevels(2, newValue);
        if(treeFile.isFile())
        {
            System.out.println(treeFile.getName());
        }
    }


    public void LoadBranch(TreeItem<String> parent, File dir) {
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                TreeItem<String> childItem = new TreeItem<>(child.getName());
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
        //System.out.println(path);
        while(!browseTree.getRoot().getChildren().contains(tempValue))
        {
            path = tempValue.getParent().getValue() + "\\" + path;
            tempValue = tempValue.getParent();
        }
        return new File(path);
    }
}
