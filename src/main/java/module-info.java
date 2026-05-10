module org.example.gendatabase {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.controlsfx.controls;
    requires java.desktop;


    opens org.example.gendatabase to javafx.fxml;
    exports org.example.gendatabase;
}