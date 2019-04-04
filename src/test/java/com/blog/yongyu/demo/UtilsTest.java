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
//        String token = JWTUtils.generateToken("123");
//        System.out.println(token);
        Map<String, Object> map = JWTUtils.validToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJEQVRBX0VYVCI6MTU1NDM0MzMwNTQ3MiwiREFUQV9VU0VSUk9MRUlEIjozLCJEQVRBX1VTRVJJRCI6N30.y_G1lQ3VkEutQ3c2mjKCVYMBcn4fkGRAwZhXlbBriCI");
        for (String key : map.keySet()) {
            System.out.println(key + ":"+ map.get(key));
        }
    }
}