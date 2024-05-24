package com.nathan.pharmacy.controllers.form;

import com.nathan.pharmacy.interfaces.FieldsController;

public class ValidLongText implements FieldsController {
    private final String longText;
    public ValidLongText(String longText){
        this.longText = longText;
    }
    @Override
    public boolean isValidField() {
        int len = longText.trim().length();
        return len >= 5 && len <= 120;
    }
}
