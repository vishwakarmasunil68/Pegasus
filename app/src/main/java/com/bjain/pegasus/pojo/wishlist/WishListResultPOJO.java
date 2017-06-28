package com.bjain.pegasus.pojo.wishlist;

/**
 * Created by sunil on 31-05-2017.
 */

public class WishListResultPOJO {

    String product_id;
    String product_name;
    String wishlist_item_id;
    String customer_id;
    String product_sku;
    String description;
    String quantity;
    String image;
    String price;
    String discount_price;

    public WishListResultPOJO(String product_id, String product_name, String wishlist_item_id, String customer_id, String product_sku, String description, String quantity, String image, String price) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.wishlist_item_id = wishlist_item_id;
        this.customer_id = customer_id;
        this.product_sku = product_sku;
        this.description = description;
        this.quantity = quantity;
        this.image = image;
        this.price = price;
    }

    public WishListResultPOJO() {
    }

    public WishListResultPOJO(String product_id, String product_name, String wishlist_item_id, String customer_id, String product_sku, String description, String quantity, String image, String price, String discount_price) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.wishlist_item_id = wishlist_item_id;
        this.customer_id = customer_id;
        this.product_sku = product_sku;
        this.description = description;
        this.quantity = quantity;
        this.image = image;
        this.price = price;
        this.discount_price = discount_price;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getWishlist_item_id() {
        return wishlist_item_id;
    }

    public void setWishlist_item_id(String wishlist_item_id) {
        this.wishlist_item_id = wishlist_item_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getProduct_sku() {
        return product_sku;
    }

    public void setProduct_sku(String product_sku) {
        this.product_sku = product_sku;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "WishListResultPOJO{" +
                "product_id='" + product_id + '\'' +
                ", product_name='" + product_name + '\'' +
                ", wishlist_item_id='" + wishlist_item_id + '\'' +
                ", customer_id='" + customer_id + '\'' +
                ", product_sku='" + product_sku + '\'' +
                ", description='" + description + '\'' +
                ", quantity='" + quantity + '\'' +
                ", image='" + image + '\'' +
                ", price='" + price + '\'' +
                '}';
    }

    public String getDiscount_price() {
        return discount_price;
    }

    public void setDiscount_price(String discount_price) {
        this.discount_price = discount_price;
    }
}
