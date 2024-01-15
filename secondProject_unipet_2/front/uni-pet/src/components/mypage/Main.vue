<script setup>

</script>

<template>
  <div class="menu">
    <button class="selbox selbox1" @click="$router.push('/myprofile')">
      <img class="my_icon" src="@/assets/images/mypage/me.png" alt="회원 정보">
      <span>회원 정보</span>
    </button>
    <button class="selbox selbox2" @click="$router.push('/mypet')">
      <img class="my_icon" src="@/assets/images/mypage/paw.png" alt="나의 펫">
      <span>나의 펫</span>
    </button>
    <button class="selbox selbox3" @click="$router.push('/mywriting')">
      <img class="my_icon" src="@/assets/images/mypage/writing.png" alt="나의 게시글">
      <span>나의 게시글</span>
    </button>
    <button class="selbox selbox4" @click="$router.push('/myshare')">
      <img class="my_icon" src="@/assets/images/mypage/share.png" alt="나눔 물품">
      <span>나눔 물품</span>
    </button>
  </div>
</template>

<style scoped>
@import "./css/myPage.css";
</style>