package com.sm.graduation.visitor.dao;

import com.sm.graduation.visitor.pojo.Visitor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Vector;

@Mapper
public interface VisitorMapper {

    List<Visitor> selectByAll(Visitor visitor);

    int insertByAll(Visitor visitor);

}
