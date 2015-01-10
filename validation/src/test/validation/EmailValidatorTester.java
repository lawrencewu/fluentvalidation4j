package test.validation;

import com.lawrence.oss.validation.results.ValidationResult;
import org.junit.Before;
import org.junit.Test;
import validation.model.Person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by z_wu on 2015/1/6.
 */
public class EmailValidatorTester {
    TestValidator validator;
    @Before
    public void setUp(){
        validator = new TestValidator();
        validator.ruleFor(Person.class, "email").emailAddress();
    }

    @Test
    public void when_the_text_is_a_valid_email_address_then_the_validator_should_pass(){
        String email = "testperson@gmail.com";
        Person person = new Person();
        person.email = email;
        ValidationResult result = validator.validate(person);
        assertTrue(result.isValid());
    }
    @Test
    public void when_the_text_is_a_valid_email_address_including_plus_validator_should_pass(){
        String email = "testperson+label@gmail.com";
        Person person = new Person();
        person.email = email;
        ValidationResult result = validator.validate(person);
        assertTrue(result.isValid());
    }

    @Test
    public void when_the_text_is_null_then_the_validator_should_pass(){
        Person person = new Person();
        person.email = null;
        ValidationResult result = validator.validate(person);
        assertTrue(result.isValid());
    }

    @Test
    public void when_the_text_is_empty_then_the_validator_should_fail(){
        Person person = new Person();
        person.email = "";
        ValidationResult result = validator.validate(person);
        assertFalse(result.isValid());
    }

    @Test
    public void when_the_text_is_not_a_valid_email_address_then_the_validator_should_fail(){
        Person person = new Person();
        person.email = "testperso";
        ValidationResult result = validator.validate(person);
        assertEquals("'email'表示的邮件地址无效.", result.errors().get(0).errorMessge());
    }
    @Test
    public void should_not_hang(){
        Person person = new Person();
        person.email = "thisisaverylongstringcodeplex.com";
        ValidationResult result = validator.validate(person);
        assertFalse(result.isValid());
    }

    @Test
    public void when_email_address_contains_upper_cases_then_the_validator_should_pass(){
        Person person = new Person();
        person.email = "testperson@gmail.com";
        ValidationResult result = validator.validate(person);
        assertTrue(result.isValid());
        person.email = "TestPerson@gmail.com";
        result = validator.validate(person);
        assertTrue(result.isValid());

    }
}
