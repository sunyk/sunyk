package com.sunyk.springbootjdbc.domian;

/**
 * Create by sunyang on 2018/9/24 17:44
 * For me:One handred lines of code every day,make myself stronger.
 */
public class User {

    private int id;

    private String name;

    private String cardNumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
