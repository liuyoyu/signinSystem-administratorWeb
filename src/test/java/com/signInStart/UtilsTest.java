package com.signInStart;

import com.signInStart.Entity.UserInfo;
import com.signInStart.Utils.DataUtils;
import com.signInStart.Utils.JWTUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UtilsTest {
    @Test
    public void JwtTest(){
//        String token = JWTUtils.generateToken(123L, 122L);
//        System.out.println(token);
        Map<String, Object> map = JWTUtils.validToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJEQVRBX0VYVCI6MTU1NDk0NTE5NTY1NywiREFUQV9VU0VSUk9MRUlEIjoxMjIsIkRBVEFfVVNFUklEIjoxMjN9.PsAET40clqimkRUpyWQ3g-Zgsz618g0u7jTzCH0P6h0");
        for (String key : map.keySet()) {
            System.out.println(key + ":"+ map.get(key));
        }
    }
    @Test
    public void DataUtilsTest() throws Exception{
        UserInfo u = new UserInfo();
        u.setNewPassword("123");
        Map<String, Object> map = DataUtils.ClassToMap(u);
        System.out.println("pwd: "+map.get("pwd"));
    }
}