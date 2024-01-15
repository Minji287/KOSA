<template>
  <main class="main_container">
    <div class="video_container">
      <video autoplay loop muted>
        <source src="../assets/video/main_bg.mp4" type="video/mp4" />
      </video>
      <div class="main_text">
        <h1>Great Companion</h1>
        <h1>with Animal</h1>
        <h2>반려동물의 건강하고 행복한 삶과 함께 합니다</h2>
      </div>
    </div>
    <div class="arrow_down_container">
      <img src="../assets/images/main/down_arrow.png" alt="down_arrow_icon" />
      <p>SCROLL</p>
    </div>
    <div class="main_text_container">
      <div class="main_text_container_innerbox">
        <h1>유기동물 커뮤니티 서비스</h1>
        <h1>UNI PET</h1>
        <p>Create a society where animals and people coexist.</p>
      </div>
      <div class="main_text_bgbox"></div>
      <div class="first_text_container">
        <h1>한 해 버려지는 유기동물</h1>
        <h1>약 10만 마리</h1>
      </div>
      <div class="second_text_container">
        <h1>유니펫은 동물과 사람이</h1>
        <h1>공존하는 사회를 바랍니다.</h1>
      </div>
    </div>
    <div class="main_text_container2 bg_black">
      <div class="main_text_container_innerbox">
        <h1>Service</h1>
        <div class="service_container hidden">
          <div class="service_cotainer_item">
            <router-link
              to="/animallist"
              class="service_cotainer_item_img photo1"
            ></router-link>
            <h2>유기동물 리스트 제공</h2>
          </div>
          <div class="service_cotainer_item">
            <router-link
              to="/board/list/0"
              class="service_cotainer_item_img photo2"
            ></router-link>
            <h2>반려동물 커뮤니티 서비스</h2>
          </div>
          <div class="service_cotainer_item">
            <router-link
              to="/hospinfo"
              class="service_cotainer_item_img photo3"
            ></router-link>
            <h2>동물 관련 유용한 정보 제공</h2>
          </div>
        </div>
        <div class="text_up_down_container hidden">
          <p>클릭하면 바로 서비스로 이동 합니다.</p>
        </div>
      </div>
    </div>
  </main>
</template>
<script setup>
import useMain from "../assets/js/main";
import { onMounted } from "vue";
import { headerTestReq } from "../api/common";

headerTestReq();
onMounted(() => {
  useMain();
});
</script>
<style scoped>
@import "../assets/css/main.css";
</style>
