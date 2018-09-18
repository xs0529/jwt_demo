package com.example.jwt.demo.user.mapper;

import com.example.jwt.demo.user.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 谢霜
 * @since 2018-09-13
 */
public interface RoleMapper extends BaseMapper<Role> {
    List<Role> getRoleByUsername(@Param(value = "username") String username);
}
