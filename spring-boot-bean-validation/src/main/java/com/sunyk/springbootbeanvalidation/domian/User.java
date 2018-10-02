package com.sunyk.springbootbeanvalidation.domian;

import com.sunyk.springbootbeanvalidation.validation.constraints.CardNumberValidation;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

/**
 * Create by sunyang on 2018/10/2 20:01
 * For me:One handred lines of code every day,make myself stronger.
 */
@Repository
public class User {

    @Max(value = 1000000)
    public long id;

    public String name;

    @NotNull
    @CardNumberValidation
    public String cardNumber;//TUHU-123456789

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
                ", cardNumber='" + cardNumber + '\'' +
                '}';
    }
}
