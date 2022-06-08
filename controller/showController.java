package com.example.spring_boot.controller;

import com.example.spring_boot.controller.utils.jwtUtils;
import com.example.spring_boot.domain.User;
import com.example.spring_boot.service.runService;
import com.example.spring_boot.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@Controller
@RequestMapping("/show")
public class showController {

    @Autowired
    private jwtUtils jwtUtils;

    @Autowired
    private runService runService;

    @RequestMapping("/show")
    public ModelAndView show(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/jsp/image.jsp");
        return modelAndView;
    }

    @RequestMapping("/image")
    public void showImage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        //获取token解析用户对象
        String token = request.getHeader("token");
        String id = jwtUtils.checkToken(token);

        //得到图片路径
        String imagePath = runService.getImagePath(Integer.parseInt(id));

        //写图片文件
        File file = new File(imagePath);
        FileInputStream fileInputStream = new FileInputStream(file);
        response.setContentType("Image/*");
        OutputStream outputStream = response.getOutputStream();
        int count = 0 ;
        byte[] bytes = new byte[1024];
        while ((count = fileInputStream.read(bytes))!=-1){
            outputStream.write(bytes,0,count);
            outputStream.flush();
        }
        fileInputStream.close();
        outputStream.close();
    }

    @RequestMapping("/video")
    public void showVideo(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        //获取token解析用户对象
        String token = request.getHeader("token");
        String id = jwtUtils.checkToken(token);

        //得到视频路径
        String videoPath = runService.getVideoPath(Integer.parseInt(id));

        //写视频文件
        File file = new File(videoPath);
        FileInputStream fileInputStream = new FileInputStream(file);
        response.setContentType("Video/*");
        OutputStream outputStream = response.getOutputStream();
        int count = 0 ;
        byte[] bytes = new byte[1024];
        while ((count = fileInputStream.read(bytes))!=-1){
            outputStream.write(bytes,0,count);
            outputStream.flush();
        }
        fileInputStream.close();
        outputStream.close();
    }
}