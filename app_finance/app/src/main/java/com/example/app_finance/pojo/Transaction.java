package com.example.app_finance.pojo;

import java.io.Serializable;

public class Transaction implements Serializable {
    public int id;
    public String name;
    public float value;
    public String category;
    public String type;
    public String date;

    //CONTRUCTORS
    public Transaction(){
    }

    public Transaction(String name, float value, String category, String type) {
        this.name = name;
        this.value = value;
        this.category = category;
        this.type = type;
    }

    public Transaction(int id, String name, float value, String category, String type, String date) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.category = category;
        this.type = type;
        this.date = date;
    }

    // GETTERS AND SETTERS
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

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
