package com.sm.graduation.visitor.pojo;

import lombok.Data;

@Data
public class Visitor {

    private Integer id;
    private String name;
    private String target;
    private String viTime;
    private Long total;
    // 记录人
    private String recorders;


}
