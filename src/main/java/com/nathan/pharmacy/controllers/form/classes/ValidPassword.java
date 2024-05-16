package com.nathan.pharmacy.controllers.form.classes;

import com.nathan.pharmacy.controllers.form.interfaces.FieldsController;

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
