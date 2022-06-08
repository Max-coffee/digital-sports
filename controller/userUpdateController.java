package com.example.spring_boot.controller;

import com.example.spring_boot.controller.utils.R;
import com.example.spring_boot.controller.utils.jwtUtils;
import com.example.spring_boot.dao.UserMapper;
import com.example.spring_boot.domain.User;
import com.example.spring_boot.service.userService;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class userUpdateController {

    @Autowired
    private SqlSessionTemplate sqlSession;

    @Autowired
    private userService userService;

    @Autowired
    private jwtUtils jwtUtils;

    @RequestMapping("/update")
    public ModelAndView update(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/jsp/updatePassword.jsp");
        return modelAndView;
    }

    @RequestMapping("/updateUser")
    public ModelAndView updateUser(HttpServletRequest request){

        //获取token解析用户对象
        String token = request.getHeader("token");
        String id = jwtUtils.checkToken(token);
        User user = userService.getById(id);
        //获得user对象
        HttpSession session = request.getSession();
//        User user = (User) session.getAttribute("user");

        //设置默认值
        session.setAttribute("uid",user.getId());
        session.setAttribute("name",user.getName());
        session.setAttribute("gender",user.getGender());
        session.setAttribute("birth",user.getBirth());
        session.setAttribute("place",user.getPlace());
        session.setAttribute("phone",user.getPhone());
        session.setAttribute("age",user.getAge());
        //返回视图
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/jsp/updateInformation.jsp");
        return modelAndView;
    }



    @RequestMapping("/updateUserInformation")
    public void updateUserInformation(HttpServletRequest request,HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        User preUser = (User) session.getAttribute("user");

        //创建user实体
        User user = new User();
        //封装修改信息
        user.setId(Integer.parseInt(request.getParameter("newUid")));
        user.setName(request.getParameter("newName"));
        user.setGender(Integer.parseInt(request.getParameter("newGender")));
        user.setBirth(request.getParameter("newBirth"));
        user.setPlace(request.getParameter("newPlace"));
        user.setPhone(request.getParameter("newPhone"));
        user.setAge(Integer.parseInt(request.getParameter("newAge")));
        user.setPassword(preUser.getPassword());

        //更新信息
        userService.updateById(user);



        //重设session中的user值
        User sufUser = userService.getById(preUser.getId());
        session.setAttribute("user",sufUser);

        //删除不需要的默认值
        session.removeAttribute("uid");
        session.removeAttribute("name");
        session.removeAttribute("gender");
        session.removeAttribute("birth");
        session.removeAttribute("place");
        session.removeAttribute("phone");
        session.removeAttribute("age");

        //返回到登录界面
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println("修改完成，返回主页");
        response.setHeader("Refresh","5; /spring-boot/jsp/userHomepage.jsp");

    }


}
