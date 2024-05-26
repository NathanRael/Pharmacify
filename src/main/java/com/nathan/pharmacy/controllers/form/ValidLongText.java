package com.nathan.pharmacy.controllers.form;

import com.nathan.pharmacy.interfaces.Validator;
import javafx.scene.control.TextField;

public class ValidLongText implements Validator {
    @Override
    public boolean isValidField(TextField textField) {
        String text = textField.getText();
        return text != null && text.trim().length() >= 5;
    }
}
