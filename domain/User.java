package com.example.spring_boot.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private Integer id;
    private String name;
    private Integer gender;
    private String birth;
    private String place;
    private String phone;
    private int age;
    private String password;


}
