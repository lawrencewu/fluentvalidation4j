package com.lawrence.oss.validation.internal;

import java.text.MessageFormat;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by z_wu on 2014/12/8.
 */
public class MessageFormatter {
    public final String propertyName = "propertyName";
    Object[] additionalArgs;
    final Map<String, Object> placeholderValues = new HashMap<String, Object>();
    public MessageFormatter appendArgument(String name, Object value){
        placeholderValues.put(name, value);
        return this;
    }

    public MessageFormatter appendPropertyName(String name) {
        return appendArgument(propertyName, name);
    }

    public MessageFormatter appendAdditionalArguments(Object[] additionalArgs){
        this.additionalArgs = additionalArgs;
        return this;
    }

    public String buildMessage(String messagetemplate){
        String result = messagetemplate;
        for (Map.Entry<String, Object> e : placeholderValues.entrySet()){
            result = replacePlaceholderWithValue(result, e.getKey(), e.getValue());
        }
        if(shouldUseAdditionalArgs()){
            return String.format(result,additionalArgs);
        }
        return result;
    }
    private boolean shouldUseAdditionalArgs(){
       return additionalArgs != null && additionalArgs.length > 0;
    }

    private String replacePlaceholderWithValue(String template, String key, Object value) {
        String placeholder = "{" + key + "}";
        return template.replace(placeholder, value == null ? null : value.toString());
    }
}
