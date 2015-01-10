package com.lawrence.oss.validation;

import com.lawrence.oss.validation.validators.IPropertyValidator;
import java.util.Map;

/**
 * Created by z_wu on 2014/12/4.
 */
public interface IValidatorDescriptor {
    String getName(String property);
    Map<String, Iterable<IPropertyValidator>> getMembersWithValidators();
    Iterable<IPropertyValidator> getValidatorsForMember(String name);
    Iterable<IValidationRule> getRulesForMember(String name);
}
