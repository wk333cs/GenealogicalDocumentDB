module org.example.gendatabase {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.gendatabase to javafx.fxml;
    exports org.example.gendatabase;
}