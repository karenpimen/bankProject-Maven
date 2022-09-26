package com.jmp.dto;

public class BankCard {

    public BankCard(String number, User user) {
        this.number = number;
        this.user = user;
    }

    String number;
    User user;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
