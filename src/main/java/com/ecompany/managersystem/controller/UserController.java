package com.ecompany.managersystem.controller;

import com.ecompany.managersystem.common.CommonStatusEnum;
import com.ecompany.managersystem.common.ResponseResult;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static com.ecompany.managersystem.common.CommonConstant.USER_RESOURCE_INFO_PATH;

/**
 * @Author: Ge
 * @Create: 2024-05-08 16:59
 * @Description: for both user and admin to use /user/{resource}
 */
@RestController
public class UserController {
    /**
     * @Description: for all user to access /user/{resource}
     * @Param: [resource, request]
     * @Return: java.lang.String
     * @Author: Ge
     * @Date: 2024/5/8
     */
    @GetMapping("/user/{resource}")
    public ResponseResult getUserResource(@PathVariable(value = "resource") String resource, HttpServletRequest request) {
        //1.get userId from header info
        String authorization = request.getHeader("Authorization");
        Long userId = getUserId(authorization);

        try {
            //2.access to the file
            BufferedReader reader = new BufferedReader(new FileReader(USER_RESOURCE_INFO_PATH));
            String line;
            while ((line = reader.readLine()) != null) {
                //3.read all lines inside file to check if user can access resource
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String part1 = parts[0];
                    String part2 = parts[1];
                    if (part1.equals(userId.toString()) && part2.equals(resource)) {
                        return ResponseResult.success("user:" + userId.toString() + " can access to the resource:" + resource);
                    }
                }
            }
            return ResponseResult.fail(CommonStatusEnum.USER_HAS_NO_ACCESS_TO_RESOURCE.getCode(),
                    CommonStatusEnum.USER_HAS_NO_ACCESS_TO_RESOURCE.getValue(),
                    "user:" + userId.toString() + " has no access to the resource:" + resource);

        } catch (FileNotFoundException e) {
            System.out.println("The resource file not found! Please make sure you have correct resource file!");
            return ResponseResult.fail(CommonStatusEnum.FILE_NOT_FOUND.getCode(), CommonStatusEnum.FILE_NOT_FOUND.getValue());
        } catch (Exception e) {
            System.out.println("Something wrong while accessing file to check if user can access resource!");
            return ResponseResult.fail(CommonStatusEnum.ERROR_CHECK_USER_RESOURCE.getCode(), CommonStatusEnum.ERROR_CHECK_USER_RESOURCE.getValue());
        }
    }

    private Long getUserId(String authorization) {
        String decodedHeader = new String(Base64.getDecoder().decode(authorization.substring(6)), StandardCharsets.UTF_8);
        JSONObject headerJson = JSONObject.fromObject(decodedHeader);
        return headerJson.getLong("userId");
    }

}
