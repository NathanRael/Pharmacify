package com.nathan.pharmacy.controllers.form;

import com.nathan.pharmacy.interfaces.FieldsController;

public class ValidPassword implements FieldsController {
    private final String field;

    public ValidPassword(String field) {
        this.field = field;
    }

    @Override
    public boolean isValidField() {
        return field.length() >= 8;
    }
}
