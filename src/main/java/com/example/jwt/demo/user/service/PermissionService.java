package com.example.jwt.demo.user.service;

import com.example.jwt.demo.user.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 谢霜
 * @since 2018-09-13
 */
public interface PermissionService extends IService<Permission> {
    Set<Permission> getPermissionByUsername(String username);
}
