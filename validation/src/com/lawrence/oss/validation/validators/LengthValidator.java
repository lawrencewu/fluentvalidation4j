package com.lawrence.oss.validation.validators;

import com.lawrence.oss.validation.ValidationErrors;
import com.lawrence.oss.validation.exception.ArgumentOutOfRangeException;
import com.lawrence.oss.validation.resource.Messages;
/**
 * Created by z_wu on 2014/12/19.
 */
public class LengthValidator extends PropertyValidator{
   public int min;
   public int max;
    public LengthValidator(int min, int max){
        this(min,max,Messages.length_error, ValidationErrors.Length);
    }
    public LengthValidator(int min, int max, String errorMessage, String messageCode){
        super(errorMessage, messageCode);
        this.min = min;
        this.max = max;
        if(max < min)
            throw new ArgumentOutOfRangeException("'Max' should be larger than min.");
    }

    @Override
    protected boolean isValid(PropertyValidatorContext context) {
        int length = context.getPropertyValue() == null ? 0 : context.getPropertyValue().toString().length();
        if(length < min || length > max){
            context.messageFormatter().appendArgument("minLength", min)
                    .appendArgument("maxLength", max)
                    .appendArgument("totalLength", length);
            return false;
        }
        return true;
    }
}
