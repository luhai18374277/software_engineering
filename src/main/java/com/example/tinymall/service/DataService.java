package com.example.tinymall.service;

import com.example.tinymall.entity.Good;
import com.example.tinymall.entity.Users;

import java.util.ArrayList;

public interface DataService {
    public ArrayList<Users> getAllUsers();
    public ArrayList<Good> getAllGoods();
    public void updateAllUsers(ArrayList<Users> users);
    public void updateAllGoods(ArrayList<Good> goods);
}
