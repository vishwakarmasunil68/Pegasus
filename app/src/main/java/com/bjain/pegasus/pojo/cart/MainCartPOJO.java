package com.bjain.pegasus.pojo.cart;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunil on 01-06-2017.
 */

public class MainCartPOJO implements Serializable {
    String grand_total;
    List<CartDataPOJO> cartDataPOJOList;

    public List<CartDataPOJO> getCartDataPOJOList() {
        return cartDataPOJOList;
    }

    public void setCartDataPOJOList(List<CartDataPOJO> cartDataPOJOList) {
        this.cartDataPOJOList = cartDataPOJOList;
    }

    @Override
    public String toString() {
        return "MainCartPOJO{" +
                "cartDataPOJOList=" + cartDataPOJOList +
                '}';
    }

    public String getGrand_total() {
        return grand_total;
    }

    public void setGrand_total(String grand_total) {
        this.grand_total = grand_total;
    }
}
