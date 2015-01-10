package com.lawrence.oss.validation.validators;

import com.lawrence.oss.validation.ValidationErrors;
import com.lawrence.oss.validation.resource.Messages;

/**
 * Created by z_wu on 2014/12/11.
 */
public class NotNullValidator extends PropertyValidator implements  IPropertyValidator{

    public NotNullValidator(){
        super(Messages.notnull_error, ValidationErrors.NotNull);
    }

    @Override
    protected boolean isValid(PropertyValidatorContext context) {
        if(context.getPropertyValue() == null)
            return false;
        return true;
    }
}
