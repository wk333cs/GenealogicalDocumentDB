package org.example.gendatabase;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Duration;

import java.sql.SQLException;

public class EditController {
    @FXML
    private Button saveButton;
    @FXML
    private Label errorLabel;
    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private ChoiceBox<String> typeCombo;
    @FXML
    private ChoiceBox<String> branchCombo;
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
    forDisplay fd;
    //initialization
    @FXML
    public void setFD(forDisplay FD){
        fd=FD;
        nameField.setText(fd.getName());
        surnameField.setText(fd.getSurname());
        yearField.setText(String.valueOf(fd.getYear()));
        //type
        typeCombo.getItems().addAll("Birth","Marriage","Death");
        String type="Birth";
        switch (fd.getType()){
            case "b":
                type = "Birth";
                break;
            case "m":
                type = "Marriage";
                break;
            case "d":
                type = "Death";
                break;
        }
        typeCombo.setValue(type);
        //branch
        branchCombo.getItems().addAll("MMM","MMF","MFM","MFF","FMM","FMF","FFM","FFF");
        branchCombo.setValue(fd.getBranch());

        parishField.setText(fd.getParish());
        cityField.setText(fd.getCity());
        villageField.setText(fd.getVillage());
        infoArea.setText(fd.getInfo());
    }
//need village city excluisve
    @FXML
    public void onSaveButtonPressed() throws SQLException {
        String name = nameField.getText();
        String surname = surnameField.getText();
        int year;
        try {
            year = Integer.parseInt(yearField.getText());
        } catch (NumberFormatException e) {
            showErrorMessage();
            return;
        }
        String typeString = typeCombo.getValue();
        String type ="b";
        String parish = parishField.getText();
        String city = cityField.getText();
        String village = villageField.getText();
        String branch = branchCombo.getValue();
        String info = infoArea.getText();
        //checks for empty
        if (name.isEmpty() || surname.isEmpty() || parish.isEmpty() || typeString == null || branch == null){
            showErrorMessage();
            return;
        }
        //changes visible string into char
        switch (typeString){
            case "Birth":
                type = "b";
                break;
            case "Marriage":
                type = "m";
                break;
            case "Death":
                type = "d";
                break;
        }
        //village + city empty check
        if(village.isEmpty() && city.isEmpty()){
            showErrorMessage();
            return;
        }
        fd.setName(name);
        fd.setSurname(surname);
        fd.setYear(year);
        fd.setType(type);
        fd.setCity(city);
        fd.setVillage(village);
        fd.setParish(parish);
        fd.setBranch(branch);
        fd.setInfo(info);

        DBManager.editDocument(fd);
        //return to display


    }

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

}
