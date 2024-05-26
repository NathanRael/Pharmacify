package com.nathan.pharmacy.controllers.form;

import com.nathan.pharmacy.interfaces.FieldValidator;
import javafx.scene.control.TextField;

public class ValidPhone implements FieldValidator {
    private final String[] phoneIndications = {"032", "033", "034", "038"};

    @Override
    public boolean isValidField(TextField textField) {
        String text = textField.getText();
        if (text.isEmpty() || text.trim().length() != 10) return false;
        for (String phoneIndication : phoneIndications ){
            if (text.substring(0,3).equals(phoneIndication)){
                return true;
            }
        }
        return false;

    }
}
