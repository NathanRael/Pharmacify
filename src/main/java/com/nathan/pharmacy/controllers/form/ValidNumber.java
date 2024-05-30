package com.nathan.pharmacy.controllers.form;

import com.nathan.pharmacy.contstants.AcceptedNumber;
import com.nathan.pharmacy.interfaces.FieldValidator;
import javafx.scene.control.TextField;

import java.util.regex.Pattern;

public class ValidNumber implements FieldValidator {

    @Override
    public boolean isValidField(TextField textField) {
        String text = textField.getText();
        if (text.isEmpty()) {
            return false;
        }
        return text.matches("^\\d+$");
    }
}
