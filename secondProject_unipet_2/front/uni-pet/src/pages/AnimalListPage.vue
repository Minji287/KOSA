<style scoped>

</style>

<script setup>
import AnimalList from '../components/animallist/AnimalList.vue';

import TopBackground from "@/components/common/TopBackground.vue";
import loginBackground from "@/assets/images/topBackground/List_bg.jpg";

const listBackgroundURL = loginBackground;
const listTitleText = "List";

window.scrollTo(0,0);
</script>

<template>
<TopBackground :imageURL="listBackgroundURL"  :titleText="listTitleText"></TopBackground>
    <AnimalList/>

</template>