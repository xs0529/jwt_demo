package com.example.jwt.demo.config.security.permission;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author: 谢霜
 * @Date: 2018/09/13 下午 19:09
 * @Description: 自定义的 GrantedAuthority 对象，实现restful权限
 */
@Data
public class MyGrantedAuthority implements GrantedAuthority {

    private String url;
    private String method;

    public MyGrantedAuthority(String url, String method) {
        this.url = url;
        this.method = method;
    }
    @Override
    public String getAuthority() {
        return this.url + ";" + this.method;
    }
}
