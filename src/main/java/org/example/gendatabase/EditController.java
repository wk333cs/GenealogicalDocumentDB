package org.example.gendatabase;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.util.Duration;

import java.io.IOException;
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
    @FXML
    private ToggleButton pinButton;
    //auto return to display
    private ShellController shellController;
    public void setShellController(ShellController shellController) {
        this.shellController = shellController;
    }
    forDisplay fd;
    //initialization
    @FXML
    public void setFD(forDisplay FD){
        //text on pin
        pinButton.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            if (isSelected) {
                pinButton.setText("Unpin");
            } else {
                pinButton.setText("Pin");
            }
        });
        fd=FD;
        nameField.setText(fd.getName());
        surnameField.setText(fd.getSurname());
        yearField.setText(String.valueOf(fd.getYear()));
        //type
        typeCombo.getItems().addAll("Birth","Marriage","Death");
        typeCombo.setValue(fd.getType());
        //branch
        branchCombo.getItems().addAll("MMM","MMF","MFM","MFF","FMM","FMF","FFM","FFF");
        branchCombo.setValue(fd.getBranch());

        parishField.setText(fd.getParish());
        cityField.setText(fd.getCity());
        villageField.setText(fd.getVillage());
        infoArea.setText(fd.getInfo());
        villageField.setDisable(!cityField.getText().isEmpty());
        cityField.setDisable(!villageField.getText().isEmpty());
        cityField.textProperty().addListener((obs, oldVal, newVal ) -> {
            villageField.setDisable(!newVal.isEmpty());
        });
        villageField.textProperty().addListener((obs, oldVal, newVal ) -> {
            cityField.setDisable(!newVal.isEmpty());
        });
        pinButton.setSelected(fd.getIsPinned());
    }
    @FXML
    public void onPinButtonPressed() throws SQLException{
        fd.setIsPinned(pinButton.isSelected());
        DBManager.pinClicked(fd);
    }

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
        if(year > 9999 || year < 0){
            showErrorMessage();
            return;
        }
        String type = typeCombo.getValue();

        String parish = parishField.getText();
        String city = cityField.getText();
        String village = villageField.getText();
        String branch = branchCombo.getValue();
        String info = infoArea.getText();
        //checks for empty
        if (name.isEmpty() || surname.isEmpty() || parish.isEmpty() || type == null || branch == null){
            showErrorMessage();
            return;
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
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("display.fxml"));
            shellController.getMainShell().setCenter(loader.load());
            DisplayController dc = loader.getController();
            dc.setFD(fd);
            dc.setShellController(shellController);

        } catch (IOException e) {
            e.printStackTrace();
        }


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
