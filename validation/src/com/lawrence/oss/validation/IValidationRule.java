package com.lawrence.oss.validation;

import com.lawrence.oss.validation.exception.ValidationException;
import com.lawrence.oss.validation.results.ValidationFailure;
import com.lawrence.oss.validation.validators.IPropertyValidator;

import java.util.List;

/**
 * Defines a rule associated with a property which can have multiple validators
 */
public interface IValidationRule {

    /**
     * The validator that are grouped under this rule
     * @return
     */
    public List<IPropertyValidator> validators();
    /**
     * Name of the rule-set to which this rule belongs
     * @return
     */
    public String getRuleSet();
    /**
     * Performs validation using a validation context and returns a collection of Validation Failures
     */
    public List<ValidationFailure> valiadate(ValidationContext context);

}
