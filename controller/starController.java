package com.example.spring_boot.controller;

import com.example.spring_boot.controller.utils.R;
import com.example.spring_boot.domain.User;
import com.example.spring_boot.service.userService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/star")
public class starController {

    @Autowired
    private userService userService;

    @GetMapping("/get")
    public R getAll(){
        List<User> list = userService.list();
//        log.debug("dubuging...");
//        log.info("infoing...");
//        log.warn("warning...");
//        log.error("error");
        System.out.println("热部署启动成功");
        return new R(true,list);
    }

    @GetMapping("/{id}")
    public R findId(@PathVariable Integer id){
        User user = userService.getById(id);
        return  new R(true,user);
    }

}
