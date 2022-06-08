package com.example.spring_boot.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.spring_boot.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserDao extends BaseMapper<User> {

    //登录匹配
    @Select("select * from f_user where phone=#{phone} and password=#{password}")
    public User findQuery(@Param("phone")String phone,@Param("password")String password);

    //更新密码
    @Update("update f_user set password=#{password} where id=#{id} ")
    public void updatePassword(@Param("password") String password,@Param("id")int id);
}
