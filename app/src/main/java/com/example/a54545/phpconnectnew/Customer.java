package com.example.a54545.phpconnectnew;

/**
 * Created by 54545 on 2018/3/28.
 */

import java.io.Serializable;
import java.math.BigDecimal;

public class Customer implements Serializable{
    private int id;
    private String name;
    private String password;
    private String sex;
    private String phone;
    private String address;
    private BigDecimal cash;
    private int times;
    private int A_id;
    public Customer(){};
    public Customer(int id, String name, String password, String sex, String phone, String address, BigDecimal cash, int times, int a_id) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.sex = sex;
        this.phone = phone;
        this.address = address;
        this.cash = cash;
        this.times = times;
        A_id = a_id;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public int getA_id() {
        return A_id;
    }

    public void setA_id(int a_id) {
        A_id = a_id;
    }
}
