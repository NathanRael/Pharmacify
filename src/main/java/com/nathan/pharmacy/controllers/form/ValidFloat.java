package com.nathan.pharmacy.controllers.form;

import com.nathan.pharmacy.interfaces.FieldValidator;
import javafx.scene.control.TextField;

public class ValidFloat implements FieldValidator {
    @Override
    public boolean isValidField(TextField textField) {
        String text = textField.getText().trim();
        if (text.isEmpty()) {
            return false;
        }
        return text.matches("^\\d+\\.?\\d+$");
    }
}
