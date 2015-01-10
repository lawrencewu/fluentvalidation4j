package com.lawrence.oss.validation.internal;

import com.lawrence.oss.validation.*;
import com.lawrence.oss.validation.util.Utils;
import com.lawrence.oss.validation.resource.IStringSource;
import com.lawrence.oss.validation.resource.Messages;
import com.lawrence.oss.validation.resource.StaticStringSource;
import com.lawrence.oss.validation.results.ValidationFailure;
import com.lawrence.oss.validation.validators.DelegatingValidator;
import com.lawrence.oss.validation.validators.IPropertyValidator;
import com.lawrence.oss.validation.validators.PropertyValidatorContext;

import java.lang.reflect.Member;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by z_wu on 2014/12/8.
 * Defines a rule associated with a property.
 * TODO: property display name
 *       assume property display name same as the property name
 */
public class PropertyRule implements IValidationRule{
    private final List<IPropertyValidator> validators = new ArrayList<>();
    public Member member;
    private IStringSource displayName;
    private String ruleSet;
    public Class<?> clazz;
    public IPropertyValidator currentValidator;
    public static Type resourceProviderType = Messages.class;
    public String propertyName;

    public PropertyRule(Class<?> clazz, String propertyName,Member m, CascadeMode c){
        this.member = m;
        this.propertyName = propertyName;
        ValidatorOptions.cascadeMode = c;
        this.clazz = clazz;
        this.displayName = initDisplayName();
    }

    public static PropertyRule create(Class<?> clazz, String propertyName, CascadeMode c){
        Member m = Utils.getMember(clazz, propertyName);
        return new PropertyRule(clazz, propertyName, m, c);
    }

    public void addValidator(IPropertyValidator validator) {
        currentValidator = validator;
        validators.add(validator);
    }

    public  void replaceValidator(IPropertyValidator orignal, IPropertyValidator newValidator){
        int index = validators.indexOf(orignal);
        if(index > -1){
            validators.set(index, newValidator);
            if(currentValidator.equals(orignal))
                currentValidator = newValidator;
        }
    }

    private  IStringSource initDisplayName(){
        return new StaticStringSource(propertyName);
    }

    public String getDisplayName() {
        if (displayName != null) {
            return displayName.getString();
        }
        return propertyName;
    }

    @Override
    public List<IPropertyValidator> validators() {
        return validators;
    }

    @Override
    public List<ValidationFailure> valiadate(ValidationContext context)  {
        ensureValidPropertyName();
        CascadeMode c = getCascadeMode();
        boolean hasAnyFailure = false;
        List<ValidationFailure> results = new ArrayList<>();
        for (IPropertyValidator validator : validators){
            if(!context.selector().canExecute(this, propertyName, context)){
                return new ArrayList<>();
            }
            results.addAll(invokePropertyValidator(context, validator, propertyName));
            boolean hasFailure = false;
            for (ValidationFailure result : results) {
                    hasAnyFailure = true;
                    hasFailure = true;
            }
            if (c == CascadeMode.StopOnFirstFailure && hasFailure) {
                break;
            }
        }
        if(hasAnyFailure){
           //output the result without exception, callback can apply here if needed
           //throw new ValidationException(String.format("Validation to %s fails",propertyName));
        }
        return  results;
    }

    protected List<ValidationFailure> invokePropertyValidator(ValidationContext context, IPropertyValidator validator, String propertyName) {
        PropertyValidatorContext  propertyContext = new PropertyValidatorContext(context, this, propertyName);
        return validator.validate(propertyContext);
    }

    public CascadeMode getCascadeMode(){
        return ValidatorOptions.cascadeMode;
    }

    public void setCascadeMode(CascadeMode cascadeMode){
        ValidatorOptions.cascadeMode = cascadeMode;
    }

    private void ensureValidPropertyName() {
        if (propertyName == null && displayName == null) {
            throw new RuntimeException("Property name could not be null. Please specify either a custom property name by calling 'WithName'.");
        }
    }

    private String buildPropertyName(ValidationContext context) {
        return context.getPropertyChain().buildPropertyName(propertyName);
    }

    public void applyCondition(boolean predicate, ApplyConditionTo applyConditionTo){
        if(applyConditionTo == ApplyConditionTo.AllValidators){
            for(IPropertyValidator validator : validators){
                DelegatingValidator wrappedValidator = new DelegatingValidator(predicate, validator);
                replaceValidator(validator, wrappedValidator);
            }
        }else{
            DelegatingValidator wrappedValidator = new DelegatingValidator(predicate, currentValidator);
            replaceValidator(currentValidator, wrappedValidator);
        }
    }

    @Override
    public String getRuleSet() {
        return ruleSet;
    }

    public void setRuleSet(String ruleSet) {
        this.ruleSet = ruleSet;
    }
}
