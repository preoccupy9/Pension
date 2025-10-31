package com.sm.graduation.accident.service;

import com.sm.graduation.accident.pojo.AccidentPojo;
import io.swagger.models.auth.In;

import java.util.List;

// 事故接口层
public interface AccidentService {

    // 页面展示
    List<AccidentPojo> selectByAll(Integer page, Integer limit,String accTime);

    // 添加
    int insertByAll(AccidentPojo accidentPojo);

    AccidentPojo listById(Long id);

    // 编辑
    int updateById(AccidentPojo accidentPojo);














}
