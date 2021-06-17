package com.example.tinymall.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer UID;

    private String id;
    private String name;
    private String phone;
    private String email;
    private String profileImage;
    private String password;
    private String birthday;

    public Users(){}

    public Users(String name, String password) {
        this.password = password;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Users{" +
                "UID=" + id +
                ", username='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", Gender='" + profileImage + '\'' +
                ", Avatar='" + profileImage + '\'' +
                ", Phone='" + phone + '\'' +
                '}';
    }
}