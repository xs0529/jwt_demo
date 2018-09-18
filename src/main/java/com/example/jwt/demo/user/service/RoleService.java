package com.example.jwt.demo.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.jwt.demo.user.entity.Role;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 谢霜
 * @since 2018-09-13
 */
public interface RoleService extends IService<Role> {
    List<Role> getRoleByUsername(String username);
}
