<template>
  <div>
    <!-- v-for 디렉티브를 사용해서 게시글 목록을 렌더링 -->
    <div v-for="board in boards" :key="board.id">
      <!-- 게시글의 내용을 렌더링 -->
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';  // Composition API 함수를 import
import axios from 'axios';

let props = defineProps(['boardId']); // boardId를 props로 받습니다.

const boards = ref([]);  // 반응형 데이터 소스를 생성

// onMounted 훅은 컴포넌트가 마운트되었을 때 호출되는 함수
onMounted(async () => {
  const response = await axios.get('/board/list/' + props.boardId);
  boards.value = response.data;
});
</script>

<style scoped>

</style>
