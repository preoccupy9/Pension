package com.sm.graduation.tubiao.service.Impl;

import com.sm.graduation.tubiao.dao.TubiaoMapper;
import com.sm.graduation.tubiao.pojo.Tubiao;
import com.sm.graduation.tubiao.service.TubiaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TubiaoServiceImpl implements TubiaoService {

    @Autowired
    private TubiaoMapper tubiaoMapper;

    //  图表1
    @Override
    public Tubiao tuOne() {
//    到mapper中查询性别信息
        List<String> strings = tubiaoMapper.selectByolderSex();
//    到mapper中查询对应的数值
        List<Integer> list = tubiaoMapper.selectByNumber();
        //创建一个图表的对象
        Tubiao tubiao = new Tubiao();
        tubiao.setOlderSex(strings);
        tubiao.setNumber(list);
        return tubiao;
    }


}
