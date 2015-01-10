package test.validation;

import com.lawrence.oss.validation.exception.ArgumentNullException;
import com.lawrence.oss.validation.results.ValidationResult;
import com.lawrence.oss.validation.validators.Comparison;
import com.lawrence.oss.validation.validators.IComparisonValidator;
import com.lawrence.oss.validation.validators.LessThanOrEqualValidator;
import com.lawrence.oss.validation.validators.LessThanValidator;
import org.junit.Before;
import org.junit.Test;
import validation.model.Person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by z_wu on 2015/1/7.
 */
public class LessThanValidatorTester {
    final int value = 1;
    TestValidator validator;
    Person person;
    @Before
    public void setUp(){
        validator = new TestValidator();
        person = new Person();
        validator.ruleFor(Person.class, "id").lessThan(value);
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
    public void should_fail_when_equal_to_input() {
        person.id = value;
        ValidationResult result = validator.validate(person);
        assertFalse(result.isValid());
    }

    @Test
    public void should_set_default_error_when_validation_fails() {
        person.id = 2;
        ValidationResult result = validator.validate(person);
        assertEquals("'id' 必须小于 '1'.", result.errors().get(0).errorMessge());
    }

    @Test
    public void comparison_type() {
        LessThanValidator lessThanValidator = (LessThanValidator)validator.createDescriptor().getValidatorsForMember("id").iterator().next();
        assertEquals(Comparison.lessThan, lessThanValidator.getComparison());
    }

    @Test
    public void validates_against_property() {
        person.id = 2;
        person.anotherInt = 1;
        TestValidator validator = new TestValidator();
        validator.ruleFor(Person.class, "id").lessThanOrEqualTo(Person.class, "anotherInt");
        ValidationResult result = validator.validate(person);
        assertFalse(result.isValid());
    }


    @Test
    public void should_throw_when_value_to_compare_is_null() {
        validator = new TestValidator();
        try {
        validator.ruleFor(Person.class, "nullableInt").lessThan(null);
        }catch (Exception ex){
            assertTrue(ex instanceof ArgumentNullException);
        }
    }

    @Test
    public void extracts_property() {
        TestValidator validator = new TestValidator();
        validator.ruleFor(Person.class, "id").lessThan(Person.class, "anotherInt");
        LessThanValidator lessThanValidator = (LessThanValidator)validator.createDescriptor().getValidatorsForMember("id").iterator().next();
        assertEquals("anotherInt", lessThanValidator.getMemberToCompare().getName());
    }

    @Test
    public void extracts_property_from_constant() {
        IComparisonValidator validator = new LessThanValidator(2);
        assertEquals(2, validator.getValueToCompare());
    }

    @Test
    public void validates_with_nullable_when_property_is_null() {
        validator.ruleFor(Person.class, "nullableInt").lessThan(5);
        ValidationResult result = validator.validate(person);
        assertTrue(result.isValid());
    }

    @Test
    public void validates_with_nullable_when_property_not_null() {
        person.nullableInt = 10;
        validator.ruleFor(Person.class, "nullableInt").lessThan(5);
        ValidationResult result = validator.validate(person);
        assertFalse(result.isValid());
    }

    @Test
    public void validates_with_nullable_when_property_is_null_cross_property() {
        person.id = 5;
        TestValidator validator = new TestValidator();
        validator.ruleFor(Person.class, "nullableInt").lessThan(Person.class, "id");
        ValidationResult result = validator.validate(person);
        assertTrue(result.isValid());
    }

    @Test
    public void validates_with_nullable_when_property_not_null_cross_property() {
        person.id = 5;
        person.nullableInt = 10;
        TestValidator validator = new TestValidator();
        validator.ruleFor(Person.class, "nullableInt").lessThan(Person.class, "id");
        ValidationResult result = validator.validate(person);
        assertFalse(result.isValid());
    }

}
