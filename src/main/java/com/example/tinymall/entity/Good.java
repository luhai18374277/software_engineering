package com.example.tinymall.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.criteria.CriteriaBuilder;

@Data
@Entity
public class Good {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer GID;

    private String id;
    private String title;
    private Double price;
    private Double oldPrice;
    private String detail;
    private String image;
    private Integer num;
}
