package com.example.jwt.demo.user.mapper;

import com.example.jwt.demo.user.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 谢霜
 * @since 2018-09-13
 */
public interface PermissionMapper extends BaseMapper<Permission> {
    Set<Permission> getPermissionByUsername(@Param(value = "username") String username);
}
