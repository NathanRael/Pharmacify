package com.nathan.pharmacy.controllers.form.classes;

import com.nathan.pharmacy.controllers.form.interfaces.FieldsController;

public class ValidPhone implements FieldsController {
    private final String[] phoneIndications = {"032", "033", "034", "038"};
    private final String field;

    public ValidPhone(String field) {
        this.field = field;
    }

    @Override
    public boolean isValidField() {
        boolean containNumber = false;
        for (String phoneIndication : phoneIndications ){
            if (field.substring(0,3).equals(phoneIndication)){
                containNumber = true;
                break;
            }
        }

        return field.length() == 10 && containNumber;
    }
}
