<template>
  <div>
    <div @click="closeModalFunction" class="black_bg"></div>
    <div class="alert">
      <p>{{ errorMessage }}</p>
      <button @click="closeModalFunction" class="alert_btn">확인</button>
    </div>
  </div>
</template>

<script setup>
import { onMounted, defineProps } from "vue";

onMounted(() => {
  // useModal();
});

const props = defineProps(["errorMessage", "closeModal"]);

const closeModalFunction = () => {
  props.closeModal();
};
</script>

<style scoped>
@import "../../assets/css/modal.css";
</style>
