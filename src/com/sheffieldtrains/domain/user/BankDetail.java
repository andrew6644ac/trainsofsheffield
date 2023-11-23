package com.sheffieldtrains.domain.user;

import java.util.Date;

public class BankDetail {
    private String bankCardName;
    private String cardHolderName;
    private String bankCardNumber;
    private Date cardExpiryDate;
    private int securityCode;


    public BankDetail(String bankCardName,
                      String cardHolderName,
                      String bankCardNumber,
                      Date cardExpiryDate,
                      int securityCode) {
        this.bankCardName = bankCardName;
        this.cardHolderName = cardHolderName;
        this.bankCardNumber = bankCardNumber;
        this.cardExpiryDate = cardExpiryDate;
        this.securityCode = securityCode;
    }

    public String getBankCardName() {
        return bankCardName;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public String getBankCardNumber() {
        return bankCardNumber;
    }

    public Date getCardExpiryDate() {
        return cardExpiryDate;
    }

    public int getSecurityCode() {
        return securityCode;
    }
}
