package test.validation;

import com.lawrence.oss.validation.results.ValidationResult;
import com.lawrence.oss.validation.validators.Comparison;
import com.lawrence.oss.validation.validators.NotEqualValidator;
import org.junit.Test;
import validation.model.Person;

import java.util.Comparator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by z_wu on 2015/1/7.
 */
public class NotEqualValidatorTester extends BaseValidatorTester{
    @Override
    public void setUp() {
        super.setUp();
        validator.ruleFor(Person.class, "forename").notEqual("foo");
    }

    @Test
    public void when_the_objects_are_equal_then_the_validator_should_fail() {
        person.setForename("foo");
        ValidationResult result = validator.validate(person);
        assertFalse(result.isValid());
    }

    @Test
    public void when_the_objects_are_not_equal_then_the_validator_should_pass() {
        person.setForename("bar");
        ValidationResult result = validator.validate(person);
        assertTrue(result.isValid());
    }

    @Test
    public void when_the_validator_fails_the_error_message_should_be_set() {
        person.setForename("foo");
        ValidationResult result = validator.validate(person);
        assertEquals("'forename' 应不等于 'foo'.", result.errors().get(0).errorMessge());
    }

    @Test
    public void validates_across_properties() {
        validator = new TestValidator();
        person.surname = "Foo";
        person.setForename("foo");
        validator.ruleFor(Person.class, "forename").notEqual(Person.class, "surname");
        ValidationResult result = validator.validate(person);
        assertTrue(result.isValid());
    }

    @Test
    public void should_store_property_to_compare() {
        validator = new TestValidator();
        validator.ruleFor(Person.class, "forename").notEqual(Person.class, "surname");
        NotEqualValidator notEqualValidator = (NotEqualValidator)validator.createDescriptor().getValidatorsForMember("forename").iterator().next();
        assertEquals("surname", notEqualValidator.getMemberToCompare().getName());
    }

    @Test
    public void should_store_comparison_type() {
        validator = new TestValidator();
        validator.ruleFor(Person.class, "forename").notEqual(Person.class, "surname");
        NotEqualValidator notEqualValidator = (NotEqualValidator)validator.createDescriptor().getValidatorsForMember("forename").iterator().next();
        assertEquals(Comparison.notEqual, notEqualValidator.getComparison());
    }

    @Test
    public void should_not_be_valid_for_case_insensitve_comparison() {
        Person person = new Person();
        person.surname = "FOO";
        validator = new TestValidator();
        validator.ruleFor(Person.class, "surname").notEqual("foo", new myComparator());
        ValidationResult result = validator.validate(person);
        assertFalse(result.isValid());
    }

    @Test
    public void should_not_be_valid_for_case_insensitve_comparison_with_expression() {
        Person person = new Person();
        person.surname = "FOO";
        person.setForename("foo");
        validator = new TestValidator();
        validator.ruleFor(Person.class, "surname").notEqual(Person.class, "forename", new myComparator());
        ValidationResult result = validator.validate(person);
        assertFalse(result.isValid());
    }

    private class myComparator implements Comparator<String> {
        @Override
        public int compare(String p1, String p2) {
            return p1.compareToIgnoreCase(p2);
        }
    }
}
