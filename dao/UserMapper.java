package com.example.spring_boot.dao;

import com.example.spring_boot.domain.User;
import org.apache.ibatis.annotations.*;

import java.io.FileInputStream;
import java.util.List;
@Mapper
public interface UserMapper  {
    @Select("select * from tb_user")
    public List<User> findAll();

    @Select("select * from tb_user where id=#{id}")
    public User findById(int id);

    @Select("select * from tb_user where name=#{name} and password=#{password}")
    public User findQuery(@Param("name")String name,@Param("password")String password);

    @Delete("delete from tb_user where id=#{id}")
    public void delete(int id);

    //更新密码
    @Update("update tb_user set password=#{password} where id=#{id} ")
    public void updatePassword(@Param("password") String password,@Param("id")int id);

    //更新uid
    @Update("update tb_user set id=#{id} where id=#{preUid}")
    public void updateUid(@Param("id")int uid,@Param("preUid")int preUid);

    //更新名字
    @Update("update tb_user set name=#{name} where id=#{preUid}")
    public void updateName(@Param("name")String name,@Param("preUid")int preUid);

    //更新性别
    @Update("update tb_user set gender=#{gender} where id=#{preUid}")
    public void updateGender(@Param("gender")int gender,@Param("preUid")int preUid);

    //更新生日
    @Update("update tb_user set birth=#{birth} where id=#{preUid}")
    public void updateBirth(@Param("birth")String birth,@Param("preUid")int preUid);

    //更新所在地
    @Update("update tb_user set place=#{place} where id=#{preUid}")
    public void updatePlace(@Param("place")String place,@Param("preUid")int preUid);

    //更新年级
    @Update("update tb_user set grade=#{grade} where id=#{preUid}")
    public void updateGrade(@Param("grade")String grade,@Param("preUid")int preUid);

    //更新年级id
    @Update("update tb_user set col_id=#{col_id} where id=#{preUid}")
    public void updateCol_id(@Param("col_id")int col_id,@Param("preUid")int preUid);





    //存图片路径
    @Update("update tb_user set imagePath=#{imagePath} where id=#{id}")
    public void imageInsert(@Param("imagePath")String imagePath,@Param("id")int id);

    //取图片路径
    @Select("select imagePath from tb_user where id=#{id}")
    public String imageQuery(@Param("id")int id);

    //保存user对象
    @Insert("insert into tb_user values(#{id},#{name},#{gender},#{birth},#{place},#{grade},#{col_id},#{password})")
    public void save(User user);
}
