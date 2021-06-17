package com.example.tinymall.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class InitSocket {
    private String name;
    private ArrayList<Map> list;
    private String state;
    private String avatar;
    private String id;
}
