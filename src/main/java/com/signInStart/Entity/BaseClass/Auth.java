/**
 * @Author liuyoyu
 * @Date:2019/6/9 21:17
 **/
package com.signInStart.Entity.BaseClass;

import java.lang.annotation.*;

/**
 * @Author liuyoyu
 * @Description //TODO  验证权限
 * @Date 21:17 2019/6/9
 * @Param 
 * @return 
 **/
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Auth {
    String value();
}