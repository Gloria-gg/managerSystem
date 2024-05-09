package com.ecompany.managersystem.common;

import lombok.Getter;

/**
 * @Author: Ge
 * @Create: 2024-05-08 11:36
 * @Description: Common status enum class
 */
public enum CommonStatusEnum {
    /**
     * add user and info fail
     */
    ADD_USER_INFO_FAIL(1099, "Error while add user info! Please check if path file not exists or some other mistakes!"),

    FILE_NOT_FOUND(1100,"The user and resource file not found, please make sure you have the file inside the system!"),

    ERROR_CHECK_USER_RESOURCE(1101,"Something wrong while accessing file to check if user can access resource!"),

    USER_HAS_NO_ACCESS_TO_RESOURCE(1102,"User has no access to the resource!"),

    USER_HAS_NO_ACCESS_TO_ADMIN_FUNCTION(1103,"User has no access to the admin function!"),


    /**
     * success
     */
    SUCCESS(1, "success"),
    /**
     * fail
     */
    FAIL(0, "fail");

    @Getter
    private int code;
    @Getter
    private String value;

    CommonStatusEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
