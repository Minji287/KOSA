<template>
  <header class="header">
    <div class="header_wrapper">
      <div class="header_container">
        <router-link to="/">
          <img
            class="header_logo"
            src="../../assets/images/header/main_logo.png"
            alt="main_logo"
          />
        </router-link>
        <div class="header_menu_container font_size_m drag_none">
          <p class="menu_text">공고 리스트</p>
          <p class="menu_text">커뮤니티</p>
          <p class="menu_text">편의기능</p>
        </div>
        <div
          v-if="!accessToken"
          class="header_sideMenu_container font_size_s drag_none"
        >
          <router-link to="/login" class="menu_text right_margin_20"
            >Login</router-link
          >
          <div class="weather_container">
            <a class="menu_text weather_icon"></a>
            <a class="menu_text weather_temp"></a>
          </div>
        </div>
        <div
          v-if="accessToken"
          class="header_sideMenu_container font_size_s drag_none"
        >
          <router-link to="/mypage" class="menu_text right_margin_20"
            >My Page</router-link
          >
          <a class="menu_text right_margin_20" @click="logout">Log Out</a>
          <div class="weather_container">
            <a class="menu_text weather_icon"></a>
            <a class="menu_text weather_temp"></a>
          </div>
        </div>
      </div>
      <div class="drop_down_container">
        <div class="drop_down_wrapper">
          <div class="drop_down_menu_container">
            <router-link
              to="/animallist"
              class="drop_down_menu_text display_none"
              >유기동물 리스트</router-link
            >
          </div>
          <div class="drop_down_menu_container">
            <router-link
                :to="{ name: 'BoardList', params: { boardId: 0 } }"
                class="drop_down_menu_text display_none"
            >공지 게시판</router-link>

            <router-link
                :to="{ name: 'BoardList', params: { boardId: 1 } }"
                class="drop_down_menu_text display_none"
            >나눔 게시판</router-link>

            <router-link
                :to="{ name: 'BoardList', params: { boardId: 2 } }"
                class="drop_down_menu_text display_none"
            >자유 게시판</router-link>

          </div>
          <div class="drop_down_menu_container">
            <router-link to="/hospinfo" class="drop_down_menu_text display_none"
              >동물병원 위치</router-link
            >
            <router-link
              to="/trailInfo"
              class="drop_down_menu_text display_none"
              >산책로</router-link
            >
          </div>
        </div>
      </div>
    </div>
  </header>
</template>

<script setup>
import useHeader from "./js/header";
import { ref, onMounted } from "vue";

const accessToken = ref(localStorage.getItem("accessToken"));

const logout = () => {
  localStorage.removeItem("accessToken");
  window.location.href = "/login";
};

onMounted(() => {
  useHeader();
});
</script>

<style scoped>
@import "./header.css";
</style>
