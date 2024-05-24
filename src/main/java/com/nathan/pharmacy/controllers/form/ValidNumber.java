package com.nathan.pharmacy.controllers.form;

import com.nathan.pharmacy.interfaces.FieldsController;

public class ValidNumber<N extends Number> implements FieldsController {

    private final N number;
    public ValidNumber(N number){
        this.number = number;
    }
    @Override
    public boolean isValidField() {
        return number.intValue() > 0 || number.floatValue() > 0 || number.longValue() > 0 || number.doubleValue() > 0;
    }
}
