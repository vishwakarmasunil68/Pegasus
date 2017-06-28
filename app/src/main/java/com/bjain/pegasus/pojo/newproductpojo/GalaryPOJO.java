package com.bjain.pegasus.pojo.newproductpojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 05-06-2017.
 */

public class GalaryPOJO {
    @SerializedName("value")
    String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "GalaryPOJO{" +
                "value='" + value + '\'' +
                '}';
    }
}
