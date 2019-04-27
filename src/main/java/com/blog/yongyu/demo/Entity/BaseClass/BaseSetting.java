/**
 * created by
 * Date:2019/4/26
 **/
package com.blog.yongyu.demo.Entity.BaseClass;

public class BaseSetting {
    public enum STATUS {
        Normal_SYS,
        Disabled_SYS
    }

    public enum ROLE {
        User_SYS,
        Admin_SYS,
        SupperAdmin_SYS
    }
    public enum DATATYPE{
        System_SYS,
        Standard_SYS
    }
    public enum ISDEFAULT {
        isDefault_SYS,
        isNotDefault_SYS
    }
}