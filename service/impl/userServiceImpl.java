package com.example.spring_boot.service.impl;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.alicp.jetcache.anno.CreateCache;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.spring_boot.dao.UserDao;
import com.example.spring_boot.domain.User;
import com.example.spring_boot.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class userServiceImpl extends ServiceImpl<UserDao,User> implements userService {

      @Autowired
      private UserDao userDao;
//内置操作数据库方法
//    //redis缓存
//    @CreateCache(name = "jetCache",expire = 10,timeUnit = TimeUnit.SECONDS,cacheType = CacheType.REMOTE)
//    private Cache<Serializable,User> jetCache;
//    //本地缓存
//    @CreateCache(name = "jetCache",expire = 10,timeUnit = TimeUnit.SECONDS,cacheType = CacheType.LOCAL)
//    private Cache<Serializable,User> jetCache1;

    @Override
    @Cached(area = "default",name = "User",key = "#id",expire = 10,timeUnit = TimeUnit.SECONDS,cacheType = CacheType.REMOTE)
    public User getById(Serializable id) {
        return super.getById(id);
    }

    public boolean delete(Integer id){
        return userDao.deleteById(id) >0;
    }


    @Override
//    @Cached(area = "loginUser",name = "loginUser",key = "#name",expire = 10,timeUnit = TimeUnit.MINUTES,cacheType = CacheType.REMOTE)
    public User findByPhoneAndPassword(String phone, String password) {
        return userDao.findQuery(phone,password);
    }

    @Override
    public void updatePassword(String password, int id) {
        userDao.updatePassword(password,id);
    }
}
