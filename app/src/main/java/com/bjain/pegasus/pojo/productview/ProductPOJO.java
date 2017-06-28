package com.bjain.pegasus.pojo.productview;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sunil on 30-05-2017.
 */

public class ProductPOJO {
    @SerializedName("success")
    String success;
    @SerializedName("result")
    List<String> prodStringList;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<String> getProdStringList() {
        return prodStringList;
    }

    public void setProdStringList(List<String> prodStringList) {
        this.prodStringList = prodStringList;
    }

    @Override
    public String toString() {
        return "ProductPOJO{" +
                "success='" + success + '\'' +
                ", prodStringList=" + prodStringList +
                '}';
    }
}
