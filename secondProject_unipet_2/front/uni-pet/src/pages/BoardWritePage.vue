<template>
  <div>
    <TopBackground
        :imageURL="boardBackgroundURL"
        :titleText="boardTitleText"
    ></TopBackground>
    <section class="main_container">
      <div class="main_wrap">
        <form @submit.prevent="submitForm">
          <input type="hidden" v-model="board.boardId">
<!--          <p>😍{{ userId }}</p><br>-->
          <input class="title" type="text" placeholder="제목을 입력해 주세요." v-model="board.title" style="width: 30%" required>
          <div class="form-floating">
            <textarea v-model="board.content"></textarea>
<!--            <div ref="editor"></div>-->
            <br>
            <button type="submit" class="btn btn-dark btn-lg">글작성</button>
          </div>
        </form>
      </div>
    </section>
  </div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue';
import axios from 'axios';
import TopBackground from "@/components/common/TopBackground.vue";
import { useRoute } from 'vue-router';  // import useRoute
import boardBackground from "../assets/images/topBackground/Board_bg.jpg";
// import ClassicEditor from '@ckeditor/ckeditor5-build-classic';
import {boardWriteReq} from "@/api/common";
import router from "@/router";

const route = useRoute();  // 라우트 객체를 가져옵니다.
const boardBackgroundURL = boardBackground;
const boardTitleText = "Board";

let board = ref({
  boardNo: '',
  boardId: '',
  title: '',
  content: '',
  postingDate: '',
  views: '',
  likeCounting: '',
  imgPath: ''
});
let userId = ref('');  // 사용자 ID를 저장할 변수.
let editorInstance = null;
let editor = ref(null);

onMounted(async () => {
  window.scrollTo(0,0);
  board.value.boardId = Number(route.params.boardId);
  editorInstance = await ClassicEditor.create(editor.value);
  editorInstance.model.document.on('change:data', () => {
    board.value.content = editorInstance.getData();
  });


});

onBeforeUnmount(() => {
  if (editorInstance) {
    editorInstance.destroy();
    editorInstance = null;
  }
});

//게시글 작성
const submitForm = async () => {
  board.value.postingDate = new Date().toISOString(); // 현재 시간 설정
  await boardWriteReq(board.value);
  router.push({ name: 'BoardList', params: { boardId: board.value.boardId } });
  alert("글작성 완료")
}
</script>

<style scoped>
@import '@/assets/css/posting.css';
.main_wrap {
  width: 75%;
}
textarea {
  border: 1px solid gainsboro;
  width: 100%;
  height: 400px;
}
</style>
