<template>
  <TopBackground
    :imageURL="convenienceBackgroundURL"
    :titleText="hospitalTitleText"
  ></TopBackground>
  <PetInfoPage
    :title="hospitalTitleKo"
    :className="hospitalClassName"
  ></PetInfoPage>
</template>

<script setup>
import Modal from "../components/common/Modal.vue";
import TopBackground from "../components/common/TopBackground.vue";
import convenienceBackground from "../assets/images/topBackground/Convenience_bg.jpg";
import PetInfoPage from "../components/petInfo/PetInfo.vue";

const convenienceBackgroundURL = convenienceBackground;
const hospitalTitleText = "Hospital";
const hospitalTitleKo = "동물 병원 리스트";
const hospitalClassName = "hospital_dataList_warpper";
window.scrollTo(0, 0);
</script>

<style scoped></style>
