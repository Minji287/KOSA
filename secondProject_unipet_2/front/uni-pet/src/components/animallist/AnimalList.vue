<template>
  <div>
    <!-- 드롭다운 필터 -->
    <select class="drop" v-model="selectedFilter">
      <option value="">모두 보기</option>
      <option value="[개]">강아지</option>
      <option value="[고양이]">고양이</option>
      <option value="others">기타</option>
    </select>

    <!-- 동물 카드 목록 -->
    <div id="card_container" class="card-deck">
      <div v-for="animal in animals" :key="animal.imageId" class="card" style="width: 18rem;">
        <a :href="`/animals/${animal.imageId}`">
          <img :src="animal.popfile" class="card-img-top" alt="Animal Image">
        </a>
        <div class="card-body">
          <h5 class="card-title">{{ animal.noticeNo }}</h5>
          <p class="card-text">{{ animal.kindCd }}</p>
        </div>
        <ul id="list_group_animals" class="list-group list-group-flush">
          <li class="list-group-item">{{ animal.processState }}</li>
          <li class="list-group-item">{{ animal.careNm }}</li>
          <li class="list-group-item">{{ animal.happenPlace }}</li>
          <li class="list-group-item">{{ animal.specialMark }}</li>
        </ul>
      </div>
    </div>

    <!-- 페이지네이션 컴포넌트 -->
    <vuejs-paginate-next class="pagination"
        :key="componentKey"
        :page-count="pageCount"
        :page-range="3"
        :margin-pages="2"
        :click-handler="changePage"
        :prev-text="'prev'"
        :next-text="'next'"
        :container-class="'pagination'"
        :page-class="'page-item'">
    </vuejs-paginate-next>
  </div>
</template>

<script>
import axios from 'axios';
import VuejsPaginateNext from 'vuejs-paginate-next';

export default {
  components: {
    VuejsPaginateNext,
  },
  data() {
    return {
      animals: [],
      selectedFilter: '',
      currentPage: 1,
      pageSize: 16,
      totalPages: 0,
      selectPage: 1,
      pageCount: 0,
      componentKey: 0,
    };
  },
  mounted() {
    this.fetchAnimals(this.currentPage);
  },
  watch: {
    selectedFilter(newValue, oldValue) {
      if(newValue !== oldValue) {
        this.currentPage = 1;
        this.componentKey++;
        this.fetchAnimals(this.currentPage);
      }
    }
  },
  methods: {
    fetchAnimals(page) {
      let filter = encodeURIComponent(this.selectedFilter);
      if (filter === 'others') {
        filter = 'others';
      } else if (!filter) {
        filter = '';
      }

      axios.get(`http://localhost:8889/animals/list?page=${page - 1}&size=${this.pageSize}&kind=${filter}`)
          .then(response => {
            this.animals = response.data.content;
            this.totalPages = response.data.totalPages;
            this.pageCount = response.data.totalPages;
            this.currentPage = page;
          })
          .catch(error => console.error('Error fetching the animal list:', error));
    },
    changePage(page) {
      this.fetchAnimals(page);
    }
  }
};
</script>


<style scoped>
@import 'bootstrap/dist/css/bootstrap.min.css';
@import "./css/animalList.css";
</style>

<style lang="css">
.pagination {
  margin: 24px;
  justify-content: center;
  display: flex;
  flex-wrap: wrap;
}
.pagination li {
  min-width: 32px;
  padding: 2px 6px;
  text-align: center;
  margin: 0 3px;
  border-radius: 6px;
  border: 1px solid #eee;
  color: #666;
}
.pagination li:hover {
  background: #E4DBD6;
}
.page-item a {
  color: #666;
  text-decoration: none;
}
.pagination li.active {
  background-color: #E7AA8D;
  color: #fff;
}
.pagination li.active a {
  color: #fff;
}

</style>
