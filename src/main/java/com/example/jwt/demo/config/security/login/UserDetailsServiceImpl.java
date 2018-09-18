package com.example.jwt.demo.config.security.login;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.jwt.demo.config.redis.RedisUtils;
import com.example.jwt.demo.config.security.permission.MyGrantedAuthority;
import com.example.jwt.demo.exception.LoginFailException;
import com.example.jwt.demo.user.entity.Permission;
import com.example.jwt.demo.user.entity.User;
import com.example.jwt.demo.user.service.PermissionService;
import com.example.jwt.demo.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author: 谢霜
 * @Date: 2018/09/13 上午 10:22
 * @Description: 处理用户登录
 */
@Slf4j
@Component
public class UserDetailsServiceImpl implements UserDetailsService {


    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RedisUtils redisUtils;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = new User();
        user.setUsername(username);
        user = user.selectOne(new QueryWrapper(user));
        if (user == null){
            throw new LoginFailException("用户名: " + username + " 不存在!");
        }
        String flagKey = "loginFailFlag:"+username;
        String value = (String) redisUtils.get(flagKey);
        Long timeRest = redisUtils.getExpire(flagKey,TimeUnit.MINUTES);
        if(StringUtils.isNotBlank(value)){
            //超过限制次数
            throw new LoginFailException("登录错误次数超过限制，请"+timeRest+"分钟后再试");
        }
        List<GrantedAuthority> authorityList = new ArrayList<>();
        Set<Permission> permissions = permissionService.getPermissionByUsername(username);
        // 添加请求权限
        if(permissions!=null&&permissions.size()>0){
            for (Permission permission : permissions) {
                if(StringUtils.isNotBlank(permission.getName())
                        &&StringUtils.isNotBlank(permission.getUrl())) {
                    authorityList.add(new MyGrantedAuthority(permission.getUrl(),permission.getMethod()));
                }
            }
        }
        return new org.springframework.security.core.userdetails.User(username,user.getPassword(),authorityList);
    }
}
