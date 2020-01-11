package com.nf.yy.config.security.filter;

import com.nf.yy.entity.UserInfo;
import com.nf.yy.util.JwtTokenUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 拦截器
 *
 * @author smile
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String token = request.getHeader(JwtTokenUtils.HEADER);
        if (!StringUtils.isEmpty(token)) {
            tokenExpirationCheck(token, request);
        }
        chain.doFilter(request, response);
    }

    /**
     * 校验令牌并获取用户信息
     */
    public void tokenExpirationCheck(String token, HttpServletRequest request) {
        if (!JwtTokenUtils.isTokenExpired(token)) {
            String userId = JwtTokenUtils.getUserNameFromToken(token);
            UserDetails userDetails = new UserInfo(userId, null);
            createAuthenticationToken(request, userDetails);
        }
    }

    /**
     * 生成Authentication令牌，方便后续验证
     */
    public void createAuthenticationToken(HttpServletRequest request, UserDetails userDetails) {
        // 将用户信息存入 authentication，方便后续校验
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, null);
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        // 将 authentication 存入 ThreadLocal，方便后续获取用户信息
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}