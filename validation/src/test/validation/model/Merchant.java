package test.validation.model;

/**
 * Created by z_wu on 2014/12/11.
 */
public class Merchant {
    private String merchantName;
    private long merchantId;
    private int numberOfOffice;

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(long merchantId) {
        this.merchantId = merchantId;
    }

    public int getNumberOfOffice() {
        return numberOfOffice;
    }

    public void setNumberOfOffice(int numberOfOffice) {
        this.numberOfOffice = numberOfOffice;
    }
}
