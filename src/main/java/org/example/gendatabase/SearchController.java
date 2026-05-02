package org.example.gendatabase;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;

public class SearchController {
    @FXML
    private AnchorPane search;
    FilterParameters fp = new FilterParameters();
    @FXML
    private Button filterButton;
    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField firstYear;
    @FXML
    private TextField lastYear;
    @FXML
    private TextField parishField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField villageField;
    @FXML
    private TableView<forDisplay> table;
    @FXML
    private TableColumn<forDisplay, String> nameCol;
    @FXML
    private TableColumn<forDisplay, String> surnameCol;
    @FXML
    private TableColumn<forDisplay, Integer>yearCol;
    @FXML
    private TableColumn<forDisplay, String> typeCol;
    @FXML
    private TableColumn<forDisplay, String> parishCol;
    @FXML
    private TableColumn<forDisplay, String> cityCol;
    @FXML
    private TableColumn<forDisplay, String> villageCol;
    @FXML
    private TableColumn<forDisplay, String> branchCol;
    //for getting the profile
    private int profile;
    protected void setProfileId(int id){
        profile=id;
    }
    @FXML
    public void initialize(){
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));
        yearCol.setCellValueFactory(new PropertyValueFactory<>("year"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        parishCol.setCellValueFactory(new PropertyValueFactory<>("parish"));
        cityCol.setCellValueFactory(new PropertyValueFactory<>("city"));
        villageCol.setCellValueFactory(new PropertyValueFactory<>("village"));
        branchCol.setCellValueFactory(new PropertyValueFactory<>("branch"));

    }

    //handling text inputs
    @FXML
    public void nameEntered() {
        String name= nameField.getText();
        fp.addName(name);
    }
    @FXML
    public void surnameEntered() {
        String surname= surnameField.getText();
        fp.addSurname(surname);
    }
    @FXML
    public void parishEntered() {
        String parish= parishField.getText();
        fp.addParish(parish);
    }
    @FXML
    public void cityEntered() {
        String city= cityField.getText();
        fp.addCity(city);
    }
    @FXML
    public void villageEntered() {
        String village= villageField.getText();
        fp.addVillage(village);
    }


    @FXML
    public void loadResults(FilterParameters fp) throws SQLException {
        ObservableList<forDisplay> resultList = FXCollections.observableArrayList();
        try {
            resultList.addAll(DBManager.search(fp,profile));
            table.setItems(resultList);

        } catch(Exception e) {
            System.out.println(e.getMessage());

        }

    }

}
