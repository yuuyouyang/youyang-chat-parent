package com.nf.yy.config.stomp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @author smile
 * @EnableWebSocketMessageBroker 注解表明： 这个配置类不仅配置了 WebSocket，还配置了基于代理的 STOMP消息；
 */
@Component
@EnableWebSocketMessageBroker
public class WebSocketStompConfig implements WebSocketMessageBrokerConfigurer {

    /** websocket连接拦截器，连接时验证用户是否登录 */
    @Autowired
    private WebSocketHandshakeInterceptor webSocketHandshakeInterceptor;

    /**
     * 将 "/stomp" 注册为一个 STOMP 端点。这个路径与之前发送和接收消息的目的地路径有所
     * 不同。这是一个端点，客户端在订阅或发布消息到目的地路径前，要连接到该端点。
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/socketServer/websocket").addInterceptors(webSocketHandshakeInterceptor).setAllowedOrigins("*");
        registry.addEndpoint("/socketServer/sockjs").addInterceptors(webSocketHandshakeInterceptor).setAllowedOrigins("*").withSockJS();
    }


    /**
     * 如果不重载它的话，将会自动配置一个简单的内存消息代理，用它来处理以"/topic"为前缀的消息
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 表明在topic、queue、users这三个域上可以向客户端发消息。
        // topic一般用于广播推送，queue用于点对点推送
        registry.enableSimpleBroker("/topic", "/queue", "/user");
        // 客户端向服务端发起请求时，需要以/app为前缀。
        registry.setApplicationDestinationPrefixes("/app");
        // 给指定用户发送一对一的消息前缀是/user。
        // 服务端通知客户端的前缀，可以不设置，默认为user
        registry.setUserDestinationPrefix("/user");
    }

}
