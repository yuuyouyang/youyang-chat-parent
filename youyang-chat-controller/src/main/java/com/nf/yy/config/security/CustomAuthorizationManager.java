package com.nf.yy.config.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义授权管理器
 * @author smile
 */
@Component
public class CustomAuthorizationManager {

    public boolean authorizationManager(Authentication authentication, HttpServletRequest request) {
        return true;
    }

}
