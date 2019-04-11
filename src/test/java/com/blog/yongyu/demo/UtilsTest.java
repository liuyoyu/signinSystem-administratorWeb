package com.blog.yongyu.demo;

import com.blog.yongyu.demo.Utils.JWTUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
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
}