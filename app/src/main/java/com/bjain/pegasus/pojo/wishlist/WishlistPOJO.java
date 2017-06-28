package com.bjain.pegasus.pojo.wishlist;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sunil on 31-05-2017.
 */

public class WishlistPOJO {
    @SerializedName("success")
    String success;
    @SerializedName("result")
    List<WishListDataPOJO> wishListDataPOJOs;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<WishListDataPOJO> getWishListDataPOJOs() {
        return wishListDataPOJOs;
    }

    public void setWishListDataPOJOs(List<WishListDataPOJO> wishListDataPOJOs) {
        this.wishListDataPOJOs = wishListDataPOJOs;
    }

    @Override
    public String toString() {
        return "WishlistPOJO{" +
                "success='" + success + '\'' +
                ", wishListDataPOJOs=" + wishListDataPOJOs +
                '}';
    }
}
