package com.lawrence.oss.validation;

import com.lawrence.oss.validation.internal.PropertyRule;
import com.lawrence.oss.validation.validators.IPropertyValidator;

import java.util.*;

/**
 * Created by z_wu on 2014/12/16.
 * Used for providing metadata about a validator.
 */
public class ValidatorDescriptor<T> implements IValidatorDescriptor {

    private Iterable<IValidationRule> rules;

    public ValidatorDescriptor(Iterable<IValidationRule> ruleBuilders){
         this.rules = ruleBuilders;
    }

    @Override
    public Iterable<IPropertyValidator> getValidatorsForMember(String name) {
        if(name == null)
            return  null;
        Map<String, Iterable<IPropertyValidator>> result = getMembersWithValidators();
        Iterable<IPropertyValidator> emptyResult = new ArrayList<>();
        return result.get(name) == null ? emptyResult : result.get(name);
    }

    @Override
    public Iterable<IValidationRule> getRulesForMember(String name) {
        List<IValidationRule> result = new ArrayList<>();
        for (IValidationRule rule : rules){
            if(((PropertyRule)rule).getDisplayName().equals(name))
            {
                result.add(rule);
            }
        }
        return result;
    }

    @Override
    public Map<String, Iterable<IPropertyValidator>> getMembersWithValidators() {
        Map<String, Iterable<IPropertyValidator>> result = new HashMap<>();
        for(IValidationRule  rule : rules){
            if(((PropertyRule)rule).member == null)
                continue;
            result.put(((PropertyRule) rule).member.getName(), rule.validators());
        }
        return result;
    }

    @Override
    public String getName(String property) {
        List<String> names = new ArrayList<>();
        for (IValidationRule rule : rules){
            String name = ((PropertyRule)rule).getDisplayName();
            if(name.equals(property)) {
                return  name;
            }
        }
        return null;
    }

    public Iterable<IValidationRule> getRules() {
        return rules;
    }

    public void setRules(Iterable<IValidationRule> rules) {
        rules = rules;
    }
}
