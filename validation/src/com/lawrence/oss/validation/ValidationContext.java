package com.lawrence.oss.validation;

import com.lawrence.oss.validation.internal.DefaultValidatorSelector;
import com.lawrence.oss.validation.internal.IValidatorSelector;
import com.lawrence.oss.validation.internal.PropertyChain;

/**
 * Created by z_wu on 2014/12/8.
 */
public class ValidationContext<T> {
    private T _instanceToValidate;
    private IValidatorSelector selector;
    private PropertyChain _propertyChain;
    private boolean isChildContext;
    public ValidationContext(T instanceToValidate){
        this (instanceToValidate, new PropertyChain(), new DefaultValidatorSelector());
    }
    public ValidationContext(T instanceToValidate,PropertyChain propertyChain, IValidatorSelector validatorSelector){
        _propertyChain = new PropertyChain(propertyChain);
        _instanceToValidate = instanceToValidate;
        selector = validatorSelector;
    }
    public Object instanceToValidate(){
        return _instanceToValidate;
    }
    public IValidatorSelector selector(){
        return this.selector;
    }

    public boolean isChildContext(){
        return isChildContext;
    }

    public void setIsChildContext(boolean isChildContext){
        this.isChildContext = isChildContext;
    }

    public PropertyChain getPropertyChain() {
        return this._propertyChain;
    }

    public ValidationContext cloneForChildValidator(Object instanceToValidate) {
        ValidationContext vc = new ValidationContext(instanceToValidate, _propertyChain, selector);
        vc.isChildContext = true;
        return  vc;
    }
}


