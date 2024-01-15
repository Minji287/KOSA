<template>
  <TopBackground
    :imageURL="convenienceBackgroundURL"
    :titleText="trailTitleText"
  ></TopBackground>
  <PetInfoPage :title="trailTitleKo" :className="trailClassName"></PetInfoPage>
</template>

<script setup>
import Modal from "../components/common/Modal.vue";
import TopBackground from "../components/common/TopBackground.vue";
import convenienceBackground from "../assets/images/topBackground/Convenience_bg.jpg";
import PetInfoPage from "../components/petInfo/PetInfo.vue";
window.scrollTo(0,0);
const convenienceBackgroundURL = convenienceBackground;
const trailTitleText = "Trail";
const trailTitleKo = "산책로 리스트";
const trailClassName = "trail_dataList_warpper";
</script>

<style scoped></style>
