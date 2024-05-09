package com.ecompany.managersystem.interceptor;

import com.ecompany.managersystem.common.CommonStatusEnum;
import com.ecompany.managersystem.common.ResponseResult;
import net.sf.json.JSONObject;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @Author: Ge
 * @Create: 2024-05-08 11:55
 * @Description: This interceptor class will check all the input urls
 * If the base64 info indicate that the user is admin, then /admin/addUser can be visited,
 * If not,/admin/addUser will not be visited;
 * If base64 info is valid, then they can visit other urls,
 * If not,then return invalid error message
 */
public class RoleInterceptor implements HandlerInterceptor {
    /**
     * @Description: process base64 info and give access to different types of users
     * @Param: [request, response, handler]
     * @Return: boolean
     * @Author: Ge
     * @Date: 2024/5/8
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1.get base64 info
        String encodedHeader = request.getHeader("Authorization");

        //2.resolve the role info inside base64
        if (encodedHeader != null && encodedHeader.startsWith("Basic ")) {
            String decodedHeader = new String(Base64.getDecoder().decode(encodedHeader.substring(6)), StandardCharsets.UTF_8);
            JSONObject headerJson = JSONObject.fromObject(decodedHeader);

            //3.check base64 info, if not satisfy the format, then return error message
            if (!checkJson(headerJson)) {
                String resutltString = "Decoded header is invalid, please input correct header!";
                PrintWriter out = response.getWriter();
                out.write(JSONObject.fromObject(ResponseResult.fail(resutltString)).toString());
                return false;
            }

            //4.check the role,if admin, then can access; if not,return error message
            if (headerJson.has("role") && headerJson.getString("role").equals("admin")) {
                return true;
            } else if (headerJson.has("role") && headerJson.getString("role").equals("user")) {
                //user can only access url starts with "/user"
                if (request.getRequestURI().startsWith("/admin")) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write(JSONObject.fromObject(ResponseResult.fail(CommonStatusEnum.USER_HAS_NO_ACCESS_TO_ADMIN_FUNCTION.getCode(), CommonStatusEnum.USER_HAS_NO_ACCESS_TO_ADMIN_FUNCTION.getValue())).toString());
                    return false;
                }
                return true;
            }
        }

        String resutltString = "Invalid Authorization header! Or invalid url! Input correct header or url!";
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().print(JSONObject.fromObject(ResponseResult.fail(resutltString)).toString());

        return false;
    }

    /*** 
     * @Description: check jsonobject if contains three parts of user information
     * @Param: [jsonObject]
     * @Return: boolean
     * @Author: Ge
     * @Date: 2024/5/8
     */
    public boolean checkJson(JSONObject jsonObject) {
        if (jsonObject.has("userId") &&
                jsonObject.has("accountName") &&
                jsonObject.has("role") &&
                (jsonObject.getString("role").equals("admin") || (jsonObject.getString("role").equals("user")))) {
            return true;
        }
        return false;
    }
}
