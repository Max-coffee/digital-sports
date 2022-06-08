package com.example.spring_boot.controller;

import com.example.spring_boot.controller.utils.R;
import com.example.spring_boot.controller.utils.jwtUtils;
import com.example.spring_boot.domain.Run;
import com.example.spring_boot.service.runService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@CrossOrigin
@RestController
@RequestMapping("/run")
public class runController {
    @Autowired
    private runService runService;

    @Autowired
    private jwtUtils jwtUtils;

    @GetMapping("/all")
    public R findAllRun(){return new R(true,runService.list());}

    @GetMapping("/find/{id}")
    public R findById(@PathVariable(value = "id")int id){return new R(true,runService.getById(id));}

    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable(value = "id") int id){return new R(runService.removeById(id));}

    @RequestMapping("/improvements")
    @ResponseBody
    public R getImprovements(HttpServletRequest request){
        //获取token解析用户对象
        String token = request.getHeader("token");
        String id = jwtUtils.checkToken(token);
        ArrayList<String> list = new ArrayList<String>();
        list.add(runService.getRunImprovements(Integer.parseInt(id)));
        return new R(true,list);
    }

    @RequestMapping("/footType")
    @ResponseBody
    public R getFootType(HttpServletRequest request){
        //获取token解析用户对象
        String token = request.getHeader("token");
        String id = jwtUtils.checkToken(token);
        ArrayList<String> list = new ArrayList<String>();
        list.add(runService.getFootType(Integer.parseInt(id)));
        return new R(true,list);
    }

    @PutMapping("/update")
    public R update(@RequestBody Run run){
        return new R(runService.updateById(run));
    }


}
