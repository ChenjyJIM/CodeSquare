package com.example.a54545.phpconnectnew.entity;

import java.io.Serializable;

/**
 * Created by 54545 on 2018/4/8.
 */

/**
 * 产品实体类
 */
public class Product implements Serializable {
        public void Product(){};
        private int id;
        private String name;
        private Double price;
        private String description;

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
