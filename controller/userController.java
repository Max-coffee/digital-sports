package com.example.spring_boot.controller;

import com.example.spring_boot.controller.utils.R;
import com.example.spring_boot.controller.utils.jwtUtils;
import com.example.spring_boot.domain.User;
import com.example.spring_boot.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class userController {

    @Autowired
    private userService userService;

    @Autowired
    private jwtUtils jwtUtils;


    @GetMapping("/all")
    public R findAll(){
        return new R(true,userService.list());
    }

    @GetMapping("/find/{id}")
    public R findById(@PathVariable(value = "id")int id){return new R(true,userService.getById(id));}

    @PostMapping("/save")
    public R save(@RequestBody User user){
        return new R(userService.save(user));
    }

    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable(value = "id") int id){
        return new R(userService.removeById(id));
    }

    @PutMapping("/update")
    public R update(@RequestBody User user){
        return new R(userService.updateById(user));
    }

    @RequestMapping("/updatePassword")
    @ResponseBody
    public R updatePassword(@RequestBody String newPassword, @RequestBody String secondPassword, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取token解析用户对象
        String token = request.getHeader("token");
        String id = jwtUtils.checkToken(token);

        //进行密码修改
        if (newPassword!=null && secondPassword!=null){
            if (newPassword.equals(secondPassword)) {
                userService.updatePassword(newPassword,Integer.parseInt(id));
                return new R(true);
            }else {
                return new R(false,"两次输入密码不一致");
            }
        }else {
            return new R(false,"未获取到修改密码值");
        }
    }

}
