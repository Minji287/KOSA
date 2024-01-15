<script setup>
import {ref, watchEffect} from "vue";
import {api, changeEmailReq} from "@/api/common";
import router from "@/router";

const props = defineProps(['currentEmail']);

const newEmail = ref('');
const selected = ref('gmail.com');
const customEmail = ref('');
const customBox = ref(false);
const domain = ref('');

function returnSelectBox() {
  customBox.value = false;
  selected.value = 'gmail.com';
}

watchEffect(() => {
  if(selected.value === 'custom') {
    customBox.value = true;
  }
})

async function submit() {
  if (customBox.value !== true) {
    domain.value = selected.value;
  } else {
    domain.value = customEmail.value;
  }

  try {
    const response = await changeEmailReq(newEmail.value, domain.value);
    console.log(response)
    alert(response.data)
    await router.push('/myprofile');
  } catch (e) {
    alert("에러가 발생하였습니다.")
  }
}

</script>

<template>
  <div class="con_info_wrapper">
    <div class="con_title">이메일 변경</div>
    <div class="my_email_container">
      <p>기존 이메일: </p>
      <p>{{ currentEmail }}</p>
    </div>
    <div class="change_email_container">
      <form method="post" @submit.prevent="submit" id="confirm_change">
        <div class="change_eamil_container_wrapper">
          <div class="after_change" >변경 후 이메일 </div>
          <div class="con_email">
            <input v-model="newEmail" class="new_email" type="text" name="email" id="customEmailField">
            &nbsp;&#64;&nbsp;
            <select v-if="!customBox" v-model="selected" name="domain" id="emailSelect" class="form-select form-select-sm"
                    aria-label=".form-select-sm example">
              <option value="gmail.com">gmail.com</option>
              <option value="naver.com">naver.com</option>
              <option value="daum.net">daum.net</option>
              <option value="kakao.com">kakao.com</option>
              <option value="custom">직접입력</option>
            </select>
            <input v-if="customBox" v-model.trim="customEmail" type="text" class="customInput">
            <button v-if="customBox" id="cancelButton" @click="returnSelectBox()">취소</button>
          </div>
        </div>
      </form>
    </div>
    <button type="submit" form="confirm_change" id="e_btn">확인</button>
  </div>
</template>

<style scoped>
@import "./css/myEmail.css";
@import "./css/myPageLayout.css";
@import "bootstrap/dist/css/bootstrap.min.css";
</style>