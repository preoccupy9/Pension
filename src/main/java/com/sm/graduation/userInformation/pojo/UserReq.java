package com.sm.graduation.userInformation.pojo;

import lombok.Data;

@Data
public class UserReq {
//    用户id
    private Integer olderId;
//    用户名称
    private String olderName;
//    药品id
    private Integer medicationId;
//    药品名称
    private String medicationName;
}
