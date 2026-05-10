package org.example.gendatabase;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.sql.SQLException;

public class EditProfileController {

        @FXML
        private TextField profileNameField;
        @FXML
        private ColorPicker colorPicker;
        @FXML
        private Label errorLabel;
        @FXML
        private Button editPButton;
        int id;
        String color;
        public void startUp(int id, String name, String color){
            this.color = color;
            this.id=id;

            colorPicker.setValue(Color.web(color));
            profileNameField.setText(name);
        }


        @FXML
        public void onColorChosen() {
            Color selectedColor = colorPicker.getValue();
            color = String.format("#%02X%02X%02X", (int)(selectedColor.getRed() * 255), (int)(selectedColor.getGreen() * 255), (int)(selectedColor.getBlue() * 255));

        }
        @FXML
        public void onSaveButtonPressed() throws SQLException {
            try {
                if (profileNameField.getText().isEmpty()) {
                    showErrorMessage();
                    return;
                } else {
                    DBManager.editProfile(profileNameField.getText(), color, id);
                    javafx.stage.Stage stage = (javafx.stage.Stage) editPButton.getScene().getWindow();
                    stage.close();


                }
            } catch (Exception e){

            }


        }
        @FXML
        public void showErrorMessage(){
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

