package test.validation;

import com.lawrence.oss.validation.exception.ValidationException;
import org.junit.Test;
import validation.model.Person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by z_wu on 2015/1/8.
 */
public class ValidateAndThrowTester extends BaseValidatorTester{

    @Override
    public void setUp() {
        super.setUp();
        validator.ruleFor(Person.class, "surname").notNull();
    }

    @Test
    public void throws_exception() {
        try {
            validator.validateAndThrow(person);
        }catch (Exception ex){
            assertTrue(ex instanceof ValidationException);
        }
    }

    @Test
    public void does_not_throw_when_valid() {
        person.surname = "foo";
        validator.validateAndThrow(person);
    }

    @Test
    public void populates_errors() {
        try {
            validator.validateAndThrow(person);
        }catch (Exception ex){
            assertEquals(1, ((ValidationException)ex).errors.size());
        }
    }

    @Test
    public void toString_provides_error_details() {
        validator.ruleFor(Person.class, "forename").notNull();
        try {
            validator.validateAndThrow(person);
        }catch (Exception ex){
            assertEquals("Validation failed:\r\n--'surname' 必须不为空.\r\n--'forename' 必须不为空.", ex.getMessage());
        }
    }
}
