package test.validation;

import com.lawrence.oss.validation.AbstractValidator;
import com.lawrence.oss.validation.internal.PropertyRule;
import org.junit.Before;
import org.junit.Test;
import validation.model.Person;
import com.lawrence.oss.validation.validators.*;

/**
 * Created by z_wu on 2015/1/6.
 */
public class IRuleBuilderOptionsTester {
    private AbstractValidator<Person> validator;
    @Before
    public void setUp(){
        validator = new AbstractValidator<Person>(Person.class);
    }

    @Test
    public void notNull_should_create_NotNullValidator() {
        validator.ruleFor(Person.class, "surname").notNull();
        assertValidator(NotNullValidator.class);
    }

    @Test
    public void notEmpty_should_create_NotEmptyValidator() {
        validator.ruleFor(Person.class, "surname").notEmpty();
        assertValidator(NotEmptyValidator.class);
    }

    @Test
    public void length_should_create_LengthValidator() {
        validator.ruleFor(Person.class, "surname").length(1, 20);
        assertValidator(LengthValidator.class);
    }

    @Test
    public void length_should_create_ExactLengthValidator() {
        validator.ruleFor(Person.class, "surname").length(5);
        assertValidator(ExactLengthValidator.class);
    }

    @Test
    public void notEqual_should_create_NotEqualValidator_with_explicit_value() {
        validator.ruleFor(Person.class, "surname").notEqual(5);
        assertValidator(NotEqualValidator.class);
    }

    @Test
    public void equal_should_create_EqualValidator_with_explicit_value() {
        validator.ruleFor(Person.class, "surname").equal("Foo");
        assertValidator(EqualValidator.class);
    }

    @Test
    public void equal_should_create_EqualValidator_with_explicit_class_and_value() {
        validator.ruleFor(Person.class, "surname").equal(Person.class, "surname");
        assertValidator(EqualValidator.class);
    }

    @Test
    public void lessThan_should_create_LessThanValidator_with_explicit_value() {
        validator.ruleFor(Person.class, "surname").lessThan("Foo");
        assertValidator(LessThanValidator.class);
    }

    @Test
    public void lessThanOrEqual_should_create_LessThanOrEqualValidator_with_explicit_value() {
        validator.ruleFor(Person.class, "surname").lessThanOrEqualTo("Foo");
        assertValidator(LessThanOrEqualValidator.class);
    }

    @Test
    public void greaterThan_should_create_GreaterThanValidator_with_explicit_value() {
        validator.ruleFor(Person.class, "surname").greaterThan("Foo");
        assertValidator(GreaterThanValidator.class);
    }

    @Test
    public void greaterThanOrEqual_should_create_GreaterThanOrEqualValidator_with_explicit_value() {
        validator.ruleFor(Person.class, "surname").greaterThanOrEqualTo("Foo");
        assertValidator(GreaterThanOrEqualValidator.class);
    }

    private void assertValidator(Class<?> validatorClass){
        PropertyRule rule =  (PropertyRule)validator.iterator().next();
        rule.currentValidator.getClass().equals(validatorClass);
    }
}
