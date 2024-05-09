package com.ecompany.managersystem.service;

import com.ecompany.managersystem.common.CommonStatusEnum;
import com.ecompany.managersystem.common.ResponseResult;
import com.ecompany.managersystem.entity.UserInfo;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static com.ecompany.managersystem.common.CommonConstant.USER_RESOURCE_INFO_PATH;

/**
 * @Author: Ge
 * @Create: 2024-05-08 17:04
 * @Description: service class for AdminController
 */
@Service
public class AdminService {
    /**
     * @Description: for admin to add user, corresponding with "addUser" method inside AdminController
     * @Param: [userInfo]
     * @Return: com.ecompany.managersystem.common.ResponseResult
     * @Author: Ge
     * @Date: 2024/5/8
     */
    public ResponseResult addUser(UserInfo userInfo) {
        // get userId and resource
        Long userId = userInfo.getUserId();
        List<String> endpoints = userInfo.getEndpoint();

        //put info into local file path
        try {
            FileWriter writer = new FileWriter(USER_RESOURCE_INFO_PATH, true);
            for (String endpoint : endpoints) {
                writer.write(userId + ":" + endpoint + "\n");
            }
            writer.close();
            System.out.println("User: " + userId + " resource info has been added successfully!");
            return ResponseResult.success();

        } catch (IOException e) {
            System.out.println("Error occurred while adding resource info for user: " + userId);
            return ResponseResult.fail(CommonStatusEnum.ADD_USER_INFO_FAIL.getCode(), CommonStatusEnum.ADD_USER_INFO_FAIL.getValue());
        }
    }
}
