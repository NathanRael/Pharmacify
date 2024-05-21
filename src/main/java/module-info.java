module com.nathan.pharmacy {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.nathan.pharmacy to javafx.fxml;
    exports com.nathan.pharmacy;
    opens com.nathan.pharmacy.controllers to javafx.fxml;
    opens com.nathan.pharmacy.databases to java.sql;
}