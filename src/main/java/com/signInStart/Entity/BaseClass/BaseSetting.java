/**
 * created by
 * Date:2019/4/26
 **/
package com.signInStart.Entity.BaseClass;

public class BaseSetting {
    public enum STATUS {
        Normal_SYS,
        Disabled_SYS
    }

    public enum ROLE { //废弃不用了
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

    public enum USRTYPE{
        Admin_SYS,
        User_SYS,
        SupperAdmin_SYS
    }
}