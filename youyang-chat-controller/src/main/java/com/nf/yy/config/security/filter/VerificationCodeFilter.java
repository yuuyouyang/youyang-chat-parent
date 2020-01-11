package com.nf.yy.config.security.filter;

import com.nf.yy.util.JsonUtils;
import com.nf.yy.vo.ResponseVO;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author smile
 */
@Component
public class VerificationCodeFilter extends OncePerRequestFilter {

    /**
     * 默认登录地址
     */
    private String defaultFilterProcessUrl = "/user/login";

    /**
     * 验证码验证失败处理器
     */
    private AuthenticationFailureHandler authenticationFailureHandler = (request, response, exception) -> {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        JsonUtils.write(response.getOutputStream(), ResponseVO.failed(exception.getMessage()));
    };

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        if ("POST".equalsIgnoreCase(request.getMethod())
                && defaultFilterProcessUrl.equals(request.getServletPath())) {
            try {
                validate(request);
            } catch (VerifyCodeException e) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     * 验证图片验证码的逻辑
     */
    private void validate(HttpServletRequest request) throws VerifyCodeException {
        /** 客户端输入的验证码 */
        String request_verification_code = request.getParameter("verifyCode");
        /** 服务端生成的验证码 */
        String verification_code = "dgob";
        if (StringUtils.isEmpty(request_verification_code)) {
            throw new VerifyCodeException("验证码不能为空!");
        }
        if (!verification_code.equalsIgnoreCase(request_verification_code)) {
            throw new VerifyCodeException("验证码错误!");
        }
    }

}
