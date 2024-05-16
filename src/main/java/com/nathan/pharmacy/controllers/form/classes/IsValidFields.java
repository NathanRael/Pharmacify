package com.nathan.pharmacy.controllers.form.classes;

import com.nathan.pharmacy.controllers.form.interfaces.FieldsController;

public class IsValidFields {
    public IsValidFields(){};
    public static boolean isValidFields(FieldsController...fieldsController){
        boolean isAllFieldValid = false;
        for (FieldsController field : fieldsController){
            if (!field.isValidField()){
                isAllFieldValid = false;
                break;
            }
            isAllFieldValid = true;
        }
        return isAllFieldValid;
    }
}
