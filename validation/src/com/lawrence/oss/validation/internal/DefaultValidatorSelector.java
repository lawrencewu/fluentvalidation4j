package com.lawrence.oss.validation.internal;

import com.lawrence.oss.validation.IValidationRule;
import com.lawrence.oss.validation.ValidationContext;

/**
 * Default validator selector that will execute all rules that do not belong to a RuleSet.
 */
public class DefaultValidatorSelector implements IValidatorSelector{
    public boolean canExecute(IValidationRule rule, String propertyPath, ValidationContext context) {
        // By default we ignore any rules part of a RuleSet.
        if ( rule.getRuleSet() != null && !rule.getRuleSet().isEmpty()) return false;
        return true;
    }
}
