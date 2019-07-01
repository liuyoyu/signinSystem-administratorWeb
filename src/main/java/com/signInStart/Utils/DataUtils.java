/**
 * created by
 * Date:2019/4/19
 **/
package com.signInStart.Utils;

import com.signInStart.Entity.BaseClass.BaseSetting;
import lombok.extern.log4j.Log4j2;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 * 对数据操作工具
 */
@Log4j2
public class DataUtils {
    /**
     * 复制数据，将source中的非空数据拷贝到target中
     * @param source
     * @param target
     * @return
     */
    public static Boolean copyProperty(Object source, Object target) {
        Class s = source.getClass();
        Class t = target.getClass();

        Field[] declaredFields = s.getDeclaredFields();
        try {
            for (Field dfield : declaredFields) {
                Field property = s.getDeclaredField(dfield.getName()); //获取属性
                property.setAccessible(true);
                Object value = property.get(source);
                if (value == null || "".equals(value.toString())) {  //去掉空值
                    continue;
                }
                Field newProperty = t.getDeclaredField(dfield.getName());
                newProperty.setAccessible(true);
                newProperty.set(target, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 判断字符串是否为空
     * @param s
     * @return
     */
    public static Boolean isEmptyString(String s) {
        return s == null || "".equals(s);
    }

    /**
     * @Author liuyoyu
     * @Description //TODO  判断是否属于usertype
     * @Date 21:14 2019/6/2
     * @Param [userType]
     * @return java.lang.Boolean
     **/
    public static Boolean containsUserType(String userType) {
        return BaseSetting.USRTYPE.Admin_SYS.toString().equals(userType) ||
                BaseSetting.USRTYPE.SupperAdmin_SYS.toString().equals(userType) ||
                BaseSetting.USRTYPE.User_SYS.toString().equals(userType);
    }
    /**
     * @Author liuyoyu
     * @Description //TODO  判断是否属于usertype
     * @Date 21:14 2019/6/2
     * @Param [userType]
     * @return java.lang.Boolean
     **/
    public static Boolean containsUserType(String[] userType) {
        for (String u : userType) {
            if (!containsUserType(u)) {
                return false;
            }
        }
        return true;
    }
    /**
     * @Author liuyoyu
     * @Description //TODO  获取当前的完整的方法名称
     * @Date 22:47 2019/6/10
     * @Params []
     * @return java.lang.String
     **/
    public static String CurrentMethodName() {
        String className = Thread.currentThread().getStackTrace()[2].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        return className + " " + methodName+"()";
    }

    public static Map<String, Object> ClassToMap(Object clazz) throws IllegalAccessException {
        if (clazz == null) {
            return new HashMap<>();
        }
        Class clz = clazz.getClass();
        Field[] declaredFields = clz.getDeclaredFields();
        Map<String, Object> map = new HashMap<>();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            Object val = field.get(clazz);
            if (val == null || "".equals(val)) {    //空值不转成map
                continue;
            }
            map.put(field.getName(), val.toString());
        }
        return map;
    }
}