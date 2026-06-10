package org.example.gendatabase;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import org.controlsfx.control.CheckComboBox;

import java.io.IOException;
import java.sql.SQLException;

public class SearchController {
    @FXML
    private AnchorPane search;

    @FXML
    private Button filterButton;
    @FXML
    private Label errorLabel;
    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private CheckComboBox<String> typeCheckCombo;
    @FXML
    private CheckComboBox<String> branchCheckCombo;
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
    @FXML
    private FlowPane nameTagField;
    @FXML
    private FlowPane surnameTagField;
    @FXML
    private FlowPane parishTagField;
    @FXML
    private FlowPane cityTagField;
    @FXML
    private FlowPane villageTagField;
    //for getting the filter parameters
    FilterParameters fp;
    private int profile;
    protected void setFPAndProfile(FilterParameters fp, int id) throws SQLException{
        this.fp=fp; //allows for modifying and accessing the FilterParameters object instantiated within the shell controller class
        profile = id;
        reconstructVisibleFilters(); //reconstructs the display of search parameters
        loadResults(this.fp);

    }
    //for switching into display
    private ShellController shellController;
    public void setShellController(ShellController shellController) {
        this.shellController = shellController;
    }

    @FXML
    public void initialize() throws IOException {

        //search results clickable
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                try{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("display.fxml"));
                    shellController.getMainShell().setCenter(loader.load());
                    DisplayController dc = loader.getController();
                    dc.setFD(newSelection);
                    dc.setShellController(shellController);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        //year input
        firstYear.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                firstYearEntered();
            }
        });
        lastYear.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                lastYearEntered();
            }
        });
        typeCheckCombo.getItems().addAll("Birth", "Marriage", "Death");
        branchCheckCombo.getItems().addAll("MMM","MMF","MFM","MFF","FMM","FMF","FFM","FFF");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));
        yearCol.setCellValueFactory(new PropertyValueFactory<>("year"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        parishCol.setCellValueFactory(new PropertyValueFactory<>("parish"));
        cityCol.setCellValueFactory(new PropertyValueFactory<>("city"));
        villageCol.setCellValueFactory(new PropertyValueFactory<>("village"));
        branchCol.setCellValueFactory(new PropertyValueFactory<>("branch"));

    }

    @FXML
    public void onFilterPressed() throws SQLException{
        //loads all selected branches and types

        ObservableList<String> chosenBranch = branchCheckCombo.getCheckModel().getCheckedItems();
        fp.getBranch().clear();
        fp.getBranch().addAll(chosenBranch);

        ObservableList<String> chosenType = typeCheckCombo.getCheckModel().getCheckedItems();
        fp.getType().clear();
        fp.getType().addAll(chosenType);
        loadResults(fp);

    }

    //handling text inputs
    @FXML
    public void nameEntered() { // public modifier is necessary for the fxml file to detect the action
        String name= nameField.getText().trim();
        if(!name.isEmpty()) {
            fp.addName(name); //inputted name is added to the set
            HBox nameTag = new HBox(5); // new HBox is created for displaying the parameter
            nameTag.setAlignment(Pos.CENTER);
            nameTag.setStyle("-fx-background-color: #ffffff; -fx-padding: 2 5 2 5; -fx-background-radius: 10;");
            //sets metadata for handling and removing duplicates
            nameTag.setUserData(name);

            Label label = new Label(name);
            Button removeButton = new Button("x");
            removeButton.setStyle("-fx-background-color: transparent; -fx-text-fill: black; -fx-cursor: hand;");
            //assigns an action event for deleting the parameter
            removeButton.setOnAction(e -> {
                nameTagField.getChildren().remove(nameTag);
                //checks for duplicates of the same name, template from https://www.geeksforgeeks.org/java/stream-anymatch-java-examples/
                //makes sure to check only through HBoxes to avoid a possible error
                boolean stillExists = nameTagField.getChildren().stream().anyMatch(node -> node instanceof HBox && name.equals(node.getUserData()));
                if(!stillExists) { // if there is no duplicates, the name is removed from the set
                    fp.getName().remove(name);
                }

            });

            nameTag.getChildren().addAll(label,removeButton);
            nameTagField.getChildren().addAll(nameTag);

            nameField.clear(); // text field is cleared

        }
    }
    @FXML
    public void surnameEntered() {
        String surname = surnameField.getText().trim();
        if(!surname.isEmpty()) {
            fp.addSurname(surname);
            HBox surnameTag = new HBox(5);
            surnameTag.setAlignment(Pos.CENTER);
            surnameTag.setStyle("-fx-background-color: #ffffff; -fx-padding: 2 5 2 5; -fx-background-radius: 10;");
            surnameTag.setUserData(surname);

            Label label = new Label(surname);
            Button removeButton = new Button("x");
            removeButton.setStyle("-fx-background-color: transparent; -fx-text-fill: black; -fx-cursor: hand;");

            removeButton.setOnAction(e -> {
                surnameTagField.getChildren().remove(surnameTag);
                boolean stillExists = surnameTagField.getChildren().stream().anyMatch(node -> node instanceof HBox && surname.equals(node.getUserData()));
                if(!stillExists) {
                    fp.getSurname().remove(surname);
                }

            });

            surnameTag.getChildren().addAll(label, removeButton);
            surnameTagField.getChildren().add(surnameTag);

            surnameField.clear();
        }
    }
    @FXML
    public void parishEntered() {
        String parish = parishField.getText().trim();
        if(!parish.isEmpty()) {
            fp.addParish(parish);
            HBox parishTag = new HBox(5);
            parishTag.setAlignment(Pos.CENTER);
            parishTag.setStyle("-fx-background-color: #ffffff; -fx-padding: 2 5 2 5; -fx-background-radius: 10;");
            parishTag.setUserData(parish);

            Label label = new Label(parish);
            Button removeButton = new Button("x");
            removeButton.setStyle("-fx-background-color: transparent; -fx-text-fill: black; -fx-cursor: hand;");

            removeButton.setOnAction(e -> {
                parishTagField.getChildren().remove(parishTag);
                boolean stillExists = parishTagField.getChildren().stream().anyMatch(node -> node instanceof HBox && parish.equals(node.getUserData()));
                if(!stillExists) {
                    fp.getParish().remove(parish);
                }

            });

            parishTag.getChildren().addAll(label, removeButton);
            parishTagField.getChildren().add(parishTag);

            parishField.clear();
        }
    }
    @FXML
    public void cityEntered() {
        String city = cityField.getText().trim();
        if(!city.isEmpty()) {
            fp.addCity(city);
            HBox cityTag = new HBox(5);
            cityTag.setAlignment(Pos.CENTER);
            cityTag.setStyle("-fx-background-color: #ffffff; -fx-padding: 2 5 2 5; -fx-background-radius: 10;");
            cityTag.setUserData(city);

            Label label = new Label(city);
            Button removeButton = new Button("x");
            removeButton.setStyle("-fx-background-color: transparent; -fx-text-fill: black; -fx-cursor: hand;");

            removeButton.setOnAction(e -> {
                cityTagField.getChildren().remove(cityTag);
                boolean stillExists = cityTagField.getChildren().stream().anyMatch(node -> node instanceof HBox && city.equals(node.getUserData()));
                if(!stillExists) {
                    fp.getCity().remove(city);
                }

            });

            cityTag.getChildren().addAll(label, removeButton);
            cityTagField.getChildren().add(cityTag);

            cityField.clear();
        }
    }
    @FXML
    public void villageEntered() {
        String village = villageField.getText().trim();
        if(!village.isEmpty()) {
            fp.addVillage(village);
            HBox villageTag = new HBox(5);
            villageTag.setAlignment(Pos.CENTER);
            villageTag.setStyle("-fx-background-color: #ffffff; -fx-padding: 2 5 2 5; -fx-background-radius: 10;");
            villageTag.setUserData(village);

            Label label = new Label(village);
            Button removeButton = new Button("x");
            removeButton.setStyle("-fx-background-color: transparent; -fx-text-fill: black ; -fx-cursor: hand;");

            removeButton.setOnAction(e -> {
                villageTagField.getChildren().remove(villageTag);
                boolean stillExists = villageTagField.getChildren().stream().anyMatch(node -> node instanceof HBox && village.equals(node.getUserData()));
                if(!stillExists) {
                    fp.getVillage().remove(village);
                }

            });

            villageTag.getChildren().addAll(label, removeButton);
            villageTagField.getChildren().add(villageTag);

            villageField.clear();
        }
    }

    @FXML
    public void firstYearEntered() {
        int y1;
        int y2;
        if(firstYear.getText().isEmpty()){
            fp.addFirstYear(0);
            return;
        }
        //sees if the input is a number
        try {
            y1 = Integer.parseInt(firstYear.getText());
        } catch (NumberFormatException e) {
            showErrorMessage();
            return;
        }

        if(lastYear.getText().isEmpty()){
            fp.addFirstYear(y1);
        } else {
            //checks if the other field is a number
            try {
                y2 = Integer.parseInt(lastYear.getText());
            } catch (NumberFormatException e) {
                showErrorMessage();
                return;
            }
            if(y1 <= y2){
                fp.addFirstYear(y1);
            } else {
                showErrorMessage();
                return;
            }

        }
    }
    @FXML
    public void lastYearEntered() {
        int y1;
        int y2;
        if(lastYear.getText().isEmpty()){
            fp.addLastYear(9999);
            return;
        }
        //sees if the input is a number
        try {
            y2 = Integer.parseInt(lastYear.getText());
        } catch (NumberFormatException e) {
            showErrorMessage();
            return;
        }

        if(firstYear.getText().isEmpty()){
            fp.addLastYear(y2);
        } else {
            //checks if the other field is a number
            try {
                y1 = Integer.parseInt(firstYear.getText());
            } catch (NumberFormatException e) {
                showErrorMessage();
                return;
            }
            if(y1 <= y2){
                fp.addLastYear(y2);
            } else {
                showErrorMessage();
                return;
            }

        }
    }


    @FXML
    private void loadResults(FilterParameters fp) throws SQLException {
        ObservableList<forDisplay> resultList = FXCollections.observableArrayList();
        try {
            resultList.addAll(DBManager.search(fp,profile));
            table.setItems(resultList);

        } catch(Exception e) {
            System.out.println(e.getMessage());

        }

    }
    //error for year
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

    private void reconstructVisibleFilters(){
        if(fp.getFirstYear()!=0){ // if the year chosen isn't set to its default value, it is displayed in the text field
            firstYear.setText(String.valueOf(fp.getFirstYear()));
        }
        if(fp.getLastYear()!=9999){
            lastYear.setText(String.valueOf(fp.getLastYear()));
        }
        if(!fp.getType().isEmpty()) {
            for (String type : fp.getType()) {
                typeCheckCombo.getCheckModel().check(type);
            }
        }
        if(!fp.getBranch().isEmpty()) {
            for (String branch : fp.getBranch()) {
                branchCheckCombo.getCheckModel().check(branch);
            }
        }

        for(String name: fp.getName()){ // for each name in the set
            HBox nameTag = new HBox(5); // new HBox is created for displaying the parameter
            nameTag.setAlignment(Pos.CENTER);
            nameTag.setStyle("-fx-background-color: #ffffff; -fx-padding: 2 5 2 5; -fx-background-radius: 10;");
            //sets metadata for handling and removing duplicates
            nameTag.setUserData(name);

            Label label = new Label(name);
            Button removeButton = new Button("x");
            removeButton.setStyle("-fx-background-color: transparent; -fx-text-fill: black; -fx-cursor: hand;");
            //assigns an action event for deleting the parameter
            removeButton.setOnAction(e -> {
                nameTagField.getChildren().remove(nameTag);
                //checks for duplicates of the same name, template from https://www.geeksforgeeks.org/java/stream-anymatch-java-examples/
                //makes sure to check only through HBoxes to avoid a possible error
                boolean stillExists = nameTagField.getChildren().stream().anyMatch(node -> node instanceof HBox && name.equals(node.getUserData()));
                if(!stillExists) { // if there is no duplicates, the name is removed from the set
                    fp.getName().remove(name);
                }

            });

            nameTag.getChildren().addAll(label,removeButton);
            nameTagField.getChildren().addAll(nameTag);


        }

        for(String surname : fp.getSurname()){
            HBox surnameTag = new HBox(5);
            surnameTag.setAlignment(Pos.CENTER);
            surnameTag.setStyle("-fx-background-color: #ffffff; -fx-padding: 2 5 2 5; -fx-background-radius: 10;");
            surnameTag.setUserData(surname);

            Label label = new Label(surname);
            Button removeButton = new Button("x");
            removeButton.setStyle("-fx-background-color: transparent; -fx-text-fill: black; -fx-cursor: hand;");

            removeButton.setOnAction(e -> {
                surnameTagField.getChildren().remove(surnameTag);
                boolean stillExists = surnameTagField.getChildren().stream().anyMatch(node -> node instanceof HBox && surname.equals(node.getUserData()));
                if(!stillExists) {
                    fp.getSurname().remove(surname);
                }

            });

            surnameTag.getChildren().addAll(label, removeButton);
            surnameTagField.getChildren().add(surnameTag);
        }

        for(String parish : fp.getParish()){
            HBox parishTag = new HBox(5);
            parishTag.setAlignment(Pos.CENTER);
            parishTag.setStyle("-fx-background-color: #ffffff; -fx-padding: 2 5 2 5; -fx-background-radius: 10;");
            parishTag.setUserData(parish);

            Label label = new Label(parish);
            Button removeButton = new Button("x");
            removeButton.setStyle("-fx-background-color: transparent; -fx-text-fill: black; -fx-cursor: hand;");

            removeButton.setOnAction(e -> {
                parishTagField.getChildren().remove(parishTag);
                boolean stillExists = parishTagField.getChildren().stream().anyMatch(node -> node instanceof HBox && parish.equals(node.getUserData()));
                if(!stillExists) {
                    fp.getParish().remove(parish);
                }

            });

            parishTag.getChildren().addAll(label, removeButton);
            parishTagField.getChildren().add(parishTag);
        }

        for(String city : fp.getCity()){
            HBox cityTag = new HBox(5);
            cityTag.setAlignment(Pos.CENTER);
            cityTag.setStyle("-fx-background-color: #ffffff; -fx-padding: 2 5 2 5; -fx-background-radius: 10;");
            cityTag.setUserData(city);

            Label label = new Label(city);
            Button removeButton = new Button("x");
            removeButton.setStyle("-fx-background-color: transparent; -fx-text-fill: black; -fx-cursor: hand;");

            removeButton.setOnAction(e -> {
                cityTagField.getChildren().remove(cityTag);
                boolean stillExists = cityTagField.getChildren().stream().anyMatch(node -> node instanceof HBox && city.equals(node.getUserData()));
                if(!stillExists) {
                    fp.getCity().remove(city);
                }

            });

            cityTag.getChildren().addAll(label, removeButton);
            cityTagField.getChildren().add(cityTag);
        }

        for(String village : fp.getVillage()){
            HBox villageTag = new HBox(5);
            villageTag.setAlignment(Pos.CENTER);
            villageTag.setStyle("-fx-background-color: #ffffff; -fx-padding: 2 5 2 5; -fx-background-radius: 10;");
            villageTag.setUserData(village);

            Label label = new Label(village);
            Button removeButton = new Button("x");
            removeButton.setStyle("-fx-background-color: transparent; -fx-text-fill: black ; -fx-cursor: hand;");

            removeButton.setOnAction(e -> {
                villageTagField.getChildren().remove(villageTag);
                boolean stillExists = villageTagField.getChildren().stream().anyMatch(node -> node instanceof HBox && village.equals(node.getUserData()));
                if(!stillExists) {
                    fp.getVillage().remove(village);
                }

            });

            villageTag.getChildren().addAll(label, removeButton);
            villageTagField.getChildren().add(villageTag);
        }



    }

}
