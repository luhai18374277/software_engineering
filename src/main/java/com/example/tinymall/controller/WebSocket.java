package com.example.tinymall.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.tinymall.config.ApplicationHelper;
import com.example.tinymall.entity.InitSocket;
import com.example.tinymall.entity.Message;
import com.example.tinymall.entity.Users;
import com.example.tinymall.service.DataService;
import com.example.tinymall.service.MessageService;
import com.example.tinymall.service.UserService;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.ContextLoader;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint(value = "/friendChat/{username}/{friendName}")
public class WebSocket {
    public static MessageService messageService ;

    public static UserService userService ;

    private String username;
    private String friendName;
    private Session session;
    //用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet<WebSocket>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    //用来记录sessionId和该session进行绑定
    private static Map<String, Session> map = new HashMap<String, Session>();

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username, @PathParam("friendName") String friendName) {
        Map<String,Object> message=new HashMap<String, Object>();
        this.session = session;
        this.username = username;
        Users user1 = userService.selectUserByUserid(username);
        Users user2 = userService.selectUserByUserid(friendName);
        map.put(user1.getId(), session);
        webSocketSet.add(this);//加入set中
        System.out.println("有新连接加入:" + username + ",当前在线人数为" + webSocketSet.size());
        //this.session.getAsyncRemote().sendText("恭喜" + username + "成功连接上WebSocket(其频道号：" + session.getId() + ")-->当前在线人数为：" + webSocketSet.size());
        message.put("name", user2.getName()); //昵称
        List<Message> messageList = new ArrayList<>();
        List<Map<String,String>> contentList = new ArrayList<>();
        messageList = messageService.selectMessageBySidandFid(user1.getId(), user2.getId());
        for (Message i :
                messageList) {
            Map<String,String> m=new HashMap<>();
            Users users=userService.selectUserByUserid(i.getFromUser());
            m.put("userId",users.getId());
            m.put("username",users.getName());
            m.put("content",i.getMsg());
            contentList.add(m);
        }
        message.put("list", contentList);
        Session toSession = map.get(user2.getId());
        message.put("state", (toSession == null) ? "离线" : "在线");
        message.put("id", user2.getId());
        message.put("avatar", user2.getProfileImage());
        onMessage(JSONObject.toJSONString(message),username,friendName);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this); //从set中删除
        System.out.println("有一连接关闭！当前在线人数为" + webSocketSet.size());
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, @PathParam("username") String username, @PathParam("friendName") String friendName) {
        System.out.println("来自客户端的消息-->" + username + ": " + message);
        Message socketMsg = null;
        InitSocket initSocket = null;
        Map<String, Object> m = new HashMap<String, Object>();
        try {
            socketMsg = JSONObject.parseObject(message, Message.class);
        } catch (JsonParseException e) {
            e.printStackTrace();
            initSocket = JSONObject.parseObject(message, InitSocket.class);
        }
        if(socketMsg!=null&&socketMsg.getFromUser()==null&&socketMsg.getToUser()==null){
            initSocket = JSONObject.parseObject(message, InitSocket.class);
        }
        if(socketMsg!=null&&socketMsg.getFromUser()!=null){
            try{
                Session fromSession = map.get(socketMsg.getFromUser());
                Session toSession = map.get(socketMsg.getToUser());
                Users user1 = userService.selectUserByUserid(socketMsg.getFromUser());
                Users user2 = userService.selectUserByUserid(socketMsg.getToUser());
                //存入数据库
                messageService.sendNewMessage(socketMsg);

                m.put("type","msg");
                m.put("state", (socketMsg.getFromUser() != username && fromSession == null) || (socketMsg.getFromUser() != friendName && toSession == null) ? "离线" : "在线");

                m.put("userId", user1.getId());
                m.put("username", user1.getName());
                m.put("msg", socketMsg.getMsg());
                if (fromSession != null)
                    fromSession.getBasicRemote().sendText(JSONObject.toJSONString(m));
                if (toSession != null)
                    toSession.getBasicRemote().sendText(JSONObject.toJSONString(m));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(initSocket!=null){
            try{
                m.put("type","init");
                m.put("data",message);
                this.session.getBasicRemote().sendText(JSONObject.toJSONString(m));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 群发自定义消息
     */
    public void broadcast(String message) {
        for (WebSocket item : webSocketSet) {
            item.session.getAsyncRemote().sendText(message);//异步发送消息.
        }
    }

}
