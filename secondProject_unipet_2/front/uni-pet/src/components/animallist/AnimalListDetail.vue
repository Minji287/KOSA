<template>
  <div v-if="animal" class="all">
    <main>
      <div class="pet-info">
        <img :src="animal.popfile" alt="Animal Image" width="600" height="400">
      </div>
      <div class="pet-details">
        <h2>{{ animal.kindCd }}</h2>
        <table class="table">
          <thead>
          <tr>
            <th class="theader" scope="row">동물번호</th>
            <th class="tbot">{{ animal.desertionNo }}</th>
          </tr>
          </thead>
          <tbody>
          <tr>
            <th class="theader" scope="row">관리번호</th>
            <th class="tbot">{{ animal.noticeNo }}</th>
          </tr>
          <tr>
            <th class="theader" scope="row">입소날짜</th>
            <th class="tbot">{{ animal.happenDt }}</th>
          </tr>
          </tbody>
          </table>

        <table class="table">
          <thead>
          <tr>
        <th class="theader" scope="row">색상</th>
            <th class="tbot">{{ animal.colorCd }}</th>
            <th class="theader" scope="row">성별</th>
            <th class="tbot">{{ animal.sexCd }}</th>
          </tr>
          </thead>
          <tbody>
          <tr>
            <th class="theader" scope="row">나이</th>
            <td>{{ animal.age }}</td>

            <th class="theader" scope="row">체중</th>
            <td>{{ animal.weight }}</td>
          </tr>
          <tr>
            <th class="theader" scope="row">중성화 여부</th>
            <td>{{ animal.neuterYn }}</td>

            <th class="theader" scope="row">보호 기간</th>
            <td>{{ animal.noticeSdt }} - {{ animal.noticeEdt }}</td>
          </tr>
          </tbody>
        </table>
      </div>
    </main>

    <section>
      <div class="pet-details1">
        <table class="table">
          <thead>
            <th class="theader" scope="col">발견장소</th>
          </thead>
          <tbody>
        <th class="tbot" scope="row">{{ animal.happenPlace }}</th>
          </tbody>
        </table>
      </div>

      <div class="pet-details1">
        <table class="table">
          <thead>
          <th class="theader" scope="col">특징</th>
          </thead>
          <tbody>
          <th class="tbot" scope="row">{{ animal.specialMark }}</th>
          </tbody>
        </table>
      </div>

      <div class="pet-details1">
        <table class="table">
          <thead>
          <tr>
            <th class="theader" scope="col">보호센터명</th>
            <th scope="col">{{ animal.careNm }}</th>
            <th class="theader" scope="col">보호소 전화번호</th>
            <th scope="col">{{ animal.careTel }}</th>
          </tr>
          </thead>
          <tbody>
          <tr>
            <th class="theader" scope="row">보호장소</th>
            <td>{{ animal.careAddr }}</td>
            <td class="theader">유기장소</td>
            <td>{{ animal.happenPlace }}</td>
          </tr>
          </tbody>
        </table>
      </div>

      <div id="map"></div>
    </section>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      animal: null
    };
  },
  mounted() {
    this.fetchAnimalDetails();
    this.initializeMap();
  },
  methods: {
    fetchAnimalDetails() {
      const animalId = this.$route.params.id;
      axios.get(`http://localhost:8889/animals/${animalId}`)
          .then(response => {
            this.animal = response.data;
          })
          .catch(error => {
            console.error('Error fetching animal details:', error);
          });
    },
    initializeMap() {
      const script = document.createElement('script');
      script.onload = () => this.setupMap();
      script.src = 'https://dapi.kakao.com/v2/maps/sdk.js?appkey=6ed169c7c2bb7b7aa98b850d7db3d8f8&autoload=false&libraries=services';
      document.head.appendChild(script);
    },
    setupMap() {
      kakao.maps.load(() => {
        const mapContainer = document.getElementById('map');
        const mapOption = {
          center: new kakao.maps.LatLng(33.450701, 126.570667),
          level: 3
        };
        const map = new kakao.maps.Map(mapContainer, mapOption);

        const geocoder = new kakao.maps.services.Geocoder();
        geocoder.addressSearch(this.animal.careAddr, (result, status) => {
          if (status === kakao.maps.services.Status.OK) {
            const coords = new kakao.maps.LatLng(result[0].y, result[0].x);
            const marker = new kakao.maps.Marker({
              map: map,
              position: coords
            });
            const infowindow = new kakao.maps.InfoWindow({
              content: '<div style="width:150px;text-align:center;padding:6px 0;">보호소 위치</div>'
            });
            infowindow.open(map, marker);
            map.setCenter(coords);

          }
        });
      });
    }
  }
};
</script>

<style scoped>
@import 'bootstrap/dist/css/bootstrap.min.css';
@import "./css/animalDetail.css";
</style>
