package org.example.gendatabase;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.SQLException;

public class DefaultController {
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
    private AnchorPane defaultScreen;
//profile and load
    private int profile;
    protected void setProfileId(int id) throws SQLException{
        profile=id;
        loadPinned();
    }

    private ShellController shellController;
    public void setShellController(ShellController shellController) {
        this.shellController = shellController;
    }

    @FXML
    public void initialize() throws SQLException{
        //for display
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

        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));
        yearCol.setCellValueFactory(new PropertyValueFactory<>("year"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        parishCol.setCellValueFactory(new PropertyValueFactory<>("parish"));
        cityCol.setCellValueFactory(new PropertyValueFactory<>("city"));
        villageCol.setCellValueFactory(new PropertyValueFactory<>("village"));
        branchCol.setCellValueFactory(new PropertyValueFactory<>("branch"));

    }

    public void loadPinned() throws SQLException {
        ObservableList<forDisplay> allPinned = FXCollections.observableArrayList();
        try {
            allPinned.addAll(DBManager.showPinned(profile));
            table.setItems(allPinned);

        } catch(Exception e) {
            System.out.println(e.getMessage());

        }

    }
}
