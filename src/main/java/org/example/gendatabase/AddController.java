package org.example.gendatabase;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

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
    private Button saveButton;
    @FXML
    public void initialize(){
        typeBox.getItems().addAll("Birth","Marriage","Death");
        branchBox.getItems().addAll("MMM","MMF","MFM","MFF","FMM","FMF","FFM","FFF");

    }
    //used to take the currently chosen profile
    private int profile;
    protected void setProfileId(int id){
        profile=id;
    }
    @FXML
    protected void onSaveButtonClicked() throws SQLException{
        String name = nameField.getText();
        String surname = surnameField.getText();
        int year;
        try {
            year = Integer.parseInt(yearField.getText());
        } catch (NumberFormatException e) {
           return;
        }
        String typeString = typeBox.getValue();
        char type ='b';
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
        String parish = parishField.getText();
        String city = cityField.getText();
        String village = villageField.getText();
        String branch = branchBox.getValue();
        String info = infoArea.getText();
        //checks for empty
        if (name.isEmpty() || surname.isEmpty() || parish.isEmpty() || typeString == null || branch == null){
            return;
        }
        //village + city
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
        }

    }

}
