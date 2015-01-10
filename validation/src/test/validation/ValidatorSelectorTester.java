package test.validation;

import com.lawrence.oss.validation.InlineValidator;
import com.lawrence.oss.validation.results.ValidationResult;
import org.junit.Before;
import org.junit.Test;
import validation.model.Address;
import validation.model.Person;

import static org.junit.Assert.*;

/**
 * Created by z_wu on 2015/1/9.
 */
public class ValidatorSelectorTester {
    TestObject testObject;
    InlineValidator<TestObject> validator;
    @Before
    public void setUp() {
        testObject = new TestObject();
        validator = new InlineValidator<>(TestObject.class);
        validator.ruleFor(TestObject.class, "someProperty").notNull();
    }

    @Test
    public void memberNameValidatorSelector_returns_true_when_property_name_matches() {
        ValidationResult result = validator.validate(testObject);
        assertEquals(1, result.errors().size());
    }

    @Test
    public void does_not_valdiate_other_property() {
        ValidationResult result = validator.validate(testObject, "someOtherProperty");
        assertEquals(0, result.errors().size());
    }

    @Test
    public void validates_nullable_property_with_when() {
        testObject.someNullableProperty = 0;
        validator = new InlineValidator<>(TestObject.class);
        validator.ruleFor(TestObject.class, "someNullableProperty").greaterThan(0).when(testObject.someNullableProperty != null);
        ValidationResult result = validator.validate(testObject, "someNullableProperty");
        assertEquals(1, result.errors().size());
    }

    @Test
    public void includes_nested_property() {
        Address address = new Address();
        Person person = new Person();
        person.address = address;
        TestValidator validator1 = new TestValidator();
        validator1.ruleFor(Person.class, "surname").notNull();
        validator1.ruleFor(Person.class, "address.id").notEqual(0);
        ValidationResult result = validator1.validate(person, "address.id");
        assertEquals(1, result.errors().size());
    }

    private class TestObject {
        public Object someProperty;
        public Object someOtherProperty;
        public Integer someNullableProperty;
    }
}
