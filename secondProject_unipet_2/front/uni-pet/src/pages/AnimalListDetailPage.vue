<script setup>
import AnimalListDetail from '../components/animallist/AnimalListDetail.vue';
import TopBackground from "@/components/common/TopBackground.vue";
import loginBackground from "@/assets/images/topBackground/List_bg.jpg";

window.scrollTo(0,0);
const listBackgroundURL = loginBackground;
const listTitleText = "List";
</script>

<template>
  <TopBackground :imageURL="listBackgroundURL"  :titleText="listTitleText"></TopBackground>
  <AnimalListDetail/>
</template>

<style scoped>
</style>

