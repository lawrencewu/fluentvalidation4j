package com.lawrence.oss.validation.validators;

import com.lawrence.oss.validation.ValidationErrors;
import com.lawrence.oss.validation.resource.Messages;

import java.lang.reflect.Member;

/**
 * Created by z_wu on 2014/12/18.
 */
public class LessThanValidator extends AbstractComparisonValidator {
    public LessThanValidator(Comparable value){
        super(value, Messages.lessthan_error, ValidationErrors.LessThan);
    }
    public LessThanValidator(Class<?>  clazz, String property){
        super(clazz,property, Messages.lessthan_error, ValidationErrors.LessThan);
    }

    @Override
    public boolean isValid(Comparable value, Comparable valueToCompare) {
        return  value.compareTo(valueToCompare) < 0;
    }

    @Override
    public Comparison getComparison() {
        return Comparison.lessThan;
    }

}
