import Vue from 'vue'
import VueRouter from 'vue-router'

// 解决路由跳转问题
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location, onResolve, onReject) {
    if (onResolve || onReject) return originalPush.call(this, location, onResolve, onReject)
    return originalPush.call(this, location).catch(err => err)
}

Vue.use(VueRouter)

const routes = [
  {
    path: '/login',
    name: '登录',
    hidden: true,
    component: () => import("../views/login/Login.vue")
  },
  // {
  //   path: '/home',
  //   name: '导航一',
  //   component: () => import("../views/Home.vue")
  // }
];

const createRouter = () =>
    new VueRouter({
      mode: "history",
      routes: routes
    });

const router = createRouter();

export function resetRouter() {
  const newRouter = createRouter();
  router.matcher = newRouter.matcher;
}

// const router = new VueRouter({
//   mode: 'history',
//   base: process.env.BASE_URL,
//   routes
// })

export default router
