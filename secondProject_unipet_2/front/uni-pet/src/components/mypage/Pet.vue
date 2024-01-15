<script setup>
import {defineEmits} from "vue";

const props = defineProps([ "mypet", "age", "nopet" ])
const emits = defineEmits(["changeForm"]);

const goToChangePetInfo = () => {
  emits('changeForm', 'changeInfo');
}

const goToRegisterPet = () => {
  emits('changeForm', 'add');
}
</script>

<template>
  <div class="con_info">
    <div class="con_info_wrapper">
      <div class="con_title">나의 펫</div>
      <input type="hidden" >
      <div class="menu" v-if="mypet">
        <img class="circle_pet" :src="mypet.petPic ? 'http://localhost:8889' + mypet.petPic : '/src/assets/images/mypage/default-image.png'" alt="Pet Image"/>
        <div class="pet_details">
          <div class="infobox">
            <span>이름 </span><span class="con_text">{{ mypet.petName }}</span>
          </div>
          <div class="infobox">
            <span>생일 </span><span class="con_text">{{ mypet.petBirth }} {{ age }}</span>
          </div>
          <div class="infobox">
            <span>성별 </span><span class="con_text">{{ mypet.petGender === 'm' ? '남아' : '여아' }}</span>
          </div>
          <div class="infobox">
            <span>종류 </span><span class="con_text">{{ mypet.petKind }}</span>
          </div>
          <div class="infobox">
            <span>중성화 여부 </span><span class="con_text">{{ mypet.petNeuter === 'y' ? '유' : '무' }}</span>
          </div>
          <div class="infobox">
            <span>색상 </span><span class="con_text">{{ mypet.petColor }}</span>
          </div>
          <div class="infobox">
            <span>체중 </span><span class="con_text">{{ mypet.petWeight ? mypet.petWeight + 'kg' : '0 kg' }}</span>
          </div>
          <div class="mypet_text_container">
            <span class="memo_title_text">메모 </span>
            <span class="con_text">{{ mypet.petTrait }}</span>
          </div>
        </div>
        <button class="change_btn" @click="goToChangePetInfo()">수정</button>
      </div>
      <div class="none_pet_container" v-else-if="!mypet">
        <p>{{ nopet }}</p>
        <button class="change_btn" @click="goToRegisterPet()">등록하러 가기</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
@import "./css/myPageLayout.css";
@import "./css/myPet.css";
@import "bootstrap/dist/css/bootstrap.min.css";
</style>