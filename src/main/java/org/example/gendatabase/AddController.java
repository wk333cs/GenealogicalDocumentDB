package org.example.gendatabase;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.sql.SQLException;

public class AddController {
    @FXML
    private AnchorPane add;
    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField yearField;
    @FXML
    private TextField parishField;
    @FXML
    private  TextField cityField;
    @FXML
    private TextField villageField;
    @FXML
    private TextArea infoArea;
    @FXML
    private ChoiceBox<String> typeBox;
    @FXML
    private ChoiceBox<String> branchBox;
    @FXML
    private Label errorLabel;
    @FXML
    private Button saveButton;
    @FXML
    public void initialize(){
        typeBox.getItems().addAll("Birth","Marriage","Death");
        branchBox.getItems().addAll("MMM","MMF","MFM","MFF","FMM","FMF","FFM","FFF");
        //mutually exclusive village and city
        cityField.textProperty().addListener((obs, oldVal, newVal ) -> {
            villageField.setDisable(!newVal.isEmpty());
        });
        villageField.textProperty().addListener((obs, oldVal, newVal ) -> {
            cityField.setDisable(!newVal.isEmpty());
        });

    }
    //used to take the currently chosen profile
    private int profile;
    protected void setProfileId(int id){
        profile=id;
    }
    //error message function
    private void showErrorMessage() {
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.3), errorLabel);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        PauseTransition stayVisible = new PauseTransition(Duration.seconds(2));

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.3), errorLabel);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        SequentialTransition sequence = new SequentialTransition(fadeIn, stayVisible, fadeOut);
        sequence.play();
    }


    @FXML
    protected void onSaveButtonClicked() throws SQLException{
        String name = nameField.getText();
        String surname = surnameField.getText();
        int year;
        try {
            year = Integer.parseInt(yearField.getText());
        } catch (NumberFormatException e) {
            showErrorMessage();
           return;
        }
        String typeString = typeBox.getValue();
        char type ='b';
        String parish = parishField.getText();
        String city = cityField.getText();
        String village = villageField.getText();
        String branch = branchBox.getValue();
        String info = infoArea.getText();
        //checks for empty
        if (name.isEmpty() || surname.isEmpty() || parish.isEmpty() || typeString == null || branch == null){
            showErrorMessage();
            return;
        }
        //changes visible string into char
        switch (typeString){
            case "Birth":
                type = 'b';
                break;
            case "Marriage":
                type = 'm';
                break;
            case "Death":
                type = 'd';
                break;
        }
        //village + city empty check
        if(village.isEmpty() && city.isEmpty()){
            showErrorMessage();
            return;
        }
        //modal class
        DocumentParameters dp = new DocumentParameters(name, surname, type, year, parish, city, village,  branch, info, profile);

        try {
            DBManager.addDocument(dp);
            nameField.clear();
            surnameField.clear();
            typeBox.setValue(null);
            branchBox.setValue(null);
            yearField.clear();
            parishField.clear();
            cityField.clear();
            villageField.clear();
            infoArea.clear();

        } catch (Exception e) {
            System.out.println("gotcha" + e);
            showErrorMessage();
        }

    }

}
