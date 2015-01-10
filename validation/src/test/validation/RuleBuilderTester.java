package test.validation;

import com.lawrence.oss.validation.CascadeMode;
import com.lawrence.oss.validation.IValidator;
import com.lawrence.oss.validation.ValidationContext;
import com.lawrence.oss.validation.exception.ArgumentNullException;
import com.lawrence.oss.validation.internal.*;
import com.lawrence.oss.validation.resource.Messages;
import com.lawrence.oss.validation.validators.DelegatingValidator;
import com.lawrence.oss.validation.validators.IPropertyValidator;
import com.lawrence.oss.validation.validators.PropertyValidator;
import com.lawrence.oss.validation.validators.PropertyValidatorContext;
import org.junit.Test;
import validation.model.Person;

import static org.junit.Assert.*;

/**
 * Created by z_wu on 2015/1/8.
 */
public class RuleBuilderTester extends BaseValidatorTester {
    RuleBuilder<Person> builder;
    @Override
    public void setUp() {
        super.setUp();
        PropertyRule rule = PropertyRule.create(Person.class, "surname", CascadeMode.Continue);
        builder = new RuleBuilder<>(rule);
    }

    @Test
    public void should_build_property_name() {
        assertEquals("surname", builder.getRule().propertyName);
    }

    @Test
    public void adding_a_validator_should_return_builder() {
        IRuleBuilderOptions builderWithOptions = builder.setValidator(new TestPropertyValidator());
        assertEquals(builderWithOptions, builder);
    }

    @Test
    public void adding_a_validator_should_store_validator() {
        TestPropertyValidator validator = new TestPropertyValidator();
        builder.setValidator(validator);
        assertEquals(builder.getRule().currentValidator, validator);
    }

    @Test
    public void should_throw_if_validator_is_null() {
        try {
            builder.setValidator((IPropertyValidator)null);
        }catch (Exception ex){
            assertTrue(ex instanceof ArgumentNullException);
        }
    }

    @Test
    public void should_throw_if_overriding_validator_is_null() {
        try {
            builder.setValidator((IValidator)null);
        }catch (Exception ex){
            assertTrue(ex instanceof ArgumentNullException);
        }
    }

    @Test
    public void should_throw_when_predicate_is_null() {
        try {
            TestPropertyValidator testPropertyValidator = new TestPropertyValidator();
            builder.setValidator(testPropertyValidator).when(null);
        }catch (Exception ex){
            assertTrue(ex instanceof ArgumentNullException);
        }
    }

    @Test
    public void should_throw_when_inverse_predicate_is_null() {
        try {
            TestPropertyValidator testPropertyValidator = new TestPropertyValidator();
            builder.setValidator(testPropertyValidator).unless(null);
        }catch (Exception ex){
            assertTrue(ex instanceof NullPointerException);
        }
    }

    @Test
    public void calling_when_should_replace_current_validator_with_predicate_validator() {
            TestPropertyValidator testPropertyValidator = new TestPropertyValidator();
            builder.setValidator(testPropertyValidator).when(true);
            assertEquals(builder.getRule().currentValidator.getClass().getSimpleName(), "DelegatingValidator");

            //IPropertyValidator predicateValidator = (DelegatingValidator)builder.getRule().currentValidator;
            //assertEquals(predicateValidator.getInnerValidator(), DelegatingValidator.class);
    }

    @Test
    public void nullable_object_with_condition_should_not_throw() {
        PropertyRule rule = PropertyRule.create(Person.class, "nullableInt", CascadeMode.Continue);
        builder = new RuleBuilder<>(rule);
        builder.greaterThanOrEqualTo(3).when(person.nullableInt != null);
        builder.getRule().valiadate(new ValidationContext(person.getClass(), new PropertyChain(), new DefaultValidatorSelector()));
    }

    @Test
    public void rule_for_a_non_exist_property_should_throw() {
        try {
            PropertyRule rule = PropertyRule.create(Person.class, "calculateSalary()", CascadeMode.Continue);
            builder = new RuleBuilder<>(rule);
            builder.greaterThan(4);
        }catch (Exception ex){
            assertTrue(ex instanceof ArgumentNullException);
        }
    }

    @Test
    public void property_should_return_property_being_validated() {
        assertEquals("surname", builder.getRule().member.getName());
    }

    @Test
    public void Property_should_return_null_when_it_is_not_a_property_being_validated() {
        builder = new RuleBuilder<Person>(PropertyRule.create(Person.class, "foo", CascadeMode.Continue));
        assertNull(builder.getRule().member);
    }



    public class TestPropertyValidator extends PropertyValidator{
        public TestPropertyValidator(){
            super(Messages.notnull_error);
        }

        @Override
        protected boolean isValid(PropertyValidatorContext context) {
            return true;
        }
    }
}
