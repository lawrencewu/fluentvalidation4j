package com.lawrence.oss.validation.validators;

import com.lawrence.oss.validation.util.Utils;

import java.lang.reflect.Member;

/**
 * Created by z_wu on 2014/12/18.
 */
public abstract class AbstractComparisonValidator extends  PropertyValidator implements IComparisonValidator{

    public abstract boolean isValid(Comparable value, Comparable valueToCompare);
    private Member memberToCompare;
    private Object valueToCompare;
    protected AbstractComparisonValidator(Comparable value, String errorMessageSelector, String errorCode){
        super(errorMessageSelector, errorCode);
        Utils.checkArgument(value, "Comparable value should not be null for AbstractComparisonValidator initialization");
        valueToCompare = value;
    }

    protected AbstractComparisonValidator(Class<?> clazz, String property, String errorMessageSelector, String errorCode){
        super(errorMessageSelector, errorCode);
        Member member = Utils.getMember(clazz, property);
        this.memberToCompare = member;
    }

    @Override
    protected final boolean isValid(PropertyValidatorContext context) {
        if (context.getPropertyValue() == null) {
            // If we're working with a nullable type then this rule should not be applied.
            // If you want to ensure that it's never null then a NotNull rule should also be applied.
            return true;
        }
        Comparable value = (Comparable)Utils.getComparisonValue(context, memberToCompare, valueToCompare);
        if (!isValid((Comparable) context.getPropertyValue(), value)) {
            context.messageFormatter().appendArgument("comparisonValue", value);
            return false;
        }
        return true;
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
