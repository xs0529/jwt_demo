package com.example.jwt.demo.config.security.jwt;

import com.example.jwt.demo.common.util.ResponseUtils;
import com.example.jwt.demo.config.security.permission.MyGrantedAuthority;
import com.example.jwt.demo.exception.LoginFailException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: 谢霜
 * @Date: 2018/09/13 上午 10:26
 * @Description: JWT过滤器
 */
@Slf4j
public class JWTAuthenticationFilter extends BasicAuthenticationFilter {

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationManager, authenticationEntryPoint);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String header = request.getHeader(MySecurityConstant.HEADER);
        if(StringUtils.isBlank(header)){
            header = request.getParameter(MySecurityConstant.HEADER);
        }
        if (StringUtils.isBlank(header) || !header.startsWith(MySecurityConstant.TOKEN_SPLIT)) {
            chain.doFilter(request, response);
            return;
        }
        try {
            UsernamePasswordAuthenticationToken authentication = getAuthentication(request, response);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }catch (Exception e){
            e.toString();
        }

        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request, HttpServletResponse response) {

        String token = request.getHeader(MySecurityConstant.HEADER);
        if (StringUtils.isNotBlank(token)) {
            // 解析token
            Claims claims = null;
            try {
                claims = Jwts.parser()
                        .setSigningKey(MySecurityConstant.JWT_SIGN_KEY)
                        .parseClaimsJws(token.replace(MySecurityConstant.TOKEN_SPLIT, ""))
                        .getBody();

                //获取用户名
                String username = claims.getSubject();

                //获取权限
                List<MyGrantedAuthority> authorities = new ArrayList<>();
                String authority = claims.get(MySecurityConstant.AUTHORITIES).toString();

                if(StringUtils.isNotBlank(authority)){
                    List<String> list = new Gson().fromJson(authority, new TypeToken<List<String>>(){}.getType());
                    for(String ga : list){
                        String[] splits = ga.split(";");
                        authorities.add(new MyGrantedAuthority(splits[0],splits[1]));
                    }
                }
                if(StringUtils.isNotBlank(username)) {
                    //Exrick踩坑提醒 此处password不能为null
                    User principal = new User(username, "", authorities);
                    return new UsernamePasswordAuthenticationToken(principal, null, authorities);
                }
            } catch (ExpiredJwtException e) {
                throw new LoginFailException("登录已失效，请重新登录");
            } catch (Exception e){
                ResponseUtils.out(response, ResponseUtils.resultMap(false,500,"解析token错误"));
            }
        }
        return null;
    }
}
