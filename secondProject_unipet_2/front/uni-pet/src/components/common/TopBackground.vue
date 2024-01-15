<template>
  <section
    class="signup_header"
    :style="{ backgroundImage: `url(${imageURL})` }"
  >
    <h1>{{ titleText }}</h1>
  </section>
</template>

<script setup>
import { defineProps } from "vue";

const props = defineProps({ imageURL: String, titleText: String });
</script>

<style scoped>
@import "../../assets/css/topBackground.css";
</style>
