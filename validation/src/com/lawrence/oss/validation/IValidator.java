package com.lawrence.oss.validation;

import com.lawrence.oss.validation.results.ValidationResult;
import java.lang.reflect.Type;

/**
 * Created by z_wu on 2014/12/4.
 * Defines a validator for a particular type.
 */
public interface IValidator<T> extends Iterable<IValidationRule>{
    /**
     * Validates the specified instance
     * @param instance
     * @return A ValidationResult containing any validation failures
     */
     public ValidationResult validate(T instance);

    /**
     * Validates the specified instance.
     * @param context
     * @return A ValidationResult object containing any validation failures.
     */
     public ValidationResult validate(ValidationContext context);

    /**
     * Creates a hook to access various meta data properties
     * @return A IValidatorDescriptor object which contains methods to access metadata
     */
     public IValidatorDescriptor createDescriptor();

    /**
     *  Checks to see whether the validator can validate objects of the specified type
     * @param type
     * @return
     */
     public boolean canValidateInstancesOfType(T type);

     public CascadeMode getCascadeMode();
}
