package com.lawrence.oss.validation;

/**
 * Created by z_wu on 2014/12/8.
 * Specifies how rules should cascade when one fails.
 */
public enum CascadeMode {
    /**
     * When a rule fails, execution continues to the next rule.
     */
    Continue,
    /**
     * When a rule fails, validation is stopped and all other rules in the chain will not be executed.
     */
    StopOnFirstFailure;
}
