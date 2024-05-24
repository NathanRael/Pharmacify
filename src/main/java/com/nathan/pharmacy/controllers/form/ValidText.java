package com.nathan.pharmacy.controllers.form;

import com.nathan.pharmacy.interfaces.FieldsController;

public class ValidText implements FieldsController {
    private final String field;
    public ValidText(String field){
        this.field = field;
    }
    @Override
    public boolean isValidField() {
        return field.trim().length() >= 6;
    }
}
