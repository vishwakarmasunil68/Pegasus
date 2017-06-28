package com.bjain.pegasus.pojo.orderlist;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sunil on 14-06-2017.
 */

public class OrderListPOJO {
    @SerializedName("Success")
    String Success;
    @SerializedName("Result")
    List<OrderListResultPOJO> orderListResultPOJOList;

    public String getSuccess() {
        return Success;
    }

    public void setSuccess(String success) {
        Success = success;
    }

    public List<OrderListResultPOJO> getOrderListResultPOJOList() {
        return orderListResultPOJOList;
    }

    public void setOrderListResultPOJOList(List<OrderListResultPOJO> orderListResultPOJOList) {
        this.orderListResultPOJOList = orderListResultPOJOList;
    }

    @Override
    public String toString() {
        return "OrderListPOJO{" +
                "Success='" + Success + '\'' +
                ", orderListResultPOJOList=" + orderListResultPOJOList +
                '}';
    }
}
