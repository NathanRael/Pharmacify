package com.nathan.pharmacy.controllers.form;

import com.nathan.pharmacy.interfaces.FieldValidator;
import javafx.scene.control.TextField;

public class ValidName implements FieldValidator {
    @Override
    public boolean isValidField(TextField textField) {
        String name = textField.getText();
        return name != null && name.matches("^\\w[èéà\\w\\d\\s_-]{4,}$");
    }
}
