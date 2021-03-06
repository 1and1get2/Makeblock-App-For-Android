package com.lee.loollool.bean;

import java.util.HashMap;
import java.util.Map;

public class ComplexItem {

    private Map<String, String> list = new HashMap<String, String>();

    public ComplexItem() {

    }

    public void put(String key, String value) {
        list.put(key, value);
    }

    public void put(String key, int value) {
        list.put(key, Integer.toString(value));
    }

    public String get(String key) {
        return list.get(key);
    }

    public int getInteger(String key) {
        return Integer.parseInt(list.get(key));
    }
}
