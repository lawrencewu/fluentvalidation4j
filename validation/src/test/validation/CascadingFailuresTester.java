package test.validation;

import com.lawrence.oss.validation.CascadeMode;
import com.lawrence.oss.validation.ValidatorOptions;
import validation.model.Person;
import com.lawrence.oss.validation.results.ValidationResult;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by z_wu on 2014/12/25.
 */
public class CascadingFailuresTester {
    TestValidator validator;
    @Before
    public void setUp() throws Exception {
        ValidatorOptions.cascadeMode = CascadeMode.Continue;
        validator = new TestValidator();
    }

    @After
    public void tearDown() throws Exception {
        ValidatorOptions.cascadeMode = CascadeMode.Continue;
    }

    @Test
    public void validation_continues_on_failure(){
        Person person = new Person();
        validator.ruleFor(Person.class, "surname").notNull().equal("Foo");
        ValidationResult result = validator.validate(person);
        assertEquals(2, result.errors().size());
    }

    @Test
    public void validation_stops_on_first_failure(){
        ValidatorOptions.cascadeMode = CascadeMode.StopOnFirstFailure;
        Person person = new Person();
        validator.ruleFor(Person.class, "surname").notNull().equal("Foo");
        ValidationResult result = validator.validate(person);
        assertEquals(1, result.errors().size());
    }

    @Test
    public void validation_continues_to_second_validator_when_first_validator_succeeds_and_cascade_set_to_stop(){
        ValidatorOptions.cascadeMode = CascadeMode.StopOnFirstFailure;
        Person person = new Person();
        person.surname = "x";
        validator.ruleFor(Person.class, "surname").notNull().length(2, 10);
        boolean result = validator.validate(person).isValid();
        assertFalse(result);
    }

    @Test
    public void validation_continues_to_second_validator_when_set_to_StopOnFirstFailure_at_validator_level(){
        ValidatorOptions.cascadeMode = CascadeMode.StopOnFirstFailure;
        Person person = new Person();
        validator.ruleFor(Person.class, "surname").notNull().equal("Foo");
        ValidationResult result = validator.validate(person);
        assertEquals(1, result.errors().size());
    }

    @Test
    public void validation_continues_to_second_validator_when_set_to_Continue_at_validator_level(){
        ValidatorOptions.cascadeMode = CascadeMode.Continue;
        Person person = new Person();
        validator.ruleFor(Person.class, "surname").notNull().equal("Foo");
        ValidationResult result = validator.validate(person);
        assertEquals(2, result.errors().size());
    }

    @Test
    public void Cascade_mode_can_be_set_after_validator_instantiated() {
        Person person = new Person();
        validator.ruleFor(Person.class, "surname").notNull().equal("Foo");
        validator.setCascadeMode(CascadeMode.StopOnFirstFailure);
        ValidationResult result = validator.validate(person);
        assertEquals(1, result.errors().size());
    }
}
