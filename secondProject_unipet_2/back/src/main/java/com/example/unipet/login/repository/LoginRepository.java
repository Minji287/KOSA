package com.example.unipet.login.repository;

import com.example.unipet.login.entity.LoginUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;

@Mapper
public interface LoginRepository extends JpaRepository<LoginUser, String> {
    boolean existsByUserId(String userId);
    boolean existsByUserIdAndPassword(String userId, String password);

    String findRolesByUserId(String userId);
    LoginUser findByUserId(String UserId);

}