package test.validation;

import com.lawrence.oss.validation.InlineRuleCreator;
import com.lawrence.oss.validation.InlineValidator;
import com.lawrence.oss.validation.internal.IRuleBuilderOptions;
import validation.model.Merchant;

/**
 * Created by z_wu on 2014/12/22.
 */
@SuppressWarnings("unchecked")
public class InlineRuleCreatorImpl implements InlineRuleCreator<Merchant> {
    @Override
    public IRuleBuilderOptions inlineRuleCreator(InlineValidator validator) {
        return validator.ruleFor(Merchant.class, "merchantName").notEmpty();
    }
}
