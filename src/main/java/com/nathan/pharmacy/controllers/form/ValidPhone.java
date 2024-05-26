package com.nathan.pharmacy.controllers.form;

import com.nathan.pharmacy.interfaces.FieldValidator;
import javafx.scene.control.TextField;

public class ValidPhone implements FieldValidator {
    private final String[] phoneIndications = {"032", "033", "034", "038"};

    @Override
    public boolean isValidField(TextField textField) {
        boolean containNumber = false;
        String text = textField.getText();
        for (String phoneIndication : phoneIndications ){
            if (text.substring(0,3).equals(phoneIndication)){
                containNumber = true;
                break;
            }
        }
        return text.length() == 10 && containNumber;
    }
}
