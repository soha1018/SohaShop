package com.itsoha.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * 购物车整体
 */
public class Cart {
    private double totalMoney;

    private Map<String, CartItem> map = new HashMap<>();

    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Map<String, CartItem> getMap() {
        return map;
    }

    public void setMap(Map<String, CartItem> map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "totalMoney=" + totalMoney +
                ", map=" + map +
                '}';
    }
}
