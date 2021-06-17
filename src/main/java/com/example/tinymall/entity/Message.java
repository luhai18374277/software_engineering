package com.example.tinymall.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer MID;

    private String fromUser;
    private String toUser;
    private String Msg;

    @Override
    public String toString() {
        return "Message{" +
                "MID=" + MID +
                ", text='" + Msg + '\'' +
                '}';
    }

}
