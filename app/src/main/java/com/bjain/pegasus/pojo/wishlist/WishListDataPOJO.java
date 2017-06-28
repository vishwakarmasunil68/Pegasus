package com.bjain.pegasus.pojo.wishlist;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 31-05-2017.
 */

public class WishListDataPOJO {
    @SerializedName("wishlist_id")
    private String wishlistId;
    @SerializedName("customer_id")
    private String customerId;
    @SerializedName("shared")
    private String shared;
    @SerializedName("sharing_code")
    private String sharingCode;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("wishlist_item_id")
    private String wishlistItemId;
    @SerializedName("product_id")
    private String productId;
    @SerializedName("store_id")
    private String storeId;
    @SerializedName("added_at")
    private String addedAt;
    @SerializedName("description")
    private String description;
    @SerializedName("qty")
    private String qty;
    @SerializedName("value_id")
    private String valueId;
    @SerializedName("entity_type_id")
    private String entityTypeId;
    @SerializedName("attribute_id")
    private String attributeId;
    @SerializedName("entity_id")
    private String entityId;
    @SerializedName("value")
    private String value;
    @SerializedName("discount_price")
    private String discount_price;
    @SerializedName("main_price")
    private String main_price;


    public String getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(String wishlistId) {
        this.wishlistId = wishlistId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getShared() {
        return shared;
    }

    public void setShared(String shared) {
        this.shared = shared;
    }

    public String getSharingCode() {
        return sharingCode;
    }

    public void setSharingCode(String sharingCode) {
        this.sharingCode = sharingCode;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getWishlistItemId() {
        return wishlistItemId;
    }

    public void setWishlistItemId(String wishlistItemId) {
        this.wishlistItemId = wishlistItemId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(String addedAt) {
        this.addedAt = addedAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getValueId() {
        return valueId;
    }

    public void setValueId(String valueId) {
        this.valueId = valueId;
    }

    public String getEntityTypeId() {
        return entityTypeId;
    }

    public void setEntityTypeId(String entityTypeId) {
        this.entityTypeId = entityTypeId;
    }

    public String getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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

    @Override
    public String toString() {
        return "WishListDataPOJO{" +
                "wishlistId='" + wishlistId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", shared='" + shared + '\'' +
                ", sharingCode='" + sharingCode + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", wishlistItemId='" + wishlistItemId + '\'' +
                ", productId='" + productId + '\'' +
                ", storeId='" + storeId + '\'' +
                ", addedAt='" + addedAt + '\'' +
                ", description='" + description + '\'' +
                ", qty='" + qty + '\'' +
                ", valueId='" + valueId + '\'' +
                ", entityTypeId='" + entityTypeId + '\'' +
                ", attributeId='" + attributeId + '\'' +
                ", entityId='" + entityId + '\'' +
                ", value='" + value + '\'' +
                ", discount_price='" + discount_price + '\'' +
                ", main_price='" + main_price + '\'' +
                '}';
    }
}
