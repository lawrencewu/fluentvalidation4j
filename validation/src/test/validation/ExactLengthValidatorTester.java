package test.validation;

import com.lawrence.oss.validation.results.ValidationResult;
import com.lawrence.oss.validation.validators.ExactLengthValidator;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import validation.model.Person;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by z_wu on 2015/1/7.
 */
public class ExactLengthValidatorTester {
    TestValidator validator;
    Person person;
    @Before
    public void setUp(){
        validator = new TestValidator();
        person = new Person();
    }

    @Test
    public void when_the_text_is_an_exact_length_the_validator_should_pass() {
        person.surname = "test";
        validator.ruleFor(Person.class, "surname").length(4);
        ValidationResult result = validator.validate(person);
        assertTrue(result.isValid());
    }
    @Test
    public void when_the_text_length_is_smaller_the_validator_should_fail() {
        person.surname = "test";
        validator.ruleFor(Person.class, "surname").length(10);
        ValidationResult result = validator.validate(person);
        assertFalse(result.isValid());
    }

    @Test
    public void when_the_text_length_is_larger_the_validator_should_fail() {
        person.surname = "test";
        validator.ruleFor(Person.class, "surname").length(1);
        ValidationResult result = validator.validate(person);
        assertFalse(result.isValid());
    }

    @Test
    public void when_the_validator_fails_the_error_message_should_be_set() {
        person.surname = "test";
        validator.ruleFor(Person.class, "surname").length(2);
        ValidationResult result = validator.validate(person);
        assertEquals("'surname' 的长度必须是 2, 你已输入 4 个字符.", result.errors().get(0).errorMessge());
    }

    @Test
    public void min_and_max_properties_should_be_set() {
        ExactLengthValidator validator = new ExactLengthValidator(5);
        TestCase.assertEquals(5, validator.min);
        TestCase.assertEquals(5, validator.max);
    }
}
