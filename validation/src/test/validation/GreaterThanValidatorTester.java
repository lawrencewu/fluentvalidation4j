package test.validation;

import com.lawrence.oss.validation.results.ValidationResult;
import com.lawrence.oss.validation.validators.Comparison;
import com.lawrence.oss.validation.validators.GreaterThanValidator;
import org.junit.Test;
import validation.model.Person;
import static org.junit.Assert.*;

/**
 * Created by z_wu on 2015/1/7.
 */
public class GreaterThanValidatorTester extends BaseValidatorTester{
    final int value = 1;
    @Override
    public void setUp() {
        super.setUp();
        validator = new TestValidator();
        validator.ruleFor(Person.class, "id").greaterThan(value);
    }

    @Test
    public void Should_fail_when_less_than_input() {
        ValidationResult result = validator.validate(person);
        assertFalse(result.isValid());
    }

    @Test
    public void should_succeed_when_greater_than_input() {
        person.id = 2;
        ValidationResult result = validator.validate(person);
        assertTrue(result.isValid());
    }

    @Test
    public void Should_fail_when_equal_to_input() {
        person.id = value;
        ValidationResult result = validator.validate(person);
        assertFalse(result.isValid());
    }

    @Test
    public void should_set_default_error_when_validation_fails() {
        ValidationResult result = validator.validate(person);
        assertEquals("'id' 必须大于 '1'.", result.errors().get(0).errorMessge());
    }

    @Test
    public void validates_with_property() {
        person.id = 0;
        person.anotherInt = 1;
        TestValidator validator = new TestValidator();
        validator.ruleFor(Person.class, "id").greaterThan(Person.class, "anotherInt");
        ValidationResult result = validator.validate(person);
        assertFalse(result.isValid());
    }

    @Test
    public void comparison_type() {
        GreaterThanValidator gtev = (GreaterThanValidator)validator.createDescriptor().getValidatorsForMember("id").iterator().next();
        assertEquals(Comparison.greaterThan, gtev.getComparison());
    }

    @Test
    public void validates_with_nullable_when_property_is_null() {
        validator = new TestValidator();
        validator.ruleFor(Person.class, "nullableInt").greaterThan(5);
        ValidationResult result = validator.validate(new Person());
        assertTrue(result.isValid());
    }

    @Test
    public void validates_with_nullable_when_property_not_null() {
        Person person = new Person();
        person.setNullableInt(1);
        validator = new TestValidator();
        validator.ruleFor(Person.class, "nullableInt").greaterThan(5);
        ValidationResult result = validator.validate(person);
        assertFalse(result.isValid());
    }

    @Test
    public void validates_with_nullable_when_property_is_null_cross_property() {
        person.id = 5;
        TestValidator validator = new TestValidator();
        validator.ruleFor(Person.class, "nullableInt").greaterThan(Person.class, "id");
        ValidationResult result = validator.validate(person);
        assertTrue(result.isValid());
    }

    @Test
    public void validates_with_nullable_when_property_not_null_cross_property() {
        person.id = 5;
        person.nullableInt = 1;
        TestValidator validator = new TestValidator();
        validator.ruleFor(Person.class, "nullableInt").greaterThan(Person.class, "id");
        ValidationResult result = validator.validate(person);
        assertFalse(result.isValid());
    }
}
