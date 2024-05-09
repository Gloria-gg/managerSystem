package com.ecompany.managersystem;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class ManagersystemApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    /**
     * @Description: access random interface with no Authorization info
     * it will get:  {"code":0,"data":"Invalid Authorization header! Or invalid url! Input correct header or url!","message":""} error message
     * @Param: []
     * @Return: void
     * @Author: Ge
     * @Date: 2024/5/8
     */
    @Test
    public void testRandomWithNoAuthorizationInfo() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/hello")
                        .header("Authorization", "")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":123456,\"endpoint\":[\"resource A\",\"resource B\",\"resource C\"]}"))
                .andDo(MockMvcResultHandlers.print());
    }


    /**
     * @Description: access random interface with Authorization info
     * it will return:  {"code":0,"data":"Invalid Authorization header! Or invalid url! Input correct header or url!","message":""} error message
     * @Param: []
     * @Return: void
     * @Author: Ge
     * @Date: 2024/5/8
     */
    @Test
    public void testRandomWithAuthorizationInfo() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/hello")
                        .header("Authorization", "Basic ewoidXNlcklkIjoxMjM0NTYsCiJhY2NvdW50TmFtZSI6ICJYWFhYWFhYIiwKInJvbGUiOiAidXNlciIKfQ==")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":123456,\"endpoint\":[\"resource A\",\"resource B\",\"resource C\"]}"))
                .andDo(MockMvcResultHandlers.print());
    }

    /***
     * @Description: Simulate admin access to /admin/addUser interface
     * test: /admin/addUser
     * it will get:{"code":1,"message":"success","data":null} success message
     * @Param: []
     * @Return: void
     * @Author: Ge
     * @Date: 2024/5/8
     */
    @Test
    public void testAdminAccessToAdminEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/addUser")
                        .header("Authorization", "Basic ewoidXNlcklkIjoxMjM0NTYsCiJhY2NvdW50TmFtZSI6ICJYWFhYWFhYIiwKInJvbGUiOiAiYWRtaW4iCn0=")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":123,\"endpoint\":[\"resource A\",\"resource B\",\"resource C\"]}"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    /***
     * @Description: Simulate user access to /admin/addUser interface
     * test: /admin/addUser
     * it will return: {"code":1103,"data":null,"message":"User has no access to the admin function!"} error message
     * @Param: []
     * @Return: void
     * @Author: Ge
     * @Date: 2024/5/8
     */
    @Test
    public void testUserAccessToAdminEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/addUser")
                        .header("Authorization", "Basic ewoidXNlcklkIjoxMjM0NTYsCiJhY2NvdW50TmFtZSI6ICJYWFhYWFhYIiwKInJvbGUiOiAidXNlciIKfQ==")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":12345678,\"endpoint\":[\"resource A\",\"resource B\",\"resource C\"]}"))
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * @Description: Simulate not the system user access to /user/resource A interface
     * the user id is:588,role is "gege" which is not the role inside the system
     * it will return:{"code":0,"data":"Decoded header is invalid, please input correct header!","message":""}
     * error message
     * @Param:
     * @Return: void
     * @Author: Ge
     * @Date: 2024/5/8
     */
    @Test
    public void testAnotherUserAccessToUserEndpoint() throws Exception {
        String resource = "resource A";
        String url = "/user/" + URLEncoder.encode(resource, StandardCharsets.UTF_8.toString()).replace("+", " ");
        mockMvc.perform(MockMvcRequestBuilders.get(url)
                        .header("Authorization", "Basic ewoidXNlcklkIjo1ODgsCiJhY2NvdW50TmFtZSI6ICJYWFhYWFhYIiwKInJvbGUiOiAiZ2VnZSIKfQ==")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * @Description: Simulate user access to /user/resource A interface
     * the user id is:123456
     * file has this user and resource
     * which means user can get the success return message
     * it will return:{"code":1,"message":"success","data":"user:123456 can access to the resource:resource A"}
     * success message
     * @Param:
     * @Return: void
     * @Author: Ge
     * @Date: 2024/5/8
     */
    @Test
    public void testUserAccessToUserEndpoint() throws Exception {
        String resource = "resource A";
        String url = "/user/" + URLEncoder.encode(resource, StandardCharsets.UTF_8.toString()).replace("+", " ");
        mockMvc.perform(MockMvcRequestBuilders.get(url)
                        .header("Authorization", "Basic ewoidXNlcklkIjoxMjM0NTYsCiJhY2NvdW50TmFtZSI6ICJYWFhYWFhYIiwKInJvbGUiOiAidXNlciIKfQ==")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * @Description: Simulate user access to /user/resource E interface
     * the user id is:123456
     * file doesn't have this user and resource
     * which means user can not get the success return message
     * it will return:{"code":1102,"message":"User has no access to the resource!","data":"user:123456 has no access to the resource:resource E"}
     * success message
     * @Param:
     * @Return: void
     * @Author: Ge
     * @Date: 2024/5/8
     */
    @Test
    public void testUserAccessToUserEndpointWithNoResource() throws Exception {
        String resource = "resource E";
        String url = "/user/" + URLEncoder.encode(resource, StandardCharsets.UTF_8.toString()).replace("+", " ");
        mockMvc.perform(MockMvcRequestBuilders.get(url)
                        .header("Authorization", "Basic ewoidXNlcklkIjoxMjM0NTYsCiJhY2NvdW50TmFtZSI6ICJYWFhYWFhYIiwKInJvbGUiOiAidXNlciIKfQ==")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }


}
