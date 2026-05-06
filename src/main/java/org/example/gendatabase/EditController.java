package org.example.gendatabase;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class EditController {
    @FXML
    private Button saveButton;
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
    @FXML
    public void setFD(forDisplay FD){
        fd=FD;
        nameField.setText(fd.getName());
        surnameField.setText(fd.getSurname());
        yearField.setText(String.valueOf(fd.getYear()));
        //type branch
        parishField.setText(fd.getParish());
        cityField.setText(fd.getCity());
        villageField.setText(fd.getVillage());
        infoArea.setText(fd.getInfo());
    }
}
