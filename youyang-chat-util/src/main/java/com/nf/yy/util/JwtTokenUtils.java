package com.nf.yy.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * jwt工具类，生成令牌，验证等等一些操作
 *
 * @author smile
 */
public class JwtTokenUtils {

    /**
     * 私钥密码，保存在服务器，客户端是不会知道密码的，以防止被攻击
     */
    private static final String SECRET = "9e75dad268984225b31967b52561c649";
    /**
     * 令牌有效时间（毫秒）
     */
    private static final long EFFECTIVE_TIME = 1000 * 60 * 30;
    /**
     * Header Name
     */
    public static final String HEADER = "Authorization";

    /**
     * 从数据声明生成令牌
     */
    private static String generateToken(Map<String, Object> claims) {
        Date expirationDate = new Date(System.currentTimeMillis() + EFFECTIVE_TIME);
        return Jwts.builder().setClaims(claims).setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, SECRET).compact();
    }

    /**
     * 从令牌中获取数据声明
     */
    private static Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    /**
     * 生成令牌
     */
    public static String generateToken(String userId) {
        Map<String, Object> claims = new HashMap<>(2);
        claims.put(Claims.SUBJECT, userId);
        claims.put(Claims.ISSUED_AT, new Date());
        return generateToken(claims);
    }

    /**
     * 从令牌中获取用户名
     */
    public static String getUserNameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 判断令牌是否过期
     */
    public static Boolean isTokenExpired(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * 刷新令牌
     */
    public static String refreshToken(String token) {
        String refreshedToken;
        try {
            Claims claims = getClaimsFromToken(token);
            claims.put(Claims.ISSUED_AT, new Date());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

}