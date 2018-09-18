package com.example.jwt.demo.user.service.impl;

import com.example.jwt.demo.user.entity.Role;
import com.example.jwt.demo.user.mapper.RoleMapper;
import com.example.jwt.demo.user.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 谢霜
 * @since 2018-09-13
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> getRoleByUsername(String username) {
        return roleMapper.getRoleByUsername(username);
    }
}
