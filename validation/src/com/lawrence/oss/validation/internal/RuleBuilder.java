package com.lawrence.oss.validation.internal;
import com.lawrence.oss.validation.Action1;
import com.lawrence.oss.validation.IValidator;
import com.lawrence.oss.validation.exception.ArgumentNullException;
import com.lawrence.oss.validation.validators.ChildValidatorAdaptor;
import com.lawrence.oss.validation.validators.IPropertyValidator;

/**
 * Created by z_wu on 2014/12/9.
 */
public class RuleBuilder<T> extends IRuleBuilderOptions<T> {
    private final PropertyRule rule;

    public IRuleBuilderOptions<T> setValidator(IPropertyValidator validator) {
        if(validator == null)
            throw  new ArgumentNullException("Cannot pass a null validator to setValidator.");
        rule.addValidator(validator);
        return this;
    }

    @Override
    public IRuleBuilderOptions<T> setValidator(IValidator validator){
        if(validator == null)
            throw  new ArgumentNullException("Cannot pass a null validator to setValidator.");
        setValidator(new ChildValidatorAdaptor(validator));
        return this;
    }

    @Override
    public IRuleBuilderOptions<T> configure(Action1 configure) {
        configure.doAction(rule);
        return this;
    }

    public RuleBuilder(PropertyRule rule) {
        this.rule = rule;
    }
    public PropertyRule getRule(){
        return rule;
    }
}
