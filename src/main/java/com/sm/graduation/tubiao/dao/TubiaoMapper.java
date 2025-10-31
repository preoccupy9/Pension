package com.sm.graduation.tubiao.dao;

import com.sm.graduation.tubiao.pojo.ToTwo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TubiaoMapper {

    List<String> selectByolderSex();

    List<Integer> selectByNumber();
//    List<ToTwo>
    List<ToTwo> selectByTwo();

}

