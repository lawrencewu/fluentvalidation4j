package com.lawrence.oss.validation.validators;

import com.lawrence.oss.validation.ValidationErrors;
import com.lawrence.oss.validation.resource.Messages;
import com.lawrence.oss.validation.util.Utils;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.util.Comparator;

/**
 * Created by z_wu on 2014/12/17.
 * TODO: equal validator for property
 */
public class EqualValidator extends PropertyValidator implements IComparisonValidator {
    private Member memberToCompare;
    private Object valueToCompare;
    private Comparator comparator;
    public EqualValidator(Object valueToCompare, Comparator comparator){
        super(Messages.equal_error, ValidationErrors.Equal);
        this.valueToCompare = valueToCompare;
        this.comparator = comparator;
    }

    public EqualValidator(Class<?> clazz,String property, Comparator comparator){
        super(Messages.equal_error, ValidationErrors.Equal);
        this.memberToCompare = Utils.getMember(clazz, property);
        this.comparator = comparator;
    }



    @Override
    protected boolean isValid(PropertyValidatorContext context) {
        Object comparisonValue = getComparisonValue(context);
        boolean result = compare(comparisonValue, context.getPropertyValue());
        if(!result){
            context.messageFormatter().appendArgument("propertyValue", valueToCompare);
            return false;
        }
        return true;
    }

    private Object getComparisonValue(PropertyValidatorContext context) {
        return  Utils.getComparisonValue(context, memberToCompare, valueToCompare);
    }

    @Override
    public Comparison getComparison() {
        return Comparison.equal;
    }

    @Override
    public Member getMemberToCompare() {
        return memberToCompare;
    }

    @Override
    public Object getValueToCompare() {
        return valueToCompare;
    }

    protected boolean compare(Object comparisonValue, Object propertyValue) {
        if(comparator != null) {
            return comparator.compare(comparisonValue, propertyValue) == 0;
        }
        return comparisonValue.equals(propertyValue);
    }
}
