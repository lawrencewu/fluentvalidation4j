package test.validation;

import com.lawrence.oss.validation.results.ValidationResult;
import org.junit.Test;
import validation.model.Person;

import static org.junit.Assert.*;

/**
 * Created by z_wu on 2015/1/8.
 */
public class RegularExpressionValidatorTester extends BaseValidatorTester {
    @Override
    public void setUp() {
        super.setUp();
        validator.ruleFor(Person.class, "surname").matches("^\\w\\d$");
    }

    @Test
    public void when_the_text_matches_the_regular_expression_then_the_validator_should_pass() {
        person.surname = "S3";
        ValidationResult result = validator.validate(person);
        assertTrue(result.isValid());
    }

    @Test
    public void when_the_text_does_not_match_the_regular_expression_then_the_validator_should_fail() {
        person.surname = "S33";
        ValidationResult result = validator.validate(person);
        assertFalse(result.isValid());

        person.surname = " 5";
        result = validator.validate(person);
        assertFalse(result.isValid());
    }

    @Test
    public void when_the_text_is_empty_then_the_validator_should_fail() {
        person.surname = "";
        ValidationResult result = validator.validate(person);
        assertFalse(result.isValid());
    }

    @Test
    public void when_the_text_is_null_then_the_validator_should_pass() {
        ValidationResult result = validator.validate(person);
        assertTrue(result.isValid());
    }

    @Test
    public void when_validation_fails_the_default_error_should_be_set() {
        person.surname = "S33";
        ValidationResult result = validator.validate(person);
        assertEquals("'surname' 格式不正确.", result.errors().get(0).errorMessge());
    }
}
