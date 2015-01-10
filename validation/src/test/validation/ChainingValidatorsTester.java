package test.validation;

import com.lawrence.oss.validation.IValidationRule;
import com.lawrence.oss.validation.internal.PropertyRule;
import org.junit.Before;
import org.junit.Test;
import validation.model.Person;
import java.util.Iterator;
import static org.junit.Assert.assertEquals;

/**
 * Created by z_wu on 2014/12/25.
 */
public class ChainingValidatorsTester {
    TestValidator validator;
    @Before
    public void setUp() throws Exception {
        validator = new TestValidator();
    }

    @Test
    public void should_create_multiple_validators() {
        Person person = new Person();
        validator.ruleFor(Person.class, "surname").notNull().notEqual("Foo");
        Iterator<IValidationRule> it = validator.iterator();
        int count = 0;
        if(it.hasNext()) {
            count = ((PropertyRule) it.next()).validators().size();
        }
        assertEquals(2, count);
    }

    @Test
    public void should_execute_multiple_validators() {
        Person person = new Person();
        validator.ruleFor(Person.class, "surname").notNull().equal("Foo");
        int count = validator.validate(person).errors().size();
        assertEquals(2, count);
    }


}
