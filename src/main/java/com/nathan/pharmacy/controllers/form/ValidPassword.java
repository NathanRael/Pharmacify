package com.nathan.pharmacy.controllers.form;

import com.nathan.pharmacy.interfaces.Validator;
import javafx.scene.control.TextField;

public class ValidPassword  implements Validator {

    @Override
    public boolean isValidField(TextField textField) {
        String password = textField.getText();
        return password != null && password.trim().length() >= 8;
    }
}
