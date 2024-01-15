<template>
  <TopBackground
      :imageURL="boardBackgroundURL"
      :titleText="boardTitleText"
  ></TopBackground>
  <div id="entire_screen">
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
    <section class="main_container" v-if="board">
      <div class="main_wrap">
        <h1 class="board_title">{{ board.title }}</h1>
        <div class="writer_wrap">
<!--          <img class="circle_pet" :src="petPicUrl || '/img/mypage/default-image.png'" alt="Pet Image"/>-->
          <div class="posting_info1">{{ board.userId }}</div>
        </div>
        <div class="posting_info2">{{ formatDate(board.postingDate) }} &nbsp; 조회 {{ board.views }}</div>
        <hr>
        <br>
        <div id="content" v-html="board.content" style="font-size: larger"></div>
      </div>
      <br>
      <hr>
      <h2>댓글 내용</h2>
      <br>
      <div id="comments">
        <div v-for="comment in comments" :key="comment.commentId">
          <div class="comment">
            <span class="comment-user">{{ comment.userId }} :</span>
<!--            <span class="comment-date">{{ formatDate(comment.commentDate) }}</span>-->
            <span class="comment-content">{{ comment.content }}</span>
            <button @click="deleteComment(comment.commentId)">삭제</button>
            <hr>
          </div>
        </div>
      </div>

      <div id="comment-form">
        <form @submit.prevent="submitComment">
          <br>
          <input type="text" v-model="comment.content" required style="width: 100%; height:100px; border-color: black" placeholder="댓글을 작성하세요">
          <button id="comment_btn" type="submit">댓글 작성</button>
        </form>
      </div>
      <div class="button_wrap">
<!--        <router-link to="/">-->
<!--          <button id="ref_btn" class="btn btn-dark">메인으로 돌아가기</button>-->
<!--        </router-link>-->
        <button id="edit_btn" class="btn btn-dark" @click="displayUpdateForm(board.boardNo)">게시글 수정</button>
        <button id="del_btn" class="btn btn-dark" @click="deleteBoard">게시글 삭제</button>
      </div>
      <div id="updateform" v-if="isUpdateFormVisible">
        <h2 style="color: lightcoral">게시글을 수정하세요.</h2>
        <br>
        <form class="revise_form" @submit.prevent="updateBoard">
          <textarea name="content" v-model="updateForm.content" required style="height: 130px; padding: 10px;"></textarea>
          <div class="revision_btn">
            <input id="revision_btn" type="submit" class="btn btn-danger" value="수정">
            <input type="button" class="btn btn-danger" value="취소" @click="isUpdateFormVisible = false">
          </div>
        </form>
      </div>
    </section>
  </div>
</template>

<script setup>
const updateForm = reactive({
  content: ''
});

import { ref, onMounted, reactive } from 'vue';
import axios from 'axios';
import TopBackground from "@/components/common/TopBackground.vue";
import boardBackground from "@/assets/images/topBackground/Board_bg.jpg";
import { useRouter } from 'vue-router';
import {boardDeleteReq, boardUpdateReq, commentDeleteReq, commentWriteReq} from "@/api/common";


const boardBackgroundURL = boardBackground;
const boardTitleText = "Board";
const board = ref({});
const comments = ref([]);
const isUpdateFormVisible = ref(false);
const router = useRouter();
const boardNo = new URLSearchParams(window.location.search).get('boardNo');
const comment = reactive({ content: '', boardNo });

onMounted(async () => {
  window.scrollTo(0,0);
  const boardNo = new URLSearchParams(window.location.search).get('boardNo');
  const response = await axios.get(`http://localhost:8889/board/content/${boardNo}`);
  board.value = response.data;
  comments.value = board.value.comments;
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

//댓글 작성
const submitComment = async () => {
  const boardNo = Number(new URLSearchParams(window.location.search).get('boardNo'));
  console.log("boardNo:", boardNo);  // 이 부분 추가
  const commentData = {
    boardNo: boardNo,
    content: comment.content,
    postingDate: new Date().toISOString()
  };
  await commentWriteReq(commentData);
  comment.content = '';

  alert('댓글 작성 완료');
  location.reload();
};
//댓글 삭제
const deleteComment = async (commentId) => {
  await commentDeleteReq(commentId);
  comments.value = comments.value.filter(comment => comment.commentId !== commentId);
  alert('댓글 삭제 완료');
};


const displayUpdateForm = (boardNo) => {
  updateForm.content = board.value.content;
  isUpdateFormVisible.value = true;
};

//게시글 삭제
const deleteBoard = async () => {
  const boardNo = new URLSearchParams(window.location.search).get('boardNo');
  await boardDeleteReq(boardNo);
  router.push({ name: 'BoardList', params: { boardId: board.value.boardId } });
  alert('게시글 삭제 완료');
};

//게시글 수정
//게시글 수정
const updateBoard = async () => {
  const boardData = {
    boardNo: board.value.boardNo,
    content: updateForm.content
  };
  await boardUpdateReq(boardData);
  alert('게시글 수정 완료');
  isUpdateFormVisible.value = false;
  board.value.content = updateForm.content;
};

</script>

<style scoped>
@import "@/assets/css/content.css";
.comment span {
  margin-right: 10px;
}

</style>
