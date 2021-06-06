package com.example.tinymall.controller;


import com.example.tinymall.api.CommonResult;
import com.example.tinymall.entity.Users;
import com.example.tinymall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Map<String, Object> registerNewUser(@RequestBody Map<String, String> remap) {
        String username = remap.get("username");
        String password = remap.get("password");
        Map<String, Object> map = new HashMap<>();
        try {
            Users student = new Users(username, password);
            Users student1 = userService.selectUserByUsername(student.getUsername());
            if (student1 != null) {
                map.put("success", false);
                map.put("message", "用户名已注册！");
            }
            else{
                userService.registerNewUser(student);
                map.put("success", true);
                map.put("message", "用户注册成功！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("message", "用户注册失败！");
        }
        return map;
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public CommonResult<String> login(@RequestBody Users user, HttpSession session) {
        String username = user.getUsername();
        String password = user.getPassword();
        try {
            Users user1 = userService.selectUserByUsername(username);
            if (user1 != null) {
                Users user2 = userService.checkUserPassword(user1.getUsername(), password);
                if (user2 != null) {
                    session.setAttribute("loginUser", user2);
                    return CommonResult.success("用户登录成功");
                } else {
                    return CommonResult.failed("用户名或密码错误");
                }
            } else {
                return CommonResult.failed("用户名不存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.failed("用户登录失败!");
        }
    }

    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    public CommonResult<String> Logout(HttpSession session) {
        session.invalidate();
        return CommonResult.success("home");
    }
}
