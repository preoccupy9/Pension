package com.sm.graduation.accident.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sm.graduation.Exception.ServiceException;
import com.sm.graduation.accident.dao.AccidentMapper;
import com.sm.graduation.accident.pojo.AccidentPojo;
import com.sm.graduation.accident.service.AccidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccidentServiceImpl implements AccidentService {

    // 将mapper层注入到 impl 层中
    @Autowired
    private AccidentMapper accidentMapper;


    // 查询页面展示
    @Override
    public List<AccidentPojo> selectByAll(Integer page, Integer limit,String accTime) {
        // 先将当前页和当前展示的数据放到page里
        PageHelper.startPage(page,limit);
        AccidentPojo accidentPojo = new AccidentPojo();
        // 如果accTime不能于空，就放值
        if (!"".equals(accTime)){
            accidentPojo.setAccTime(accTime);
        }
        // 查询展示的数据，到mapper中
        List<AccidentPojo> accidentPojos = accidentMapper.selectByAll(accidentPojo);
        if (accidentPojos != null && accidentPojos.size() >0){
            // 查到的数据和page信息结合起来
            PageInfo<AccidentPojo> objectPageInfo = new PageInfo(accidentPojos);
            // 给获取到的其一条数据的total赋值
            accidentPojos.get(0).setTotal(objectPageInfo.getTotal());
        }
        return accidentPojos;
    }

    @Override
    public int insertByAll(AccidentPojo accidentPojo) {
        panduan(accidentPojo);
        // 到mapper层做sql语句处理
        int i = accidentMapper.insertByAll(accidentPojo);
        if (i>0){
            return i;
        }
        return 0;
    }

    @Override
    public AccidentPojo listById(Long id) {
        if (id == null){
            throw new ServiceException("id不能为空");
        }
        AccidentPojo accidentPojo = accidentMapper.listById(id);
        if (accidentPojo != null){
            return accidentPojo;
        }
        return null;
    }



    // 编辑
    @Override
    public int updateById(AccidentPojo accidentPojo) {
        panduan(accidentPojo);
        // mapper层
        int i = accidentMapper.updateById(accidentPojo);
        if (i >0){
            return i;
        }
        return 0;
    }


    // 对数据不能为空进行判断
    public void panduan(AccidentPojo accidentPojo){
        if ("".equals(accidentPojo.getAccident()) && accidentPojo.getAccident() == null){
            throw new ServiceException("事故不能为空");
        }
        if ("".equals(accidentPojo.getAccTime()) && accidentPojo.getAccTime() == null){
            throw new ServiceException("事故的发生时间不能为空");
        }
        if ("".equals(accidentPojo.getDetail()) && accidentPojo.getDetail() == null){
            throw new ServiceException("事故原因不能为空");
        }
        if ("".equals(accidentPojo.getLoss()) && accidentPojo.getLoss() == null){
            throw new ServiceException("事故损失不能为空");
        }
    }



}
