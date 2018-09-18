package com.example.jwt.demo.user.service.impl;

import com.example.jwt.demo.user.entity.Permission;
import com.example.jwt.demo.user.mapper.PermissionMapper;
import com.example.jwt.demo.user.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 谢霜
 * @since 2018-09-13
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public Set<Permission> getPermissionByUsername(String username) {
        return permissionMapper.getPermissionByUsername(username);
    }
}
