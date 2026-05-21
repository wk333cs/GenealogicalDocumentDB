package org.example.gendatabase;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class ShellController {
    private int chosenProfileId;
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
    private Circle profileDisplay;
    @FXML
    private BorderPane mainShell;
    @FXML
    public void startUp(ProfileParameters pp) throws SQLException {
        this.chosenProfileId= pp.getProfileId();
        profileDisplay.setFill(Color.web(pp.getColour()));
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
            addToggle.setSelected(true);
        } catch (IOException e){
            e.printStackTrace();
        }

    }
    public BorderPane getMainShell() {
        return mainShell;
    }
    //switch to search screen
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
            searchToggle.setSelected(true);

        } catch (IOException e){
            e.printStackTrace();
        }


    }
    //command for either returning to profile selection or pinned
    @FXML
    public void onReturnButtonPressed() throws SQLException, IOException {
        if(isOnDefault){
            Stage stage = (Stage) mainShell.getScene().getWindow();
            FXMLLoader loader =new FXMLLoader(getClass().getResource("profileSelection.fxml"));
            stage.getScene().setRoot(loader.load());


        } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("default.fxml"));
                mainShell.setCenter(loader.load());
                DefaultController controller = loader.getController();
                controller.setProfileId(chosenProfileId);
                //for display and edit
                controller.setShellController(this);
                addToggle.setSelected(false);
                searchToggle.setSelected(false);
                isOnDefault =true;
                returnButton.setText("Profiles");

        }
    }
    public void deselectToggles() {
        addToggle.setSelected(false);
        searchToggle.setSelected(false);
    }





}
