package com.bjain.pegasus.pojo.gradeapi;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sunil on 25-06-2017.
 */

public class GradePOJO {
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
        return "GradePOJO{" +
                "success='" + success + '\'' +
                ", stringList=" + stringList +
                '}';
    }
}
