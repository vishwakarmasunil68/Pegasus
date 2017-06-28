package com.bjain.pegasus.pojo.orderlist;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 14-06-2017.
 */

public class OrderListResultPOJO {
    @SerializedName("entity_id")
    String entity_id;
    @SerializedName("status")
    String status;
    @SerializedName("customer_id")
    String customer_id;
    @SerializedName("grand_total")
    String grand_total;
    @SerializedName("increment_id")
    String increment_id;
    @SerializedName("customer_email")
    String customer_email;
    @SerializedName("created_at")
    String created_at;
    @SerializedName("updated_at")
    String updated_at;
    @SerializedName("order_currency_code")
    String order_currency_code;
    @SerializedName("lastname")
    String lastname;
    @SerializedName("firstname")
    String firstname;

    public String getEntity_id() {
        return entity_id;
    }

    public void setEntity_id(String entity_id) {
        this.entity_id = entity_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getGrand_total() {
        return grand_total;
    }

    public void setGrand_total(String grand_total) {
        this.grand_total = grand_total;
    }

    public String getIncrement_id() {
        return increment_id;
    }

    public void setIncrement_id(String increment_id) {
        this.increment_id = increment_id;
    }

    public String getCustomer_email() {
        return customer_email;
    }

    public void setCustomer_email(String customer_email) {
        this.customer_email = customer_email;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getOrder_currency_code() {
        return order_currency_code;
    }

    public void setOrder_currency_code(String order_currency_code) {
        this.order_currency_code = order_currency_code;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Override
    public String toString() {
        return "OrderListResultPOJO{" +
                "entity_id='" + entity_id + '\'' +
                ", status='" + status + '\'' +
                ", customer_id='" + customer_id + '\'' +
                ", grand_total='" + grand_total + '\'' +
                ", increment_id='" + increment_id + '\'' +
                ", customer_email='" + customer_email + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", order_currency_code='" + order_currency_code + '\'' +
                ", lastname='" + lastname + '\'' +
                ", firstname='" + firstname + '\'' +
                '}';
    }
}
