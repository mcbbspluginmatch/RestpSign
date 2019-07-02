package com.ayou.restpsign.signs;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Ayou
 * @Date: 2019/7/2 18:29
 */
public class TpsignVault {
    private String perm;
    private double money;
    private double editmoney;
    private double discount;

    public TpsignVault(String perm,double money,double editmoney,double discount) {
        this.perm = perm;
        this.money = money;
        this.editmoney= editmoney;
        this.discount = discount;
    }

    public String getPerm() {
        return perm;
    }

    public double getEditmoney() {
        return editmoney;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public void setPerm(String perm) {
        this.perm = perm;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "TpsignVault{" +
                "perm='" + perm + '\'' +
                ", money=" + money +
                ", editmoney=" + editmoney +
                ", discount=" + discount +
                '}';
    }
}
