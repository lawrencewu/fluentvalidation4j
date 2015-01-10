package com.lawrence.oss.validation.resource;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * Created by z_wu on 2014/12/8.
 */
public class LocalizedStringSource implements  IStringSource{
    final Class resourceType;
    final String resourceName;

    public LocalizedStringSource(Class resourceType, String resourceName) {
        this.resourceType = resourceType;
        this.resourceName = resourceName;
    }
    public String getString() {
        return "";
    }

    public Class getResourceType(){
        return resourceType;
    }

    public String getResourceName(){
        return resourceName;
    }
}
