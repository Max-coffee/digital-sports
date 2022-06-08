package com.example.spring_boot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.spring_boot.domain.Administrator;


public interface administratorService extends IService<Administrator> {
    Administrator findByNameAndPassword(String name, String password);
    void updatePassword(String password ,int id);
}
