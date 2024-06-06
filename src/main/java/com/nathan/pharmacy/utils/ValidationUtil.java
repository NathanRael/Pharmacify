package com.nathan.pharmacy.utils;

import com.nathan.pharmacy.interfaces.FieldValidator;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class ValidationUtil {
    public static boolean validTextField(TextField textField, FieldValidator fieldValidator){
        return fieldValidator.isValidField(textField);
    }

    public static String purifyValue(Object value){
        if (value instanceof String){
            return ((String) value).replaceAll("'|;|(--)", "`");
        }
        return value.toString();
    }

    public static boolean validateSelect(ChoiceBox choiceBox){
        return false;
    }
}
