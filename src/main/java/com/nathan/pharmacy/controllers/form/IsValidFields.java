package com.nathan.pharmacy.controllers.form;

import com.nathan.pharmacy.interfaces.FieldsController;
import javafx.scene.control.TextField;

public class IsValidFields {
    public IsValidFields(){};
    public static boolean isValidFields(FieldsController...fieldsController){
        for (FieldsController field : fieldsController) {
            if (!field.isValidField()) {
                return false;
            }
        }
        return true;
    }
}
