package com.example.a54545.phpconnectnew.transaction;

import com.example.a54545.phpconnectnew.entity.Customer;
import com.example.a54545.phpconnectnew.entity.Order;
import com.example.a54545.phpconnectnew.entity.Product;

import java.util.ArrayList;

/**
 * Created by 54545 on 2018/4/6.
 */

/**
 * 此接口用于activity与fragment之间的交互
 * activity实现该接口中的方法，在fragment中的onattach方法中可以调用该接口的方法来获取数据。
 */
public interface transaction {
    String getAccount();
    Customer getCustomer();
    ArrayList<Order> getOrderList();
    ArrayList<Product>getProductList();
}
