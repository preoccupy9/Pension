package com.sm.graduation.userInformation.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sm.graduation.Exception.ServiceException;
import com.sm.graduation.userInformation.dao.UserInformationMapper;
import com.sm.graduation.userInformation.pojo.UserInfoAddReq;
import com.sm.graduation.userInformation.pojo.UserInfomation;
import com.sm.graduation.userInformation.pojo.UserInformationReq;
import com.sm.graduation.userInformation.service.UserInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserInformationServiceImpl implements UserInformationService {

    @Autowired
    private UserInformationMapper userInformationMapper;
    @Override
    public List<UserInfomation> selectByAll(Integer page, Integer limit, UserInformationReq req) {
//        到当前页存到Page里
        PageHelper.startPage(page,limit);
        List<UserInfomation> userInfomations = null;
        if (req.getOlderName() == null && req.getDischargeTime() ==null){
            userInfomations =userInformationMapper.selectB();
        }
        if (req.getOlderName() !=null && !"".equals(req.getOlderName()) || req.getDischargeTime() !=null && !"".equals(req.getDischargeTime())){
            userInfomations =userInformationMapper.selectByAll(req);
        }
//        到maper中做业务逻辑处理
        //List<UserInfomation> userInfomations =userInformationMapper.selectByAll(req);
        if (userInfomations.size() > 0 && userInfomations !=null){
            PageInfo<UserInfomation> pageInfo = new PageInfo<>(userInfomations);
            userInfomations.get(0).setTotal(pageInfo.getTotal());
            return userInfomations;
        }
        return null;
    }

    @Override
    public int addUserInfo(UserInfoAddReq req) {
        judge(req);
        int i = userInformationMapper.addUserInfo(req);
        if (i >0)
        {
            return i;
        }

        return 0;
    }

//通过id对数据进行查询
    @Override
    public UserInfomation selectById(Integer id) {
//        mapper中做sql语句查询
        UserInfomation userInfomation = userInformationMapper.selectById(id);
        if (userInfomation !=null){
            return userInfomation;
        }
        return null;
    }

    @Override
    public int updateById(UserInfoAddReq req) {
        judge(req);
        int i = userInformationMapper.updateById(req);
        if (i>0)
        {
            return  i;
        }
        return 0;
    }

    public void judge(UserInfoAddReq req){
        if (req.getOlderId() == null || "".equals(req.getOlderId())){
            throw new ServiceException("用户不能为空");
        }
        if (req.getMedicationId() == null || "".equals(req.getMedicationId())){
            throw new ServiceException("药品不能为空");
        }
        if (req.getDischargeTime()== null || "".equals(req.getDischargeTime())){
            throw new ServiceException("出院时间不能为空");
        }
        if (req.getCost() == null || "".equals(req.getCost())){
            throw new ServiceException("饮食费用不能为空");
        }
    }
}
