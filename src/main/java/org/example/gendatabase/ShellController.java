package org.example.gendatabase;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.sql.SQLException;

public class ShellController {
    private int chosenProfileId = 1;
    //for returning
    private boolean isOnDefault;
    public void setOnDefaultToFalse(){
        isOnDefault=false;
        returnButton.setText("Pinned");
    }
    FilterParameters FP= new FilterParameters();
    @FXML
    private ToggleButton searchToggle;
    @FXML
    private ToggleButton addToggle;
    @FXML
    private Button returnButton;
    @FXML
    private BorderPane mainShell;
    @FXML
    public void initialize() throws SQLException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("default.fxml"));
            mainShell.setCenter(loader.load());
            DefaultController controller = loader.getController();
            controller.setProfileId(chosenProfileId);
            //for display and edit
            controller.setShellController(this);
            isOnDefault =true;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //switch to add screen
    @FXML
    private void onAddTogglePressed() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("add.fxml"));
            mainShell.setCenter(loader.load());
            AddController controller = loader.getController();
            controller.setProfileId(chosenProfileId);
            isOnDefault = false;
            returnButton.setText("Pinned");
        } catch (IOException e){
            e.printStackTrace();
        }

    }
    public BorderPane getMainShell() {
        return mainShell;
    }
    @FXML
    private void onSearchTogglePressed() throws IOException, SQLException{
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("search.fxml"));
            mainShell.setCenter(loader.load());
            SearchController controller = loader.getController();

            controller.setProfileId(chosenProfileId);

            //for display and edit
            controller.setShellController(this);
            // sets fp and autoloads results
            controller.setFP(FP);
            isOnDefault= false;
            returnButton.setText("Pinned");

        } catch (IOException e){
            e.printStackTrace();
        }


    }

    @FXML
    public void onReturnButtonPressed() throws SQLException{
        if(isOnDefault){

            //placeholder

        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("default.fxml"));
                mainShell.setCenter(loader.load());
                DefaultController controller = loader.getController();
                controller.setProfileId(chosenProfileId);
                //for display and edit
                controller.setShellController(this);
                isOnDefault =true;
                returnButton.setText("Profiles");

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }





}
