package test.validation;

import com.lawrence.oss.validation.AbstractValidator;
import com.lawrence.oss.validation.Predicate;
import com.lawrence.oss.validation.Predicate1;
import com.lawrence.oss.validation.results.ValidationResult;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import validation.model.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by z_wu on 2014/12/25.
 */
public class CollectionValidatorTests {
    TestValidator validator;
    Person person;
    @Before
    public void setUp() throws Exception {
        validator = new TestValidator();
        person = new Person();
        Country country = new Country();
        Address address = new Address();
        address.country = country;
        List<Order> orders = new ArrayList<>();
        Order order1 = new Order();
        order1.amount = 5;
        Order order2 = new Order();
        order2.productName = "Foo";
        orders.add(order1);
        orders.add(order2);
        person.address = address;
        person.orders = orders;
    }

    @After
    public void tearDown(){
        validator = null;
        person = null;
    }

    @Test
    public void validates_collection() {
        TestValidator validator = new TestValidator();
        validator.ruleFor(Person.class, "surname").notNull();
        validator.ruleFor(Person.class, "orders").setCollectionValidator(new OrderValidator());
        ValidationResult result = validator.validate(person);
        int count = result.errors().size();
        assertEquals(3, count);
    }

    @Test
    public void can_validate_collection_using_validator_for_base_type() {
        TestValidator validator = new TestValidator();
        validator.ruleFor(Person.class, "orders").setCollectionValidator(new OrderInterfaceValidator());
        ValidationResult result = validator.validate(person);
        assertFalse(result.isValid());
    }

    @Test
    public void specifiy_condition_for_individual_collection_elements() {
        validator = new TestValidator();
        validator.ruleFor(Person.class, "orders").setCollectionValidator(new OrderValidator()).where(new Predicate1<Order>() {
            @Override
            public boolean doPredicate(Order order) {
                return order.productName != null;
            }
        });

        ValidationResult result = validator.validate(person);
        int count = result.errors().size();
        assertEquals(1, count);
    }

    @Test
    public void collection_should_be_explicitly_included_with_string() {
        validator = new TestValidator();
        validator.ruleFor(Person.class, "surname").notNull();
        validator.ruleFor(Person.class, "orders").setCollectionValidator(new OrderValidator());
        ValidationResult result = validator.validate(person, "orders");
        int count = result.errors().size();
        assertEquals(2, count);
    }

    @Test
    public void collection_should_be_excluded() {
        validator = new TestValidator();
        validator.ruleFor(Person.class, "surname").notNull();
        validator.ruleFor(Person.class, "orders").setCollectionValidator(new OrderValidator());
        ValidationResult result = validator.validate(person, "forename");
        int count = result.errors().size();
        assertEquals(0, count);
    }

    @Test
    public void condition_should_work_with_child_collection() {
        validator = new TestValidator();
        validator.ruleFor(Person.class, "orders").setCollectionValidator(new OrderValidator()).when(person.orders.size() == 3);
        ValidationResult result = validator.validate(person);
        assertTrue(result.isValid());
    }

    public class OrderValidator extends AbstractValidator<Order> {
        public OrderValidator() {
            super(Order.class);
            ruleFor(Order.class, "productName").notEmpty();
            ruleFor(Order.class, "amount").notEqual(0);
        }
    }

    public class OrderInterfaceValidator extends AbstractValidator<IOrder> {
        public OrderInterfaceValidator() {
            super(IOrder.class);
            ruleFor(IOrder.class, "amount").notEqual(0);
        }
    }
}
