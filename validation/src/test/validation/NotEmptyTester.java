package test.validation;

import com.lawrence.oss.validation.InlineValidator;
import com.lawrence.oss.validation.results.ValidationResult;
import org.junit.Test;
import validation.model.Person;
import java.util.ArrayList;
import static org.junit.Assert.*;

/**
 * Created by z_wu on 2015/1/7.
 */
public class NotEmptyTester extends BaseValidatorTester {

    @Override
    public void setUp() {
        super.setUp();
        validator.ruleFor(Person.class, "surname").notEmpty();
    }

    @Test
    public void when_there_is_a_value_then_the_validator_should_pass() {
        person.surname = "foo";
        ValidationResult result = validator.validate(person);
        assertTrue(result.isValid());
    }

    @Test
    public void when_value_is_null_validator_should_fail() {
        ValidationResult result = validator.validate(person);
        assertFalse(result.isValid());
    }

    @Test
    public void when_value_is_empty_string_validator_should_fail() {
        person.surname = "";
        ValidationResult result = validator.validate(person);
        assertFalse(result.isValid());
    }

    @Test
    public void when_value_is_whitespace_validation_should_fail() {
        person.surname = "       ";
        ValidationResult result = validator.validate(person);
        assertFalse(result.isValid());
    }

    @Test
    public void when_value_is_Default_for_type_validator_should_fail_int() {
        validator = new TestValidator();
        validator.ruleFor(Person.class, "id").notEmpty();
        ValidationResult result = validator.validate(person);
        assertFalse(result.isValid());

        person.id = 1;
        ValidationResult result1 = validator.validate(person);
        assertTrue(result1.isValid());
    }
    @Test
    public void fails_when_collection_empty() {
        person.children = new ArrayList<>();
        validator = new TestValidator();
        validator.ruleFor(Person.class, "children").notEmpty();
        ValidationResult result = validator.validate(person);
        assertFalse(result.isValid());
    }

    @Test
    public void when_validation_fails_error_should_be_set() {
        ValidationResult result = validator.validate(person);
        assertEquals("'surname' 不能为空字符串.", result.errors().get(0).errorMessge());
    }

    /*@Test
    public void fails_for_iterable_that_doesnt_implement_Iterable() {
        TestModel testModel = new TestModel();
        InlineValidator validator1 = new InlineValidator(TestModel.class);
        validator1.ruleFor(TestModel.class, "names").notEmpty();
        ValidationResult result = validator1.validate(testModel);
        assertFalse(result.isValid());
    }

    public class TestModel {
        public Iterable<String> names;
        public TestModel(){
            names = new ArrayList<>();
        }
    }*/
}
