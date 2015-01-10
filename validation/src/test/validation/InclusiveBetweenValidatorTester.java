package test.validation;
import com.lawrence.oss.validation.exception.ArgumentOutOfRangeException;
import com.lawrence.oss.validation.results.ValidationResult;
import com.lawrence.oss.validation.validators.ExclusiveBetweenValidator;
import com.lawrence.oss.validation.validators.InclusiveBetweenValidator;
import org.junit.Test;
import validation.model.Person;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by z_wu on 2015/1/7.
 */
public class InclusiveBetweenValidatorTester extends BaseValidatorTester{
    Calendar fromDate;
    Calendar toDate;
    @Override
    public void setUp(){
        super.setUp();
        fromDate = new GregorianCalendar(2009, 1, 1);
        toDate = new GregorianCalendar(2009,12,31);
    }

    @Test
    public void when_the_value_is_between_the_range_specified_then_the_validator_should_pass() {
        person.id =  5;
        validator.ruleFor(Person.class, "id").inclusiveBetween(1, 10);
        ValidationResult result = validator.validate(person);
        assertTrue(result.isValid());
    }

    @Test
    public void when_the_value_is_smaller_than_the_range_then_the_validator_should_fail() {
        validator.ruleFor(Person.class, "id").inclusiveBetween(1, 10);
        ValidationResult result = validator.validate(person);
        assertFalse(result.isValid());
    }

    @Test
    public void when_the_value_is_exactly_the_size_of_the_upper_bound_then_the_validator_should_pass() {
        person.id =  10;
        validator.ruleFor(Person.class, "id").inclusiveBetween(1, 10);
        ValidationResult result = validator.validate(person);
        assertTrue(result.isValid());
    }
    @Test
    public void when_the_value_is_exactly_the_size_of_the_lower_bound_then_the_validator_should_pass() {
        person.id =  1;
        validator.ruleFor(Person.class, "id").inclusiveBetween(1, 10);
        ValidationResult result = validator.validate(person);
        assertTrue(result.isValid());
    }

    @Test
    public void when_the_to_is_smaller_than_the_from_then_the_validator_should_throw() {
        try {
            validator.ruleFor(Person.class, "id").inclusiveBetween(10, 1);
        }catch (Exception ex){
            assertTrue(ex instanceof ArgumentOutOfRangeException);
        }
    }

    @Test
    public void when_the_validator_fails_the_error_message_should_be_set() {
        validator.ruleFor(Person.class, "id").inclusiveBetween(1, 10);
        ValidationResult result = validator.validate(person);
        assertEquals("'id' 必须在 1 到 10 之间. 你输入了：0.", result.errors().get(0).errorMessge());
    }

    @Test
    public void to_and_from_properties_should_be_set() {
        InclusiveBetweenValidator propertyValidator = new InclusiveBetweenValidator(1, 10);
        assertEquals(1,propertyValidator.from());
        assertEquals(10, propertyValidator.to());
    }

    @Test
    public void when_the_value_is_between_the_range_specified_then_the_validator_should_pass_for_strings() {
        person.surname = "bbb";
        validator.ruleFor(Person.class, "id").inclusiveBetween("aa", "zz");
        ValidationResult result = validator.validate(person, "surname");
        assertTrue(result.isValid());
    }

    @Test
    public void when_the_value_is_smaller_than_the_range_then_the_validator_should_fail_for_strings() {
        person.surname = "aaa";
        validator.ruleFor(Person.class, "surname").inclusiveBetween("bbb", "zz");
        ValidationResult result = validator.validate(person);
        assertFalse(result.isValid());
    }

    @Test
    public void when_the_text_is_larger_than_the_range_then_the_validator_should_fail_for_strings() {
        person.surname = "zzz";
        validator.ruleFor(Person.class, "surname").inclusiveBetween("aaa", "bbb");
        ValidationResult result = validator.validate(person);
        assertFalse(result.isValid());
    }

    @Test
    public void when_the_to_is_smaller_than_the_from_then_the_validator_should_throw_for_strings() {
        try {
            InclusiveBetweenValidator propertyValidator = new InclusiveBetweenValidator("ccc", "aa");
        }catch (Exception ex){
            assertTrue(ex instanceof ArgumentOutOfRangeException);
        }
    }

    @Test
    public void when_the_validator_fails_the_error_message_should_be_set_for_strings() {
        person.surname = "aaa";
        validator.ruleFor(Person.class, "surname").inclusiveBetween("bbb", "zzz");
        ValidationResult result = validator.validate(person);
        assertEquals("'surname' 必须在 bbb 到 zzz 之间. 你输入了：aaa.", result.errors().get(0).errorMessge());
    }

    @Test
    public void to_and_from_properties_should_be_set_for_strings() {
        InclusiveBetweenValidator propertyValidator = new InclusiveBetweenValidator("a", "c");
        assertEquals("a",propertyValidator.from());
        assertEquals("c", propertyValidator.to());
    }

    @Test
    public void to_and_from_properties_should_be_set_for_dates() {
        InclusiveBetweenValidator propertyValidator = new InclusiveBetweenValidator(fromDate, toDate);
        assertEquals(fromDate,propertyValidator.from());
        assertEquals(toDate, propertyValidator.to());
    }

    @Test
    public void validates_with_nullable_when_property_is_null() {
        validator.ruleFor(Person.class, "nullableInt").inclusiveBetween(1, 10);
        ValidationResult result = validator.validate(person);
        assertTrue(result.isValid());
    }

    @Test
    public void validates_with_nullable_when_property_not_null() {
        person.setNullableInt(11);
        validator.ruleFor(Person.class, "nullableInt").inclusiveBetween(1, 10);
        ValidationResult result = validator.validate(person);
        assertFalse(result.isValid());
    }
}
