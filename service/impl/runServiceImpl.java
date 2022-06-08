package com.example.spring_boot.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.spring_boot.dao.RunDao;
import com.example.spring_boot.domain.Run;
import com.example.spring_boot.service.runService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class runServiceImpl extends ServiceImpl<RunDao, Run> implements runService {

    @Autowired
    private RunDao runDao;

    @Override
    public String getImagePath(int id) {
        return runDao.getFoot_image_path(id);
    }

    @Override
    public String getFootType(int id) {
        return runDao.getFoot_type(id);
    }

    @Override
    public String getVideoPath(int id) {
        return runDao.getVideo_path(id);
    }

    @Override
    public String getRunImprovements(int id) {
        return runDao.getRun_improvements(id);
    }


}
