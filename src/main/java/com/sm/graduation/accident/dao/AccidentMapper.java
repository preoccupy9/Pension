package com.sm.graduation.accident.dao;

import com.sm.graduation.accident.pojo.AccidentPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 *      mapper层与mapper.xml
 *      1. mapper.xml的命名必须跟对应的mapper名一致；
 *      2.namespace：是要对应mapper的地址
 *      3.mapper中的方法名是唯一的
 *
 */

@Mapper
public interface AccidentMapper {

//    @Select("SELECT id,accident,detail,acc_time,loss FROM accident_record")
    List<AccidentPojo> selectByAll(AccidentPojo accidentPojo);

    // 添加
    int insertByAll(AccidentPojo accidentPojo);

    AccidentPojo listById(Long id);

    int updateById(AccidentPojo accidentPojo);

    // 删除
    int deleteById(Integer id);





}
