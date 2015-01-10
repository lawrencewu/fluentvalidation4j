package test.validation;

import com.lawrence.oss.validation.results.ValidationResult;
import com.lawrence.oss.validation.validators.Comparison;
import com.lawrence.oss.validation.validators.LessThanOrEqualValidator;
import org.junit.Before;
import org.junit.Test;
import validation.model.Person;

import static org.junit.Assert.*;

/**
 * Created by z_wu on 2015/1/7.
 */
public class LessThanOrEqualToValidatorTester extends BaseValidatorTester {
    final int value = 1;
    TestValidator validator;
    Person person;
    @Before
    public void setUp(){
        validator = new TestValidator();
        person = new Person();
        validator.ruleFor(Person.class, "id").lessThanOrEqualTo(value);
    }

    @Test
    public void should_fail_when_greater_than_input() {
        person.id = 2;
        ValidationResult result = validator.validate(person);
        assertFalse(result.isValid());
    }

    @Test
    public void should_succeed_when_less_than_input() {
        ValidationResult result = validator.validate(person);
        assertTrue(result.isValid());
    }

    @Test
    public void should_succeed_when_equal_to_input() {
        person.id = value;
        ValidationResult result = validator.validate(person);
        assertTrue(result.isValid());
    }

    @Test
    public void should_set_default_error_when_validation_fails() {
        person.id = 2;
        ValidationResult result = validator.validate(person);
        assertEquals("'id' 必须小于或等于 '1'.", result.errors().get(0).errorMessge());
    }

    @Test
    public void comparison_type() {
        LessThanOrEqualValidator lessThanOrEqualToValidator = (LessThanOrEqualValidator)validator.createDescriptor().getValidatorsForMember("id").iterator().next();
        assertEquals(Comparison.lessThanOrEqual, lessThanOrEqualToValidator.getComparison());
    }

    @Test
    public void validates_with_property() {
        person.id = 1;
        person.anotherInt = 0;
        TestValidator validator = new TestValidator();
        validator.ruleFor(Person.class, "id").lessThanOrEqualTo(Person.class, "anotherInt");
        ValidationResult result = validator.validate(person);
        assertFalse(result.isValid());
    }

    @Test
    public void validates_with_nullable_when_property_is_null() {
        validator = new TestValidator();
        validator.ruleFor(Person.class, "nullableInt").lessThanOrEqualTo(5);
        ValidationResult result = validator.validate(new Person());
        assertTrue(result.isValid());
    }

    @Test
    public void validates_with_nullable_when_property_not_null() {
        person.setNullableInt(10);
        validator = new TestValidator();
        validator.ruleFor(Person.class, "nullableInt").lessThanOrEqualTo(5);
        ValidationResult result = validator.validate(person);
        assertFalse(result.isValid());
    }

    @Test
    public void validates_with_nullable_when_property_is_null_cross_property() {
        person.id = 5;
        TestValidator validator = new TestValidator();
        validator.ruleFor(Person.class, "nullableInt").lessThanOrEqualTo(Person.class, "id");
        ValidationResult result = validator.validate(person);
        assertTrue(result.isValid());
    }

    @Test
    public void validates_with_nullable_when_property_not_null_cross_property() {
        person.id = 5;
        person.nullableInt = 10;
        TestValidator validator = new TestValidator();
        validator.ruleFor(Person.class, "nullableInt").lessThanOrEqualTo(Person.class, "id");
        ValidationResult result = validator.validate(person);
        assertFalse(result.isValid());
    }
}
