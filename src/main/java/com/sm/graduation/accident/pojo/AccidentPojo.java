package com.sm.graduation.accident.pojo;

import io.swagger.models.auth.In;
import lombok.Data;

// 事故的页面展示
@Data
public class AccidentPojo {
    // 事故id
    private Integer id;
    // 事故
    private String accident;
    // 原因
    private String detail;
    // 年月
    private String accTime;
    // 损失
    private Integer loss;
    // 总条数
    private Long total;


}
