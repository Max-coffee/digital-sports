package com.example.spring_boot.interceptor;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.spring_boot.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import com.example.spring_boot.controller.utils.jwtUtils;


public class loginInterceptor implements HandlerInterceptor {

    @Autowired
    private jwtUtils jwt ;
    //目标方法执行前执行
    //判断用户有没有登录，没有则返回登录界面
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())){
            System.out.println("OPTIONS请求，放行");
            return true;
        }
        response.addHeader("Access-Control-Allow-Headers","token");
        Map<String,String> map = new HashMap<>();
        String url = request.getRequestURI();
        System.out.println(url);
        boolean flag = true;

        if(url != null){
            //登录请求直接放行
            if(("/spring-boot/query".equals(url))||("/spring-boot/administrator/login".equals(url))||("/spring-boot/".equals(url))){
                return HandlerInterceptor.super.preHandle(request, response, handler);
            }else{
                //其他请求验证token
                String token = request.getHeader("token");
                System.out.println(token);
                if(StringUtils.isNotBlank(token)){
                    //token验证结果
                    int verify  = jwt.verify(token);
                    if(verify != 1){
                        //验证失败
                        if(verify == 2){
                            map.put("errorMsg","token已过期,请重新登录");
                            map.put("code","200");
                        }else if(verify == 0){
                            map.put("errorMsg","用户信息验证失败");
                        }
                    }else if(verify  == 1){
                        //验证成功，放行
                        return HandlerInterceptor.super.preHandle(request, response, handler);
                    }
                }else{
                    //token为空的返回
                    map.put("errorMsg","未携带token信息");
                }
            }
            response.reset();
            JSONObject jsonObject = new JSONObject(map);
            response.setContentType("application/json");
            //设置响应的编码
            response.setCharacterEncoding("utf-8");
            //响应
            PrintWriter pw=response.getWriter();
            pw.write(jsonObject.toString());
            pw.flush();
            pw.close();

        }else {
            //跳转回主页面
            flag = false;
            request.getRequestDispatcher("").forward(request,response);
            return flag;
        }
        return flag;
    }

    //目标方法执行后，视图对象返回前执行
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("执行中");
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    //流程结束后执行
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("执行结束");
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
