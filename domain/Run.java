package com.example.spring_boot.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class Run implements Serializable {
    private Integer id;
    private String userName;
    private String footType;
    private String footImagePath;
    private String videoPath;
    private String runImprovements;
}
