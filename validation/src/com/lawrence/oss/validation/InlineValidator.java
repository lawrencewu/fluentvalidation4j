package com.lawrence.oss.validation;

/**
 * Created by z_wu on 2014/12/17.
 */
public class InlineValidator<T> extends AbstractValidator<T>{
    Class<T> clazz;
    public InlineValidator(Class<T> clazz){
        super(clazz);
    }
    public void add(InlineRuleCreator inlineRuleCreator){
        inlineRuleCreator.inlineRuleCreator(this);
    }
}
