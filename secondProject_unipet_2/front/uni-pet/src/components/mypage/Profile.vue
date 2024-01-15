<script setup>
import {onMounted, reactive, ref, watch, watchEffect} from "vue";
import PwCheck from "@/components/mypage/PwCheck.vue";
import EmailChange from "@/components/mypage/EmailChange.vue";
import MyProfile from "@/components/mypage/MyProfile.vue";
import router from "@/router";
import TopBackground from "@/components/common/TopBackground.vue";
import MyNav from "@/components/mypage/MyNav.vue";
import mypageBackground from "@/assets/images/topBackground/Mypage_bg.jpg";
import {myPageProfileReq} from "@/api/common";

const mypageBackgroundURL = mypageBackground;
const mypageTitleText = "Mypage";

const showProfile = ref(true);
const showEmailEditForm = ref(false);
const showPwEditForm = ref(false);
const currentEmail = ref('');
const checkKind = ref('');

const name= ref('');
const email= ref('');
const petPic= ref('');

const handleCurrentEmail = (email) => {
  console.log(email)
  currentEmail.value = email;
};

const handleEditProfile = (kind) => {
  checkKind.value = kind;
  switch (checkKind.value) {
    case 'email':
      showEmailEditForm.value = true;
      break;
    case 'pw':
      showPwEditForm.value = true;
      break;
    case 'del':
      showPwEditForm.value = true;
      break;
  }
  showProfile.value = false;
};

watchEffect(() => {
  const newPath = router.currentRoute.value.path;
  if (newPath === '/myprofile') {
    showProfile.value = true;
    showEmailEditForm.value = false;
    showPwEditForm.value = false;
  }
});


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
  <TopBackground
      :imageURL="mypageBackgroundURL"
      :titleText="mypageTitleText"
  ></TopBackground>
  <div class="middle">
    <div class="nav_wrapper">
      <MyNav :name="name" :petPic="petPic"/>
    </div>
    <div class="con_info" >
      <MyProfile v-if="showProfile"
                 @editProfile="handleEditProfile"
                 @currentEmail="handleCurrentEmail" />
      <EmailChange v-if="showEmailEditForm" :currentEmail="currentEmail"/>
      <PwCheck v-if="showPwEditForm" :checkKind="checkKind"/>
    </div>
  </div>
</template>

<style scoped>
@import "../mypage/css/myPageLayout.css";
@import "bootstrap/dist/css/bootstrap.min.css";
.middle{
  display: flex;
}
.nav_wrapper{
  width: 200px;
  min-height: 100%;
}
</style>
