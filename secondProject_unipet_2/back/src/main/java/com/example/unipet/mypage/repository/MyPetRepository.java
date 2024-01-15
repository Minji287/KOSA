package com.example.unipet.mypage.repository;

import com.example.unipet.mypage.entity.Mypet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MyPetRepository extends JpaRepository<Mypet, Integer> {
    Optional<Mypet> findMyPetByUserId(String userId);

    @Query("SELECT p.petPic " +
            "FROM Mypet p " +
            "WHERE p.userId = :userId")
    String findPetPicByUserId(@Param("userId") String userId);

}
