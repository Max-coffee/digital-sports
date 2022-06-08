package com.example.spring_boot.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.spring_boot.controller.utils.R;
import com.example.spring_boot.controller.utils.jwtUtils;
import com.example.spring_boot.controller.utils.uuFilenameUtils;
import com.example.spring_boot.domain.Run;
import com.example.spring_boot.domain.User;
import com.example.spring_boot.service.runService;
import com.example.spring_boot.service.userService;
import org.python.jline.internal.InputStreamReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

@Controller
public class loadController {


    @Autowired
    private runService runService;

    @Autowired
    private jwtUtils jwtUtils;


    @RequestMapping("/file")
    @ResponseBody
    public R fileUpload(@RequestParam("info") String info, @RequestPart MultipartFile[] uploads, HttpServletRequest request) throws IOException {

        //获取token解析用户对象
        String token = request.getHeader("token");
        String id = jwtUtils.checkToken(token);
        //取出对应run对象
        Run run = runService.getById(Integer.parseInt(id));
        System.out.println(run.toString());

        //统计文件上传情况
        int i = 0;
        Map<String,String> map = new HashMap<>();
        System.out.println(info);

        for (MultipartFile multipartFile : uploads){
            i++;
            String filename = multipartFile.getOriginalFilename();
            //获得后缀名判断文件类型
            String type = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
            String uuFilename = uuFilenameUtils.get_uuFilename(filename);
            if (uuFilename!=null) {

//                File file = new File("E:\\develop\\project\\Maven_test\\spring_boot\\src\\main\\webapp\\fileStorage" + "\\" + uuFilename);
                File file = new File("//home//ljh//springboot//filestorage" + "//" + uuFilename);
                multipartFile.transferTo(file);

                if(type.equals(".jpg")){
                    //保存处理的图片路径
                    run.setFootImagePath("//home//ljh//springboot//filestorage" + "//" + uuFilename);
                }else if (type.equals(".mp4")){
                    //设置视频路径
                    run.setVideoPath("//home//ljh//springboot//filestorage" + "//" + uuFilename);
                }else {
                    map.put("error","第"+i+"个文件格式存在问题");
                }
                //更新数据库
                runService.updateById(run);
            }
        }
        //结果返回
        if (map.get("error")!=null){
            return new R(false,map);
        }else {
            return new R(true,"上传成功");
        }
    }

    @RequestMapping("/execute")
    @ResponseBody
    public Callable<R> fileExecute(@RequestParam("type")String type, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取token解析用户对象
        String token = request.getHeader("token");
        String id = jwtUtils.checkToken(token);
        //取出对应run对象
        Run run = runService.getById(Integer.parseInt(id));
        System.out.println(run.toString());
        ArrayList<String> list = new ArrayList<String>();
        //设置需求对象
        final String[] path = {null};
        final String[] name = {null};
        final String[] pyPath = {null};
        final String[] finalPath = {null};
        Callable<R> result = new Callable<R>(){
            @Override
            public R call() throws Exception {
                //查明处理文件对象
                if (type.equals("image")){
                    path[0] = run.getFootImagePath();
                    String[] split = path[0].split("//");
                    name[0] = split[split.length-1];
                    pyPath[0] = "//dealwith_pic//footkind.py";
                    finalPath[0] = "//home//ljh//springboot//executefile"+"//"+type+"//"+ name[0];
                    run.setFootImagePath(finalPath[0]);
                }else if (type.equals("video")){
                    path[0] = run.getVideoPath();
                    String[] split = path[0].split("//");
                    name[0] = split[split.length-1];
                    pyPath[0] = "//dealwith_pic//pic_pose.py";
                    finalPath[0] = "//home//ljh//springboot//executefile"+"//"+type+"//"+ name[0];
                    run.setVideoPath(finalPath[0]);
                }else {
                    return new R(false,"格式有误，找不到指定文件");
                }

                //执行脚本
                String[] videoExecute = new String[]{"python3", pyPath[0], path[0], finalPath[0]};
                Process p = Runtime.getRuntime().exec(videoExecute);
                //输出返回结果
                BufferedReader bufferedInputStream = new BufferedReader(new InputStreamReader(p.getInputStream(), Charset.defaultCharset()));
                String line = null;
                while ((line = bufferedInputStream.readLine()) != null) {
                    list.add(line);
                }
                //存处理结果
                if (type.equals("image")){
                    run.setFootType(list.get(list.size()-1));
                }else if(type.equals("video")){
                    run.setRunImprovements(list.get(list.size()-1));
                }else {
                    return new R(false,"格式有误，处理失败");
                }
                //更新数据库
                runService.updateById(run);
                return new R(true,list);
            }
        };
        return result;
    }

}
