package com.bjain.pegasus.pojo.productview;

/**
 * Created by sunil on 30-05-2017.
 */

public class SingleProductPOJO {
    String product_id;
    String name;
    String description;
    String base_price;
    String discount_price;
    String image;
    String sku;
    String author;


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBase_price() {
        return base_price;
    }

    public void setBase_price(String base_price) {
        this.base_price = base_price;
    }

    public String getDiscount_price() {
        return discount_price;
    }

    public void setDiscount_price(String discount_price) {
        this.discount_price = discount_price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    @Override
    public String toString() {
        return "SingleProductPOJO{" +
                "product_id='" + product_id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", base_price='" + base_price + '\'' +
                ", discount_price='" + discount_price + '\'' +
                ", image='" + image + '\'' +
                ", sku='" + sku + '\'' +
                '}';
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
