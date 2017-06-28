package com.bjain.pegasus.pojo.relatedproduct;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sunil on 30-05-2017.
 */

public class RelatedPOJO {
    @SerializedName("success")
    String success;
    @SerializedName("result")
    List<RelatedProductPOJO> relatedProductPOJOList;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<RelatedProductPOJO> getRelatedProductPOJOList() {
        return relatedProductPOJOList;
    }

    public void setRelatedProductPOJOList(List<RelatedProductPOJO> relatedProductPOJOList) {
        this.relatedProductPOJOList = relatedProductPOJOList;
    }

    @Override
    public String toString() {
        return "RelatedPOJO{" +
                "success='" + success + '\'' +
                ", relatedProductPOJOList=" + relatedProductPOJOList +
                '}';
    }
}
