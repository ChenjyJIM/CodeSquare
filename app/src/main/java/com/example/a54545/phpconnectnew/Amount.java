package com.example.a54545.phpconnectnew;

import java.io.Serializable;

/**
 * Created by 54545 on 2018/3/28.
 */


public class Amount implements Serializable{
    private String P_name;
    private int P_price;
    private int O_number;
    private int amount;
    public Amount(){}

    public Amount(String p_name, int p_price, int o_number, int amount) {
        P_name = p_name;
        P_price = p_price;
        O_number = o_number;
        this.amount = amount;
    }

    public String getP_name() {
        return P_name;
    }

    public void setP_name(String p_name) {
        P_name = p_name;
    }

    public int getP_price() {
        return P_price;
    }

    public void setP_price(int p_price) {
        P_price = p_price;
    }

    public int getO_number() {
        return O_number;
    }

    public void setO_number(int o_number) {
        O_number = o_number;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
    public String toString(){
        return "      "+this.P_name+"             "+Integer.toString(this.P_price)+"             "+Integer.toString(this.O_number)+"                 "+Integer.toString(this.amount);
    }
}
