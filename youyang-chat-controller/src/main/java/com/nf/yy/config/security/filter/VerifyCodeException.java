package com.nf.yy.config.security.filter;

import org.springframework.security.core.AuthenticationException;

/**
 * 自定义验证码错误异常
 * @author smile
 */
public class VerifyCodeException extends AuthenticationException {

    public VerifyCodeException(String msg) {
        super(msg);
    }

}
