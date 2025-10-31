package com.sm.graduation.userInformation.service;


import com.sm.graduation.common.result.AjaxResult;
import com.sm.graduation.userInformation.pojo.UserInfoAddReq;
import com.sm.graduation.userInformation.pojo.UserInfomation;
import com.sm.graduation.userInformation.pojo.UserInformationReq;

import java.util.List;

public interface UserInformationService {
    List<UserInfomation> selectByAll (Integer page,Integer limit,UserInformationReq req);
    int addUserInfo(UserInfoAddReq req);
//    通过id对数据进行查询
    UserInfomation selectById(Integer id);
    int updateById(UserInfoAddReq req);
}
