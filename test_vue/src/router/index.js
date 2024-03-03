import { createRouter, createWebHistory } from "vue-router";
import SignUp from "@/components/SignUp.vue";
import SignIn from "@/components/SignIn.vue";

const routes = [
  { path: "/signin", component: SignIn },
  { path: "/signup", component: SignUp },

  // 다른 라우트들도 필요하다면 여기에 추가
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
