package com.lawrence.oss.validation.resource;

import java.lang.reflect.Type;

/**
 * Created by z_wu on 2014/12/8.
 */
public class StaticStringSource implements IStringSource{
    private final String message;
    public StaticStringSource(String message){
        this.message = message;
    }

    public String getString(){
        return message;
    }

    public String getResourceName(){
        return null;
    }

    public Type getResourceType(){
        return null;
    }
}
