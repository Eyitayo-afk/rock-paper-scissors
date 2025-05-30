module isaac {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens isaac to javafx.fxml;
    exports isaac;
}
