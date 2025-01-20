<template>
  <!--  <div id="nav">-->
  <!--&lt;!&ndash;    <router-link to="/">Home</router-link> |&ndash;&gt;-->
  <!--&lt;!&ndash;    <router-link to="/about">About</router-link>&ndash;&gt;-->
  <!--  </div>-->
  <!--  <router-view/>-->
  <el-container v-if="$route.path != '/'">
    <el-header v-if="!$store.state.hideTop">
      <header-layout :list="$store.state.menuList"></header-layout>
    </el-header>
    <el-container>
      <el-aside
        v-if="['/', '/index', '/datascreen'].indexOf($route.path) == -1"
      >
        <aside-layout :list="$store.state.sideMenuList"></aside-layout>
      </el-aside>
      <el-main
        :style="{
          height: !$store.state.hideTop ? 'calc(100vh - 60px)' : '100vh',
        }"
      >
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
  <router-view v-else></router-view>
</template>
<script lang="ts" setup>
import { nextTick, onMounted, reactive, ref, watch } from "vue";
import HeaderLayout from "./views/Layout/HeaderLayout/HeaderLayout.vue";
import AsideLayout from "./views/Layout/AsideLayout/AsideLayout.vue";
import InfoCard from "./components/InfoCard/InfoCard.vue";
import ContainerTable from "./components/ContainerTable/ContainerTable.vue";
import commMethods from "./assets/js/common";
import Dialog from "./components/Dialog/Dialog.vue";
import { useRoute } from "vue-router";
import { useStore } from "vuex";
import { dicApi, menuApi } from "./utils/Api";
const $route = useRoute();
const $store = useStore();
const sideMenuList = ref([]);
watch(
  () => [$route.path, $store.state.menuList],
  (newval) => {
    console.log("path", newval, $store.state.menuList);
    for (let i in $store.state.menuList) {
      if ($route.path.indexOf($store.state.menuList[i].menuAction) > -1) {
        $store.state.sideMenuList = $store.state.menuList[i].children;
        break;
      }
    }
  }
);
onMounted(() => {
  if (commMethods.getStorage("loginInfo")) {
    getAllDic();
    getMenuList();
  }
});
const getAllDic = () => {
  dicApi
    .findAll({})
    .then((res) => {
      commMethods.setStorage("dicList", res.data.list);
    })
    .catch((err) => {});
};
const getMenuList = () => {
  menuApi.menuRecurrenceUser().then((res) => {
    console.log("当前用户菜单", res);
    $store.state.menuList = res.data.children;
  });
};
</script>
<style lang="scss">
.el-sub-menu__title:hover {
  background: #1683e6 !important;
  color: white !important;
}
.el-sub-menu__title {
  background: #f9f9f9;
  border-top: 1px solid #f1f1f1;
  border-bottom: 1px solid #f1f1f1;
}
</style>
<style scoped lang="scss">
.el-header {
  box-shadow: 0px 4px 25px rgba(0, 0, 0, 0.05);
  background: #1891ff;
  border-bottom: 1px solid #ddd;
}
#nav {
  overflow: hidden;
  width: 100vw;
  height: 100vh;
}
.el-aside {
  height: calc(100vh - 60px);
  overflow-x: hidden;
  overflow-y: scroll;
  width: fit-content;
  &::-webkit-scrollbar {
    display: none;
  }
  box-shadow: 10px 10px 10px #ebebeb;
  background: white;
}
.el-main {
  min-width: 1280px;

  overflow: scroll;
  &::-webkit-scrollbar {
    display: none;
  }
  background: #f0f2f5;
}
::v-deep(.info-dialog) {
  .el-dialog__header {
    background: #e8e8e8 !important;
    margin-right: 0 !important;
    padding-bottom: 18px !important;
    border-bottom: 1px solid #cbcbcb !important;
    display: flex;
    align-items: center;
    .dialog-title {
      line-height: 20px;
      font-size: 16px;
    }
  }
  .el-dialog__body {
    padding-bottom: 10px;
    border-bottom: 1px solid #e1e1e1 !important;
  }
  .el-dialog__footer {
    padding-top: 20px;
  }
  border-radius: 5px;
  overflow: hidden;
}
::v-deep(.drawermodelclass) {
  background-color: rgba(0, 0, 0, 0) !important;
}
::v-deep(.mapchart) {
  .el-card__body {
    height: calc(100% - 40px) !important;
  }
}
::v-deep(.data-screen) {
  .el-input {
    border: none !important;
    .el-input__wrapper {
      background: rgba(0, 0, 0, 0) !important;
      border: none !important;
      box-shadow: none;
    }
    .el-input__inner {
      color: white;
      text-align: center;
    }
    .el-icon {
      opacity: 0;
      width: 0;
    }
  }
}
</style>
