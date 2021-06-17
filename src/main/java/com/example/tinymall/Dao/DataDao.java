package com.example.tinymall.Dao;

import com.example.tinymall.entity.Good;
import com.example.tinymall.entity.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

@Mapper
public interface DataDao {
    public ArrayList<Users> getAllUsers();
    public ArrayList<Good> getAllGoods();
    public void updateAllUsers(@Param("users") ArrayList<Users> users);
    public void updateAllGoods(@Param("goods") ArrayList<Good> goods);
    public void deleteRepeatGood();
    public void deleteRepeatUser();
}
