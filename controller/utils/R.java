package com.example.spring_boot.controller.utils;

import lombok.Data;

@Data
public class R {
    private boolean flag;
    private Object date;
    private String msg;


    public  R (){}

    public  R (boolean flag){
        this.flag =flag;
    }
    public  R (boolean flag,Object date){
        this.flag =flag;
        this.date =date;
    }

    public  R (String msg){
        this.flag =false;
        this.msg=msg;
    }

    public  R (boolean flag,Object date,String msg){
        this.flag =flag;
        this.date =date;
        this.msg =msg;
    }
}
