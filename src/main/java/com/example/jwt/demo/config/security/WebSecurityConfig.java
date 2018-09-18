package com.example.jwt.demo.config.security;

import com.example.jwt.demo.config.security.jwt.JWTAuthenticationFilter;
import com.example.jwt.demo.config.security.login.AuthenticationFailHandler;
import com.example.jwt.demo.config.security.login.UserDetailsServiceImpl;
import com.example.jwt.demo.config.security.permission.IgnoredUrlsProperties;
import com.example.jwt.demo.config.security.permission.MyAccessDecisionManager;
import com.example.jwt.demo.config.security.permission.MyFilterSecurityInterceptor;
import com.example.jwt.demo.config.security.permission.RestAccessDeniedHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 谢霜
 * @Date: 2018/09/13 上午 10:21
 * @Description: security配置类
 * 调用顺序 1
 */
@Slf4j
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private IgnoredUrlsProperties ignoredUrlsProperties;

    // 登录处理
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    // 登录成功处理
    @Autowired
    private AuthenticationSuccessHandler successHandler;
    // 登录失败处理
    @Autowired
    private AuthenticationFailHandler failHandler;
    // 没有权限处理
    @Autowired
    private RestAccessDeniedHandler accessDeniedHandler;

    @Autowired
    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    /*@Override
    public void configure(WebSecurity web) throws Exception {
        for(String url:ignoredUrlsProperties.getUrls()){
            web.ignoring().antMatchers(url);
        }
    }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http
                .authorizeRequests();

        //除配置文件忽略路径其它所有请求都需经过认证和授权
        for(String url:ignoredUrlsProperties.getUrls()){
            registry.antMatchers(url).permitAll();
        }

        registry.and()
                //表单登录方式
                .formLogin()
               /* .loginPage("/xboot/common/needLogin")*/
                //登录请求url
                .loginProcessingUrl("/login")
                .permitAll()
                //成功处理类
                .successHandler(successHandler)
                //失败
                .failureHandler(failHandler)
                .and()
                //允许网页iframe
                .headers().frameOptions().disable()
                .and()
                //登出请求
                .logout()
                //都可以访问
                .permitAll()
                .and()
                .authorizeRequests()
                //任何请求
                .anyRequest()
                //需要身份认证
                .authenticated()
                .and()
                //关闭跨站请求防护
                .csrf().disable()
                //前后端分离采用JWT 不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //自定义权限拒绝处理类
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler)
                .and()
                //添加自定义权限过滤器
                /*.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class)*/
                //添加JWT过滤器 除/xboot/login其它请求都需经过此过滤器
                .addFilter(new JWTAuthenticationFilter(authenticationManager()));
    }

    @Bean
    public MyAccessDecisionManager myAccessDecisionManager(){
        MyAccessDecisionManager myAccessDecisionManager = new MyAccessDecisionManager();
        List<AntPathRequestMatcher> matcherList = new ArrayList<>();
        for(String url:ignoredUrlsProperties.getUrls()){
            matcherList.add(new AntPathRequestMatcher(url));
        }
        myAccessDecisionManager.setIgnoredMatchers(matcherList);
        return myAccessDecisionManager;
    }
}
