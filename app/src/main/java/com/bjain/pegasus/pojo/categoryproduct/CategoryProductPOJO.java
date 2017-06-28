package com.bjain.pegasus.pojo.categoryproduct;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sunil on 30-05-2017.
 */

public class CategoryProductPOJO {
    @SerializedName("data")
    List<CategoryProductResultPOJO> categoryProductResultPOJOs;

    public List<CategoryProductResultPOJO> getCategoryProductResultPOJOs() {
        return categoryProductResultPOJOs;
    }

    public void setCategoryProductResultPOJOs(List<CategoryProductResultPOJO> categoryProductResultPOJOs) {
        this.categoryProductResultPOJOs = categoryProductResultPOJOs;
    }

    @Override
    public String toString() {
        return "CategoryProductPOJO{" +
                "categoryProductResultPOJOs=" + categoryProductResultPOJOs.toString() +
                '}';
    }
}
