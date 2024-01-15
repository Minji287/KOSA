<script setup>
import {onMounted, ref} from "vue";
import {mywritingsReq} from "@/api/common";
import Paginate from "vuejs-paginate-next";

const isActive = ref(false);
const currentPage = ref(0);
const writingList = ref([]);
const totalPages = ref(0);
const noWritings = ref('');
const userName = ref('');
const petPic = ref('');
const nav = ref({
  userName: userName,
  petPic: petPic
})

const emits = defineEmits(['navInfo'])

async function fetchWritings(currentPage, boarId) {
  const boardId = parseInt(boarId);
  try {
    const response = await mywritingsReq(currentPage, boardId);
    writingList.value = response.data.list.content;
    totalPages.value = response.data.list.totalPages;
    userName.value = response.data.userName;
    petPic.value = response.data.petPic;
    isActive.value = true;
  } catch (e) {
    noWritings.value = e.response.data.noWritings;
    userName.value = e.response.data.userName;
    petPic.value = e.response.data.petPic;
  }
  emits('navInfo', nav.value);
}

onMounted(async () => {
  await fetchWritings(currentPage.value, 2)
});

const onClickHandler = (page) => {
  currentPage.value = page-1;
  fetchWritings(currentPage.value, 2);
};

const goToMyWritingItem = (writingId) => {
  const url = '/board/content?boardNo=' + parseInt(writingId);
  location.href = 'http://localhost:5173' + url;
}

</script>

<template>
    <div class="con_info">
      <div class="con_info_wrapper">
        <div class="con_title">나의 게시글</div>
      <div v-if="isActive">
        <table class="table table-hover">
          <thead class="table-light">
            <tr>
              <th scope="col">번호</th>
              <th scope="col">제목</th>
              <th scope="col">날짜</th>
            </tr>
          </thead>
          <tbody class="table-group-divider">
            <tr v-for="item in writingList" :key="item.boardNo" >
              <td class="tb_num">{{ item.boardNo }}</td>
              <td class="tb_title">
                <a class="title_link" @click="goToMyWritingItem(item.boardNo)">{{ item.title }}</a>
                </td>
              <td>{{ item.postingDate }}</td>
            </tr>
          </tbody>
        </table>

        <!-- paginate next Library -->
        <paginate
            :page-range="3"
            :page-count="totalPages"
            :click-handler="onClickHandler"
            :prev-text="'이전'"
            :next-text="'다음'"
            :container-class="'pagination'"
            :page-class="'page-item'"
        />
      </div>
        <div v-else>
          {{ noWritings }}
        </div>
      </div>
    </div>
</template>

<style scoped>
@import "./css/myWriting.css";
@import "./css/myPageLayout.css";
@import "bootstrap/dist/css/bootstrap.min.css";

.pagination {
  width: 53%;
  height: 28px;
  position: absolute;
  justify-content: center;
  bottom: 28%;
  line-height: 28px;
}
.pagination li {
  min-width: 32px;
  padding:6px 8px;
  text-align:center;
  margin:0 3px;
  border-radius: 6px;
  border:1px solid #eee;
  color:#666;
}
.pagination li:hover {
  background: #E4DBD6;
}
.page-item a {
  cursor: pointer;
  color:#666;
  text-decoration: none;
}
.pagination li.active {
  background-color : #ccc;
  color:#fff;
}
.pagination li.active a {
  color:#fff;
}
</style>

