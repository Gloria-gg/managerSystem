package com.ecompany.managersystem.controller;

import com.ecompany.managersystem.common.ResponseResult;
import com.ecompany.managersystem.entity.UserInfo;
import com.ecompany.managersystem.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Ge
 * @Create: 2024-05-08 16:31
 * @Description: for admin to access the unique url: /admin/addUser
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    @PostMapping("/addUser")
    public ResponseResult addUser(@RequestBody UserInfo userInfo) {
        return adminService.addUser(userInfo);
    }
}
