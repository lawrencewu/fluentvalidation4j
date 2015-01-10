package com.lawrence.oss.validation.validators;

import com.lawrence.oss.validation.util.Utils;
import com.lawrence.oss.validation.ValidationErrors;
import com.lawrence.oss.validation.resource.Messages;

import java.lang.reflect.Member;
import java.util.Comparator;

/**
 * Created by z_wu on 2014/12/19.
 */
public class NotEqualValidator extends PropertyValidator implements IComparisonValidator {
    static Comparator comparator ;
    private Member memberToCompare;
    private Object valueToCompare;

    public NotEqualValidator(Class<?> clazz, String property){
        super(Messages.notequal_error, ValidationErrors.NotEqual);
        this.memberToCompare = Utils.getMember(clazz, property);
    }

    public NotEqualValidator(Class<?> clazz, String property, Comparator comparator){
        super(Messages.notequal_error, ValidationErrors.NotEqual);
        this.memberToCompare = Utils.getMember(clazz, property);;
        this.comparator = comparator;
    }
    public NotEqualValidator(Object comparisonValue){
        super(Messages.notequal_error, ValidationErrors.NotEqual);
        this.valueToCompare = comparisonValue;
    }

    public NotEqualValidator(Object comparisonValue, Comparator comparator){
        super(Messages.notequal_error, ValidationErrors.NotEqual);
        this.valueToCompare = comparisonValue;
        this.comparator = comparator;
    }

    @Override
    protected boolean isValid(PropertyValidatorContext context) {
        Object  comparisonValue = Utils.getComparisonValue(context, memberToCompare, valueToCompare);
        boolean result = !compare(comparisonValue, context.getPropertyValue());
        if(!result){
            context.messageFormatter().appendArgument("propertyValue", context.getPropertyValue());
            return false;
        }
        return true;
    }

    @Override
    public Comparison getComparison() {
        return Comparison.notEqual;
    }

    protected boolean compare(Object comparisonValue, Object propertyValue){
        if(comparator != null){
            return comparator.compare(comparisonValue, propertyValue) == 0;
        }
        return comparisonValue.equals(propertyValue);
    }

    @Override
    public Member getMemberToCompare() {
        return memberToCompare;
    }

    @Override
    public Object getValueToCompare() {
        return valueToCompare;
    }
}
