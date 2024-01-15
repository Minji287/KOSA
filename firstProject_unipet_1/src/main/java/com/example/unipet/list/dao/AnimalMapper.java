package com.example.unipet.list.dao;

import org.apache.ibatis.annotations.*;
import com.example.unipet.list.domain.AnimalListDTO;

import java.util.List;

@Mapper
public interface AnimalMapper {
    @Select("SELECT * FROM picture WHERE happenDt >= #{startDate} ORDER BY happenDt DESC")
    List<AnimalListDTO> selectAllAnimals(@Param("startDate") String startDate);


    @Insert({
            "<script>",
            "INSERT INTO picture (image_id, popfile, specialMark, happenDt, desertionNo, happenPlace, careNm, kindCd, noticeSdt, noticeEdt, processState, sexCd, age, colorCd, weight, neuterYn, careTel, careAddr,noticeNo)",
            "VALUES ",
            "<foreach collection='animals' item='animal' separator=','>",
            "(#{animal.image_id}, #{animal.popfile}, #{animal.specialMark}, #{animal.happenDt}, #{animal.desertionNo}, #{animal.happenPlace}, #{animal.careNm}, #{animal.kindCd}, #{animal.noticeSdt}, #{animal.noticeEdt}, #{animal.processState}, #{animal.sexCd}, #{animal.age}, #{animal.colorCd}, #{animal.weight}, #{animal.neuterYn}, #{animal.careTel}, #{animal.careAddr}, #{animal.noticeNo})",
            "</foreach>",
            "</script>"
    })
    void insertAnimals(@Param("animals") List<AnimalListDTO> animals);


    @Delete("DELETE FROM picture WHERE noticeEdt < #{endDate}")
    void deleteExpiredAnimals(@Param("endDate") String endDate);

    @Select("SELECT * FROM picture WHERE image_id = #{image_id}")
    AnimalListDTO selectByImageNo(int image_id);

    /*
        @Select("SELECT * FROM picture WHERE desertionNo = #{desertionNo}")
        AnimalListDTO selectByNoticeNo(int desertionNo);
    */

    /*
        @Update("UPDATE picture SET file_path = #{popfile}, caption = #{specialMark}, " +
            "uploaded_at = #{happenDt}, happen_place = #{happenPlace}, " +
            "care_nm = #{careNm}, kind_cd = #{kindCd}, " +
            "notice_sdt = #{noticeSdt}, notice_edt = #{noticeEdt}, " +
            "process_state = #{processState}, " +
            "sexCd = #{sexCd}, age = #{age}, colorCd = #{colorCd}, weight = #{weight}, " +
            "neuterYn = #{neuterYn}, careTel = #{careTel}, careAddr = #{careAddr}, noticeNo = #{noticeNo} " +
            "WHERE notice_no = #{desertionNo}")
    void updateAnimal(AnimalListDTO animal);
*/
}