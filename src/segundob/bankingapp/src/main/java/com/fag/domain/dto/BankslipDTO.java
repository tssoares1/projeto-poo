package com.fag.domain.dto;

public class BankslipDTO {

    private String barcode;

    private String transactionId;

    private Double value;

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getValue() {
        return value;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

}