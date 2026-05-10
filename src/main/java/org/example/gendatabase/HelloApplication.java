package org.example.gendatabase;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("profileSelection.fxml"));
        DBManager.createTable();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("GenDB");
        stage.setScene(scene);
        stage.setMaximized(true);

        stage.show();
    }
}
