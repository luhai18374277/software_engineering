package com.example.tinymall.controller;

import com.example.tinymall.Dao.DataDao;
import com.example.tinymall.api.CommonResult;
import com.example.tinymall.entity.*;
import com.example.tinymall.service.DataService;
import com.example.tinymall.service.MessageService;
import com.example.tinymall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DataController {
    @Autowired
    private DataService dataService;

    @RequestMapping(value = "/getData",method = RequestMethod.POST)
    public CommonResult getData() {
        try {
            Map<String,Map<String,Object>> resultMap=new HashMap<>();
            Map<String,Object> hashmap=new HashMap<>();
            ArrayList<Users> users_list=dataService.getAllUsers();
            ArrayList<Good> good_list = dataService.getAllGoods();
            hashmap.put("users",users_list);
            hashmap.put("goods",good_list);
            resultMap.put("data",hashmap);
            return CommonResult.success(hashmap);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.failed("获取数据失败");
        }
    }

    @RequestMapping(value = "/storeUsers",method = RequestMethod.POST)
    public CommonResult<String> storeUsers(@RequestBody Map<String,ArrayList<Users>> users) {
        try {
            dataService.updateAllUsers(users.get("data"));
            return CommonResult.success("store users success");
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.failed("store users failed");
        }
    }

    @RequestMapping(value = "/storeGoods",method = RequestMethod.POST)
    public CommonResult<String> storeGoods(@RequestBody Map<String,ArrayList<Good>> goods) {
        try {
            dataService.updateAllGoods(goods.get("data"));
            return CommonResult.success("store goods success");
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.failed("store users failed");
        }
    }

    @RequestMapping(value = "/test",method = RequestMethod.POST)
    public CommonResult<String> test(@RequestBody String username) {
        try {
            System.out.println(username);
            return CommonResult.success("yes!");
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.failed("no!!!");
        }
    }

}
