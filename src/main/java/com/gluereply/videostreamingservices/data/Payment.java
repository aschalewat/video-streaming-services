package com.gluereply.videostreamingservices.data;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Size;

public class Payment {

    private long id;
    @Size(min=3, max=5)
    private String username;
    private String creditCard;
    private int amount;


    public Payment(long id, String username, String creditCard, int amount) {
        this.id = id;
        this.username = username;
        this.creditCard = creditCard;
        this.amount = amount;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
