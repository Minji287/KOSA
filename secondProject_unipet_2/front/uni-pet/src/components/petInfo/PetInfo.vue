<template>
  <div class="hospital_dataList_head_text">
    <h1>{{ title }}</h1>
  </div>
  <div class="hospital_dataList_container">
    <div v-bind:class="className"></div>
  </div>
</template>

<script setup>
import usePetInfo from "../../assets/js/petInfo";
import { onMounted, defineProps } from "vue";

onMounted(() => {
  usePetInfo();
});

const props = defineProps({ title: String, className: String });
</script>

<style>
@import "../../assets/css/petInfo.css";
</style>
