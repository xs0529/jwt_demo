package com.example.jwt.demo.user.service.impl;

import com.example.jwt.demo.user.entity.UserRole;
import com.example.jwt.demo.user.mapper.UserRoleMapper;
import com.example.jwt.demo.user.service.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 谢霜
 * @since 2018-09-13
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
