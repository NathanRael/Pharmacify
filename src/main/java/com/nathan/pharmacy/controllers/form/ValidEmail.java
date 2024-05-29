package com.nathan.pharmacy.controllers.form;

import com.nathan.pharmacy.interfaces.FieldValidator;
import javafx.scene.control.TextField;

public class ValidEmail implements FieldValidator {

    @Override
    public boolean isValidField(TextField textField) {
        String text = textField.getText();
        return text.matches("^[a-z]+[\\w-_\\.]+@[\\w-_]+\\.[\\w-_]{2,4}$");
    }
}
