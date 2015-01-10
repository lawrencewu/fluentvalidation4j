package test.validation;

import com.lawrence.oss.validation.results.ValidationResult;
import org.junit.Test;
import validation.model.Person;

import static org.junit.Assert.*;

/**
 * Created by z_wu on 2015/1/7.
 */
public class NotNullTester  extends BaseValidatorTester{
    @Override
    public void setUp() {
        super.setUp();
        validator.ruleFor(Person.class, "surname").notEmpty();
    }

    @Test
    public void notNullValidator_should_pass_if_value_has_value() {
        person.surname = "foo";
        validator = new TestValidator();
        ValidationResult result = validator.validate(person);
        assertTrue(result.isValid());
    }

    @Test
    public void notNullValidator_should_fail_if_value_is_null() {
        ValidationResult result = validator.validate(person);
        assertFalse(result.isValid());
    }

    @Test
    public void when_the_validator_fails_the_error_message_should_be_set() {
        ValidationResult result = validator.validate(person);
        assertEquals("'surname' 不能为空字符串.", result.errors().get(0).errorMessge());
    }
}
