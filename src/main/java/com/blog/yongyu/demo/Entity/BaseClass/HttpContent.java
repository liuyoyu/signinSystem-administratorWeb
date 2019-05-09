package com.blog.yongyu.demo.Entity.BaseClass;

import com.blog.yongyu.demo.Entity.Dictionary;
import com.blog.yongyu.demo.Entity.UserRole;

import java.util.HashSet;
import java.util.Set;

public class HttpContent {
    public static final String data = "data";
    public static final String userId = "userId";
    public static final String userRole = "userRole";
    public static final String Token = "token";      //请求头，验证参数
    public static final String emailCode = "emailCode";     //邮箱验证码
    public static final String total = "total";

    //不能删除的数据
    public static Set<String> removeIngoreSet = new HashSet<String>(){{

        add(BaseSetting.STATUS.Normal_SYS.toString());
        add(BaseSetting.STATUS.Disabled_SYS.toString());
//        add(BaseRole.UserId.toString());
//        add(BaseRole.AdminId.toString());
        add(BaseSetting.ROLE.Admin_SYS.toString());
        add(BaseSetting.ROLE.User_SYS.toString());
        add(BaseSetting.DATATYPE.System_SYS.toString());
        add(BaseSetting.DATATYPE.Standard_SYS.toString());
        add("userType");

    }};
}
