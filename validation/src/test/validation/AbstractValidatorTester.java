package test.validation;

import com.lawrence.oss.validation.Action;
import com.lawrence.oss.validation.IValidator;
import validation.model.Address;
import validation.model.Person;
import com.lawrence.oss.validation.results.ValidationResult;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by z_wu on 2014/12/22.
 */
public class AbstractValidatorTester {
    public AbstractValidatorTester(){}
    TestValidator testValidator;
    @Before
    public void setUp() throws Exception {
        testValidator = new TestValidator();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void when_the_validators_pass_then_the_validator_runner_return_true(){
        Person person = new Person();
        person.setForename("Jeremy");
        testValidator.ruleFor(Person.class, "forename").notNull();
        boolean result = testValidator.validate(person).isValid();
        assertTrue(result);
    }

    @Test
    public void when_the_validators_fail_then_the_validator_runner_return_false(){
        Person person = new Person();
        testValidator.ruleFor(Person.class, "forename").notNull();
        boolean result = testValidator.validate(person).isValid();
        assertFalse(result);
    }

    @Test
    public void when_the_validators_fail_then_the_errors_Should_be_accessible_via_the_errors_property(){
        Person person = new Person();
        testValidator.ruleFor(Person.class, "forename").notNull();
        ValidationResult result = testValidator.validate(person);
        assertEquals(result.errors().size(), 1);
    }
    @Test
    public void should_validate_public_Field(){
        Person person = new Person();
        testValidator.ruleFor(Person.class, "forename").notNull();
        ValidationResult result = testValidator.validate(person);
        assertEquals(result.errors().size(), 1);
    }
    @Test
    public void should_not_main_state() {
        Person person = new Person();
        testValidator.ruleFor(Person.class, "forename").notNull();
        testValidator.validate(person);
        ValidationResult result = testValidator.validate(person);
        assertEquals(result.errors().size(), 1);
    }

    @Test
    public void canValidateInstancesOfType_returns_true_when_comparing_against_same_type(){
        IValidator validator = (IValidator)this.testValidator;
        boolean result = validator.canValidateInstancesOfType(Person.class);
        assertTrue(result);
    }

    @Test
    public void canValidateInstancesOfType_returns_true_when_comparing_against_subclass(){
        IValidator validator = (IValidator)this.testValidator;
        boolean result = validator.canValidateInstancesOfType(DerivedPerson.class);
        assertTrue(result);
    }


    @Test
    public void canValidateInstancesOfType_returns_true_when_comparing_against_other_class(){
        IValidator validator = (IValidator)this.testValidator;
        boolean result = validator.canValidateInstancesOfType(Address.class);
        assertFalse(result);
    }

    @Test
    public void throw_for_non_exist_property_when_validating_single_property(){
        Person person = new Person();
        try {
            testValidator.validate(person, "foo");
        }catch (Exception ex) {
            assertEquals("property does not exist", ex.getMessage());
        }
    }

    @Test
    public void validate_single_property_where_property_as_string() {
        Person person = new Person();
        testValidator.ruleFor(Person.class, "forename").notNull();
        testValidator.ruleFor(Person.class, "surname").notNull();
        ValidationResult result = testValidator.validate(person, "surname");
        assertEquals(1, result.errors().size());
    }

    @Test
    public void validate_single_property_where_invalid_property_as_string() {
        Person person = new Person();
        testValidator.ruleFor(Person.class, "surname").notNull();
        try {
            ValidationResult result = testValidator.validate(person, "surname1");
        }catch (Exception ex){
            assertEquals("property does not exist", ex.getMessage());
        }
    }

    @Test
    public void validates_type_when_using_non_generic_validate_overload() {
        IValidator validator = testValidator;
        try {
            validator.validate("foo");
        }catch (Exception ex){
            assertTrue(ex instanceof IllegalArgumentException);
        }
    }

    @Test
    public void ruleset_to_validate(){
        final Person person = new Person();
        testValidator.ruleSet("Names", new Action() {
            @Override
            public void doAction() {
                testValidator.ruleFor(Person.class, "surname").notEmpty();
                testValidator.ruleFor(Person.class, "forename").notNull();
            }
        });
        testValidator.ruleFor(Person.class, "id").notEqual(0);
        ValidationResult result = testValidator.validate(person, null, new String[]{"Names"});
        assertEquals(2, result.errors().size());
    }

    public static class DerivedPerson extends Person{

    }
}
