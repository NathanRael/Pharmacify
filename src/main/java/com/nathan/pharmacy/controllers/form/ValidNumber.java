package com.nathan.pharmacy.controllers.form;

import com.nathan.pharmacy.contstants.AcceptedNumber;
import com.nathan.pharmacy.interfaces.FieldValidator;
import javafx.scene.control.TextField;

public class ValidNumber implements FieldValidator {

    @Override
    public boolean isValidField(TextField textField) {
        String text = textField.getText();
        if (text.isEmpty()) {
            return false;
        }
        try {
            return Double.parseDouble(text) > 0;
        }catch (Exception ex){
            return false;
        }
    }
}
