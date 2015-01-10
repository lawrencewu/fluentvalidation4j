package test.validation;

import com.lawrence.oss.validation.AbstractValidator;
import com.lawrence.oss.validation.InlineValidator;
import com.lawrence.oss.validation.Predicate;
import com.lawrence.oss.validation.internal.IRuleBuilderOptions;
import com.lawrence.oss.validation.results.ValidationResult;
import org.junit.Before;
import org.junit.Test;
import validation.model.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by z_wu on 2015/1/4.
 */
public class ComplexValidationTester {
    PersonValidator validator;
    Person person;

    @Before
    public void setUp(){
        validator = new PersonValidator();
        person = new Person();
        Country country = new Country();
        Address address = new Address();
        address.country = country;
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(){
            {
                amount = 5;
            }
        });
        orders.add(new Order(){
            {
                productName = "Foo";
            }
        });
        person.address = address;
        person.orders = orders;
    }

    @Test
    public void validates_complex_property() {
        ValidationResult result = validator.validate(person);
        int count = result.errors().size();
        assertEquals(3, count);
        assertEquals("forename", result.errors().get(0).propertyName());
        assertEquals("postcode", result.errors().get(1).propertyName());
        assertEquals("name", result.errors().get(2).propertyName());
    }

    @Test
    public void complex_validator_should_not_be_invoked_on_null_property() {
        ValidationResult results = validator.validate(new Person());
        int count = results.errors().size();
        assertEquals(1, count);
    }

    @Test
    public void should_allow_normal_rules_and_complex_property_on_same_property() {
        validator.ruleFor(Person.class, "address.line1").notNull();
        ValidationResult result = validator.validate(person);
        int count = result.errors().size();
        assertEquals(4, count);
    }

    @Test
    public void explicitly_included_properties_should_be_propogated_to_nested_validators() {
        ValidationResult result = validator.validate(person, "address");
        int count = result.errors().size();
        assertEquals(2, count);
    }

    @Test
    public void complex_property_should_be_excluded() {
        ValidationResult result = validator.validate(person, "surname");
        int count = result.errors().size();
        assertEquals(0, count);
    }

    @Test
    public void should_throw_when_not_a_member_expression() {
        validator.ruleFor(Person.class, "PointlessMethod()").setValidator(new PointlessStringValidator());
        try{
            ValidationResult result = validator.validate(person);
        }catch (Exception ex){
            assertTrue(ex instanceof RuntimeException);
        }
    }

    @Test
    public void condition_should_work_with_complex_property() {
        TestValidator validator = new TestValidator();
        validator.ruleFor(Person.class, "address").setValidator(new AddressValidator()).when(person.address.line1 == "foo");
        ValidationResult result = validator.validate(person);
        assertTrue(result.isValid());
    }

    @Test
    public void can_validate_using_validator_for_base_type() {
        Person person1 = new Person();
        person1.address = new Address();
        InlineValidator<IAddress> addressValidator = new InlineValidator<>(IAddress.class);
        addressValidator.ruleFor(IAddress.class, "line1").notNull();

        TestValidator validator = new TestValidator();
        validator.ruleFor(Person.class, "address").setValidator(addressValidator);
        ValidationResult result = validator.validate(person1);
        assertFalse(result.isValid());
    }

    private static String pointlessMethod() { return null; }

    public class CountryValidator extends  AbstractValidator<Country>{
        public CountryValidator(){
            super(Country.class);
            ruleFor(Country.class, "name").notNull();
        }
    }

    public class AddressValidator extends AbstractValidator<Address>{
        public AddressValidator(){
            super(Address.class);
            ruleFor(Address.class, "postcode").notNull();
            ruleFor(Address.class, "country").setValidator(new CountryValidator());
        }
    }

    public class PersonValidator extends AbstractValidator<Person>
    {
        public PersonValidator(){
            super(Person.class);
            ruleFor(Person.class, "forename").notNull();
            ruleFor(Person.class, "address").setValidator(new AddressValidator());
        }
    }

    public class PointlessStringValidator extends AbstractValidator<String> {
        public PointlessStringValidator(){
            super(String.class);
        }
    }
}
