<script setup>
import {onMounted, ref, watchEffect, provide} from 'vue';
import MyNav from "@/components/mypage/MyNav.vue";
import Profile from "@/components/mypage/Profile.vue";
import Mypet from "@/components/mypage/Mypet.vue";
import {useRouter} from "vue-router";
import Mywriting from "@/components/mypage/Mywriting.vue";
import Myshare from "@/components/mypage/Myshare.vue";
import Main from "@/components/mypage/Main.vue";
import TopBackground from "../components/common/TopBackground.vue";
import mypageBackground from "../assets/images/topBackground/Mypage_bg.jpg"
import {myPageProfileReq} from "@/api/common";

const mypageBackgroundURL = mypageBackground;
const mypageTitleText = "Mypage";

const router = useRouter();
const path = ref(router.currentRoute.value.path);

const name = ref('');
const petPic = ref('');

watchEffect(() => {
  path.value = router.currentRoute.value.path;
  console.log(path.value);
});

const getInfo = (navObj) => {
  name.value = navObj.userName;
  petPic.value = navObj.petPic;
}

</script>

<template>
  <TopBackground
      :imageURL="mypageBackgroundURL"
      :titleText="mypageTitleText"
  ></TopBackground>
  <Main v-if="path === '/mypage'" />
  <div class="middle">
    <div class="nav_wrapper">
      <MyNav v-if="path !== '/mypage'" :name="name" :petPic="petPic"/>
    </div>
      <Profile v-if="path === '/myprofile'"/>
      <Mypet v-if="path === '/mypet'" />
      <Mywriting v-if="path === '/mywriting'" @navInfo="getInfo" />
      <Myshare v-if="path === '/myshare'" @navInfo="getInfo" />
  </div>
</template>

<style scoped>
@import "../../src/components/mypage/css/myPageLayout.css";
.middle{
  display: flex;
}
.nav_wrapper{
  width: 200px;
  min-height: 100%;
}
</style>
