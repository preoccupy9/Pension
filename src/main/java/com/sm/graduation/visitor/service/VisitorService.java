package com.sm.graduation.visitor.service;

import com.sm.graduation.visitor.pojo.Visitor;

import java.util.List;

public interface VisitorService {

    List<Visitor> selectByAll(Integer page,Integer limit,String name);

    int insertByAll(Visitor visitor);


}
