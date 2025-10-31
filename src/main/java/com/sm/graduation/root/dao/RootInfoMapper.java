package com.sm.graduation.root.dao;

import java.util.List;
import java.util.Map;

import com.sm.graduation.root.pojo.RootInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


@Mapper
public interface RootInfoMapper {



	@Select("select * from root_info where name = #{name} ")
	RootInfo seleByName(String name);




	@Select("select count(*) from root_info where name = #{name} and pwd = #{pwd} ")
	int seleByNamePwd(RootInfo rootInfo);







	@Update("update root_info set pwd=#{pwd} where id=1")
	int altPwd(String pwd);


}
