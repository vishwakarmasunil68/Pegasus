package com.bjain.pegasus.pojo.newarrival;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sunil on 08-06-2017.
 */

public class NewArrivalPOJO {
    @SerializedName("success")
    String success;
    @SerializedName("result")
    List<NewArrivalResultPOJO> newArrivalResultPOJOList;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<NewArrivalResultPOJO> getNewArrivalResultPOJOList() {
        return newArrivalResultPOJOList;
    }

    public void setNewArrivalResultPOJOList(List<NewArrivalResultPOJO> newArrivalResultPOJOList) {
        this.newArrivalResultPOJOList = newArrivalResultPOJOList;
    }

    @Override
    public String toString() {
        return "NewArrivalPOJO{" +
                "success='" + success + '\'' +
                ", newArrivalResultPOJOList=" + newArrivalResultPOJOList +
                '}';
    }
}
