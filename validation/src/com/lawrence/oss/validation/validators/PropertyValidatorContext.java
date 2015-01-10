package com.lawrence.oss.validation.validators;

import com.lawrence.oss.validation.ValidationContext;
import com.lawrence.oss.validation.internal.MessageFormatter;
import com.lawrence.oss.validation.internal.PropertyRule;
import java.lang.reflect.Field;

/**
 * Created by z_wu on 2014/12/8.
 */
public class PropertyValidatorContext {
    private final MessageFormatter messageFormatter = new MessageFormatter();
    private boolean propertyValueSet;
    private Object propertyValue;
    public ValidationContext parentContext;
    public PropertyRule propertyRule;
    public String propertyName;
    private int count;
    public PropertyValidatorContext(ValidationContext parentContext, PropertyRule rule, String propertyName)
    {
        this.parentContext = parentContext;
        this.propertyRule = rule;
        this.propertyName = propertyName;
    }
    public String propertyDescription(){
        return propertyRule.getDisplayName();
    }
    public Object instance(){
        return parentContext.instanceToValidate();
    }

    public MessageFormatter messageFormatter(){
        return messageFormatter;
    }
    public Object getPropertyValue(){
        if(!propertyValueSet) {
            propertyValue = getValue();
            propertyValueSet = true;
        }
          return propertyValue;
    }
    public void setPropertyValue(Object propertyValue){
        this.propertyValueSet = true;
        this.propertyValue = propertyValue;
    }

    /**
     * used to get propagated properties like "address.line1"
     * e.g.
     * TestValidator validator = new TestValidator();
     * validator.ruleFor(Person.class, "address.lin1").notNull();
     */
    private Object getValue() {
        Object value = null;
        String[] props = propertyName.split("\\.");
        Class<?> clazz = instance().getClass();
        Object instance = instance();
        boolean exist;
        while (props.length > 0 && count < props.length) {
            try {
                Field field = clazz.getDeclaredField(props[count]);
                field.setAccessible(true);
                value = field.get(instance);
                if(value != null) {
                    clazz = value.getClass();
                    instance = value;
                }
                exist = true;
            } catch (NoSuchFieldException | IllegalAccessException ex) {
                exist = false;
            }
            if(!exist)
                break;
            count ++;
        }
        return value;
    }
}
