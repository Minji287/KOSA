import "./assets/css/global.css";

import { createApp } from "vue";
import App from "./App.vue";
import index from "./router";
import { createPinia } from "pinia";

createApp(App).use(index).use(createPinia()).mount("#app");
