package com.example.unipet.mypage.repository;

import com.example.unipet.mypage.domain.UserDTO;
import com.example.unipet.mypage.domain.UserResultDTO;
import com.example.unipet.mypage.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MyRepository extends JpaRepository<User, Integer> {

    User findUserByUserId(@Param("userId") String userId);

    @Query("SELECT u.name " +
            "FROM User u " +
            "WHERE u.userId = :userId")
    String findNameByUserId(@Param("userId") String userId);

    @Query("SELECT u.password " +
            "FROM User u " +
            "WHERE u.userId = :userId")
    String findPasswordByUserId(@Param("userId") String userId);
}
