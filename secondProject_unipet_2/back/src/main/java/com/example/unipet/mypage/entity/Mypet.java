package com.example.unipet.mypage.entity;

import com.example.unipet.mypage.domain.MypetDTO;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Mypet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int petId;

    @NotNull
    private String userId;

    @NotNull
    private String petName;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate petBirth;

    @NotNull
    private String petGender; // f or m

    @NotNull
    private String petKind;

    @NotNull
    private String petNeuter; // y or n

    private String petPic;
    private String petColor;
    private Double petWeight;
    private String petTrait;

    // 변경된 값만 업데이트하는 메서드
    public Mypet updatePetInfo(MypetDTO updatedPet) {
        if (updatedPet.getPetName() != null) {
            this.petName = updatedPet.getPetName();
        }
        if (updatedPet.getPetPic() != null) {
            this.petPic = updatedPet.getPetPic();
        }
        if (updatedPet.getPetBirth() != null) {
            this.petBirth = updatedPet.getPetBirth();
        }
        if (updatedPet.getPetGender() != null) {
            this.petGender = updatedPet.getPetGender();
        }
        if (updatedPet.getPetKind() != null) {
            this.petKind = updatedPet.getPetKind();
        }
        if (updatedPet.getPetNeuter() != null) {
            this.petNeuter = updatedPet.getPetNeuter();
        }
        if (updatedPet.getPetWeight() != null) {
            this.petWeight = updatedPet.getPetWeight();
        }
        if (updatedPet.getPetColor() != null) {
            this.petColor = updatedPet.getPetColor();
        }
        if (updatedPet.getPetTrait() != null) {
            this.petTrait = updatedPet.getPetTrait();
        }
        return this;
    }
}
