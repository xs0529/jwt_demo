package com.example.jwt.demo.config.security.permission;

import com.example.jwt.demo.common.util.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: 谢霜
 * @Date: 2018/09/14 上午 11:17
 * @Description: 处理没有权限
 */
@Component
@Slf4j
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {

        ResponseUtils.out(response,ResponseUtils.resultMap(false,403,"抱歉，您没有访问权限"));
    }

}
