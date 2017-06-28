package com.bjain.pegasus.pojo.newsearch;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sunil on 24-06-2017.
 */

public class NewSearchPOJO {
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
        return "NewSearchPOJO{" +
                "success='" + success + '\'' +
                ", stringList=" + stringList +
                '}';
    }
}
