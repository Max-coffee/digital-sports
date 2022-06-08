package com.example.spring_boot.dao;

import com.example.spring_boot.domain.School;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;


import java.util.List;

public interface SchoolMapper {

    @Select("select * from tb_yonghu y,tb_college c where y.col_id = c.col_id")
    @Results({
        @Result(column = "col_id",property = "col_id"),
        @Result(column = "col_name",property = "col_name"),
            @Result(column = "uid",property = "user.uid"),
            @Result(column = "username",property = "user.name"),
            @Result(column = "gender",property = "user.gender"),
            @Result(column = "birth",property = "user.birth"),
            @Result(column = "place",property = "user.place"),
            @Result(column = "grade",property = "user.grade"),
            @Result(column = "user_col_id",property = "user.col_id"),
            @Result(column = "password",property = "user.password"),

    })
    public List<School> findAll();
}
