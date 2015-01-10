package com.lawrence.oss.validation.validators;

import com.lawrence.oss.validation.ValidationErrors;
import com.lawrence.oss.validation.resource.Messages;
import com.lawrence.oss.validation.util.Utils;

import java.lang.reflect.Member;

/**
 * Created by z_wu on 2014/12/18.
 */
public class GreaterThanOrEqualValidator extends AbstractComparisonValidator {
    public GreaterThanOrEqualValidator(Comparable value){
        super(value, Messages.greaterthanorequal_error, ValidationErrors.GreaterThanOrEqual);
    }

    public GreaterThanOrEqualValidator(Class<?> clazz, String property){
        super(clazz, property, Messages.greaterthanorequal_error, ValidationErrors.GreaterThanOrEqual);
    }

    @Override
    public boolean isValid(Comparable value, Comparable valueToCompare) {
        return value.compareTo(valueToCompare) >= 0;
    }

    @Override
    public Comparison getComparison() {
        return Comparison.greaterThanOrEqual;
    }
}
