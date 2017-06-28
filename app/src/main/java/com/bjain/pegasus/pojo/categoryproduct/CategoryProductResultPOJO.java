package com.bjain.pegasus.pojo.categoryproduct;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sunil on 30-05-2017.
 */

public class CategoryProductResultPOJO {

    @SerializedName("product_id")
    private String productId;
    @SerializedName("sku")
    private String sku;
    @SerializedName("set")
    private String set;
    @SerializedName("type")
    private String type;
    @SerializedName("categories")
    private List<String> categories = null;
    @SerializedName("websites")
    private List<String> websites = null;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("type_id")
    private String typeId;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("short_description")
    private String shortDescription;
    @SerializedName("weight")
    private String weight;
    @SerializedName("status")
    private String status;
    @SerializedName("url_key")
    private String urlKey;
    @SerializedName("url_path")
    private String urlPath;
    @SerializedName("visibility")
    private String visibility;
    @SerializedName("category_ids")
    private List<String> categoryIds = null;
    @SerializedName("has_options")
    private String hasOptions;
    @SerializedName("price")
    private String price;
    @SerializedName("tax_class_id")
    private String taxClassId;
    @SerializedName("custom_design")
    private String customDesign;
    @SerializedName("options_container")
    private String optionsContainer;
    @SerializedName("discount")
    private String discount;
    @SerializedName("url")
    private String url;
    private String discount_price;
    private String main_price;


    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<String> getWebsites() {
        return websites;
    }

    public void setWebsites(List<String> websites) {
        this.websites = websites;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUrlKey() {
        return urlKey;
    }

    public void setUrlKey(String urlKey) {
        this.urlKey = urlKey;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public List<String> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<String> categoryIds) {
        this.categoryIds = categoryIds;
    }

    public String getHasOptions() {
        return hasOptions;
    }

    public void setHasOptions(String hasOptions) {
        this.hasOptions = hasOptions;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTaxClassId() {
        return taxClassId;
    }

    public void setTaxClassId(String taxClassId) {
        this.taxClassId = taxClassId;
    }

    public String getCustomDesign() {
        return customDesign;
    }

    public void setCustomDesign(String customDesign) {
        this.customDesign = customDesign;
    }

    public String getOptionsContainer() {
        return optionsContainer;
    }

    public void setOptionsContainer(String optionsContainer) {
        this.optionsContainer = optionsContainer;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "CategoryProductResultPOJO{" +
                "productId='" + productId + '\'' +
                ", sku='" + sku + '\'' +
                ", set='" + set + '\'' +
                ", type='" + type + '\'' +
                ", categories=" + categories +
                ", websites=" + websites +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", typeId='" + typeId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", weight='" + weight + '\'' +
                ", status='" + status + '\'' +
                ", urlKey='" + urlKey + '\'' +
                ", urlPath='" + urlPath + '\'' +
                ", visibility='" + visibility + '\'' +
                ", categoryIds=" + categoryIds +
                ", hasOptions='" + hasOptions + '\'' +
                ", price='" + price + '\'' +
                ", taxClassId='" + taxClassId + '\'' +
                ", customDesign='" + customDesign + '\'' +
                ", optionsContainer='" + optionsContainer + '\'' +
                ", discount='" + discount + '\'' +
                ", url='" + url + '\'' +
                '}';
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
}
