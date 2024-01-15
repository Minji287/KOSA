<template>
  <Modal
    v-if="showModal"
    :errorMessage="message"
    :closeModal="closeModal"
  ></Modal>
  <TopBackground
    :imageURL="signupBackgroundURL"
    :titleText="signupTitleText"
  ></TopBackground>
  <section class="sign_main_container">
    <h1>회원가입</h1>
    <form @submit.prevent="submitForm">
      <div class="info_container">
        <label class="input_lable_text" for="userId">아아디</label>
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

        <label class="input_lable_text" for="rePassword">비밀번호 확인</label>
        <input
          v-model="formData.rePassword"
          class="inputTag"
          id="rePassword"
          type="password"
          name="rePassword"
        />

        <label class="input_lable_text" for="email">이메일</label>
        <input
          v-model="formData.email"
          class="inputTag"
          id="email"
          type="email"
          name="email"
        />

        <label class="input_lable_text" for="name">이름</label>
        <input
          v-model="formData.name"
          class="inputTag"
          id="name"
          type="text"
          name="name"
        />
      </div>

      <div class="info_container">
        <h3>나의 펫 정보</h3>
        <span class="input_lable_text">펫 보유 여부</span>
        <div class="radio_container">
          <input
            v-model="formData.havePet"
            id="havePetTrue"
            type="radio"
            name="havePet"
            value="true"
          />
          <label class="margin90" for="havePetTrue">보유</label>
          <input
            v-model="formData.havePet"
            id="havePetFalse"
            type="radio"
            name="havePet"
            value="false"
          />
          <label for="havePetFalse">미보유</label>
        </div>

        <label class="input_lable_text" for="petName">펫 이름 </label>
        <input
          v-model="formData.petName"
          class="inputTag disable_toggle"
          id="petName"
          type="text"
          name="petName"
        />

        <label class="input_lable_text" for="petBirth">생일 </label>
        <input
          v-model="formData.petBirth"
          class="inputTag disable_toggle"
          type="datetime-local"
          id="petBirth"
          name="petBirth"
        />

        <span class="input_lable_text">암 / 수</span>
        <div class="radio_container">
          <input
            v-model="formData.petGender"
            id="petGenderF"
            type="radio"
            name="petGender"
            value="f"
            class="disable_toggle"
          />
          <label class="margin90" for="petGenderF">여아</label>
          <input
            v-model="formData.petGender"
            id="petGenderW"
            type="radio"
            name="petGender"
            value="m"
            class="disable_toggle"
          />
          <label for="petGenderW">남아</label>
        </div>

        <label class="input_lable_text" for="petKind">종류</label>
        <input
          v-model="formData.petKind"
          class="inputTag disable_toggle"
          id="petKind"
          type="text"
          name="petKind"
        />

        <span class="input_lable_text">중성화 여부</span>
        <div class="radio_container">
          <input
            v-model="formData.petNeuter"
            id="doNeuteringTrue"
            type="radio"
            name="petNeuter"
            value="y"
            class="disable_toggle"
          />
          <label class="margin90" for="doNeuteringTrue">⭕</label>
          <input
            v-model="formData.petNeuter"
            id="doNeuteringFalse"
            type="radio"
            name="petNeuter"
            value="n"
            class="disable_toggle"
          />
          <label for="doNeuteringFalse">❌</label>
        </div>

        <label class="input_lable_text" for="petColor">펫 색상</label>
        <input
          v-model="formData.petColor"
          class="inputTag disable_toggle"
          id="petColor"
          type="text"
          name="petColor"
        />

        <label class="input_lable_text" for="petWeight">펫 몸무게</label>
        <input
          v-model="formData.petWeight"
          class="inputTag disable_toggle"
          type="number"
          step="any"
          id="petWeight"
          name="petWeight"
        />

        <label class="input_lable_text" for="petTrait">펫 특징</label>
        <textarea
          v-model="formData.petTrait"
          class="textarea_tag disable_toggle"
          id="petTrait"
          name="petTrait"
          cols="30"
          rows="10"
        ></textarea>
        <button class="sign_btn" type="submit">회원가입 전송</button>
      </div>
    </form>
  </section>
</template>

<script setup>
import Modal from "../components/common/Modal.vue";
import TopBackground from "../components/common/TopBackground.vue";
import useHavePet from "../assets/js/havePetSignup";
import signupBackground from "../assets/images/topBackground/Signup_bg.jpg";
import { onMounted, ref } from "vue";
import { signupReq } from "../api/common";
import { useRouter } from "vue-router";

const router = useRouter();
const signupBackgroundURL = signupBackground;
const signupTitleText = "Signup";

onMounted(() => {
  useHavePet();
});

const formData = ref({
  userId: "",
  password: "",
  rePassword: "",
  email: "",
  name: "",
  havePet: false,
  petName: "",
  petBirth: "",
  petGender: "",
  petKind: "",
  petNeuter: "",
  petColor: "",
  petWeight: "",
  petTrait: "",
});

let message = ref("");
const showModal = ref(false);
const closeModal = () => {
  showModal.value = false;
};

const openModal = () => {
  window.scrollTo(0, 0);
  showModal.value = true;
};

const submitForm = () => {
  if (validateForm()) {
    signupReq(formData.value)
      .then((res) => {
        console.log("성공");
        router.push("/login");
      })
      .catch((error) => {
        window.scrollTo(0, 0);
        openModal();
        message.value = error.response.data;
        console.log("에러발생");
      });
  }
};

const validateForm = () => {
  const {
    userId,
    password,
    rePassword,
    email,
    name,
    havePet,
    petName,
    petBirth,
    petGender,
    petKind,
    petNeuter,
    petColor,
    petWeight,
    petTrait,
  } = formData.value;
  console.log(havePet);
  if (
    userId.trim() === "" ||
    password.trim() === "" ||
    rePassword.trim() === "" ||
    email.trim() === "" ||
    name.trim() === ""
  ) {
    openModal();
    message.value = "빈 항목을 작성하세요.";
    window.scrollTo(0, 0);
    return false;
  }
  if (password !== rePassword) {
    openModal();
    message.value = "비밀번호가 일치하지 않습니다.";
    window.scrollTo(0, 0);
    return false;
  }

  if (havePet == "true") {
    console.log(havePet);
    if (
      petName.trim() === "" ||
      petBirth.trim() === "" ||
      petGender.trim() === "" ||
      petKind.trim() === "" ||
      petNeuter.trim() === "" ||
      petColor.trim() === "" ||
      petWeight === null ||
      petTrait.trim() === ""
    ) {
      openModal();
      message.value = "펫 항목을 작성하세요.";
      window.scrollTo(0, 0);
      return false;
    }
  }
  return true;
};
</script>

<style scoped>
@import "../assets/css/sign.css";
</style>
