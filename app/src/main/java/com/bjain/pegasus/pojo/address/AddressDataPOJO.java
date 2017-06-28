package com.bjain.pegasus.pojo.address;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 01-06-2017.
 */

public class AddressDataPOJO {
    @SerializedName("first_name")
    String first_name;
    @SerializedName("last_name")
    String last_name;
    @SerializedName("city")
    String city;
    @SerializedName("country_code")
    String country_code;
    @SerializedName("state")
    String state;
    @SerializedName("post_code")
    String post_code;
    @SerializedName("phone_no")
    String phone_no;

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPost_code() {
        return post_code;
    }

    public void setPost_code(String post_code) {
        this.post_code = post_code;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    @Override
    public String toString() {
        return "AddressDataPOJO{" +
                "first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", city='" + city + '\'' +
                ", country_code='" + country_code + '\'' +
                ", state='" + state + '\'' +
                ", post_code='" + post_code + '\'' +
                ", phone_no='" + phone_no + '\'' +
                '}';
    }
}
