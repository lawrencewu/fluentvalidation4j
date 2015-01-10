package com.lawrence.oss.validation.resource;

import java.lang.reflect.Type;

/**
 * Created by z_wu on 2014/12/5.
 */
public interface IStringSource {

    /**
     * The name of the resource if localized
     */
    public String getResourceName();
    /**
     * Construct the error message template
     */
    public String getString();

    /**
     * The type of the resource provider if localized
     * @return Type
     */
    public Type getResourceType();
}
