package com.bjain.pegasus.pojo.cart;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sunil on 31-05-2017.
 */

public class CartAttrPOJO implements Serializable{
    @SerializedName("item_id")
    private String itemId;
    @SerializedName("quote_id")
    private String quoteId;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("product_id")
    private String productId;
    @SerializedName("store_id")
    private String storeId;
    @SerializedName("parent_item_id")
    private String parentItemId;
    @SerializedName("is_virtual")
    private String isVirtual;
    @SerializedName("sku")
    private String sku;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("applied_rule_ids")
    private String appliedRuleIds;
    @SerializedName("additional_data")
    private String additionalData;
    @SerializedName("free_shipping")
    private String freeShipping;
    @SerializedName("is_qty_decimal")
    private String isQtyDecimal;
    @SerializedName("no_discount")
    private String noDiscount;
    @SerializedName("weight")
    private String weight;
    @SerializedName("qty")
    private String qty;
    @SerializedName("price")
    private String price;
    @SerializedName("base_price")
    private String basePrice;
    @SerializedName("custom_price")
    private String customPrice;
    @SerializedName("discount_percent")
    private String discountPercent;
    @SerializedName("discount_amount")
    private String discountAmount;
    @SerializedName("base_discount_amount")
    private String baseDiscountAmount;
    @SerializedName("tax_percent")
    private String taxPercent;
    @SerializedName("tax_amount")
    private String taxAmount;
    @SerializedName("base_tax_amount")
    private String baseTaxAmount;
    @SerializedName("row_total")
    private String rowTotal;
    @SerializedName("base_row_total")
    private String baseRowTotal;
    @SerializedName("row_total_with_discount")
    private String rowTotalWithDiscount;
    @SerializedName("row_weight")
    private String rowWeight;
    @SerializedName("product_type")
    private String productType;
    @SerializedName("base_tax_before_discount")
    private String baseTaxBeforeDiscount;
    @SerializedName("tax_before_discount")
    private String taxBeforeDiscount;
    @SerializedName("original_custom_price")
    private String originalCustomPrice;
    @SerializedName("redirect_url")
    private String redirectUrl;
    @SerializedName("base_cost")
    private String baseCost;
    @SerializedName("price_incl_tax")
    private String priceInclTax;
    @SerializedName("base_price_incl_tax")
    private String basePriceInclTax;
    @SerializedName("row_total_incl_tax")
    private String rowTotalInclTax;
    @SerializedName("base_row_total_incl_tax")
    private String baseRowTotalInclTax;
    @SerializedName("hidden_tax_amount")
    private String hiddenTaxAmount;
    @SerializedName("base_hidden_tax_amount")
    private String baseHiddenTaxAmount;
    @SerializedName("gift_message_id")
    private String giftMessageId;
    @SerializedName("weee_tax_disposition")
    private String weeeTaxDisposition;
    @SerializedName("weee_tax_row_disposition")
    private String weeeTaxRowDisposition;
    @SerializedName("base_weee_tax_disposition")
    private String baseWeeeTaxDisposition;
    @SerializedName("base_weee_tax_row_disposition")
    private String baseWeeeTaxRowDisposition;
    @SerializedName("weee_tax_applied")
    private String weeeTaxApplied;
    @SerializedName("weee_tax_applied_amount")
    private String weeeTaxAppliedAmount;
    @SerializedName("weee_tax_applied_row_amount")
    private String weeeTaxAppliedRowAmount;
    @SerializedName("base_weee_tax_applied_amount")
    private String baseWeeeTaxAppliedAmount;
    @SerializedName("base_weee_tax_applied_row_amnt")
    private String baseWeeeTaxAppliedRowAmnt;
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

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
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

    public String getParentItemId() {
        return parentItemId;
    }

    public void setParentItemId(String parentItemId) {
        this.parentItemId = parentItemId;
    }

    public String getIsVirtual() {
        return isVirtual;
    }

    public void setIsVirtual(String isVirtual) {
        this.isVirtual = isVirtual;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
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

    public String getAppliedRuleIds() {
        return appliedRuleIds;
    }

    public void setAppliedRuleIds(String appliedRuleIds) {
        this.appliedRuleIds = appliedRuleIds;
    }

    public String getAdditionalData() {
        return additionalData;
    }

    public void setAdditionalData(String additionalData) {
        this.additionalData = additionalData;
    }

    public String getFreeShipping() {
        return freeShipping;
    }

    public void setFreeShipping(String freeShipping) {
        this.freeShipping = freeShipping;
    }

    public String getIsQtyDecimal() {
        return isQtyDecimal;
    }

    public void setIsQtyDecimal(String isQtyDecimal) {
        this.isQtyDecimal = isQtyDecimal;
    }

    public String getNoDiscount() {
        return noDiscount;
    }

    public void setNoDiscount(String noDiscount) {
        this.noDiscount = noDiscount;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(String basePrice) {
        this.basePrice = basePrice;
    }

    public String getCustomPrice() {
        return customPrice;
    }

    public void setCustomPrice(String customPrice) {
        this.customPrice = customPrice;
    }

    public String getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(String discountPercent) {
        this.discountPercent = discountPercent;
    }

    public String getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(String discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getBaseDiscountAmount() {
        return baseDiscountAmount;
    }

    public void setBaseDiscountAmount(String baseDiscountAmount) {
        this.baseDiscountAmount = baseDiscountAmount;
    }

    public String getTaxPercent() {
        return taxPercent;
    }

    public void setTaxPercent(String taxPercent) {
        this.taxPercent = taxPercent;
    }

    public String getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(String taxAmount) {
        this.taxAmount = taxAmount;
    }

    public String getBaseTaxAmount() {
        return baseTaxAmount;
    }

    public void setBaseTaxAmount(String baseTaxAmount) {
        this.baseTaxAmount = baseTaxAmount;
    }

    public String getRowTotal() {
        return rowTotal;
    }

    public void setRowTotal(String rowTotal) {
        this.rowTotal = rowTotal;
    }

    public String getBaseRowTotal() {
        return baseRowTotal;
    }

    public void setBaseRowTotal(String baseRowTotal) {
        this.baseRowTotal = baseRowTotal;
    }

    public String getRowTotalWithDiscount() {
        return rowTotalWithDiscount;
    }

    public void setRowTotalWithDiscount(String rowTotalWithDiscount) {
        this.rowTotalWithDiscount = rowTotalWithDiscount;
    }

    public String getRowWeight() {
        return rowWeight;
    }

    public void setRowWeight(String rowWeight) {
        this.rowWeight = rowWeight;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getBaseTaxBeforeDiscount() {
        return baseTaxBeforeDiscount;
    }

    public void setBaseTaxBeforeDiscount(String baseTaxBeforeDiscount) {
        this.baseTaxBeforeDiscount = baseTaxBeforeDiscount;
    }

    public String getTaxBeforeDiscount() {
        return taxBeforeDiscount;
    }

    public void setTaxBeforeDiscount(String taxBeforeDiscount) {
        this.taxBeforeDiscount = taxBeforeDiscount;
    }

    public String getOriginalCustomPrice() {
        return originalCustomPrice;
    }

    public void setOriginalCustomPrice(String originalCustomPrice) {
        this.originalCustomPrice = originalCustomPrice;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getBaseCost() {
        return baseCost;
    }

    public void setBaseCost(String baseCost) {
        this.baseCost = baseCost;
    }

    public String getPriceInclTax() {
        return priceInclTax;
    }

    public void setPriceInclTax(String priceInclTax) {
        this.priceInclTax = priceInclTax;
    }

    public String getBasePriceInclTax() {
        return basePriceInclTax;
    }

    public void setBasePriceInclTax(String basePriceInclTax) {
        this.basePriceInclTax = basePriceInclTax;
    }

    public String getRowTotalInclTax() {
        return rowTotalInclTax;
    }

    public void setRowTotalInclTax(String rowTotalInclTax) {
        this.rowTotalInclTax = rowTotalInclTax;
    }

    public String getBaseRowTotalInclTax() {
        return baseRowTotalInclTax;
    }

    public void setBaseRowTotalInclTax(String baseRowTotalInclTax) {
        this.baseRowTotalInclTax = baseRowTotalInclTax;
    }

    public String getHiddenTaxAmount() {
        return hiddenTaxAmount;
    }

    public void setHiddenTaxAmount(String hiddenTaxAmount) {
        this.hiddenTaxAmount = hiddenTaxAmount;
    }

    public String getBaseHiddenTaxAmount() {
        return baseHiddenTaxAmount;
    }

    public void setBaseHiddenTaxAmount(String baseHiddenTaxAmount) {
        this.baseHiddenTaxAmount = baseHiddenTaxAmount;
    }

    public String getGiftMessageId() {
        return giftMessageId;
    }

    public void setGiftMessageId(String giftMessageId) {
        this.giftMessageId = giftMessageId;
    }

    public String getWeeeTaxDisposition() {
        return weeeTaxDisposition;
    }

    public void setWeeeTaxDisposition(String weeeTaxDisposition) {
        this.weeeTaxDisposition = weeeTaxDisposition;
    }

    public String getWeeeTaxRowDisposition() {
        return weeeTaxRowDisposition;
    }

    public void setWeeeTaxRowDisposition(String weeeTaxRowDisposition) {
        this.weeeTaxRowDisposition = weeeTaxRowDisposition;
    }

    public String getBaseWeeeTaxDisposition() {
        return baseWeeeTaxDisposition;
    }

    public void setBaseWeeeTaxDisposition(String baseWeeeTaxDisposition) {
        this.baseWeeeTaxDisposition = baseWeeeTaxDisposition;
    }

    public String getBaseWeeeTaxRowDisposition() {
        return baseWeeeTaxRowDisposition;
    }

    public void setBaseWeeeTaxRowDisposition(String baseWeeeTaxRowDisposition) {
        this.baseWeeeTaxRowDisposition = baseWeeeTaxRowDisposition;
    }

    public String getWeeeTaxApplied() {
        return weeeTaxApplied;
    }

    public void setWeeeTaxApplied(String weeeTaxApplied) {
        this.weeeTaxApplied = weeeTaxApplied;
    }

    public String getWeeeTaxAppliedAmount() {
        return weeeTaxAppliedAmount;
    }

    public void setWeeeTaxAppliedAmount(String weeeTaxAppliedAmount) {
        this.weeeTaxAppliedAmount = weeeTaxAppliedAmount;
    }

    public String getWeeeTaxAppliedRowAmount() {
        return weeeTaxAppliedRowAmount;
    }

    public void setWeeeTaxAppliedRowAmount(String weeeTaxAppliedRowAmount) {
        this.weeeTaxAppliedRowAmount = weeeTaxAppliedRowAmount;
    }

    public String getBaseWeeeTaxAppliedAmount() {
        return baseWeeeTaxAppliedAmount;
    }

    public void setBaseWeeeTaxAppliedAmount(String baseWeeeTaxAppliedAmount) {
        this.baseWeeeTaxAppliedAmount = baseWeeeTaxAppliedAmount;
    }

    public String getBaseWeeeTaxAppliedRowAmnt() {
        return baseWeeeTaxAppliedRowAmnt;
    }

    public void setBaseWeeeTaxAppliedRowAmnt(String baseWeeeTaxAppliedRowAmnt) {
        this.baseWeeeTaxAppliedRowAmnt = baseWeeeTaxAppliedRowAmnt;
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

    @Override
    public String toString() {
        return "CartAttrPOJO{" +
                "itemId='" + itemId + '\'' +
                ", quoteId='" + quoteId + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", productId='" + productId + '\'' +
                ", storeId='" + storeId + '\'' +
                ", parentItemId='" + parentItemId + '\'' +
                ", isVirtual='" + isVirtual + '\'' +
                ", sku='" + sku + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", appliedRuleIds='" + appliedRuleIds + '\'' +
                ", additionalData='" + additionalData + '\'' +
                ", freeShipping='" + freeShipping + '\'' +
                ", isQtyDecimal='" + isQtyDecimal + '\'' +
                ", noDiscount='" + noDiscount + '\'' +
                ", weight='" + weight + '\'' +
                ", qty='" + qty + '\'' +
                ", price='" + price + '\'' +
                ", basePrice='" + basePrice + '\'' +
                ", customPrice='" + customPrice + '\'' +
                ", discountPercent='" + discountPercent + '\'' +
                ", discountAmount='" + discountAmount + '\'' +
                ", baseDiscountAmount='" + baseDiscountAmount + '\'' +
                ", taxPercent='" + taxPercent + '\'' +
                ", taxAmount='" + taxAmount + '\'' +
                ", baseTaxAmount='" + baseTaxAmount + '\'' +
                ", rowTotal='" + rowTotal + '\'' +
                ", baseRowTotal='" + baseRowTotal + '\'' +
                ", rowTotalWithDiscount='" + rowTotalWithDiscount + '\'' +
                ", rowWeight='" + rowWeight + '\'' +
                ", productType='" + productType + '\'' +
                ", baseTaxBeforeDiscount='" + baseTaxBeforeDiscount + '\'' +
                ", taxBeforeDiscount='" + taxBeforeDiscount + '\'' +
                ", originalCustomPrice='" + originalCustomPrice + '\'' +
                ", redirectUrl='" + redirectUrl + '\'' +
                ", baseCost='" + baseCost + '\'' +
                ", priceInclTax='" + priceInclTax + '\'' +
                ", basePriceInclTax='" + basePriceInclTax + '\'' +
                ", rowTotalInclTax='" + rowTotalInclTax + '\'' +
                ", baseRowTotalInclTax='" + baseRowTotalInclTax + '\'' +
                ", hiddenTaxAmount='" + hiddenTaxAmount + '\'' +
                ", baseHiddenTaxAmount='" + baseHiddenTaxAmount + '\'' +
                ", giftMessageId='" + giftMessageId + '\'' +
                ", weeeTaxDisposition='" + weeeTaxDisposition + '\'' +
                ", weeeTaxRowDisposition='" + weeeTaxRowDisposition + '\'' +
                ", baseWeeeTaxDisposition='" + baseWeeeTaxDisposition + '\'' +
                ", baseWeeeTaxRowDisposition='" + baseWeeeTaxRowDisposition + '\'' +
                ", weeeTaxApplied='" + weeeTaxApplied + '\'' +
                ", weeeTaxAppliedAmount='" + weeeTaxAppliedAmount + '\'' +
                ", weeeTaxAppliedRowAmount='" + weeeTaxAppliedRowAmount + '\'' +
                ", baseWeeeTaxAppliedAmount='" + baseWeeeTaxAppliedAmount + '\'' +
                ", baseWeeeTaxAppliedRowAmnt='" + baseWeeeTaxAppliedRowAmnt + '\'' +
                ", valueId='" + valueId + '\'' +
                ", entityTypeId='" + entityTypeId + '\'' +
                ", attributeId='" + attributeId + '\'' +
                ", entityId='" + entityId + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
