package com.lawrence.oss.validation.validators;

import com.lawrence.oss.validation.Predicate1;
import com.lawrence.oss.validation.internal.IRuleBuilderOptions;

/**
 * Created by z_wu on 2015/1/4.
 */
public abstract class ICollectionValidatorRuleBuilder extends IRuleBuilderOptions {
   public abstract ICollectionValidatorRuleBuilder where(Predicate1 predicate);
}
