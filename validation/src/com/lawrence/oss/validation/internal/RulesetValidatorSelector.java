package com.lawrence.oss.validation.internal;

import com.lawrence.oss.validation.IValidationRule;
import com.lawrence.oss.validation.ValidationContext;

/**
 * Created by z_wu on 2014/12/22.
 */
public class RulesetValidatorSelector implements IValidatorSelector{
    String[] ruleSetToExecute;
    public RulesetValidatorSelector(String ...ruleSetToExecute){
        this.ruleSetToExecute = ruleSetToExecute;
    }

    @Override
    public boolean canExecute(IValidationRule rule, String propertyPath, ValidationContext context) {
        if(isNullOrEmpty(rule.getRuleSet()) && ruleSetToExecute.length ==0)
            return true;
        if(!isNullOrEmpty(rule.getRuleSet()) && ruleSetToExecute.length >0 && contains(rule.getRuleSet()))
            return true;
        if(contains("*")) return true;
        return false;
    }

    private boolean contains(String ruleSet){
        for (String rule : ruleSetToExecute){
            if(rule.contains("*")) return true;
            if(rule.compareToIgnoreCase(ruleSet) == 0)
                return true;
        }
        return false;
    }

    private boolean isNullOrEmpty(String ruleSet){
       return  ruleSet == null || ruleSet.isEmpty();
    }
}
