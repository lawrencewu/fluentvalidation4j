package com.lawrence.oss.validation.validators;

import com.lawrence.oss.validation.ValidationErrors;
import com.lawrence.oss.validation.resource.Messages;
import com.google.common.base.Converter;
import javafx.beans.binding.ObjectExpression;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.collections.IteratorUtils;

import java.util.Iterator;

/**
 * Created by z_wu on 2014/12/11.
 */
public class NotEmptyValidator extends PropertyValidator implements  IPropertyValidator{
    final Object defaultValueForType;
    public NotEmptyValidator(Object defaultValueForType){
        super(Messages.notempty_error, ValidationErrors.NotEmpty);
        this.defaultValueForType = defaultValueForType;
    }
    @Override
    protected boolean isValid(PropertyValidatorContext context) {
        if(context.getPropertyValue() == null
                ||isInvalidString(context.getPropertyValue())
                ||isEmptyCollection(context.getPropertyValue())
                ||(context.getPropertyValue() != null ? context.getPropertyValue().equals(defaultValueForType) : true)) {
            return false;
        }
        return true;
    }

    boolean isInvalidString(Object value) {
        if (value instanceof String) {
            return isNullOrWhitespace((String)value);
        }
        return false;
    }

    boolean isNullOrWhitespace(String value){
        if(value != null) {
            char[] chars = ((String) value).toCharArray();
            for (char c : chars) {
                if (!Character.isWhitespace(c)) {
                    return false;
                }
            }
        }
        return true;
    }

    boolean isEmptyCollection(Object propertyValue){
        if(propertyValue == null) return false;
        if(propertyValue instanceof Iterable) {
            Iterable<?> collection = (Iterable) propertyValue;
            return collection != null && !collection.iterator().hasNext();
        }
        return false;
    }
}