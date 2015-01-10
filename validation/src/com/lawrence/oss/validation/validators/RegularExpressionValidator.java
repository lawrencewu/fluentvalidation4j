package com.lawrence.oss.validation.validators;

import com.lawrence.oss.validation.ValidationErrors;
import com.lawrence.oss.validation.resource.Messages;
import java.util.regex.Pattern;

/**
 * Created by z_wu on 2014/12/19.
 */
public class RegularExpressionValidator extends PropertyValidator{
    final String expression;
    public RegularExpressionValidator(String expression){
        super(Messages.regex_error, ValidationErrors.RegularExpression);
        this.expression = expression;
    }

    @Override
    protected boolean isValid(PropertyValidatorContext context) {
        final Pattern pattern = Pattern.compile(expression);
        Object result = context.getPropertyValue();
        if(result != null && !pattern.matcher((String) result).matches()){
            return false;
        }
        return true;
    }

}
