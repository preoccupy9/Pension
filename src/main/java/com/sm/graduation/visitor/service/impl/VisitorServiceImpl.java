package com.sm.graduation.visitor.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sm.graduation.Exception.ServiceException;
import com.sm.graduation.visitor.dao.VisitorMapper;
import com.sm.graduation.visitor.pojo.Visitor;
import com.sm.graduation.visitor.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Vector;

@Service
public class VisitorServiceImpl implements VisitorService {

    @Autowired
    private VisitorMapper visitorMapper;

    @Override
    public List<Visitor> selectByAll(Integer page, Integer limit, String name) {
        PageHelper.startPage(page,limit);
        Visitor visitor = new Visitor();
        if (name != null && !"".equals(name)){
            visitor.setName(name);
        }
        // 调用mapper
        List<Visitor> vectors = visitorMapper.selectByAll(visitor);
        if (vectors.size() >0 && vectors != null){
            PageInfo<Vector> objectPageInfo = new PageInfo(vectors);
            vectors.get(0).setTotal(objectPageInfo.getTotal());
            return vectors;
        }
        return null;
    }

    @Override
    public int insertByAll(Visitor visitor) {
        insertB(visitor);
        visitor.setRecorders("王五");
        int i = visitorMapper.insertByAll(visitor);
        if (i>0){
            return i;
        }
        return 0;
    }

    public void insertB(Visitor visitor){
        if (visitor.getName() == null && "".equals(visitor.getName())){
            throw new ServiceException("姓名不能为空");
        }
        if (visitor.getTarget() == null && "".equals(visitor.getTarget())){
            throw new ServiceException("访问对象不能为空");
        }
        if (visitor.getViTime() == null && "".equals(visitor.getViTime())){
            throw new ServiceException("访问时间不能为空");
        }
    }

}
