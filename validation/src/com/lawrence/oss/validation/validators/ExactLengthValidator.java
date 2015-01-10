package com.lawrence.oss.validation.validators;

import com.lawrence.oss.validation.ValidationErrors;
import com.lawrence.oss.validation.resource.Messages;

/**
 * Created by z_wu on 2014/12/19.
 */
public class ExactLengthValidator extends LengthValidator {
    public ExactLengthValidator(int length){
        super(length, length, Messages.exact_length_error, ValidationErrors.ExactLength);
    }
}
