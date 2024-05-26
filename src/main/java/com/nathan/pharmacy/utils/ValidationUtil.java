package com.nathan.pharmacy.utils;

import com.nathan.pharmacy.interfaces.Validator;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class ValidationUtil {
    public static boolean validTextField(TextField textField, Validator validator){
        return validator.isValidField(textField);
    }

    public static boolean validateSelect(ChoiceBox choiceBox){
        return false;
    }
}
