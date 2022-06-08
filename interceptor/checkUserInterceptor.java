package com.example.spring_boot.interceptor;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.spring_boot.controller.utils.jwtUtils;
import com.example.spring_boot.domain.User;
import com.example.spring_boot.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class checkUserInterceptor implements HandlerInterceptor {

    //redis缓存
    @CreateCache(area = "token",name = "user",expire = 10,timeUnit = TimeUnit.SECONDS,cacheType = CacheType.REMOTE)
    private Cache<Serializable,String> redis;

    @Autowired
    private jwtUtils jwtUtils;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String,String> map = new HashMap<>();
        String token = request.getHeader("token");
        if (StringUtils.isNotBlank(token)){
            String id = jwtUtils.checkToken(token);
            String preToken = redis.get(id);
            System.out.println(preToken);
            if (token.equals(preToken)){
                //放行
                return HandlerInterceptor.super.preHandle(request, response, handler);
            }else {
                Date nowDate = jwtUtils.DateToken(token);
                Date preDate = jwtUtils.DateToken(preToken);

                int i = nowDate.compareTo(preDate);
                if (i==1){
                    redis.put(id,token);
                    map.put("successful","在本设备上登录");
                }else if (i==-1){
                    map.put("error","账号已在别处登录");

                }else {
                    map.put("warn","同一设备登录");
                }
                JSONObject jsonObject = new JSONObject(map);
                response.setContentType("application/json");
                //设置响应的编码
                response.setCharacterEncoding("utf-8");
                //响应
                PrintWriter pw=response.getWriter();
                pw.write(jsonObject.toString());
                pw.flush();
//                pw.close();
                return true;
            }
        }else {
            //放行
            return HandlerInterceptor.super.preHandle(request, response, handler);
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
