package com.lawrence.oss.validation;

/**
 * Created by z_wu on 2014/12/8.
 * Specifies where a When/Unless condition should be applied
 */
public enum ApplyConditionTo {
    /**
     * Applies the condition to all validators declared so far in the chain.
     */
    AllValidators,
    /**
     * Applies the condition to the current validator only.
     */
    CurrentValidator;
}
