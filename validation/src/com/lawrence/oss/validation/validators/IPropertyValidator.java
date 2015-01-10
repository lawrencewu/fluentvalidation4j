package com.lawrence.oss.validation.validators;

import com.lawrence.oss.validation.resource.IStringSource;
import com.lawrence.oss.validation.results.ValidationFailure;

import java.util.List;

/**
 * Created by z_wu on 2014/12/4.
 */
public interface IPropertyValidator {
   public List<ValidationFailure> validate(PropertyValidatorContext context);
}
