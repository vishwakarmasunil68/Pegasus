package com.bjain.pegasus.pojo.search;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 26-05-2017.
 */

public class SearchResultPOJO {
    @SerializedName("product_id")
    String product_id;
    @SerializedName("name")
    String name;
    @SerializedName("price")
    String price;
    @SerializedName("short_description")
    String short_description;
    @SerializedName("sku")
    String sku;
    @SerializedName("small_image")
    String small_image;
    @SerializedName("thumbnail")
    String thumbnail;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getSmall_image() {
        return small_image;
    }

    public void setSmall_image(String small_image) {
        this.small_image = small_image;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public String toString() {
        return "SearchResultPOJO{" +
                "product_id='" + product_id + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", short_description='" + short_description + '\'' +
                ", sku='" + sku + '\'' +
                ", small_image='" + small_image + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                '}';
    }
}
