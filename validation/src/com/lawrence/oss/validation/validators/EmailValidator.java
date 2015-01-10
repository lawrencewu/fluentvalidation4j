package com.lawrence.oss.validation.validators;

import com.lawrence.oss.validation.ValidationErrors;
import com.lawrence.oss.validation.resource.Messages;

import java.util.regex.Pattern;

/**
 * Created by z_wu on 2014/12/19.
 */
public class EmailValidator extends PropertyValidator {

    String expression = "^(?:[\\w\\!\\#\\$\\%\\&\\'\\*\\+\\-\\/\\=\\?\\^\\`\\{\\|\\}\\~]+\\.)*[\\w\\!\\#\\$\\%\\&\\'\\*\\+\\-\\/\\=\\?\\^\\`\\{\\|\\}\\~]+@(?:(?:(?:[a-zA-Z0-9](?:[a-zA-Z0-9\\-](?!\\.)){0,61}[a-zA-Z0-9]?\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9\\-](?!$)){0,61}[a-zA-Z0-9]?)|(?:\\[(?:(?:[01]?\\d{1,2}|2[0-4]\\d|25[0-5])\\.){3}(?:[01]?\\d{1,2}|2[0-4]\\d|25[0-5])\\]))$";
    Pattern pattern ;
    public EmailValidator(){
       super(Messages.email_error, ValidationErrors.Email);
        pattern = Pattern.compile(expression);
    }

    @Override
    protected boolean isValid(PropertyValidatorContext context) {
        Object result = context.getPropertyValue();
        if(result == null) return true;
        if(!pattern.matcher((String) result).matches()){
            return false;
        }
        return true;
    }
}
