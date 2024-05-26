module com.nathan.pharmacy {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires de.jensd.fx.glyphs.fontawesome;
    requires de.jensd.fx.glyphs.commons;

    opens com.nathan.pharmacy to javafx.fxml;
    opens com.nathan.pharmacy.models to javafx.base;
    exports com.nathan.pharmacy;
    opens com.nathan.pharmacy.controllers to javafx.fxml;
    opens com.nathan.pharmacy.controllers.auth to javafx.fxml;
    opens com.nathan.pharmacy.controllers.components to javafx.fxml;
    opens com.nathan.pharmacy.controllers.dashboard to javafx.fxml;
    opens com.nathan.pharmacy.controllers.medicament to javafx.fxml;
    opens com.nathan.pharmacy.controllers.purchase to javafx.fxml;
    opens com.nathan.pharmacy.controllers.supplier to javafx.fxml;
    opens com.nathan.pharmacy.controllers.user to javafx.fxml;
    opens com.nathan.pharmacy.controllers.stock to javafx.fxml;
    opens com.nathan.pharmacy.controllers.delivery to javafx.fxml;
    opens com.nathan.pharmacy.views to javafx.fxml;
    opens com.nathan.pharmacy.test to javafx.fxml;
    opens com.nathan.pharmacy.databases to java.sql;
    opens com.nathan.pharmacy.interfaces to javafx.fxml;
}