package com.ecompany.managersystem.entity;

import lombok.Data;

import java.util.List;

/**
 * @Author: Ge
 * @Create: 2024-05-08 16:46
 * @Description: for admin to add user with corresponding resource info
 */
@Data
public class UserInfo {
    private Long userId;
    private List<String> endpoint;
}
