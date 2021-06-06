package com.example.tinymall.Dao;

import com.example.tinymall.entity.Users;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    public Users selectUserByUsername(String name);
    public void registerNewUser(Users user);

    public Users checkUserPassword(String username, String password);
}
