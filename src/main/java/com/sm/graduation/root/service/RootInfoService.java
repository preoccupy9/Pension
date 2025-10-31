package com.sm.graduation.root.service;

import com.sm.graduation.root.dao.RootInfoMapper;
import com.sm.graduation.root.pojo.RootInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RootInfoService {

    @Autowired
    private RootInfoMapper rootInfoMapper;


    public RootInfo seleByName(String name){
        return rootInfoMapper.seleByName(name);
    }

    public int seleByNamePwd(RootInfo rootInfo){
        return rootInfoMapper.seleByNamePwd(rootInfo);
    }



    public int altPwd(String pwd) {
    	return rootInfoMapper.altPwd(pwd);
    }


}
