package test.validation.model;

/**
 * Created by z_wu on 2014/12/17.
 */
public interface IAddress {
    String getLine1();
    String getLine2();
    String getTown();
    String getCounty();
    String getPostcode();
    Country getCountry();
}
