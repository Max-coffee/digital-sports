package com.example.spring_boot.domain;

import lombok.Data;

import java.io.Serializable;
@Data
public class Administrator implements Serializable {
    private Integer id;
    private String name;
    private String password;
    private String permission;
}
