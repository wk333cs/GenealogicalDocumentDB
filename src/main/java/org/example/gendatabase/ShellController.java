package org.example.gendatabase;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class ShellController {
    static int chosenProfileId = 1;
    @FXML
    private ToggleButton searchToggle;
    @FXML
    private ToggleButton addToggle;
    @FXML
    private Button returnButton;
    @FXML
    private BorderPane mainShell;
    @FXML
    public void initialize() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("default.fxml"));
            mainShell.setCenter(loader.load());

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
        } catch (IOException e){
            e.printStackTrace();
        }

    }





}
