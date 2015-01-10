package test.validation;

import com.lawrence.oss.validation.IValidatorDescriptor;
import com.lawrence.oss.validation.validators.IPropertyValidator;
import com.lawrence.oss.validation.validators.NotNullValidator;
import org.junit.Test;
import validation.model.Person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by z_wu on 2015/1/9.
 */
public class ValidatorDescriptorTester extends BaseValidatorTester {

    @Test
    public void gets_validators_for_property() {
        validator.ruleFor(Person.class, "surname").notNull();
        IValidatorDescriptor descriptor = validator.createDescriptor();
        Iterable<IPropertyValidator> validators = descriptor.getValidatorsForMember("surname");
        assertEquals(NotNullValidator.class, validators.iterator().next().getClass());
    }

    @Test
    public void returns_empty_collection_for_property_with_no_validators() {
        validator.ruleFor(Person.class, "surname").notNull();
        IValidatorDescriptor descriptor = validator.createDescriptor();
        Iterable<IPropertyValidator> validators = descriptor.getValidatorsForMember("surname");
        assertTrue(validators.iterator().hasNext());
    }

    @Test
    public void does_not_throw_when_rule_declared_without_property() {
        validator.ruleFor(Person.class, null).notNull();
        IValidatorDescriptor descriptor = validator.createDescriptor();
        descriptor.getValidatorsForMember("surname");
    }

}
