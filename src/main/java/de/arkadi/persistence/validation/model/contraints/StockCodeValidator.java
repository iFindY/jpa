package de.arkadi.persistence.validation.model.contraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * Source code github.com/readlearncode
 *
 * @author Alex Theedom www.readlearncode.com
 * @version 1.0
 */
//TIP String is the type of the field you want to annotate with  the StockCode annotation
public class StockCodeValidator implements ConstraintValidator<StockCode, String> {

    private int size;

    @Override
    public void initialize(StockCode stockCode) {
        this.size = stockCode.size();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        // Is not null?
        if (value == null) {
            return false;
        }

        // Is length = size?
        if (value.length() != size) {
            return false;
        }

        // Matches pattern?
        return Pattern.compile("^[A-Z]*$").matcher(value).matches();
    }
}