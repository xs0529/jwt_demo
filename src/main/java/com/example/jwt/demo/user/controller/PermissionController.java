package com.example.jwt.demo.user.controller;


import com.example.jwt.demo.common.base.BaseController;
import com.example.jwt.demo.user.entity.Permission;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 谢霜
 * @since 2018-09-13
 */
@Controller
@RequestMapping("/permission")
public class PermissionController extends BaseController<Permission> {

}

