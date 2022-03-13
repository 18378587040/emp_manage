import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    currentUser: JSON.parse(window.sessionStorage.getItem('user')),
    name: "",
    roleList: [],
    phone: null,
    email: null,
    address: null,
    userId: null,
    avatar: null,
    collapse: false,
    tabList: [{ name: "首页", path: "/" }],
    admins:[],
    userMenuList: [],
    positions: [],
    jobtitles: [],
    departs: [],
  },
  mutations: {
    saveTab(state, tab) {
      if (state.tabList.findIndex(item => item.path === tab.path) == -1) {
        state.tabList.push({ name: tab.name, path: tab.path });
      }
    },
    removeTab(state, tab) {
      var index = state.tabList.findIndex(item => item.name === tab.name);
      state.tabList.splice(index, 1);
    },
    resetTab(state) {
      state.tabList = [{ name: "首页", path: "/" }];
    },
    trigger(state) {
      state.collapse = !state.collapse;
    },
    saveUserMenuList(state, userMenuList) {
      state.userMenuList = userMenuList;
    },
    updateUserInfo(state, data) {
      state.userId = data.id;
      state.name = data.name;
      state.phone = data.phone;
      state.email = data.email;
      state.address = data.address;
    },
    INIT_ADMIN(state,data) {
      state.admins = data;
      state.roleList = data.roles;
      state.avatar = data.userFace;
      state.userId = data.id;
      state.name = data.name;
      state.phone = data.phone;
      state.email = data.email;
      state.address = data.address;
    },
    logout(state) {
      state.userId = null;
      state.roleList = [];
      state.avatar = null;
      state.name = null;
      state.phone = null;
      state.email = null;
      state.address = null;
      state.userMenuList = [];
    },
    updateAvatar(state, avatar) {
      state.avatar = avatar;
    },
    updatePosition(state, positions){
      state.positions = positions;
    },
    updateJobtitle(state, jobtitles){
      state.jobtitles = jobtitles;
    },
    updateDepart(state, departs){
      state.departs = departs;
    }
  },
  actions: {
  },
  modules: {
  },
  plugins: [
    createPersistedState({
      storage: window.sessionStorage
    })
  ]
})
