package com.example.tinymall.service.lmpl;

import com.example.tinymall.Dao.DataDao;
import com.example.tinymall.Dao.FriendDao;
import com.example.tinymall.entity.Good;
import com.example.tinymall.entity.Users;
import com.example.tinymall.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DataServicelmpl implements DataService {
    @Autowired
    private DataDao dataDao;

    @Override
    public ArrayList<Users> getAllUsers() {
        return dataDao.getAllUsers();
    }

    @Override
    public ArrayList<Good> getAllGoods() {
        return dataDao.getAllGoods();
    }

    @Override
    public void updateAllUsers(ArrayList<Users> users) {
        dataDao.deleteRepeatUser();
        dataDao.updateAllUsers(users);
    }

    @Override
    public void updateAllGoods(ArrayList<Good> goods) {
        dataDao.deleteRepeatGood();
        dataDao.updateAllGoods(goods);
    }
}
