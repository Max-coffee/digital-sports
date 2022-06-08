package com.example.spring_boot.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.spring_boot.domain.Run;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RunDao extends BaseMapper<Run> {


    @Select("select footImagePath from f_run where id=#{id}")
    public String getFoot_image_path(int id);

    @Select("select footType from f_run where id=#{id}")
    public String getFoot_type(int id);

    @Select("select videoPath from f_run where id=#{id}")
    public String getVideo_path(int id);

    @Select("select runImprovements from f_run where id=#{id}")
    public String getRun_improvements(int id);

}
