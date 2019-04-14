package com.blog.yongyu.demo.Utils;

import com.blog.yongyu.demo.Entity.BaseClass.DataResult;

public class ResultUtils {
    public static DataResult success(Object object) {
        DataResult dataResult = new DataResult();
        dataResult.setStatus(0);
        dataResult.setMsg("成功");
        dataResult.setData(object);
        return dataResult;
    }

    public static DataResult success(Object object, Integer total) {
        DataResult dataResult = new DataResult();
        dataResult.setStatus(0);
        dataResult.setMsg("成功");
        dataResult.setTotal(total);
        dataResult.setData(object);
        return dataResult;
    }

    public static DataResult success(){
        return success(null);
    }

    public static DataResult error(Integer status, String msg) {
        DataResult dataResult = new DataResult();
        dataResult.setStatus(status);
        dataResult.setMsg(msg);
        return dataResult;
    }
}
