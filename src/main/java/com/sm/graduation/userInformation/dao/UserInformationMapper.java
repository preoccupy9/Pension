package com.sm.graduation.userInformation.dao;

import com.sm.graduation.userInformation.pojo.UserInfoAddReq;
import com.sm.graduation.userInformation.pojo.UserInfomation;
import com.sm.graduation.userInformation.pojo.UserInformationReq;
import com.sm.graduation.userInformation.pojo.UserReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserInformationMapper {
    List<UserInfomation> selectByAll(UserInformationReq req);
    List<UserInfomation>  selectB();
    List<UserReq> selectByUser();
    List<UserReq> selectBYmedication();
    int addUserInfo(UserInfoAddReq req);

    UserInfomation selectById(Integer id);

    int updateById(UserInfoAddReq req);
//    删除
    int deleteById(Integer id);
}
