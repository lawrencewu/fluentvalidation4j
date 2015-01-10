package com.lawrence.oss.validation.validators;

import com.lawrence.oss.validation.ValidationErrors;
import com.lawrence.oss.validation.internal.MessageFormatter;
import com.lawrence.oss.validation.resource.Messages;

import java.lang.reflect.Member;

/**
 * Created by z_wu on 2014/12/18.
 */
public class GreaterThanValidator extends AbstractComparisonValidator{
    public GreaterThanValidator(Comparable value){
        super(value, Messages.greaterthan_error, ValidationErrors.GreaterThan);
    }

    public GreaterThanValidator(Class<?>  clazz, String property){
        super(clazz, property, Messages.greaterthan_error, ValidationErrors.GreaterThan);
    }

    @Override
    public boolean isValid(Comparable value, Comparable valueToCompare) {
        return  value.compareTo(valueToCompare) > 0;
    }

    @Override
    public Comparison getComparison() {
        return Comparison.greaterThan;
    }
}
