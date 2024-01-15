package com.example.unipet.signup.dao;

import com.example.unipet.signup.model.SignupDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SignupMapper {
    @Insert("insert into user(user_id, password, email, name, created_at, modified_At, roles) values(#{user_id}, #{password}, #{email},#{name}, now(), now(), 'user')")
    public boolean insertUserInfo(SignupDTO dto);

    @Insert("insert into mypet (user_id, pet_name, pet_birth, pet_gender, pet_kind, pet_neuter, pet_color, pet_weight, pet_trait ) values (#{user_id}, #{petName}, #{petBirthday} , #{petGender}, #{petType}, #{doNeutering}, #{petColor}, #{petWeight}, #{petChar})")
    public boolean insertPetInfo(SignupDTO dto);

}
