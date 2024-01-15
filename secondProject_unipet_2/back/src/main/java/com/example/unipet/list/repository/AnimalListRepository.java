package com.example.unipet.list.repository;

import com.example.unipet.list.entity.Animal;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface AnimalListRepository extends JpaRepository<Animal, Integer> {
    Animal findByDesertionNo(long desertionNo);
    List<Animal> findByHappenDtGreaterThanEqualOrderByHappenDtDesc(String startDate);
    List<Animal> findByHappenDtGreaterThanEqual(String startDate);

    @Transactional
    @Modifying
    @Query("DELETE FROM Animal a WHERE a.noticeEdt < :endDate")
    void deleteAllByNoticeEdtBefore(@Param("endDate") String endDate);
    boolean existsByNoticeNo(String noticeNo);

    Animal findByImageId(int imageId);

    Page<Animal> findByKindCdContainingAndHappenDtGreaterThanEqual(String kind, String startDate, Pageable pageable);

}
