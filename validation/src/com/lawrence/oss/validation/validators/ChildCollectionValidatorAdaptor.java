package com.lawrence.oss.validation.validators;

import com.lawrence.oss.validation.IValidator;
import com.lawrence.oss.validation.Predicate1;
import com.lawrence.oss.validation.ValidationContext;
import com.lawrence.oss.validation.results.ValidationFailure;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by z_wu on 2014/12/19.
 */
public class ChildCollectionValidatorAdaptor implements IPropertyValidator {
    static IValidator childValidator;
    public Predicate1 predicate;

    public ChildCollectionValidatorAdaptor(IValidator childValidator){
        this.childValidator = childValidator;
    }

    @Override
    public List<ValidationFailure> validate(PropertyValidatorContext context) {
        List<ValidationFailure> validationFailures = new ArrayList<>();
        if(context.propertyRule.member == null){
            throw new RuntimeException("Nested validators can only be used with Member Expressions.");
        }
        List<Object> collection = (List)context.getPropertyValue();
        if(collection == null)
            return new ArrayList<>();
        Iterator<Object> it = collection.iterator();

        while (it.hasNext()){
            Object element = it.next();
            boolean result = false;
            if(predicate == null)
                result = true;
            if(predicate != null)
                result = predicate.doPredicate(element);
            if(element == null || !result)
                continue;
            ValidationContext newContext = context.parentContext.cloneForChildValidator(element);
            newContext.getPropertyChain().add(context.propertyRule.member);
            List<ValidationFailure> vfs = childValidator.validate(newContext).errors();
            validationFailures.addAll(vfs);
        }
        return validationFailures;
    }

    public IValidator getValidator(){
        return childValidator;
    }
}
