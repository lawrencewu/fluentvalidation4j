package test.validation;

import com.lawrence.oss.validation.InlineValidator;
import validation.model.Person;

/**
 * Created by z_wu on 2014/12/22.
 */
public class TestValidator extends InlineValidator<Person> {
    public TestValidator(){
        super(Person.class);
    }
}
