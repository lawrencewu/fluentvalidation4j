package com.lawrence.oss.validation.results;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by z_wu on 2014/12/4.
 */
public class ValidationResult {
    public ValidationResult(){

    }
    private final List<ValidationFailure> _errors = new ArrayList<ValidationFailure>();
    public boolean isValid(){
        return _errors.isEmpty();
    }
    public List<ValidationFailure> errors(){
        return _errors;
    }
    public ValidationResult(List<ValidationFailure> failures){
        for(ValidationFailure failure : failures) {
             if(failure != null) {
                 _errors.add(failure);
             }
        }
    }
}
