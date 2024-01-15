<script setup>
import {ref} from "vue";
import {addMyPetReq, api, changeMyPetReq, deleteMyPetReq} from "@/api/common";
import axios from "axios";
import router from "@/router";

const props = defineProps([ "mypet", "userId" ])
const modifiedPet = { ...props.mypet };
const selectedFile = ref(undefined);
const preview = ref(undefined);

// 이미지 미리보기
const showImagePreview = (e) => {
  const input = e.target;
  if (input.files && input.files[0]) {
    var reader = new FileReader();
    reader.onload = function(e) {
      preview.value = e.target.result;
      selectedFile.value = input.files[0]
    };
    reader.readAsDataURL(input.files[0]);
  }
}

// submit
const submit = async (buttonType) => {
  const confirmationMessage = buttonType === 'modify' ? "수정하시겠습니까?" : "등록하시겠습니까?";
  if (confirm(confirmationMessage)) {
    let formData = new FormData();
    if (selectedFile.value) {
      formData.append("attachFile", selectedFile.value);
    }
    if(!modifiedPet.userId) {
      modifiedPet.userId = props.userId;
    }
    formData.append("mypetDTO",
        new Blob([JSON.stringify(modifiedPet)],
        { type: "application/json" }));

    let response;
    try {
      if(buttonType === 'modify') {
        response = await changeMyPetReq(formData);
      } else {
        for (let key of formData.keys()) {
          console.log(key, ":", formData.get(key));
        }
        response = await addMyPetReq(formData);
      }
      alert(response.data);
      await router.push('/mypet');
    } catch (error) {
      console.error(error);
    }
  }
}

// 삭제
const deletePet = async () => {
  if(confirm("정말 삭제하시겠습니까?")) {
    const response = await deleteMyPetReq();
    alert(response.data)
    location.href = 'http://localhost:5173/mypet';
  }
}
</script>

<template>
  <div class="con_info">
    <div class="con_info_wrapper">
      <div v-if="mypet" class="con_title">나의 펫 수정</div>
      <div v-else class="con_title">나의 펫 추가</div>
      <div class="con_petform">
        <form @submit.prevent="submit" id="changeForm" method="post" enctype="multipart/form-data">
          <div class="con_petchangepic">
            <label class="con_petchangepic_container" for="attach">
              <div class="image-container">
                <img class="circle_petchange" v-if="modifiedPet.petPic"
                     :src="'http://localhost:8889' + modifiedPet.petPic"
                     alt="Pet Image"/>
                <img class="circle_petchange" v-else
                     src="/src/assets/images/mypage/default-image.png" alt="No Image"/>
                <img class="icon" src="/src/assets/images/mypage/mypicture.png" alt="이미지 아이콘"
                     style="cursor: pointer;">
              </div>
              <input type="file" name="attachFile" id="attach" accept="image/*" style="display: none;"
                     max="1048576" @change="showImagePreview">
            </label>
            <img v-if="preview" :src="preview" alt="Image Preview" style="max-width: 100px; max-height: 100px;">
          </div>
          <div class="withoutPic">
            <div class="mb-3 row">
              <label for="pet_name" class="col-sm-2 col-form-label">이름</label>
              <div class="col-sm-10">
                <input v-model="modifiedPet.petName" type="text" class="form-control" id="pet_name" name="pet_name">
              </div>
            </div>
            <div class="infobox row mb-3">
              <label for="petBirthPicker" class="col-sm-2 col-form-label">생일</label>
              <div class="col-sm-10">
                <input v-model="modifiedPet.petBirth" type="date" class="form-control" name="pet_birth" id="petBirthPicker">
              </div>
            </div>

            <div class="petChange_select_container mb-3">
              <label class="col-sm-2 col-form-label">성별</label>
              <div id="select_container" class="col-sm-10">
                <div class="form-check form-check-inline">
                  <input v-model="modifiedPet.petGender" type="radio" class="form-check-input" name="pet_gender" value="m"
                         id="genderMale"
                         :checked="modifiedPet.petGender === 'm'">
                  <label class="form-check-label" for="genderMale">남아</label>
                </div>
                <div class="form-check form-check-inline">
                  <input v-model="modifiedPet.petGender" type="radio" class="form-check-input" name="pet_gender" value="f"
                         id="genderFemale" :checked="modifiedPet.petGender === 'f'">
                  <label class="form-check-label" for="genderFemale">여아</label>
                </div>
              </div>
            </div>

            <div class="infobox row mb-3">
              <label for="pet_kind" class="col-sm-2 col-form-label">종류</label>
              <div class="col-sm-10">
                <input v-model="modifiedPet.petKind" type="text" class="form-control" name="pet_kind" id="pet_kind">
              </div>
            </div>

            <div class="petChange_select_container mb-3">
              <label class="col-sm-2 col-form-label">중성화 여부</label>
              <div id="select_container2" class="col-sm-10">
                <div class="form-check form-check-inline">
                  <input v-model="modifiedPet.petNeuter" type="radio" class="form-check-input" name="pet_neuter" value="y"
                         id="neuterYes"
                         :checked="modifiedPet.petNeuter === 'y'">
                  <label class="form-check-label" for="neuterYes">유</label>
                </div>
                <div class="form-check form-check-inline">
                  <input v-model="modifiedPet.petNeuter" type="radio" class="form-check-input" name="pet_neuter" value="n"
                         id="neuterNo"
                         :checked="modifiedPet.petNeuter === 'n'">
                  <label class="form-check-label" for="neuterNo">무</label>
                </div>
              </div>
            </div>

            <div class="infobox row mb-3">
              <label for="pet_color" class="col-sm-2 col-form-label">색상</label>
              <div class="col-sm-10">
                <input v-model="modifiedPet.petColor" type="text" class="form-control" name="pet_color" id="pet_color">
              </div>
            </div>

            <div class="infobox row mb-3">
              <label for="pet_weight" class="col-sm-2 col-form-label">체중</label>
              <div class="col-sm-10">
                <div class="input-group">
                  <input v-model.number.trim="modifiedPet.petWeight" class="form-control" step="0.01" id="pet_weight"
                         name="pet_weight" min="0" max="100">
                  <div class="input-group-append">
                    <span class="input-group-text">kg</span>
                  </div>
                </div>
              </div>
            </div>

            <div class="infobox row mb-3">
              <label class="col-sm-2 col-form-label">메모</label>
              <div class="col-sm-10">
                <input v-model="modifiedPet.petTrait" type="text" class="form-control" name="pet_trait" id="pet_trait">
              </div>
            </div>
          </div>
        </form>
        <div class="btn_container">
          <button v-if="mypet" class="petForm_btn" @click="submit('modify')">완료</button>
          <button v-if="mypet" class="petForm_btn" @click="deletePet">삭제</button>
          <button v-else class="petForm_btn" @click="submit('add')">등록</button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
@import "./css/myPageLayout.css";
@import "./css/myPetChange.css";
@import "bootstrap/dist/css/bootstrap.min.css";
</style>