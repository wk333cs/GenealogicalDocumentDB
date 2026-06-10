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
    protected void startUp(ProfileParameters pp) throws SQLException { //ProfileParameters are passed by the ProfileSelectionController
        this.chosenProfileId= pp.getProfileId(); // sets the profile id
        profileDisplay.setFill(Color.web(pp.getColour())); // sets the color of the icon to match the chosen profile
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("default.fxml")); // new loader is created and the fxml view is instantiated
            mainShell.setCenter(loader.load()); // the default screen is loaded into the BorderPane
            DefaultController controller = loader.getController(); // a new controller is instantiated
            controller.setProfileId(chosenProfileId); //sets profile id and loads pinned documents
            //passes the ShellController for display and edit controllers to use
            controller.setShellController(this);
            isOnDefault =true; // used for the return button

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
    private void onSearchTogglePressed() throws SQLException{
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("search.fxml"));
            mainShell.setCenter(loader.load());
            SearchController controller = loader.getController();
            //passes the ShellController for display and edit controllers to use
            controller.setShellController(this);
            // sets the profile id, fp, reconstructs the dynamic GUI elements and autoloads results
            controller.setFPAndProfile(FP, chosenProfileId);
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
