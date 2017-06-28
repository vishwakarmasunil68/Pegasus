package com.bjain.pegasus.pojo.newproductpojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sunil on 05-06-2017.
 */

public class NewProductPOJO {
    @SerializedName("success")
    String success;
    @SerializedName("result")
    List<NewProductResultPOJO> newProductResultPOJOList;
    @SerializedName("Galary")
    List<GalaryPOJO> galaryPOJOList;
    @SerializedName("pgsprice")
    PricePOJO pricePOJO;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<NewProductResultPOJO> getNewProductResultPOJOList() {
        return newProductResultPOJOList;
    }

    public void setNewProductResultPOJOList(List<NewProductResultPOJO> newProductResultPOJOList) {
        this.newProductResultPOJOList = newProductResultPOJOList;
    }

    public List<GalaryPOJO> getGalaryPOJOList() {
        return galaryPOJOList;
    }

    public void setGalaryPOJOList(List<GalaryPOJO> galaryPOJOList) {
        this.galaryPOJOList = galaryPOJOList;
    }

    public PricePOJO getPricePOJO() {
        return pricePOJO;
    }

    public void setPricePOJO(PricePOJO pricePOJO) {
        this.pricePOJO = pricePOJO;
    }

    @Override
    public String toString() {
        return "NewProductPOJO{" +
                "success='" + success + '\'' +
                ", newProductResultPOJOList=" + newProductResultPOJOList +
                ", galaryPOJOList=" + galaryPOJOList +
                ", pricePOJO=" + pricePOJO +
                '}';
    }
}
