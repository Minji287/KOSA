import { createRouter, createWebHistory } from "vue-router";

import MainPage from "@/pages/MainPage.vue";
import Mypage from "@/pages/MyPage.vue";
import LoginPage from "@/pages/LoginPage.vue";
import SignupPage from "@/pages/SignupPage.vue";
import Profile from "@/components/mypage/Profile.vue";
import Mypet from "@/components/mypage/Mypet.vue";
import HospitalInfoPage from "@/pages/HospitalInfoPage.vue";
import TrailInfoPage from "@/pages/TrailInfoPage.vue";
import AnimalListPage from "@/pages/AnimalListPage.vue";
import AnimalListDetailPage from "@/pages/AnimalListDetailPage.vue";
import BoardListPage from "@/pages/BoardListPage.vue";
import BoardWritePage from '@/pages/BoardWritePage.vue';
import BoardContentPage from '@/pages/BoardContentPage.vue';


const index = createRouter({
  history: createWebHistory(),
  routes: [
    { path: "/", component: MainPage },
    { path: "/login", component: LoginPage },
    { path: "/signup", component: SignupPage },
    { path: "/hospinfo", component: HospitalInfoPage },
    { path: "/trailinfo", component: TrailInfoPage },

    { path: "/mypage", component: Mypage },
    { path: "/myprofile", component: Profile },
    { path: "/mypet", component: Mypet },
    { path: "/mywriting", component: Mypage },
    { path: "/myshare", component: Mypage },
    { path: "/myprofile/:kind", name: 'Edit', component: Profile },
    { path: "/mypet/:kind", component: Mypet },
    { path: "/animalList", component: AnimalListPage },
    { path: "/animals/:id", component: AnimalListDetailPage, props: true },
    { path: "/board/list/:boardId", name: 'BoardList', component: BoardListPage, props: route => ({ boardId: Number(route.params.boardId) }) },
    { path: "/write/:boardId", name: 'BoardWrite', component: BoardWritePage, props: route => ({ boardId: Number(route.params.boardId) }) },
    { path: '/board/content', name: 'BoardContentPage', component: BoardContentPage },
  ],
});

export default index;
