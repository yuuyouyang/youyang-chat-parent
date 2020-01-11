package com.nf.yy.config.security.handler;

import com.nf.yy.util.JsonUtils;
import com.nf.yy.util.JwtTokenUtils;
import com.nf.yy.vo.ResponseVO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录成功处理器
 *
 * @author smile
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = JwtTokenUtils.generateToken(userDetails.getUsername());
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        JsonUtils.write(response.getOutputStream(), ResponseVO.success(token));
    }

}
