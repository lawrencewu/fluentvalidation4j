package test.validation;

import com.lawrence.oss.validation.AbstractValidator;
import com.lawrence.oss.validation.ApplyConditionTo;
import com.lawrence.oss.validation.Predicate;
import com.lawrence.oss.validation.results.ValidationResult;
import org.junit.Before;
import org.junit.Test;
import validation.model.Person;

import static org.junit.Assert.*;

/**
 * Created by z_wu on 2015/1/6.
 */
public class ConditionTester {
    Person person;

    @Before
    public void setUp(){
        person = new Person();
    }

    @Test
    public void validation_should_succeed_when_condition_does_not_match() {
        person = new Person();
        person.id = 1;
        TestConditionValidator validator = new TestConditionValidator();
        ValidationResult result = validator.validate(person);
        assertTrue(result.isValid());
    }

    @Test
    public void validation_should_fail_when_condition_matches() {
        TestConditionValidator validator = new TestConditionValidator();
        ValidationResult result = validator.validate(new Person());
        assertFalse(result.isValid());
    }

    @Test
    public void validation_should_succeed_when_condition_matches() {
        InverseConditionValidator validator = new InverseConditionValidator();
        ValidationResult result = validator.validate(new Person());
        assertTrue(result.isValid());
    }

    @Test
    public void validation_should_fail_when_condition_does_not_match() {
        person.id = 1;
        InverseConditionValidator validator = new InverseConditionValidator();
        ValidationResult result = validator.validate(person);
        assertFalse(result.isValid());
    }

    @Test
    public void condition_is_applied_to_all_validators_in_the_chain() {
        TestValidator validator = new TestValidator();
        validator.ruleFor(Person.class, "surname").notNull().notEqual("foo").when(person.id > 0);
        ValidationResult result = validator.validate(person);
        assertEquals(0, result.errors().size());
    }


    @Test
    public void condition_is_applied_to_single_validator_in_the_chain_when_ApplyConditionTo_set_to_CurrentValidator() {
        TestValidator validator = new TestValidator();
        validator.ruleFor(Person.class, "surname").notNull().notEqual("foo").when(person.id > 0, ApplyConditionTo.CurrentValidator);
        ValidationResult result = validator.validate(person);
        assertEquals(1, result.errors().size());
    }

    private class TestConditionValidator extends AbstractValidator<Person> {
        public TestConditionValidator(){
            super(Person.class);
            ruleFor(Person.class, "forename").notNull().when(person.id == 0);
        }
    }

    private class InverseConditionValidator extends AbstractValidator<Person> {
        public InverseConditionValidator(){
            super(Person.class);
            ruleFor(Person.class, "forename").notNull().unless(new Predicate() {
                @Override
                public boolean doPredicate() {
                    return person.id == 0;
                }
            });
        }
    }
}
