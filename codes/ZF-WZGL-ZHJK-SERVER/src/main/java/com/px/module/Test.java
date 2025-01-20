package com.px.module;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

/**
 * @author 品讯科技
 * @version V 1.0
 * @Package com.px.module
 * @ClassName: Test
 * @Description:
 * @time 2019/10/22
 */
public class Test {
    public static void main(String[] args) {
        String str = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIwMDI1NTE3ZDRhNzU0ODgxOWNkZGMxNmIzZTU0NDEwMCIsInVzZXJJbmZvIjoie1wicmVhbE5hbWVcIjpcIueblui0ulwiLFwiaXNSZW1vdmVkXCI6XCIxXCIsXCJ0cmF2ZWxJZFwiOlwiMVwiLFwibG9naW5OYW1lXCI6XCJhZG1pblwiLFwib3BlcmF0ZVR5cGVcIjpcIlwiLFwidXNlck5hbWVcIjpcIui2hee6p-euoeeQhuWRmFwiLFwibWFya1wiOlwiMFwiLFwidXNlckNvZGVcIjpcIjAwMTU3XCIsXCJzdGF0dXNcIjpcIjFcIn0iLCJleHAiOjE2MDM4ODU5NDF9.YQCR4eXndzun-T4cg3CN7elecPOObsUmgHAfJPQAK7M";
        JSONObject json = getUser(str);
        System.err.println(json);
    }



    public static JSONObject getUser(String token) {
        DecodedJWT jwt = null;
        try {
            //使用用户密码进行解码
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("yunuci2" + getUserId(token))).build();
            jwt = jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            return null;
        }
        JSONObject jsonObject = new JSONObject();    //获取受众
        JSONObject data = JSONObject.parseObject(jwt.getClaims().get("userInfo").asString());

        return data;
    }

    public static String getUserId(String token) throws JWTDecodeException {
        return JWT.decode(token).getAudience().get(0);
    }
}