package com.example.tinymall.config;

import com.example.tinymall.controller.WebSocket;
import com.example.tinymall.service.MessageService;
import com.example.tinymall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
    @Autowired
    public void setMessageService(MessageService messageService){
        WebSocket.messageService=messageService;
    }
    @Autowired
    public void setUserService(UserService userService){
        WebSocket.userService=userService;
    }
}