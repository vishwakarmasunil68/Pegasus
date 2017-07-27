package com.bjain.pegasus.pojo.newbookitopojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 10-07-2017.
 */

public class NewBookitoResultPOJO {
    @SerializedName("value_id")
    String value_id;
    @SerializedName("entity_type_id")
    String entity_type_id;
    @SerializedName("attribute_id")
    String attribute_id;
    @SerializedName("store_id")
    String store_id;
    @SerializedName("entity_id")
    String entity_id;
    @SerializedName("value")
    String value;
    @SerializedName("category_id")
    String category_id;
    @SerializedName("product_id")
    String product_id;
    @SerializedName("position")
    String position;
    @SerializedName("is_parent")
    String is_parent;
    @SerializedName("discount_price")
    String discount_price;
    @SerializedName("main_price")
    String main_price;
    @SerializedName("product_image")
    String product_image;
    @SerializedName("sku")
    String sku;
    @SerializedName("image_url")
    String image_url;



    public String getValue_id() {
        return value_id;
    }

    public void setValue_id(String value_id) {
        this.value_id = value_id;
    }

    public String getEntity_type_id() {
        return entity_type_id;
    }

    public void setEntity_type_id(String entity_type_id) {
        this.entity_type_id = entity_type_id;
    }

    public String getAttribute_id() {
        return attribute_id;
    }

    public void setAttribute_id(String attribute_id) {
        this.attribute_id = attribute_id;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

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

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getIs_parent() {
        return is_parent;
    }

    public void setIs_parent(String is_parent) {
        this.is_parent = is_parent;
    }

    public String getDiscount_price() {
        return discount_price;
    }

    public void setDiscount_price(String discount_price) {
        this.discount_price = discount_price;
    }

    public String getMain_price() {
        return main_price;
    }

    public void setMain_price(String main_price) {
        this.main_price = main_price;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    @Override
    public String toString() {
        return "NewBookitoResultPOJO{" +
                "value_id='" + value_id + '\'' +
                ", entity_type_id='" + entity_type_id + '\'' +
                ", attribute_id='" + attribute_id + '\'' +
                ", store_id='" + store_id + '\'' +
                ", entity_id='" + entity_id + '\'' +
                ", value='" + value + '\'' +
                ", category_id='" + category_id + '\'' +
                ", product_id='" + product_id + '\'' +
                ", position='" + position + '\'' +
                ", is_parent='" + is_parent + '\'' +
                ", discount_price='" + discount_price + '\'' +
                ", main_price='" + main_price + '\'' +
                ", product_image='" + product_image + '\'' +
                ", sku='" + sku + '\'' +
                '}';
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
