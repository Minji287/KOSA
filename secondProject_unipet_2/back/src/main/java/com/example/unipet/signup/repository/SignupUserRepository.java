package com.example.unipet.signup.repository;

import com.example.unipet.signup.entity.SignupUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;

@Mapper
public interface SignupUserRepository extends JpaRepository<SignupUser, String> {
        boolean existsByuserId(String userId);
}