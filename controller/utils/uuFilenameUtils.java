package com.example.spring_boot.controller.utils;

import java.util.UUID;

public class uuFilenameUtils {

    public static String get_uuFilename(String filename){
        //唯一文件名产生
        String uuFilename = null;
        if(filename!="") {
             uuFilename = UUID.randomUUID().toString().replace("-", "")+" "+filename;
        }

            return uuFilename;
    }
}
