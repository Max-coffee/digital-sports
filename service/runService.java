package com.example.spring_boot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.spring_boot.domain.Run;

public interface runService extends IService<Run> {
    String getImagePath(int id);
    String getFootType(int id);
    String getVideoPath(int id);
    String getRunImprovements(int id);
}
