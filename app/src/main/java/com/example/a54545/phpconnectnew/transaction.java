package com.example.a54545.phpconnectnew;

import java.util.ArrayList;
//用于activity与fragment间的交互
/**
 * Created by 54545 on 2018/4/6.
 */

public interface transaction {
    String getAccount();
    Customer getCustomer();
    ArrayList<Order> getOrderList();
    ArrayList<Product>getProductList();
}
