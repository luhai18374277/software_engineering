package com.example.tinymall.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer OID;

    private String id;
    private Integer GID; //商品ID
    private Integer UID; //用户ID
    private String address; //配送位置
    private Integer num;//商品数量

    public Orders() {}

    public Orders(Integer GID, Integer UID, String address) {
        this.GID = GID;
        this.UID = UID;
        this.address = address;
    }

}
