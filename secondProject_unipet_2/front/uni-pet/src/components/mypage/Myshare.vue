<script setup>
import {onMounted, ref} from "vue";
import {api, mywritingsReq} from "@/api/common";
import router from "@/router";

const isActive = ref(false);
const noWritings = ref('');
const currentPage = ref(0);
const shareList = ref([]);
const pageSize = 6;
const pageNumber = ref(0);
const totalPages = ref(0);
const first = ref(null);
const last = ref(null);
const userName = ref('');
const petPic = ref('');
const nav = ref({
  userName: userName,
  petPic: petPic
})

const emits = defineEmits(['navInfo'])

async function fetchWritings(boarId, currentPage) {
  const boardId = parseInt(boarId);
  try {
    const response = await mywritingsReq(currentPage, boardId);
    isActive.value = true;
    shareList.value = shareList.value = [...shareList.value, ...response.data.list.content];
    pageNumber.value = response.data.list.pageable.pageNumber;
    totalPages.value = response.data.list.totalPages;
    first.value = response.data.list.first;
    last.value = response.data.list.last;
    userName.value = response.data.userName;
    petPic.value = response.data.petPic;
  } catch (e) {
    userName.value = e.response.data.userName;
    petPic.value = e.response.data.petPic;
    noWritings.value = e.response.data.noWritings;
  }

  emits('navInfo', nav.value);
}

onMounted(async () => {
  await fetchWritings(1, currentPage.value)
});

const loadMoreItems = async () => {
  currentPage.value++;
  await fetchWritings(1, currentPage.value)
}

const goToMyShareItem = (shareId) => {
  const url = '/board/content?boardNo=' + parseInt(shareId);
  location.href = 'http://localhost:5173' + url;
}

</script>

<template>
    <div class="con_info">
      <div class="con_info_wrapper">
        <div class="con_title">나눔 물품</div>
        <div class="cardbox" v-if="isActive">
          <div v-for="item in shareList" :key="item.boardNo" class="sharecard">
            <div class="card mb-3">
              <div class="row g-0">
                <div class="col-md-4">
                  <img :src="item.imgPath ? '/src/assets/images/mypage/default-image.png' : '/src/assets/images/mypage/default-image.png'" class="img-fluid rounded-start" id="pic" alt="...">
                </div>
                <div class="col-md-8">
                  <div class="card-body">
                    <a id="card_atag" @click="goToMyShareItem(item.boardNo)">
                      <h5 class="card-title">{{ item.title }}</h5>
                      <p class="card-text">{{ item.content }}</p>
                      <p class="card-text">{{ item.postingDate }}<small
                          class="text-muted"></small></p>
                    </a>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
          <div v-else>
            {{ noWritings }}
          </div>
        <div v-if="isActive">
          <button id="load-more" v-if="!last" @click="loadMoreItems()">더 보기</button>
        </div>
      </div>
    </div>
</template>

<style scoped>
@import "./css/myShare.css";
@import "./css/myPageLayout.css";
@import "bootstrap/dist/css/bootstrap.min.css";
</style>