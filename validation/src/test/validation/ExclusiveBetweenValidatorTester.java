package test.validation;

import com.lawrence.oss.validation.exception.ArgumentOutOfRangeException;
import com.lawrence.oss.validation.results.ValidationResult;
import com.lawrence.oss.validation.validators.ExclusiveBetweenValidator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import validation.model.Person;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;

/**
 * Created by z_wu on 2015/1/7.
 */
public class ExclusiveBetweenValidatorTester {
    Calendar fromDate;
    Calendar toDate;
    TestValidator validator;
    Person person;
    @Before
    public void setUp(){
        fromDate = new GregorianCalendar(2009, 1, 1);
        toDate = new GregorianCalendar(2009,12,31);
        validator = new TestValidator();
        person = new Person();
    }

    @After
    public void tearDown(){
        validator = null;
        person = null;
    }

    @Test
    public void when_the_value_is_between_the_range_specified_then_the_validator_should_pass() {
        person.id =  5;
        validator.ruleFor(Person.class, "id").exclusiveBetween(1, 10);
        ValidationResult result = validator.validate(person);
        assertTrue(result.isValid());
    }

    @Test
    public void when_the_value_is_smaller_than_the_range_then_the_validator_should_fail() {
        validator.ruleFor(Person.class, "id").exclusiveBetween(1, 10);
        ValidationResult result = validator.validate(person);
        assertFalse(result.isValid());
    }

    @Test
    public void when_the_text_is_larger_than_the_range_then_the_validator_should_fail() {
        person.id =  11;
        validator.ruleFor(Person.class, "id").exclusiveBetween(1, 10);
        ValidationResult result = validator.validate(person);
        assertFalse(result.isValid());
    }

    @Test
    public void when_the_value_is_exactly_the_size_of_the_upper_bound_then_the_validator_should_pass() {
        person.id =  10;
        validator.ruleFor(Person.class, "id").exclusiveBetween(1, 10);
        ValidationResult result = validator.validate(person);
        assertFalse(result.isValid());
    }

    @Test
    public void when_the_value_is_exactly_the_size_of_the_lower_bound_then_the_validator_should_pass() {
        person.id =  1;
        validator.ruleFor(Person.class, "id").exclusiveBetween(1, 10);
        ValidationResult result = validator.validate(person);
        assertFalse(result.isValid());
    }

    @Test
    public void when_the_to_is_smaller_than_the_from_then_the_validator_should_throw() {
        try {
            validator.ruleFor(Person.class, "id").exclusiveBetween(10, 1);
        }catch (Exception ex){
            assertTrue(ex instanceof ArgumentOutOfRangeException);
            assertEquals("Argument 'to' should be larger than from.", ex.getMessage());
        }
    }

    @Test
    public void when_the_validator_fails_the_error_message_should_be_set() {
        validator.ruleFor(Person.class, "id").exclusiveBetween(1, 10);
        ValidationResult result = validator.validate(person);
        assertEquals("'id' 应该介于 1 到 10 之间(exclusive). 你输入了：0.", result.errors().get(0).errorMessge());
    }

    @Test
    public void to_and_from_properties_should_be_set() {
        ExclusiveBetweenValidator propertyValidator = new ExclusiveBetweenValidator(1, 10);
        assertEquals(1,propertyValidator.from());
        assertEquals(10, propertyValidator.to());
    }

    @Test
    public void when_the_value_is_between_the_range_specified_then_the_validator_should_pass_for_strings() {
        Person person1 = new Person();
        person1.surname = "bbb";
        validator.ruleFor(Person.class, "id").exclusiveBetween("aa", "zz");
        ValidationResult result = validator.validate(person1, "surname");
        assertTrue(result.isValid());
    }

    @Test
    public void when_the_value_is_smaller_than_the_range_then_the_validator_should_fail_for_strings() {
        person.surname = "aaa";
        validator.ruleFor(Person.class, "surname").exclusiveBetween("bbb", "zz");
        ValidationResult result = validator.validate(person);
        assertFalse(result.isValid());
    }

    @Test
    public void when_the_text_is_larger_than_the_range_then_the_validator_should_fail_for_strings() {
        person.surname = "zzz";
        validator.ruleFor(Person.class, "surname").exclusiveBetween("aaa", "bbb");
        ValidationResult result = validator.validate(person);
        assertFalse(result.isValid());
    }

    @Test
    public void when_the_value_is_exactly_the_size_of_the_upper_bound_then_the_validator_should_fail_for_strings() {
        person.surname = "aa";
        validator.ruleFor(Person.class, "surname").exclusiveBetween("aa", "zz");
        ValidationResult result = validator.validate(person);
        assertFalse(result.isValid());
    }

    @Test
    public void when_the_value_is_exactly_the_size_of_the_lower_bound_then_the_validator_should_fail_for_strings() {
        person.surname = "zz";
        validator.ruleFor(Person.class, "surname").exclusiveBetween("aa", "zz");
        ValidationResult result = validator.validate(person);
        assertFalse(result.isValid());
    }

    @Test
    public void when_the_to_is_smaller_than_the_from_then_the_validator_should_throw_for_strings() {
        try {
            ExclusiveBetweenValidator propertyValidator = new ExclusiveBetweenValidator("ccc", "aa");
        }catch (Exception ex){
            assertTrue(ex instanceof ArgumentOutOfRangeException);
        }
    }

    @Test
    public void when_the_validator_fails_the_error_message_should_be_set_for_strings() {
        person.surname = "aaa";
        validator.ruleFor(Person.class, "surname").exclusiveBetween("bbb", "zzz");
        ValidationResult result = validator.validate(person);
        assertEquals("'surname' 应该介于 bbb 到 zzz 之间(exclusive). 你输入了：aaa.", result.errors().get(0).errorMessge());
    }

    @Test
    public void to_and_from_properties_should_be_set_for_strings() {
        ExclusiveBetweenValidator propertyValidator = new ExclusiveBetweenValidator("a", "c");
        assertEquals("a",propertyValidator.from());
        assertEquals("c", propertyValidator.to());
    }

    @Test
    public void to_and_from_properties_should_be_set_for_dates() {
        ExclusiveBetweenValidator propertyValidator = new ExclusiveBetweenValidator(fromDate, toDate);
        assertEquals(fromDate,propertyValidator.from());
        assertEquals(toDate, propertyValidator.to());
    }

    @Test
    public void validates_with_nullable_when_property_is_null() {
        validator.ruleFor(Person.class, "nullableInt").exclusiveBetween(1, 10);
        ValidationResult result = validator.validate(person);
        assertTrue(result.isValid());
    }

    @Test
    public void validates_with_nullable_when_property_not_null() {
        person.setNullableInt(11);
        validator.ruleFor(Person.class, "nullableInt").exclusiveBetween(1, 10);
        ValidationResult result = validator.validate(person);
        assertFalse(result.isValid());
    }
}
