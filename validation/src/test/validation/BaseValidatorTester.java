package test.validation;

import org.junit.Before;
import validation.model.Person;

/**
 * Created by z_wu on 2015/1/7.
 */
public class BaseValidatorTester {
    TestValidator validator;
    Person person;
    @Before
    public void setUp(){
        validator = new TestValidator();
        person = new Person();
    }
}
