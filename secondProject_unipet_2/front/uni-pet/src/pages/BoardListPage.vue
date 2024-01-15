<template>
  <div>
    <TopBackground
        :imageURL="boardBackgroundURL"
        :titleText="boardTitleText"
    ></TopBackground>
    <section id="entire_screen">
      <div class="container_nav">
        <nav class="nav_flex_column">
          <router-link :to="{ name: 'BoardList', params: { boardId: 0 } }" class="nav-link">
            <span class="bnav">공지 게시판</span>
          </router-link>
          <router-link :to="{ name: 'BoardList', params: { boardId: 1 } }" class="nav-link">
            <span class="bnav">나눔 게시판</span>
          </router-link>
          <router-link :to="{ name: 'BoardList', params: { boardId: 2 } }" class="nav-link">
            <span class="bnav">자유 게시판</span>
          </router-link>
        </nav>
      </div>
      <div class="main_container">
<!--        <form id="search_btn" @submit.prevent="search">-->
<!--          <input-->
<!--              type="text"-->
<!--              id="search_text"-->
<!--              placeholder="검색어를 입력해주세요."-->
<!--              v-model="searchText"-->
<!--          />-->
<!--          <button type="submit" class="btn btn-light">-->
<!--            <img :src="searchIcon" alt="" />-->
<!--            &lt;!&ndash; 이미지 src에 바인딩 &ndash;&gt;-->
<!--          </button>-->
<!--        </form>-->
        <br />
        <br />
        <div class="table">
          <table class="table">
            <thead>
            <tr class="table-dark">
              <th width="600px">제목</th>
              <th>작성자</th>
              <th>작성일</th>
              <th>조회</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="board in boardList" :key="board.boardNo">
              <td>
                <router-link
                    :to="`/board/content?boardNo=${board.boardNo}`"
                >{{ board.title }}</router-link
                >
              </td>
              <td>{{ board.userId }}</td>
              <td>{{ formatDate(board.postingDate) }}</td>
              <td>{{ board.views }}</td>
            </tr>
            </tbody>
          </table>
          <button id="posting_btn1" class="btn btn-dark" @click="goToPosting">
            글쓰기
          </button>
        </div>
<!--        <div v-if="boardList.length === 0">-->
<!--          <h3>{{ msg }}</h3>-->
<!--          <router-link to="/">-->
<!--            <button class="btn btn-dark">메인으로 돌아가기</button>-->
<!--          </router-link>-->
<!--          <button id="posting_btn2" class="btn btn-dark" @click="goToPosting">-->
<!--            글쓰기-->
<!--          </button>-->
<!--        </div>-->
      </div>
    </section>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from "vue";
import axios from "axios";
import TopBackground from "@/components/common/TopBackground.vue";
import searchIcon from "@/assets/images/board/searchIcon.png"; //검색 이미지 아이콘
import router from "@/router";
import boardBackground from "../assets/images/topBackground/Board_bg.jpg";

// props를 정의합니다.
const props = defineProps({
  boardId: {
    type: Number,
    required: true
  }
});
const formatDate = (dateString) => {
  const date = new Date(dateString);
  const year = date.getFullYear();
  const month = (date.getMonth() + 1).toString().padStart(2, '0');
  const day = date.getDate().toString().padStart(2, '0');
  const hours = date.getHours().toString().padStart(2, '0');
  const minutes = date.getMinutes().toString().padStart(2, '0');

  return `${year}-${month}-${day} ${hours}:${minutes}`;
};


const boardBackgroundURL = boardBackground;
const boardTitleText = "Board";

const boardList = ref([]); // 게시판 리스트 데이터를 저장할 변수
const searchText = ref(""); // 검색어를 저장할 변수
const msg = ref(""); // 메시지를 저장할 변수

// fetchData 함수를 정의합니다. 이 함수는 boardId에 따른 게시판 리스트를 불러옵니다.
const fetchData = async () => {
  const response = await axios.get(`http://localhost:8889/board/list/${props.boardId}`);
  console.log(response.data); // 서버로부터 받은 데이터를 콘솔에 출력
  boardList.value = response.data.sort((a, b) => new Date(b.postingDate) - new Date(a.postingDate));
};

// 컴포넌트가 마운트될 때 fetchData 함수를 실행합니다.
onMounted(fetchData);
window.scrollTo(0,0);
// boardId prop이 변경될 때마다 fetchData 함수를 실행합니다.
watch(() => props.boardId, fetchData);

const search = () => {
  // 검색 기능을 구현하세요.
};

const goToPosting = () => {
  // 글쓰기 페이지로 이동하는 기능을 구현하세요.
  router.push({ name: 'BoardWrite', params: { boardId: props.boardId } });
};
</script>

<style scoped>
@import "@/assets/css/board.css";
</style>
