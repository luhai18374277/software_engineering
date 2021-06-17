package com.example.tinymall.Dao;

import com.example.tinymall.entity.Message;
import com.example.tinymall.entity.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface MessageDao {


    public void sendNewMessage(Message message);

    public ArrayList<Message> selectMessageBySidandFid(@Param("uid") String userId, @Param("fid") String friendId);

    public void messageDeleteByMid(Message message);
}
