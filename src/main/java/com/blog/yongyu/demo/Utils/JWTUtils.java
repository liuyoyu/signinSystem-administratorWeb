package com.blog.yongyu.demo.Utils;

import com.blog.yongyu.demo.Entity.UserRole;
import com.nimbusds.jose.*;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.extern.log4j.Log4j2;
import net.minidev.json.JSONObject;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
public class JWTUtils {
    /**
     * 验证返回参数列表
     */
    public enum params{
        STATUS,
        DATA,
        DATA_EXT,
        DATA_USERID,
        DATA_USERROLEID,
    }
    /**
     * 密钥
     */
    private static final byte[] SECRET = "ZhuangJinhuiZhuangzhenZhuWeidaHelingLiuyoyu".getBytes();
    /**
     * 初始化head部分的数据
     * {
     * "alg":"HS256",
     * "type":"JWT"
     * }
     */
    private static final JWSHeader header = new JWSHeader(JWSAlgorithm.HS256, JOSEObjectType.JWT,null, null, null, null, null, null, null, null, null, null, null);
    /**
     * token状态
     */
    public enum TokenStatus{
        Valid,      //有效
        Invalid,    //失效
        Expired,    //过期
        Parse,      //语法错误
        JoseError   //格式错误
    }
    private static final Integer expirationMillis = 60*60*24*7;

    /**
     * 生成Token
     * @param userid, role
     * @return
     */
    public static String generateToken(String userid, String roleId) {
        JWTClaimsSet claim = new JWTClaimsSet.Builder()
                .claim(params.DATA_EXT.toString(),new Date().getTime() + expirationMillis )
                .claim(params.DATA_USERID.toString(),userid)
                .claim(params.DATA_USERROLEID.toString(), roleId)
                .build();
        String tokenString = null;
        JWSObject jwsObject = new JWSObject(header, new Payload(claim.toJSONObject()));
        try {
            jwsObject.sign(new MACSigner(SECRET));
            tokenString = jwsObject.serialize();
        } catch (KeyLengthException e) {
            System.out.println("签名失败:"+e.getMessage());
            return null;
        } catch (JOSEException e) {
            System.out.println("签名失败:"+e.getMessage());
            return null;
        }
        return tokenString;
    }

    /**
     * 校验Token
     * @param token
     * @return
     */
    public static Map<String, Object> validToken(String token) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            JWSObject jwsObject = JWSObject.parse(token);
            Payload payload = jwsObject.getPayload();
            JWSVerifier verifier = new MACVerifier(SECRET);
            if (jwsObject.verify(verifier)) {
                JSONObject jsObject = payload.toJSONObject();
                if (jsObject.containsKey(params.DATA_EXT.toString())) {
                    Long extTime = Long.valueOf(jsObject.get(params.STATUS.toString()).toString());
                    Long curTime = new Date().getTime();
                    if (curTime > extTime) {
                        resultMap.put(params.STATUS.toString(), TokenStatus.Expired.toString());
                        return resultMap;
                    }
                }
                resultMap.put(params.STATUS.toString(), TokenStatus.Valid.toString());
                resultMap.put(params.DATA.toString(), jsObject);
            } else {
                resultMap.put(params.STATUS.toString(), TokenStatus.Invalid.toString());
            }

        } catch (ParseException e) {
            resultMap.put(params.STATUS.toString(),TokenStatus.Parse.toString());
        } catch (JOSEException e) {
            resultMap.put(params.STATUS.toString(), TokenStatus.JoseError.toString());
        }
        return resultMap;
    }

}