package test.validation;

import com.lawrence.oss.validation.results.ValidationResult;
import com.lawrence.oss.validation.validators.PropertyValidator;
import com.lawrence.oss.validation.validators.PropertyValidatorContext;
import org.junit.Test;
import validation.model.Person;
import static org.junit.Assert.*;

/**
 * Created by z_wu on 2015/1/7.
 */
public class PropertyValidatorTester extends BaseValidatorTester{

    @Test
    public void when_passing_string_to_localizable_lambda_should_convert_to_string_accessor() {
        TestValidator validator = new TestValidator();
        validator.ruleFor(Person.class, "surname").setValidator(new FooValidator());
        ValidationResult result = validator.validate(person);
        assertEquals("foo", result.errors().get(0).errorMessge());
    }

    private class FooValidator extends PropertyValidator {
        public FooValidator() {
            super("foo");
        }

        @Override
        protected boolean isValid(PropertyValidatorContext context) {
              return false;
        }
    }
}
