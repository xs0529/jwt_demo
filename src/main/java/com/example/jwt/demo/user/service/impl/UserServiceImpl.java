package com.example.jwt.demo.user.service.impl;

import com.example.jwt.demo.user.entity.User;
import com.example.jwt.demo.user.mapper.UserMapper;
import com.example.jwt.demo.user.service.UserService;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
