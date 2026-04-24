package org.example.gendatabase;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class HelloController {
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("search.fxml"));
            mainShell.setCenter(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}
