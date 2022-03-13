import Vue from 'vue'
import App from './App.vue'
import router from './router'
import axios from "axios";
import VueAxios from "vue-axios";
import store from './store'
import ElementUI from 'element-ui'
import "./assets/css/index.css";
import 'element-ui/lib/theme-chalk/index.css'
import "./assets/css/iconfont.css"
import NProgress from "nprogress";
import "nprogress/nprogress.css";
import dayjs from "dayjs";

import {postRequest} from './utils/api'
import {getRequest} from './utils/api'
import {putRequest} from './utils/api'
import {deleteRequest} from './utils/api'
import { generaMenu } from "./utils/menu";
import {downloadRequest} from "./utils/download";
Vue.config.productionTip = false
Vue.use(ElementUI);
Vue.use(ElementUI,{size: 'small'});
Vue.use(VueAxios, axios);
//插件形式使用请求
Vue.prototype.postRequest = postRequest;
Vue.prototype.getRequest = getRequest;
Vue.prototype.putRequest = putRequest;
Vue.prototype.deleteRequest = deleteRequest;
Vue.prototype.downloadRequest = downloadRequest;

Vue.filter("date", function(value, formatStr = "YYYY-MM-DD") {
  return dayjs(value).format(formatStr);
});

Vue.filter("dateTime", function(value, formatStr = "YYYY-MM-DD HH:mm:ss") {
  return dayjs(value).format(formatStr);
});

NProgress.configure({
  easing: "ease", // 动画方式
  speed: 500, // 递增进度条的速度
  showSpinner: false, // 是否显示加载ico
  trickleSpeed: 200, // 自动递增间隔
  minimum: 0.3 // 初始化时的最小百分比
});

// 路由导航守卫
router.beforeEach((to, from, next) => {
  // 刷新进度条开始
  NProgress.start();
  // if (to.path == '/login') {
  //   next();
  // } else if (!store.state.userId){
  //   next({ path: "/login" });
  // } else {
  //   next();
  // }
  if (window.sessionStorage.getItem('tokenStr')) {
    // 加载用户菜单
    generaMenu();
    if (!window.sessionStorage.getItem('user')) {
      //判断用户信息是否存在
      return getRequest('/api/admin/info').then(resp => {
        if (resp) {
          // 存入用户信息
          window.sessionStorage.setItem('user', JSON.stringify(resp));
          store.commit('INIT_ADMIN',resp);
          next();
        }
      });
    }
    next();
  } else {
    if (to.path == '/login') {
      next()
    } else {
      next({ path: "/login" })
    }
  }
});

router.afterEach(() => {
  // 刷新进度条结束
  NProgress.done();
});

new Vue({
  router,
  store,
  render: function (h) { return h(App) }
}).$mount('#app')
