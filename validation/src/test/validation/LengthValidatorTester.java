package test.validation;

import com.lawrence.oss.validation.exception.ArgumentOutOfRangeException;
import com.lawrence.oss.validation.results.ValidationResult;
import com.lawrence.oss.validation.validators.LengthValidator;
import org.junit.Test;
import validation.model.Person;
import static org.junit.Assert.*;

/**
 * Created by z_wu on 2015/1/7.
 */
public class LengthValidatorTester extends BaseValidatorTester {

    @Override
    public void setUp() {
        super.setUp();
        person.surname = "test";
    }

    @Test
    public void when_the_text_is_between_the_range_specified_then_the_validator_should_pass() {

        validator = new TestValidator();
        validator.ruleFor(Person.class, "surname").length(1, 10);
        ValidationResult result = validator.validate(person);
        assertTrue(result.isValid());
    }
    @Test
    public void when_the_text_is_smaller_than_the_range_then_the_validator_should_fail() {
        validator = new TestValidator();
        validator.ruleFor(Person.class, "surname").length(5, 10);
        ValidationResult result = validator.validate(person);
        assertFalse(result.isValid());
    }

    @Test
    public void when_the_text_is_larger_than_the_range_then_the_validator_should_fail() {
        validator = new TestValidator();
        validator.ruleFor(Person.class, "surname").length(1, 2);
        ValidationResult result = validator.validate(person);
        assertFalse(result.isValid());
    }

    @Test
    public void when_the_text_is_exactly_the_size_of_the_upper_bound_then_the_validator_should_pass() {
        validator = new TestValidator();
        validator.ruleFor(Person.class, "surname").length(1, 4);
        ValidationResult result = validator.validate(person);
        assertTrue(result.isValid());
    }

    @Test
    public void when_the_text_is_exactly_the_size_of_the_lower_bound_then_the_validator_should_pass() {
        validator = new TestValidator();
        validator.ruleFor(Person.class, "surname").length(4, 5);
        ValidationResult result = validator.validate(person);
        assertTrue(result.isValid());
    }

    @Test
    public void when_the_to_is_smaller_than_the_from_then_the_validator_should_throw() {
        try {
            validator.ruleFor(Person.class, "id").length(10, 1);
        }catch (Exception ex){
            assertTrue(ex instanceof ArgumentOutOfRangeException);
        }
    }

    @Test
    public void when_the_validator_fails_the_error_message_should_be_set() {
        person.surname = "Gire and gimble in the wabe";
        validator = new TestValidator();
        validator.ruleFor(Person.class, "surname").length(1, 2);
        ValidationResult result = validator.validate(person);
        assertEquals("'surname' 的长度必须在 1 到 2 之间.您输入了 27 个字符.", result.errors().get(0).errorMessge());
    }

    @Test
    public void min_and_max_properties_should_be_set() {
        LengthValidator validator = new LengthValidator(1, 5);
        assertEquals(1, validator.min);
        assertEquals(5, validator.max);
    }
}
