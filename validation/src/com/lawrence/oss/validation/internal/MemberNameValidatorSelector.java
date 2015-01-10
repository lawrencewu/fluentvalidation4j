package com.lawrence.oss.validation.internal;

import com.lawrence.oss.validation.IValidationRule;
import com.lawrence.oss.validation.ValidationContext;
import java.util.List;

/**
 * Created by z_wu on 2014/12/24.
 */
public class MemberNameValidatorSelector implements IValidatorSelector {
    String[] memberNames;
    public MemberNameValidatorSelector(String[] memberNames){
         this.memberNames = memberNames;
     }

    @Override
    public boolean canExecute(IValidationRule rule, String propertyPath, ValidationContext context) {
        // Validator selector only applies to the top level.
        // If we're running in a child context then this means that the child validator has already been selected
        // Because of this, we assume that the rule should continue (ie if the parent rule is valid, all children are valid)
        return  context.isChildContext() || contains(memberNames, propertyPath);
    }

    private boolean contains(String[] memberNames, String property){
        if(memberNames == null || memberNames.length == 0) return false;
        for(String mn : memberNames){
            if(mn.equals(property)){
                return true;
            }
        }
        return false;
    }
}
