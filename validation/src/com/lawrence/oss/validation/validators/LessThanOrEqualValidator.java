package com.lawrence.oss.validation.validators;

import com.lawrence.oss.validation.ValidationErrors;
import com.lawrence.oss.validation.resource.Messages;

import java.lang.reflect.Member;

/**
 * Created by z_wu on 2014/12/18.
 */
public class LessThanOrEqualValidator extends AbstractComparisonValidator {
    public LessThanOrEqualValidator(Comparable value){
        super(value, Messages.lessthanorequal_error, ValidationErrors.LessThanOrEqual);
    }

    public LessThanOrEqualValidator(Class<?>  clazz, String property){
        super(clazz, property, Messages.lessthanorequal_error, ValidationErrors.LessThanOrEqual);
    }

    @Override
    public boolean isValid(Comparable value, Comparable valueToCompare) {
        return value.compareTo(valueToCompare) <= 0;
    }

    @Override
    public Comparison getComparison() {
        return Comparison.lessThanOrEqual;
    }
}
