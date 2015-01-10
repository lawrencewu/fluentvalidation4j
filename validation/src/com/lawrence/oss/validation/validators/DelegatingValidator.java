package com.lawrence.oss.validation.validators;

import com.lawrence.oss.validation.Predicate;
import com.lawrence.oss.validation.resource.IStringSource;
import com.lawrence.oss.validation.results.ValidationFailure;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by z_wu on 2014/12/17.
 */
public class DelegatingValidator implements IPropertyValidator{
    private boolean condition;
    private IStringSource errorMessageSource;
    private String errorCode;
    private boolean supportsStandaloneValidation;

    private IPropertyValidator innerValidator;
    public DelegatingValidator(boolean condition, IPropertyValidator innerValidator) {
        this.condition = condition;
        this.innerValidator = innerValidator;
    }

    @Override
    public List<ValidationFailure> validate(PropertyValidatorContext context) {
        if(condition)
            return innerValidator.validate(context);
        return new ArrayList<>();
    }

    public IPropertyValidator getInnerValidator() {
        return innerValidator;
    }

    public void setInnerValidator(IPropertyValidator innerValidator) {
        this.innerValidator = innerValidator;
    }
    public IStringSource getErrorMessageSource() {
        return errorMessageSource;
    }

    public void setErrorMessageSource(IStringSource errorMessageSource) {
        this.errorMessageSource = errorMessageSource;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public boolean isSupportsStandaloneValidation() {
        return supportsStandaloneValidation;
    }

    public void setSupportsStandaloneValidation(boolean supportsStandaloneValidation) {
        this.supportsStandaloneValidation = supportsStandaloneValidation;
    }
}
