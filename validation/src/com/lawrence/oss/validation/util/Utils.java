package com.lawrence.oss.validation.util;

import com.lawrence.oss.validation.exception.ArgumentNullException;
import com.lawrence.oss.validation.validators.PropertyValidatorContext;
import java.lang.reflect.*;

/**
 * Created by z_wu on 2014/12/18.
 */
public class Utils {

    public static <T> Member getMember(T instance, String propertyName){
       return getMember(instance.getClass(), propertyName);
    }

    public static Member getMember(Class<?> clazz, String propertyName){
        checkArgument(clazz, "Class should not be null for getMemberForProperty calling");
        if(propertyName == null) return null;
        Member m ;
        try {
            m = clazz.getDeclaredField(propertyName);
        }catch (NoSuchFieldException ex){
            m = null;
        }
        return m;
    }

    public static boolean isPropertyExist(Class<?> clazz, String propertyName){
        Member m = getMember(clazz, propertyName);
        return  m != null;
    }

    public static <T> boolean isPropertyExist(T instance, String ... propertyNames){
        if(propertyNames == null || instance == null) return false;
        for(String property : propertyNames){
            if(!isPropertyExist(instance,property)){
                return false;
            }
        }
        return true;
    }

    public static <T> boolean isPropertyExist(T instance, String propertyName){
        Object value;
        String[] props = propertyName.split("\\.");
        Class<?> clazz = instance.getClass();
        Object ins = instance;
        Field field = null;
        boolean exist;
        int count = 0;
        while (props.length > 0 && count < props.length) {
            try {
                field = clazz.getDeclaredField(props[count]);
                field.setAccessible(true);
                value = field.get(ins);
                if (value != null) {
                    clazz = value.getClass();
                    ins = value;
                }
                exist = true;
            } catch (NoSuchFieldException | IllegalAccessException ex) {
                exist = false;
            }
            if(!exist)
                break;
            count ++;
        }
        return field != null;
    }

    public static void checkArgument(Object arg, String message){
        if(arg == null){
            throw new ArgumentNullException(message);
        }
    }

    public static Object getComparisonValue(PropertyValidatorContext context, Member memberToCompare, Object valueToCompare) {
        if(memberToCompare != null && context != null) {
            Object targetInstance = context.instance();
            if(targetInstance == null) return  null;
            try {
                Field field = targetInstance.getClass().getDeclaredField(memberToCompare.getName());
                if(field != null){
                    field.setAccessible(true);
                    Comparable result = (Comparable)field.get(targetInstance);
                    return result;
                }
            }catch (NoSuchFieldException | IllegalAccessException ex){
            }
        }
        return valueToCompare;
    }

    public static Object getDefaultValueForProperty(Class<?> clazz, String property){
        checkArgument(clazz, "Class should not be null for getMemberForProperty calling");
        checkArgument(clazz, "Property should not be null for getMemberForProperty calling");
        Object defaultValue;
        try{
            Field field = clazz.getDeclaredField(property);
            defaultValue = field.get(clazz.newInstance());
        }catch (NoSuchFieldException | IllegalAccessException | InstantiationException ex){
            throw new RuntimeException(String.format("Cannot find property %s for getDefaultValueForProperty calling", property));
        }
        return defaultValue;
    }
}
