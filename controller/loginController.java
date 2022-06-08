package com.example.spring_boot.controller;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import com.example.spring_boot.controller.utils.R;
import com.example.spring_boot.dao.UserMapper;
import com.example.spring_boot.controller.utils.jwtUtils;
import com.example.spring_boot.domain.Administrator;
import com.example.spring_boot.domain.LoginUser;
import com.example.spring_boot.domain.User;
import com.example.spring_boot.service.administratorService;
import com.example.spring_boot.service.userService;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Controller
public class loginController {
    //redis缓存
    @CreateCache(area = "token",name = "user",expire = 10,timeUnit = TimeUnit.MINUTES,cacheType = CacheType.REMOTE)
    private Cache<Serializable,String> redis;

    @Autowired
    private SqlSessionTemplate sqlSession;

    @Autowired
    private userService userService;


    @Autowired
    private jwtUtils jwtUtils;

    @RequestMapping("/download")
    public ModelAndView output(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/jsp/load.jsp");
        return modelAndView;
    }

    @RequestMapping("/query")
    @ResponseBody
    public R query(@RequestBody LoginUser loginUser, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //获得验证码
        request.setCharacterEncoding("utf-8");
        String phone = loginUser.getPhone();
        String password = loginUser.getPassword();
//        String checkCode = request.getParameter("checkCode");

        //准备对象

        User user;
        Map<String,String> map =new HashMap<>();
        try {
            user = userService.findByPhoneAndPassword(phone,password);
        }catch (EmptyResultDataAccessException e){
            user = null;
        }

        //获得验证码
        HttpSession session = request.getSession();
//        String checkCode_session = (String) session.getAttribute("CHECK CODE_SERVER");
        //删除session中的验证码
//        session.removeAttribute("CHECK CODE_SERVER");
//        if (checkCode_session!=null&&checkCode_session.equals(checkCode)) {
            if (user == null) {
                request.getRequestDispatcher("/loginFail").forward(request, response);
                return new R(false);
            } else {
                //设置token值
                String token = jwtUtils.createToken(user.getId().toString(), user.getName());
                redis.put(user.getId().toString(),token);
                map.put("token",token);
                map.put("user",user.toString());
                return new R(true,map);
            }
//        }else {
//            response.setContentType("text/html;charset=utf-8");
//            response.getWriter().write("登陆失败! 验证码错误!");
//            //跳转回登录页面
//            response.setHeader("Refresh","5; /spring-boot");
//        }

    }


    @RequestMapping("/loginSuccessful")
    public void loginSuccessful(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user != null) {
            request.setAttribute("loginUserName",user.getName());
            request.getRequestDispatcher("/jsp/userHomepage.jsp").forward(request,response);
        }
    }

    @RequestMapping("/loginFail")
    public void loginFail(HttpServletRequest request,HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write("登陆失败! 用户名或者密码错误!");

        //跳转回登录页面
        response.setHeader("Refresh","5; /spring-boot");
    }

    @RequestMapping("/down")
    public void down(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        request.getRequestDispatcher("").forward(request,response);
    }

    @RequestMapping("/successful")
    public ModelAndView successful(HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        System.out.println(user.toString());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/jsp/userHomepage.jsp");
        return modelAndView;
    }
}
