package com.lawrence.oss.validation.validators;

import com.lawrence.oss.validation.util.Utils;
import com.lawrence.oss.validation.resource.IStringSource;
import com.lawrence.oss.validation.resource.StaticStringSource;
import com.lawrence.oss.validation.results.ValidationFailure;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by z_wu on 2014/12/11.
 * //TODO:to deal with custom format
 */
public abstract class PropertyValidator implements IPropertyValidator {
    private IStringSource errorSource;
    private String errorCode;
    public List<Object> customFormatArgs = new ArrayList<>();
    protected PropertyValidator(String errorMessage, String errorCode) {
        this.errorSource = new StaticStringSource(errorMessage);
        this.errorCode = errorCode;
    }

    protected PropertyValidator(String errorMessage) {
        errorSource = new StaticStringSource(errorMessage);
    }

    protected abstract boolean isValid(PropertyValidatorContext context);

    @Override
    public List<ValidationFailure> validate(PropertyValidatorContext context) {
        List<ValidationFailure> failures = new ArrayList<>();
        context.messageFormatter().appendPropertyName(context.propertyDescription());
        if(!isValid(context)) {
            failures.add(createValidationError(context));
        }
        return failures;
    }

    public ValidationFailure createValidationError(PropertyValidatorContext context){
        context.messageFormatter().appendAdditionalArguments(customFormatArgs.toArray());
        String error = context.messageFormatter().buildMessage(errorSource.getString());
        ValidationFailure failure = new ValidationFailure(context.propertyName, error, context.getPropertyValue());
        return failure;
    }

    public IStringSource getErrorSource() {
        return errorSource;
    }
    public void setErrorSource(IStringSource errorSource) {
        Utils.checkArgument(errorSource, "Error source should not be null for setErrorSource calling");
        this.errorSource = errorSource;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        Utils.checkArgument(errorCode, "Error code should not be null for setErrorCode calling");
        this.errorCode = errorCode;
    }
}
