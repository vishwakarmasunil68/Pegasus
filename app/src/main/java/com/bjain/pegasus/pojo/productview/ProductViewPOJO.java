package com.bjain.pegasus.pojo.productview;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 30-05-2017.
 */

public class ProductViewPOJO {
    @SerializedName("value_id")
    private String valueId;
    @SerializedName("description")
    private String description;
    @SerializedName("entity_type_id")
    private String entityTypeId;
    @SerializedName("attribute_id")
    private String attributeId;
    @SerializedName("store_id")
    private String storeId;
    @SerializedName("entity_id")
    private String entityId;
    @SerializedName("value")
    private String value;
    @SerializedName("deal_id")
    private String dealId;
    @SerializedName("product_id")
    private String productId;
    @SerializedName("after_end")
    private String afterEnd;
    @SerializedName("recent")
    private String recent;
    @SerializedName("price")
    private String price;
    @SerializedName("qty")
    private String qty;
    @SerializedName("date_from")
    private String dateFrom;
    @SerializedName("date_to")
    private String dateTo;
    @SerializedName("status")
    private String status;
    @SerializedName("qty_sold")
    private String qtySold;
    @SerializedName("creation_time")
    private String creationTime;
    @SerializedName("update_time")
    private String updateTime;
    @SerializedName("is_active")
    private String isActive;
    @SerializedName("discount_price")
    private String discount_price;
    @SerializedName("main_price")
    private String main_price;


    public String getValueId() {
        return valueId;
    }

    public void setValueId(String valueId) {
        this.valueId = valueId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
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

    public String getDealId() {
        return dealId;
    }

    public void setDealId(String dealId) {
        this.dealId = dealId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getAfterEnd() {
        return afterEnd;
    }

    public void setAfterEnd(String afterEnd) {
        this.afterEnd = afterEnd;
    }

    public String getRecent() {
        return recent;
    }

    public void setRecent(String recent) {
        this.recent = recent;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getQtySold() {
        return qtySold;
    }

    public void setQtySold(String qtySold) {
        this.qtySold = qtySold;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
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
        return "ProductViewPOJO{" +
                "valueId='" + valueId + '\'' +
                ", description='" + description + '\'' +
                ", entityTypeId='" + entityTypeId + '\'' +
                ", attributeId='" + attributeId + '\'' +
                ", storeId='" + storeId + '\'' +
                ", entityId='" + entityId + '\'' +
                ", value='" + value + '\'' +
                ", dealId='" + dealId + '\'' +
                ", productId='" + productId + '\'' +
                ", afterEnd='" + afterEnd + '\'' +
                ", recent='" + recent + '\'' +
                ", price='" + price + '\'' +
                ", qty='" + qty + '\'' +
                ", dateFrom='" + dateFrom + '\'' +
                ", dateTo='" + dateTo + '\'' +
                ", status='" + status + '\'' +
                ", qtySold='" + qtySold + '\'' +
                ", creationTime='" + creationTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", isActive='" + isActive + '\'' +
                ", discount_price='" + discount_price + '\'' +
                ", main_price='" + main_price + '\'' +
                '}';
    }
}
