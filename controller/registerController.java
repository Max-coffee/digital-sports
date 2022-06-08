package com.example.spring_boot.controller;

import com.example.spring_boot.controller.utils.R;
import com.example.spring_boot.dao.UserMapper;
import com.example.spring_boot.domain.Run;
import com.example.spring_boot.domain.User;
import com.example.spring_boot.service.runService;
import com.example.spring_boot.service.userService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
@CrossOrigin
@Controller
public class registerController {
    @Autowired
    private runService runService;

    @Autowired
    private userService userService;

    @RequestMapping("/Register")
    public ModelAndView register(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/jsp/register.jsp");
        return modelAndView;
    }

    @RequestMapping("/save")
    @ResponseBody
    public R save(@RequestBody User testUser, HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");


        //进行注册，即保存数据到数据库
        userService.save(testUser);

        //为用户创建运动数据表，除id，姓名外其余默认值为xx
        Run run = new Run();
        run.setId(testUser.getId());
        run.setUserName(testUser.getName());
        run.setFootType("xx");
        run.setFootImagePath("xx");
        run.setVideoPath("xx");
        run.setRunImprovements("xx");

        //存表
        runService.save(run);

        return new R(true,"注册成功");


    }
}
