package com.lawrence.oss.validation.validators;

import com.lawrence.oss.validation.IValidator;
import com.lawrence.oss.validation.ValidationContext;
import com.lawrence.oss.validation.results.ValidationFailure;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by z_wu on 2014/12/19.
 */
public class ChildValidatorAdaptor implements IPropertyValidator {
    final IValidator validator;
    public ChildValidatorAdaptor(IValidator validator){
        this.validator = validator;
    }
    @Override
    public List<ValidationFailure> validate(PropertyValidatorContext context) {
        if(context.propertyRule.member == null)
            throw new RuntimeException("Nested validators can only be used with Member Expressions.");
        Object instanceToValidate = context.getPropertyValue();
        if(instanceToValidate == null)
            return new ArrayList<>();
        ValidationContext newContext = createNewValidationContextForChildValidator(instanceToValidate, context);
        return validator.validate(newContext).errors();
    }

    public boolean supportsStandaloneValidation() {
        return false;
    }

    protected IValidator  getValidator(PropertyValidatorContext context){
        return validator;
    }

    protected ValidationContext createNewValidationContextForChildValidator(Object instanceToValidate, PropertyValidatorContext context) {
        ValidationContext newContext = context.parentContext.cloneForChildValidator(instanceToValidate);
        newContext.getPropertyChain().add(context.propertyRule.member);
        return newContext;
    }
}
