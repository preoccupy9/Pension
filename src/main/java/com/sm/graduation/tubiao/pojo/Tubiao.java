package com.sm.graduation.tubiao.pojo;

import lombok.Data;

import java.util.List;

@Data
public class Tubiao {
    //x轴 性别
    private List<String> olderSex;
    //y值 对应的值的集合
    private List<Integer> number;
}
