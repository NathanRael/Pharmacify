package com.nathan.pharmacy.controllers.form;

import com.nathan.pharmacy.contstants.AcceptedNumber;
import com.nathan.pharmacy.interfaces.FieldValidator;
import javafx.scene.control.TextField;

public class ValidNumber<T extends AcceptedNumber> implements FieldValidator {
    private final T type;

    public ValidNumber(T type) {
        this.type = type;
    }
    @Override
    public boolean isValidField(TextField textField) {
        String text = textField.getText();
        if (text.isEmpty()) {
            return false;
        }
        try {
            if (type == AcceptedNumber.INTEGER) {
                int number = Integer.parseInt(text);
                return number > 0;
            } else if (type == AcceptedNumber.FLOAT) {
                float number = Float.parseFloat(text);
                return number > 0;
            } else if (type == AcceptedNumber.DOUBLE) {
                double number = Double.parseDouble(text);
                return number > 0;
            } else if (type == AcceptedNumber.LONG) {
                long number = Long.parseLong(text);
                return number > 0;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return false;
    }
}