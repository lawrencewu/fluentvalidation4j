package com.lawrence.oss.validation;

import com.lawrence.oss.validation.exception.ValidationException;
import com.lawrence.oss.validation.util.Utils;
import com.lawrence.oss.validation.internal.*;
import com.lawrence.oss.validation.results.ValidationFailure;
import com.lawrence.oss.validation.results.ValidationResult;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by z_wu on 2014/12/9.
 * TODO:
 * 1. custom validation rule
 *
 */
public class AbstractValidator<T> implements IValidator<T>, Iterable<IValidationRule> {
    private final List<IValidationRule> ruleList = new ArrayList<>();
    private final  Class<T> clazz;
    public AbstractValidator(Class<T> clazz){
        this.clazz = clazz;
    }
    private ConcurrentHashMap<String,List<PropertyRule>> ruleSetsMap = new ConcurrentHashMap<>();

    public final IRuleBuilderOptions<T> ruleFor(Class<T> clazz, String propertyName){
        Utils.checkArgument(clazz, "Class should not be null for ruleFor");
        //Utils.checkArgument(propertyName, "Property name should not be null for ruleFor");
        PropertyRule rule = PropertyRule.create(clazz, propertyName, getCascadeMode());
        addRule(rule);
        RuleBuilder ruleBuilder = new RuleBuilder(rule);
        return ruleBuilder;
    }

    public IValidatorDescriptor createDescriptor() {
        return new ValidatorDescriptor(ruleList);
    }

    /**
     * Defines a RuleSet that can be used to group together several validators.
     * @param ruleSetName
     */
    public final void ruleSet(String ruleSetName,  Action action){
        Utils.checkArgument(ruleSetName, "A name must be specified when calling RuleSet.");
        Utils.checkArgument(action, "A ruleset definition must be specified when calling RuleSet.");
        final int size = ruleList.size();
        List<PropertyRule> propertyRules = new ArrayList<PropertyRule>();
        action.doAction();
        for (int index = size; index < ruleList.size(); index ++) {
            propertyRules.add((PropertyRule)ruleList.get(index));
        }
        for (PropertyRule rule : propertyRules){
            rule.setRuleSet(ruleSetName);
        }
        ruleSetsMap.put(ruleSetName, propertyRules);
    }

    public void when(Predicate predicate,  Action action){
        List<PropertyRule> propertyRules = new ArrayList<PropertyRule>();
        final int size = ruleList.size();
        if(predicate.doPredicate()) {
            action.doAction();
        }
        for (int index = size + 1; index < ruleList.size(); index ++) {
            propertyRules.add((PropertyRule)ruleList.get(index));
        }

        for (PropertyRule rule : propertyRules){
            rule.applyCondition(predicate.doPredicate(), ApplyConditionTo.AllValidators);
        }
    }

    public void unless(Predicate predicate, Action action) {
        if(!predicate.doPredicate()) {
            when(predicate, action);
        }
    }

    @Override
    public ValidationResult validate(T instance) {
        Utils.checkArgument(instance, "instance should not be null");
        if(!((IValidator)this).canValidateInstancesOfType(instance.getClass())){
            throw new IllegalArgumentException(String.format("Cannot validate instances of type %s This validator can only validate instances of type %s.", instance.getClass().getSimpleName(), clazz.getClass().getSimpleName()));
        }
        return validate(new ValidationContext(instance, new PropertyChain(), new DefaultValidatorSelector()));
    }

    @Override
    public ValidationResult validate(ValidationContext context){
        Utils.checkArgument(context, "Validation context should not be null for validation");
        ValidationContext newContext = new ValidationContext(context.instanceToValidate(), context.getPropertyChain(), context.selector());
        newContext.setIsChildContext(context.isChildContext());
        return validate(newContext, null);
    }

    /**
     * To validate specified properties
     * @param instance
     * @param properties
     * @return
     */
    public ValidationResult validate(T instance, String ... properties){
        Utils.checkArgument(instance, "Validation context should not be null");
        if(!Utils.isPropertyExist(instance, properties)){
            throw new IllegalArgumentException("property does not exist");
        }
        ValidationContext context = new ValidationContext(instance, new PropertyChain(), new MemberNameValidatorSelector(properties));
        return  validate(context);
    }

    public <T> ValidationResult validate(T instance, IValidatorSelector selector,String... ruleSets) {
        if(selector != null && ruleSets != null){
            throw new IllegalArgumentException("Cannot specify both an IValidatorSelector and a RuleSet.");
        }
        if(selector == null)
            selector = new DefaultValidatorSelector();
        if(ruleSets != null)
            selector = new RulesetValidatorSelector(ruleSets);

        ValidationContext context = new ValidationContext(instance, new PropertyChain(), selector);
        return validate(context, ruleSets);
    }

    /**
     * To validate with specified rule set
     * @param context
     * @param
     * @return
     */
    public ValidationResult validate(ValidationContext context, String... ruleSets) {
        Utils.checkArgument(context, "Validation context should not be null for validation");
        List<ValidationFailure> failures = new ArrayList<>();
        if(ruleSets == null || contains(ruleSets, "*")){
            for (IValidationRule rule : ruleList) {
                failures.addAll(rule.valiadate(context));
            }
        }else {
            for (String ruleSet : ruleSets) {
                for (IValidationRule rule : ruleSetsMap.get(ruleSet)) {
                    failures.addAll(rule.valiadate(context));
                }
            }
        }
        return new ValidationResult(failures);
    }

    public void validateAndThrow (T instance) {
        ValidationResult result = this.validate(instance);
        if (!result.isValid()) {
            throw new ValidationException(result.errors());
        }
    }

    public void addRule(IValidationRule rule) {
        ruleList.add(rule);
    }

    public void setCascadeMode(CascadeMode cascadeMode){
        ValidatorOptions.cascadeMode = cascadeMode;
    }

    @Override
    public CascadeMode getCascadeMode() {
        return ValidatorOptions.cascadeMode;
    }

    @Override
    public boolean canValidateInstancesOfType(T type) {
        return clazz.isAssignableFrom((Class)type);
    }

    @Override
    public Iterator<IValidationRule> iterator() {
       return ruleList.iterator();
    }

    private boolean contains(String[] collection, String value){
       return Arrays.asList(collection).contains(value);
    }
}
