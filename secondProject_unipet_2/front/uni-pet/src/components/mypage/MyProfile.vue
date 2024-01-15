<script setup>
import {myPageProfileReq} from "@/api/common";
import {onMounted, defineEmits, ref} from "vue";
import router from "@/router";

const emits = defineEmits(["editProfile", "currentEmail"]);
const props = defineProps(['name', 'email']);

const name = ref('');
const email = ref('');
const petPic = ref('');

const goToEditProfile = (kind) => {
    router.push({ name: 'Edit', params: { kind: kind } });
    emits("editProfile", kind);
    emits("currentEmail", email.value);
};

// 로그아웃
const logout = async () => {
  if (confirm("로그아웃 하시겠습니까?")) {
    const accessToken = ref(localStorage.getItem("accessToken"));
    localStorage.removeItem("accessToken");
    window.location.href = "http://localhost:5173/";
    console.log("로그아웃")

  }
}

const deleteAccount = async (kind) => {
  if(confirm("정말 탈퇴하시겠습니까?")){
    emits("editProfile", kind);
    await router.push({name: 'Edit', params: {kind: kind}});
  }
}

async function fetchUser() {
  try {
    const response = await myPageProfileReq();
    name.value = response.data.user.name;
    email.value = response.data.user.email;
    petPic.value = response.data.user.petPic;
  } catch (error) {
    console.error('Error fetching user:', error);
  }
}

onMounted(() => {
  fetchUser();
})
</script>

<template>
    <div class="con_info_wrapper">
      <div class="con_title">회원 정보</div>
      <div class="infobox">
        <p>이름 </p><span class="con_name">{{ name }}</span>
      </div>
      <div class="infobox">
        <p>이메일 </p><a class="con_link" @click="goToEditProfile('email')">{{ email }} &gt;</a>
      </div>
      <div class="infobox">
        <p>비밀번호 변경</p><a class="con_link" @click="goToEditProfile('pw')">&gt;</a>
      </div>
      <div class="con_bottom">
        <a class="con_link_bottom" @click="logout()">로그아웃</a> | <a class="con_link_bottom" @click="deleteAccount('del')">회원탈퇴</a>
      </div>
    </div>
</template>

<style scoped>
@import "./css/myProfile.css";
@import "./css/myPageLayout.css";
@import "bootstrap/dist/css/bootstrap.min.css";
</style>