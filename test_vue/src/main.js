import { createApp } from "vue";
import App from "./App.vue";
import ElementPlus from "element-plus";
import "element-plus/dist/index.css";
import router from "./router";

const app = createApp(App);
app.use(ElementPlus); // Element Plus를 플러그인으로 사용
app.use(router);
app.mount("#app");
