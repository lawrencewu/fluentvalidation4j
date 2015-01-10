package test.validation;

import com.lawrence.oss.validation.ValidatorDescriptor;
import com.lawrence.oss.validation.results.ValidationResult;
import com.lawrence.oss.validation.validators.Comparison;
import com.lawrence.oss.validation.validators.EqualValidator;
import org.junit.Before;
import org.junit.Test;
import validation.model.Person;
import java.util.Comparator;
import static org.junit.Assert.*;

/**
 * Created by z_wu on 2015/1/6.
 */
public class EqualValidatorTester {
    TestValidator validator;
    Person person;
    @Before
    public void setUp(){
        validator = new TestValidator();
        person = new Person();
    }

    @Test
    public void when_the_objects_are_equal_validation_should_succeed() {
        person.setForename("foo");
        validator.ruleFor(Person.class, "forename").equal("foo");
        ValidationResult result = validator.validate(person);
        assertTrue(result.isValid());
    }

    @Test
    public void when_the_objects_are_not_equal_validation_should_fail() {
        person.setForename("bar");
        validator.ruleFor(Person.class, "forename").equal("foo");
        ValidationResult result = validator.validate(person);
        assertFalse(result.isValid());
    }

    @Test
    public void when_validation_fails_the_error_should_be_set() {
        person.setForename("bar");
        validator.ruleFor(Person.class, "forename").equal("foo");
        ValidationResult result = validator.validate(person);
        assertEquals("'forename' 应该等于 'foo'.", result.errors().get(0).errorMessge());
    }

    @Test
    public void should_store_property_to_compare() {
        validator.ruleFor(Person.class, "forename").equal(Person.class, "surname");
        ValidatorDescriptor descriptor = (ValidatorDescriptor)validator.createDescriptor();
        EqualValidator propertyValidator = (EqualValidator)descriptor.getValidatorsForMember("forename").iterator().next();
        assertEquals(propertyValidator.getMemberToCompare().getName(), "surname");
    }

    @Test
    public void should_store_comparison_type() {
        validator.ruleFor(Person.class, "surname").equal("foo");
        ValidatorDescriptor descriptor = (ValidatorDescriptor)validator.createDescriptor();
        EqualValidator propertyValidator = (EqualValidator)descriptor.getValidatorsForMember("surname").iterator().next();
        assertEquals(propertyValidator.getComparison(), Comparison.equal);
    }

    @Test
    public void validates_against_property() {
        Person person = new Person();
        person.surname = "foo";
        person.setForename("foo");
        validator.ruleFor(Person.class, "surname").equal(Person.class, "forename");
        ValidationResult result = validator.validate(person);
        assertTrue(result.isValid());
    }

    @Test
    public void should_succeed_on_case_insensitive_comparison() {
        Person person = new Person();
        person.surname = "FOO";
        validator.ruleFor(Person.class, "surname").equal("foo", new myComparator());
        ValidationResult result = validator.validate(person);
        assertTrue(result.isValid());
    }

    @Test
    public void should_succeed_on_case_insensitive_comparison_using_member() {
        Person person = new Person();
        person.surname = "FOO";
        person.setForename("foo");
        validator.ruleFor(Person.class, "surname").equal(Person.class, "forename", new myComparator());
        ValidationResult result = validator.validate(person);
        assertTrue(result.isValid());
    }

    private class myComparator implements Comparator<String>{
        @Override
        public int compare(String p1, String p2) {
            return p1.compareToIgnoreCase(p2);
        }
    }
}
