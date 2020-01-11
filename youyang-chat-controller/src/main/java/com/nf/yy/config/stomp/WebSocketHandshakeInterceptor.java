package com.nf.yy.config.stomp;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;

/**
 * websocket拦截器，连接时验证用户是否登录
 * @author smile
 */
@Component
public class WebSocketHandshakeInterceptor extends HttpSessionHandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!StringUtils.isEmpty(userId)) {
            attributes.put("userId", userId);
            return true;
        } else {
            return false;
        }
    }

}
