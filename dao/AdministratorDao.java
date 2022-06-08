package com.example.spring_boot.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.spring_boot.domain.Administrator;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AdministratorDao extends BaseMapper<Administrator> {

    //登录匹配
    @Select("select * from f_administrator where name=#{name} and password=#{password}")
    public Administrator findQuery(@Param("name")String name, @Param("password")String password);

    //更新密码
    @Update("update f_administrator set password=#{password} where id=#{id} ")
    public void updatePassword(@Param("password") String password,@Param("id")int id);
}
