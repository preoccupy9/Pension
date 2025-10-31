package com.sm.graduation.userInformation.pojo;

import lombok.Data;

@Data
public class UserInfoAddReq {
    private Integer id;
    private Integer olderId;
    private Integer medicationId;
    private String dischargeTime;
    private String cost;
}
