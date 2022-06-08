package com.example.spring_boot.service.impl;

import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.spring_boot.dao.AdministratorDao;
import com.example.spring_boot.domain.Administrator;
import com.example.spring_boot.domain.User;
import com.example.spring_boot.service.administratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class administratorServiceImpl extends ServiceImpl<AdministratorDao, Administrator> implements administratorService {

    @Autowired
    private AdministratorDao administratorDao;

    @Override
//    @Cached(area = "administrator",name = "loginAdministrator",key = "#name",expire = 10,timeUnit = TimeUnit.MINUTES,cacheType = CacheType.REMOTE)
    public Administrator findByNameAndPassword(String name, String password) {return administratorDao.findQuery(name,password);}

    @Override
    public void updatePassword(String password, int id) {administratorDao.updatePassword(password, id);}
}
