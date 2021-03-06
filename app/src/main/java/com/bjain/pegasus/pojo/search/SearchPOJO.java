package com.bjain.pegasus.pojo.search;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sunil on 26-05-2017.
 */

public class SearchPOJO {
    @SerializedName("success")
    String success;
    @SerializedName("result")
    List<String> stringList;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<String> getStringList() {
        return stringList;
    }

    public void setStringList(List<String> stringList) {
        this.stringList = stringList;
    }

    @Override
    public String toString() {
        return "SearchPOJO{" +
                "success='" + success + '\'' +
                ", stringList=" + stringList +
                '}';
    }
}
