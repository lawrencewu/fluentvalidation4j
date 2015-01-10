package com.lawrence.oss.validation.validators;

/**
 * Created by z_wu on 2014/12/18.
 */
public interface IBetweenValidator extends IPropertyValidator {
    Comparable from();
    Comparable to();
}
