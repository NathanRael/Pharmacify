package com.nathan.pharmacy.controllers.form;

import com.nathan.pharmacy.interfaces.FieldValidator;
import javafx.scene.control.TextField;

public class ValidText implements FieldValidator {

    @Override
    public boolean isValidField(TextField textField) {
        String text = textField.getText();
        return text != null && !text.trim().isEmpty();
    }
}
