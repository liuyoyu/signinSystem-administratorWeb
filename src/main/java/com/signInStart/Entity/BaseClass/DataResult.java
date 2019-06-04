package com.signInStart.Entity.BaseClass;

import net.minidev.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DataResult<T> {
    private Integer status = 1;
    private String msg;
    private T data;
    private Integer total;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public String toString() {
        JSONObject map = new JSONObject();
        map.put("msg", getMsg());
        map.put("status", getStatus().toString());
        return map.toString();
    }
}
