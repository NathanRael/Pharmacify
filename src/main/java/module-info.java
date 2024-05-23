module com.nathan.pharmacy {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires de.jensd.fx.glyphs.fontawesome;
    requires de.jensd.fx.glyphs.commons;

    opens com.nathan.pharmacy to javafx.fxml;
    exports com.nathan.pharmacy;
    exports com.nathan.pharmacy.views;
    opens com.nathan.pharmacy.controllers to javafx.fxml;
    opens com.nathan.pharmacy.controllers.auth to javafx.fxml;
    opens com.nathan.pharmacy.controllers.components to javafx.fxml;
    opens com.nathan.pharmacy.views to javafx.fxml;
    opens com.nathan.pharmacy.databases to java.sql;
    opens com.nathan.pharmacy.controllers.user to javafx.fxml;
    opens com.nathan.pharmacy.controllers.dashboard to javafx.fxml;
}