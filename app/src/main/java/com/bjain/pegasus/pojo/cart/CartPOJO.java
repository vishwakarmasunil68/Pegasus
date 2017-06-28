package com.bjain.pegasus.pojo.cart;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunil on 31-05-2017.
 */

public class CartPOJO implements Serializable {
    @SerializedName("success")
    String success;
    @SerializedName("grand_total")
    String grand_total;
    @SerializedName("result")
    List<CartAttrPOJO> cartAttrPOJOList;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<CartAttrPOJO> getCartAttrPOJOList() {
        return cartAttrPOJOList;
    }

    public void setCartAttrPOJOList(List<CartAttrPOJO> cartAttrPOJOList) {
        this.cartAttrPOJOList = cartAttrPOJOList;
    }

    public void setGrand_total(String grand_total) {
        this.grand_total = grand_total;
    }

    public String getGrand_total() {
        return grand_total;
    }

    @Override
    public String toString() {
        return "CartPOJO{" +
                "success='" + success + '\'' +
                ", cartAttrPOJOList=" + cartAttrPOJOList +
                '}';
    }
}
