package com.nathan.pharmacy.controllers.form;

import com.nathan.pharmacy.interfaces.Validator;
import javafx.scene.control.TextField;

public class ValidNumber implements Validator {
    @Override
    public boolean isValidField(TextField textField) {
        String text = textField.getText();
        if (text.isEmpty()) {
            return false;
        }
        try {
            int number = Integer.parseInt(text);
            return number > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
