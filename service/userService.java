package com.example.spring_boot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.spring_boot.domain.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface userService extends IService<User> {
    User findByPhoneAndPassword(String Phone,String password);
    void updatePassword(String password ,int id);

}
