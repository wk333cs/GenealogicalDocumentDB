package org.example.gendatabase;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class ProfileSelectionController {
    @FXML
    private AnchorPane profiles;
    @FXML
    private Button addProfile;
    @FXML
    private HBox profileHolder;
    @FXML
    public void initialize() throws SQLException {
        loadProfiles();
    }
    @FXML
    public void loadProfiles() throws SQLException {
        profileHolder.getChildren().clear();
        List<ProfileParameters> allProfiles = DBManager.getProfiles();
        for (ProfileParameters pp: allProfiles ){

            profileHolder.setSpacing(45);
            VBox profileBox = new VBox(5);
            profileBox.setAlignment(Pos.CENTER);

            Button profIcon = new Button();
            profIcon.setPrefSize(100, 100);
            profIcon.setMinSize(100, 100);
            profIcon.setMaxSize(100, 100);
            profIcon.setStyle("-fx-background-color: " + pp.getColour() + "; -fx-background-radius: 100;"  + "-fx-border-color: black;" + "-fx-border-radius: 101;" +  "-fx-border-width: 1;"  );

            profIcon.setOnAction(e -> {
                try {
                    Stage stage = (Stage) profiles.getScene().getWindow();
                    FXMLLoader loader =new FXMLLoader(getClass().getResource("mainShell.fxml"));

                    stage.getScene().setRoot(loader.load());
                    ShellController sc = loader.getController();
                    sc.startUp(pp);


                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                ;

            });

            Label nameLabel = new Label(pp.getProfileName());
            nameLabel.setStyle("-fx-font-size: 17px; -fx-font-weight: bold;");

            Button editButton = new Button("edit");
            editButton.setStyle("-fx-font-size: 17px; -fx-font-weight: bold;");
            editButton.setOnAction(e -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("editProfile.fxml"));
                    Parent root = loader.load();
                    EditProfileController epc = loader.getController();
                    epc.startUp(pp.getProfileId(),pp.getProfileName(),pp.getColour());

                    Stage popup = new Stage();
                    Stage original = (Stage) profiles.getScene().getWindow();
                    popup.initOwner(original);
                    popup.initModality(Modality.APPLICATION_MODAL);
                    popup.setScene(new Scene(root));
                    popup.showAndWait();
                    loadProfiles();


                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });

            profileBox.getChildren().addAll(profIcon, nameLabel, editButton);
            profileHolder.getChildren().add(profileBox);

        }





    }
    @FXML
    public void onAddProfilePressed() throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addProfile.fxml"));
        Parent root = loader.load();

        Stage popup = new Stage();
        Stage original = (Stage) profiles.getScene().getWindow();
        popup.initOwner(original);
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setScene(new Scene(root));
        popup.showAndWait();
        loadProfiles();


    }
    @FXML
    public void onEditProfilePressed(){}
}
