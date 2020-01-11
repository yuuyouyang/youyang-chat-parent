package com.nf.yy.config.security.handler;

import com.nf.yy.util.JsonUtils;
import com.nf.yy.vo.ResponseVO;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义未授权处理器
 * @author smile
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        JsonUtils.write(response.getOutputStream(), ResponseVO.failed("抱歉，你没有访问权限！"));
    }

}
