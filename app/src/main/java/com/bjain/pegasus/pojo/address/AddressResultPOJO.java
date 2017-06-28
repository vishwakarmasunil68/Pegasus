package com.bjain.pegasus.pojo.address;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 06-05-2017.
 */

public class AddressResultPOJO {
    @SerializedName("entity_id")
    private String entity_id;
    @SerializedName("value")
    private String value;
    @SerializedName("attribute_id")
    private String attribute_id;

    public String getEntity_id() {
        return entity_id;
    }

    public void setEntity_id(String entity_id) {
        this.entity_id = entity_id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getAttribute_id() {
        return attribute_id;
    }

    public void setAttribute_id(String attribute_id) {
        this.attribute_id = attribute_id;
    }

    @Override
    public String toString() {
        return "AddressResultPOJO{" +
                "entity_id='" + entity_id + '\'' +
                ", value='" + value + '\'' +
                ", attribute_id='" + attribute_id + '\'' +
                '}';
    }
}
