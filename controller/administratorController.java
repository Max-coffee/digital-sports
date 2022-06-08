package com.example.spring_boot.controller;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import com.example.spring_boot.controller.utils.R;
import com.example.spring_boot.controller.utils.jwtUtils;
import com.example.spring_boot.domain.Administrator;
import com.example.spring_boot.domain.AdministratorUser;
import com.example.spring_boot.domain.LoginUser;
import com.example.spring_boot.domain.User;
import com.example.spring_boot.service.administratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@CrossOrigin
@RestController
@RequestMapping("/administrator")
public class administratorController {
    //redis缓存
    @CreateCache(area = "token",name = "administrator",expire = 10,timeUnit = TimeUnit.MINUTES,cacheType = CacheType.REMOTE)
    private Cache<Serializable,String> redis;
    @Autowired
    private jwtUtils jwtUtils;
    @Autowired
    private com.example.spring_boot.service.administratorService administratorService;


    @RequestMapping("/login")
    @ResponseBody
    public R administratorLogin(@RequestBody AdministratorUser administratorUser, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //获得验证码
        request.setCharacterEncoding("utf-8");
        String username = administratorUser.getName();
        String password = administratorUser.getPassword();

        //准备对象
        Administrator administrator;
        Map<String,String> map =new HashMap<>();
        try {
            administrator = administratorService.findByNameAndPassword(username, password);
        }catch (EmptyResultDataAccessException e){
            administrator=null;
        }

        //获得验证码
        HttpSession session = request.getSession();
        if (administrator == null) {
//            request.getRequestDispatcher("/loginFail").forward(request, response);
            return new R(false,"登录失败，不存在该管理员账号");
        } else {
            //设置token值
            String token = jwtUtils.createToken(administrator.getId().toString(), administrator.getName());
            redis.put(administrator.getId().toString(),token);
            map.put("token",token);
            map.put("administrator",administrator.toString());
            return new R(true,map,"登录成功");
        }

    }

    @PostMapping("/save")
    public R save(@RequestBody Administrator administrator){
        return new R(administratorService.save(administrator));
    }

}
