package com.bjain.pegasus.pojo.cart;

import java.io.Serializable;

/**
 * Created by sunil on 31-05-2017.
 */

public class CartDataPOJO implements Serializable {
    String cart_id;
    String product_id;
    String product_price;
    String quantity;
    String product_name;
    String product_sku;
    String row_price;
    String product_image;

    public CartDataPOJO(String cart_id, String product_id, String product_price, String quantity, String product_name, String product_sku, String product_image) {
        this.cart_id = cart_id;
        this.product_id = product_id;
        this.product_price = product_price;
        this.quantity = quantity;
        this.product_name = product_name;
        this.product_sku = product_sku;
        this.product_image = product_image;
    }

    public CartDataPOJO() {
    }

    public String getCart_id() {
        return cart_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_sku() {
        return product_sku;
    }

    public void setProduct_sku(String product_sku) {
        this.product_sku = product_sku;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getRow_price() {
        return row_price;
    }

    public void setRow_price(String row_price) {
        this.row_price = row_price;
    }

    @Override
    public String toString() {
        return "CartDataPOJO{" +
                "cart_id='" + cart_id + '\'' +
                ", product_id='" + product_id + '\'' +
                ", product_price='" + product_price + '\'' +
                ", quantity='" + quantity + '\'' +
                ", product_name='" + product_name + '\'' +
                ", product_sku='" + product_sku + '\'' +
                ", row_price='" + row_price + '\'' +
                ", product_image='" + product_image + '\'' +
                '}';
    }
}
