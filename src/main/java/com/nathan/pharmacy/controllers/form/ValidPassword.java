package com.nathan.pharmacy.controllers.form;

import com.nathan.pharmacy.interfaces.FieldValidator;
import javafx.scene.control.TextField;

public class ValidPassword  implements FieldValidator {

    @Override
    public boolean isValidField(TextField textField) {
        String password = textField.getText();
        return password != null && password.trim().length() >= 8;
    }
}
