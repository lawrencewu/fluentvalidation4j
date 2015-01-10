package test.validation.model;

import com.sun.istack.internal.Nullable;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

/**
 * Created by z_wu on 2014/12/17.
 */
public class Person {
    public String nameField;
    public int id;
    public String surname ;
    private String forename ;
    public List<Person> children ;
    public String[] nickNames;
    public Date dateOfBirth ;

    @Nullable
    public Integer nullableInt;

    public Person() {
        children = new ArrayList<>();
        orders = new ArrayList<>();
    }

    public int calculateSalary() {
        return 20;
    }
    public Address address;
    public List<Order> orders ;
    public String email;
    public DecimalFormat discount;
    public double age;
    public int anotherInt;
    public String creditCard ;
    public int otherNullableInt;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getNameField() {
        return nameField;
    }

    public void setNameField(String nameField) {
        this.nameField = nameField;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public List<Person> getChildren() {
        return children;
    }

    public void setChildren(List<Person> children) {
        this.children = children;
    }

    public String[] getNickNames() {
        return nickNames;
    }

    public void setNickNames(String[] nickNames) {
        this.nickNames = nickNames;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getNullableInt() {
        return nullableInt;
    }

    public void setNullableInt(@Nullable int nullableInt) {
        this.nullableInt = nullableInt;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public DecimalFormat getDiscount() {
        return discount;
    }

    public void setDiscount(DecimalFormat discount) {
        this.discount = discount;
    }

    public double getAge() {
        return age;
    }

    public void setAge(double age) {
        this.age = age;
    }

    public int getAnotherInt() {
        return anotherInt;
    }

    public void setAnotherInt(int anotherInt) {
        this.anotherInt = anotherInt;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public int getOtherNullableInt() {
        return otherNullableInt;
    }

    public void setOtherNullableInt(int otherNullableInt) {
        this.otherNullableInt = otherNullableInt;
    }
}











