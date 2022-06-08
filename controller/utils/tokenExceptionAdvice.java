package com.example.spring_boot.controller.utils;


import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class tokenExceptionAdvice {

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseBody
    public R doException(ExpiredJwtException e){
        e.printStackTrace();
        return new R("token超时");
    }

}
