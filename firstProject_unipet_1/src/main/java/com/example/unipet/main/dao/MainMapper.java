package com.example.unipet.main.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MainMapper {
    @Select("SELECT name FROM uni_pet.User WHERE user_id = #{id}")
    public String getName(String id);

}