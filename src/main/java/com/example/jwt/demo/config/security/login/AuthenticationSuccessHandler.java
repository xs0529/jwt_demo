package com.example.jwt.demo.config.security.login;

import com.example.jwt.demo.common.util.ResponseUtils;
import com.example.jwt.demo.config.security.jwt.MySecurityConstant;
import com.example.jwt.demo.config.security.permission.MyGrantedAuthority;
import com.google.gson.Gson;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author: 谢霜
 * @Date: 2018/09/13 上午 10:26
 * @Description: 认证成功的处理类
 */
@Slf4j
@Component
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Value("${tokenExpireTime}")
    private Integer tokenExpireTime;
    @Value("${saveLoginTime}")
    private Integer saveLoginTime;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        //用户选择保存登录状态几天
        String saveTime = request.getParameter(MySecurityConstant.SAVE_LOGIN);
        if(StringUtils.isNotBlank(saveTime)&&Boolean.valueOf(saveTime)){
            tokenExpireTime = saveLoginTime * 60 * 24;
        }
        String username = ((UserDetails)authentication.getPrincipal()).getUsername();
        Collection<MyGrantedAuthority> collection = (Collection<MyGrantedAuthority>) ((UserDetails) authentication.getPrincipal()).getAuthorities();
        List<String> authorities = new ArrayList<>();
        for(MyGrantedAuthority g : collection){
            authorities.add(g.getAuthority());
        }
        //登陆成功生成JWT
        String token = Jwts.builder()
                //主题 放入用户名
                .setSubject(username)
                //自定义属性 放入用户拥有请求权限
                .claim(MySecurityConstant.AUTHORITIES, new Gson().toJson(authorities))
                //失效时间
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpireTime * 60 * 1000))
                //签名算法和密钥
                .signWith(SignatureAlgorithm.HS512, MySecurityConstant.JWT_SIGN_KEY)
                .compact();
        // 再次加密，解密时分割此段
        token = MySecurityConstant.TOKEN_SPLIT + token;

        ResponseUtils.out(response, ResponseUtils.resultMap(true,200,"登录成功",token));
    }
}