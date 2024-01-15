<script setup>
import {pwCheckReq, userOutReq} from "@/api/common";
import {ref} from "vue";
import PwChange from "@/components/mypage/PwChange.vue";

const props = defineProps(['checkKind']);
const checkPw = ref('');
const pwChecked = ref(false);

async function submit(checkKind) {
  try {
    await pwCheckReq(checkPw.value);
    if(checkKind === 'del') {
      const response = await userOutReq();
      alert(response.data)

      localStorage.removeItem("accessToken");
      location.href = 'http://localhost:5173/'
    } else {
      pwChecked.value = true;
    }
  } catch (e) {
    alert("비밀번호가 일치하지 않습니다.")
  }
}

</script>

<template>
  <div class="con_info_wrapper" v-if="!pwChecked">
    <div class="con_title" v-if="checkKind === 'pw'">비밀번호 변경</div>
    <div class="con_title" v-if="checkKind === 'del'">회원탈퇴</div>
    <form method="post" @submit.prevent="() => submit(checkKind)" id="confirm_change">
      <div class="infobox">
        <p class="passwdcheck">비밀번호 확인 </p><input v-model="checkPw" type="password" class="customInput" name="currentPass">
      </div>
    </form>
    <button type="submit" form="confirm_change" id="e_btn">확인</button>
  </div>
  <PwChange v-if="pwChecked" />
</template>

<style scoped>
@import "./css/myPasscheck.css";
@import "./css/myPageLayout.css";
@import "bootstrap/dist/css/bootstrap.min.css";
</style>