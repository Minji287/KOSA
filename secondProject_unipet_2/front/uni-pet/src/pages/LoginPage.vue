<template>
  <Modal
    v-if="showModal"
    :errorMessage="modalMessage"
    :closeModal="closeModal"
  ></Modal>
  <TopBackground
    :imageURL="loginBackgroundURL"
    :titleText="loginTitleText"
  ></TopBackground>
  <section class="sign_main_container">
    <h1>로그인</h1>
    <form @submit.prevent="submitForm">
      <div class="info_container">
        <label class="input_lable_text" for="userId">아이디</label>
        <input
          v-model="formData.userId"
          class="inputTag"
          id="userId"
          type="text"
          name="userId"
        />
        <label class="input_lable_text" for="password">비밀번호</label>
        <input
          v-model="formData.password"
          class="inputTag"
          id="password"
          type="password"
          name="password"
        />
        <div class="go_signup_text_container margin_bottom_50">
          <router-link to="/signup">회원가입</router-link>
        </div>
        <button class="sign_btn" type="submit">로그인</button>
      </div>
    </form>
  </section>
</template>

<script setup>
import Modal from "../components/common/Modal.vue";
import TopBackground from "../components/common/TopBackground.vue";
import loginBackground from "../assets/images/topBackground/Login_bg.jpg";
import { onMounted, ref } from "vue";
import { loginReq } from "../api/common";

const loginBackgroundURL = loginBackground;
const loginTitleText = "Login";

const showModal = ref(false);
const modalMessage = ref("");

const formData = ref({
  userId: "",
  password: "",
});

onMounted(() => {
  window.scrollTo(0, 0);
});

const closeModal = () => {
  showModal.value = false;
};

const openModal = () => {
  window.scrollTo(0, 0);
  showModal.value = true;
};

const submitForm = () => {
  loginReq(formData.value)
    .then((res) => {
      if (res.status === 200) {
        let tokenHeader = res.headers.authorization;
        console.log(tokenHeader);
        if (tokenHeader && tokenHeader.startsWith("Bearer ")) {
          let accessToken = tokenHeader.substring(7);
          localStorage.setItem("accessToken", accessToken);
          window.location.href = "/";
        }
      }
    })
    .catch((error) => {
      openModal();
      modalMessage.value = "아이디와 비밀번호를 확인해주세요.";
    });

  // 실패 시
};
</script>

<style scoped>
@import "../assets/css/sign.css";
</style>
