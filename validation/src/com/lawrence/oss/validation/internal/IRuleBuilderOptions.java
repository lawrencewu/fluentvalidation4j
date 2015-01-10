package com.lawrence.oss.validation.internal;

import com.lawrence.oss.validation.*;
import com.lawrence.oss.validation.util.Utils;
import com.lawrence.oss.validation.exception.ValidationException;
import com.lawrence.oss.validation.results.ValidationResult;
import com.lawrence.oss.validation.validators.*;
import java.lang.reflect.Member;
import java.util.Comparator;

/**
 * Created by z_wu on 2014/12/9.
 */
public abstract class IRuleBuilderOptions<T> implements IRuleBuilder<T>, IConfigurable<PropertyRule, IRuleBuilderOptions<T>> {
    public IRuleBuilderOptions(){
    }

    /**
     * Defines a 'not empty' validator on the current rule builder.
     * @return
     */
    public IRuleBuilderOptions notEmpty() {
        String propertyName = ((RuleBuilder)this).getRule().propertyName;
        Class<?> currentClazz = ((RuleBuilder)this).getRule().clazz;
        Object value = Utils.getDefaultValueForProperty(currentClazz, propertyName);
        setValidator(new NotEmptyValidator(value));
        return this;
    }

    /**
     * Defines a 'not null' validator on the current rule builder.
     * Validation will fail if the property is null.
     * Validation will fail if the property is null, an empty or the default value for the type (for example, 0 for integers)
     * @return
     */
    public IRuleBuilderOptions notNull() {
        setValidator(new NotNullValidator());
        return this;
    }

    /**
     * Defines a length validator on the current rule builder, but only for string properties.
     * Validation will fail if the length of the string is outside of the specifed range. The range is inclusive.
     * @param min
     * @param max
     * @return
     */
    public IRuleBuilderOptions length(int min, int max) {
        setValidator(new LengthValidator(min, max));
        return this;
    }

    /**
     * Defines a length validator on the current rule builder, but only for string properties.
     * Validation will fail if the length of the string is not equal to the length specified.
     * @param exactLength
     * @return
     */
    public IRuleBuilderOptions length(int exactLength) {
        setValidator(new ExactLengthValidator(exactLength));
        return this;
    }

    /**
     * Defines a regular expression validator on the current rule builder, but only for string properties.
     * Validation will fail if the value returned by the lambda does not match the regular expression.
     * @param expression
     * @return
     */
    public IRuleBuilderOptions matches(String expression) {
        setValidator(new RegularExpressionValidator(expression));
        return this;
    }

    /**
     * Defines a regular expression validator on the current rule builder, but only for string properties.
     * Validation will fail if the value returned by the lambda is not a valid email address.
     * @return
     */
    public IRuleBuilderOptions emailAddress() {
        setValidator(new EmailValidator());
        return this;
    }

    /**
     * Defines a 'not equal' validator on the current rule builder.
     * Validation will fail if the specified value is equal to the value of the property.
     * @param toCompare
     * @param <TProperty>
     * @return
     */
    public <TProperty> IRuleBuilderOptions notEqual(TProperty toCompare) {
        notEqual(toCompare, null);
        return this;
    }

    public <TProperty> IRuleBuilderOptions notEqual(TProperty toCompare, Comparator comparator) {
        setValidator(new NotEqualValidator(toCompare, comparator));
        return this;
    }

    public IRuleBuilderOptions notEqual(Class<?> clazz,String property) {
        notEqual(clazz, property, null);
        return this;
    }

    public IRuleBuilderOptions notEqual(Class<?> clazz,String property, Comparator comparator) {
        setValidator(new NotEqualValidator(clazz, property, comparator));
        return this;
    }

    /**
     * Defines an 'equals' validator on the current rule builder.
     * Validation will fail if the specified value is not equal to the value of the property.
     * @param toCompare
     * @param <TProperty>
     * @return
     */
    public <TProperty> IRuleBuilderOptions equal(TProperty toCompare) {
        setValidator(new EqualValidator(toCompare, null));
        return this;
    }

    public <TProperty> IRuleBuilderOptions equal(TProperty toCompare, Comparator comparator) {
        setValidator(new EqualValidator(toCompare, comparator));
        return this;
    }

    public IRuleBuilderOptions equal(Class<?> clazz,String property, Comparator comparator) {
        setValidator(new EqualValidator(clazz, property, comparator));
        return this;
    }

    public IRuleBuilderOptions equal(Class<?> clazz,String property) {
        setValidator(new EqualValidator(clazz, property, null));
        return this;
    }

    /**
     * Defines a 'less than' validator on the current rule builder.
     * The validation will succeed if the property value is less than the specified value.
     * The validation will fail if the property value is greater than or equal to the specified value.
     * @param valueToCompare
     * @param <TProperty>
     * @return
     */
    public <TProperty extends Comparable> IRuleBuilderOptions lessThan(TProperty valueToCompare) {
        setValidator(new LessThanValidator(valueToCompare));
        return this;
    }

    /**
     * Defines a 'less than' validator on the current rule builder with specified property.
     * The validation will succeed if the property value is less than the specified value.
     * The validation will fail if the property value is greater than or equal to the specified value.
     * @param clazz
     * @param property
     * @return
     */
    public IRuleBuilderOptions lessThan(Class<?>  clazz, String property) {
        setValidator(new LessThanValidator(clazz, property));
        return this;
    }

    /**
     * Defines a 'less than or equal' validator on the current rule builder.
     * The validation will succeed if the property value is less than or equal to the specified value.
     * The validation will fail if the property value is greater than the specified value.
     * @param valueToCompare
     * @param <TProperty>
     * @return
     */
    public <TProperty extends Comparable> IRuleBuilderOptions lessThanOrEqualTo(TProperty valueToCompare) {
        setValidator(new LessThanOrEqualValidator(valueToCompare));
        return this;
    }

    /**
     * Defines a 'less than or equal' validator on the current rule builder with specified property.
     * The validation will succeed if the property value is less than or equal to the specified value.
     * The validation will fail if the property value is greater than the specified value.
     * @param clazz
     * @param property
     * @return
     */
    public <TProperty extends Comparable> IRuleBuilderOptions lessThanOrEqualTo(Class<?> clazz, String property) {
        setValidator(new LessThanOrEqualValidator(clazz, property));
        return this;
    }

    /**
     * Defines a 'greater than' validator on the current rule builder.
     * The validation will succeed if the property value is greater than the specified value.
     * The validation will fail if the property value is less than or equal to the specified value.
     * @param valueToCompare
     * @param <TProperty>
     * @return
     */
    public <TProperty extends Comparable> IRuleBuilderOptions greaterThan(TProperty valueToCompare) {
        setValidator(new GreaterThanValidator(valueToCompare));
        return this;
    }

    public <TProperty extends Comparable> IRuleBuilderOptions greaterThan(Class<?> clazz, String property) {
        setValidator(new GreaterThanValidator(clazz, property));
        return this;
    }

    /**
     * Defines a 'greater than or equal' validator on the current rule builder.
     * The validation will succeed if the property value is greater than or equal the specified value.
     * The validation will fail if the property value is less than the specified value.
     * @param valueToCompare
     * @param <TProperty>
     * @return
     */
    public <TProperty extends Comparable> IRuleBuilderOptions greaterThanOrEqualTo(TProperty valueToCompare) {
        setValidator(new GreaterThanOrEqualValidator(valueToCompare));
        return this;
    }

    /**
     * Defines a 'greater than or equal' validator on the current rule builder with specified property.
     * The validation will succeed if the property value is greater than or equal the specified value.
     * The validation will fail if the property value is less than the specified value.
     * @param clazz
     * @param property
     * @return
     */
    public IRuleBuilderOptions greaterThanOrEqualTo(Class<?> clazz, String property) {
        setValidator(new GreaterThanOrEqualValidator(clazz, property));
        return this;
    }

    /**
     * Defines an 'inclusive between' validator on the current rule builder, but only for properties that is Comparable.
     * Validation will fail if the value of the property is outside of the specifed range. The range is inclusive.
     * @param from
     * @param to
     * @param <TProperty>
     * @return
     */
    public <TProperty extends Comparable> IRuleBuilderOptions inclusiveBetween(TProperty from, TProperty to) {
        setValidator(new InclusiveBetweenValidator(from, to));
        return this;
    }

    /**
     * Defines an 'inclusive between' validator on the current rule builder, but only for properties that is Comparable.
     * Validation will fail if the value of the property is outside of the specifed range. The range is inclusive.
     * @param from
     * @param to
     * @param <TProperty>
     * @return
     */
    public <TProperty extends Comparable> IRuleBuilderOptions exclusiveBetween(TProperty from, TProperty to) {
        setValidator(new ExclusiveBetweenValidator(from, to));
        return this;
    }

    /**
     * Specifies a condition limiting when the validator should run.
     * @param predicate
     * @param <T>
     * @return
     */
    public <T> IRuleBuilderOptions when(Boolean predicate){

        return when(predicate, ApplyConditionTo.AllValidators);
    }

    public <T> IRuleBuilderOptions when(final Boolean predicate, final ApplyConditionTo applyConditionTo){
        Utils.checkArgument(predicate, "Predicate should not be null for calling");
        return this.configure(new Action1<PropertyRule>() {
            @Override
            public void doAction(PropertyRule rule) {
                rule.applyCondition(predicate, applyConditionTo);
            }
        });
    }

    /**
     * Specifies a condition limiting when the validator should not run.
     * @param predicate
     * @return
     */
    public IRuleBuilderOptions unless(Predicate predicate){
        return this.unless(predicate, ApplyConditionTo.AllValidators);
    }

    public IRuleBuilderOptions unless(Predicate predicate, ApplyConditionTo applyConditionTo){
        return this.when(!predicate.doPredicate(), applyConditionTo);
    }

    public <T> IRuleBuilderOptions cascade(final CascadeMode cascadeMode){
        return this.configure(new Action1<PropertyRule>() {
            @Override
            public void doAction(PropertyRule instance) {
                instance.setCascadeMode(cascadeMode);
            }
        });
    }

    public ICollectionValidatorRuleBuilder setCollectionValidator(IValidator validator){
        ChildCollectionValidatorAdaptor adaptor = new ChildCollectionValidatorAdaptor(validator);
        this.setValidator(adaptor);
        return new CollectionValidatorRuleBuilder(this, adaptor);
    }

    public class CollectionValidatorRuleBuilder extends ICollectionValidatorRuleBuilder{
        IRuleBuilder ruleBuilder;
        ChildCollectionValidatorAdaptor adaptor;

        public CollectionValidatorRuleBuilder(IRuleBuilder ruleBuilder, ChildCollectionValidatorAdaptor adaptor){
            this.ruleBuilder = ruleBuilder;
            this.adaptor = adaptor;
        }

        @Override
        public IRuleBuilderOptions setValidator(IPropertyValidator validator) {
            return ruleBuilder.setValidator(validator);
        }

        @Override
        public IRuleBuilderOptions setValidator(IValidator validator) {
            return ruleBuilder.setValidator(validator);
        }

        @Override
        public Object configure(Action1 configure) {
            return ((IRuleBuilderOptions)ruleBuilder).configure(configure);
        }

        @Override
        public ICollectionValidatorRuleBuilder where(Predicate1 predicate){
            Utils.checkArgument(predicate, "predicate should ne be null for where calling");
            adaptor.predicate = predicate;
            return this;
        }
    }
}
