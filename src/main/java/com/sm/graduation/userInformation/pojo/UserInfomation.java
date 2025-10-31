package com.sm.graduation.userInformation.pojo;

import lombok.Data;

import java.lang.ref.PhantomReference;

@Data
//页面查询
public class UserInfomation {

    private  Integer id;
    private String olderName;
    private String olderSex;
    private String olderIdcard;
    private String medication;
    private String dischargeTime;
    private Integer cost;
    private Long total;

}
