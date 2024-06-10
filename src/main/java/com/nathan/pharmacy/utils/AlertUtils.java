package com.nathan.pharmacy.utils;

import com.nathan.pharmacy.contstants.AlertType;
import javafx.scene.control.Alert;

public class AlertUtils {
    public  static void showAlert(String message, AlertType alertType){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        switch (alertType){
            case ERROR -> {
                alert.setTitle("Erreur");
                alert.setAlertType(Alert.AlertType.ERROR);
            }
            case SUCCESS -> {
                alert.setTitle("Succes");
                alert.setAlertType(Alert.AlertType.INFORMATION);
            }
        }

        alert.setContentText(message);
        alert.showAndWait();
    }
}
