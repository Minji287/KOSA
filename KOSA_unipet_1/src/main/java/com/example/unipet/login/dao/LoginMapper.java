package com.example.unipet.login.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LoginMapper {
//	@Insert("insert into reply (name, content, refid) values (#{name}, #{content}, #{refid})")
//	public boolean insertSignup(SignupDTO vo);

    @Select("SELECT COUNT(*) > 0 FROM uni_pet.User WHERE user_id = #{id}")
    public boolean checkId(String id);

    @Select("SELECT COUNT(*) > 0 AS user_exists FROM uni_pet.User WHERE user_id = #{id} AND password = #{password}")
    public boolean checkPassword(@Param("id") String id, @Param("password") String password );

    @Select("SELECT roles FROM uni_pet.User WHERE user_id = #{id}")
    public String checkRoles(String id);
}