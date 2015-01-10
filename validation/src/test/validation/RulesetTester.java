package test.validation;

import com.lawrence.oss.validation.*;
import com.lawrence.oss.validation.internal.PropertyChain;
import com.lawrence.oss.validation.internal.RulesetValidatorSelector;
import com.lawrence.oss.validation.results.ValidationResult;
import org.junit.Test;
import validation.model.Address;
import validation.model.Order;
import validation.model.Person;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by z_wu on 2015/1/8.
 */
public class RulesetTester extends BaseValidatorTester{

    @Override
    public void setUp() {
        super.setUp();
        validator.ruleSet("Names", new Action() {
            @Override
            public void doAction() {
                validator.ruleFor(Person.class, "surname").notNull();
                validator.ruleFor(Person.class, "forename").notNull();
            }
        });
        validator.ruleFor(Person.class, "id").notEmpty();
    }

    @Test
    public void executes_rules_in_specified_ruleset() {
       //TestValidator validator = new TestValidator();
        ValidationResult result = validator.validate(new ValidationContext(new Person(), new PropertyChain(), new RulesetValidatorSelector("Names")));
        assertEquals(2, result.errors().size());
    }

    @Test
    public void executes_rules_not_specified_in_ruleset() {
        //TestValidator validator = new TestValidator();
        ValidationResult result = validator.validate(new Person());
        assertEquals(1, result.errors().size());
    }

    @Test
    public void ruleset_cascades_to_child_validator() {
        Person person = new Person();
        Address address = new Address();
        person.address = address;
        final InlineValidator<Address> addressInlineValidator = new InlineValidator<>(Address.class);
        addressInlineValidator.ruleSet("Test", new Action() {
            @Override
            public void doAction() {
                addressInlineValidator.ruleFor( Address.class,"line1").notNull();
            }
        });
        //final TestValidator validator = new TestValidator();
        validator.ruleSet("Test", new Action() {
            @Override
            public void doAction() {
                validator.ruleFor(Person.class, "address").setValidator(addressInlineValidator);
            }
        });

        ValidationResult result = validator.validate(new ValidationContext(person, new PropertyChain(), new RulesetValidatorSelector("Test")));
        assertEquals(1, result.errors().size());
    }

    @Test
    public void ruleset_cascades_to_child_collection_validator() {
        Person person = new Person();
        List<Order> orderList = new ArrayList<>();
        orderList.add(new Order());
        orderList.add(new Order());
        person.orders = orderList;
        final InlineValidator<Order> orderValidator = new InlineValidator<>(Order.class);
        orderValidator.ruleSet("Test", new Action() {
            @Override
            public void doAction() {
                orderValidator.ruleFor( Order.class,"productName").notNull();
            }
        });
        final TestValidator validator = new TestValidator();
        validator.ruleSet("Test", new Action() {
            @Override
            public void doAction() {
                validator.ruleFor(Person.class, "orders").setCollectionValidator(orderValidator);
            }
        });

        ValidationResult result = validator.validate(new ValidationContext(person, new PropertyChain(), new RulesetValidatorSelector("Test")));
        assertEquals(2, result.errors().size());
    }

    @Test
    public void executes_multiple_rulesets() {
        Person person = new Person();
        //final TestValidator validator = new TestValidator();
        validator.ruleSet("Id", new Action() {
            @Override
            public void doAction() {
                validator.ruleFor(Person.class, "id").notEqual(0);
            }
        });

        ValidationResult result = validator.validate(new ValidationContext(person, new PropertyChain(), new RulesetValidatorSelector("Names", "Id")));
        assertEquals(3, result.errors().size());
    }

    @Test
    public void executes_all_rules() {
        Person person = new Person();
        //TestValidator validator = new TestValidator();
        ValidationResult result = validator.validate(person, null, new String[]{"*"});
        assertEquals(3, result.errors().size());
    }

    @Test
    public void executes_rules_in_default_ruleset_and_specific_ruleset() {
        //final TestValidator validator = new TestValidator();
        Person person = new Person();
        validator.ruleSet("foo", new Action() {
            @Override
            public void doAction() {
                validator.ruleFor(Person.class, "age").notEqual(0);
            }
        });
        ValidationResult result = validator.validate(person, null, new String[]{"*","Names"});
        assertEquals(3, result.errors().size());
    }

    /*private class TestValidator extends AbstractValidator<Person> {

        public TestValidator(){
            super(Person.class);
            ruleSet("Names", new Action() {
                @Override
                public void doAction() {
                    ruleFor(Person.class, "surname").notNull();
                    ruleFor(Person.class, "forename").notNull();
                }
            });
            ruleFor(Person.class, "id").notEmpty();
        }
    }*/
}
