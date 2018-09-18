package com.example.jwt.demo.user.service;

import com.example.jwt.demo.DemoApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * @author: 谢霜
 * @Date: 2018/09/13 下午 18:48
 * @Description:
 */
public class PermissionServiceTest extends DemoApplicationTests {

    @Autowired
    private PermissionService permissionService;

    @Test
    public void getPermissionByUsernameTest(){
        permissionService.getPermissionByUsername("谢霜").forEach(System.err::println);
    }
}