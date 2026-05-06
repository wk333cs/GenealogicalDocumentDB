package org.example.gendatabase;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class DisplayController {
    @FXML
    private AnchorPane display;
    @FXML
    private Button editButton;
    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField typeField;
    @FXML
    private TextField branchField;
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
    //for going from display to edit
    private ShellController shellController;
    public void setShellController(ShellController shellController) {
        this.shellController = shellController;
    }

    //object which was clicked
    forDisplay fd;
    @FXML
    protected void setFD(forDisplay FD){
        fd=FD;
        //initializes all fields
        nameField.setText(fd.getName());
        surnameField.setText(fd.getSurname());
        yearField.setText(String.valueOf(fd.getYear()));
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
        typeField.setText(type);
        branchField.setText(fd.getBranch());
        parishField.setText(fd.getParish());
        cityField.setText(fd.getCity());
        villageField.setText(fd.getVillage());
        infoArea.setText(fd.getInfo());


    }
    @FXML
    public void onEditButtonPressed(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("edit.fxml"));
            shellController.getMainShell().setCenter(loader.load());
            EditController ec = loader.getController();
            ec.setFD(fd);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
