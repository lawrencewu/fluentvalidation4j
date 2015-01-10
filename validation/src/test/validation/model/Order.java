package test.validation.model;

import java.text.DecimalFormat;

/**
 * Created by z_wu on 2014/12/17.
 */
public class Order implements IOrder{
    public String productName ;
    public int amount;

    @Override
    public int getAmount() {
        return amount;
    }
}
