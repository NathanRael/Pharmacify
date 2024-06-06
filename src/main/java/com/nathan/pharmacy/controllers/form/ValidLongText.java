package com.nathan.pharmacy.controllers.form;

import com.nathan.pharmacy.interfaces.FieldValidator;
import javafx.scene.control.TextField;

public class ValidLongText implements FieldValidator {
    @Override
    public boolean isValidField(TextField textField) {
        String text = textField.getText();
        if (text == null || text.isEmpty()) {
            return false; // Return false if the text is null or empty
        }
        return text.trim().length() >= 5;
    }
}
