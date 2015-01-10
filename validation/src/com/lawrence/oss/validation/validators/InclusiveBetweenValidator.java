package com.lawrence.oss.validation.validators;

import com.lawrence.oss.validation.ValidationErrors;
import com.lawrence.oss.validation.exception.ArgumentOutOfRangeException;
import com.lawrence.oss.validation.resource.Messages;

import java.util.Comparator;

/**
 * Created by z_wu on 2014/12/18.
 */
public class InclusiveBetweenValidator extends PropertyValidator implements IBetweenValidator{
    private Comparable from;
    private Comparable to;
    public InclusiveBetweenValidator(Comparable from, Comparable to){
        super(Messages.inclusivebetween_error, ValidationErrors.InclusiveBetween);
        this.from = from;
        this.to = to;
        if(to.compareTo(from) == -1){
            throw new ArgumentOutOfRangeException("Argument 'to' should be larger than from.");
        }
    }

    @Override
    protected boolean isValid(PropertyValidatorContext context) {
        Comparable propertyValue = (Comparable) context.getPropertyValue();
        if(propertyValue == null) return  true;
        if(propertyValue.compareTo(from) < 0 || propertyValue.compareTo(to) > 0){
            context.messageFormatter().appendArgument("from", from)
                                      .appendArgument("to", to)
                                      .appendArgument("value", context.getPropertyValue());
            return false;
        }
        return true;
    }

    @Override
    public Comparable to() {
        return to;
    }

    @Override
    public Comparable from() {
        return from;
    }
}
