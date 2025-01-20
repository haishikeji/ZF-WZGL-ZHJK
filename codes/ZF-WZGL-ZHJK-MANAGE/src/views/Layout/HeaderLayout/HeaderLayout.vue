<template>
  <div class="hl flex-n fac">
    <!-- <div class="icon"></div> -->
    <div class="title">智慧疾控物资管理</div>
    <el-scrollbar height="60px" class="flex-1">
      <div class="flex-n fac menu-btn">
        <div
          class="flex-n fac menu-btn-item"
          v-for="(item, index) in list"
          :key="index"
          :class="$route.path.indexOf(item.menuAction) == 0 ? 'choose' : ''"
          @click="choosePage(item.menuAction)"
        >
          <el-icon
            ><component :is="dictIcon[item.menuIconUrl]"></component
          ></el-icon>
          <div class="ml-10">{{ item.menuName }}</div>
        </div>
        <!-- <div class="flex-n fac menu-btn-item" :class="$route.path.indexOf('index')==1?'choose':''" @click="choosePage('index')"><el-icon><HomeFilled /></el-icon><div class="ml-10">首页</div></div> -->
        <!-- <div class="flex-n fac menu-btn-item" :class="$route.path.indexOf('antiepidemicinfor')==1?'choose':''" @click="choosePage('antiepidemicinfor')"><el-icon><HomeFilled /></el-icon><div class="ml-10">防疫信息</div></div>
        <div class="flex-n fac menu-btn-item" :class="$route.path.indexOf('antiepidemictesting')==1?'choose':''" @click="choosePage('antiepidemictesting')"><el-icon><HomeFilled /></el-icon><div class="ml-10">防疫检测</div></div>
        <div class="flex-n fac menu-btn-item" :class="$route.path.indexOf('accessmonitoring')==1?'choose':''" @click="choosePage('accessmonitoring')"><el-icon><HomeFilled /></el-icon><div class="ml-10">进出监控</div></div>
        <div class="flex-n fac menu-btn-item" :class="$route.path.indexOf('system')==1?'choose':''" @click="choosePage('system')"><el-icon><HomeFilled /></el-icon><div class="ml-10">系统管理</div></div>
        <div class="flex-n fac menu-btn-item" :class="$route.path.indexOf('reportform')==1?'choose':''" @click="choosePage('reportform')"><el-icon><HomeFilled /></el-icon><div class="ml-10">报表管理</div></div>
        <div class="flex-n fac menu-btn-item" :class="$route.path.indexOf('case')==1?'choose':''" @click="choosePage('case')"><el-icon><HomeFilled /></el-icon><div class="ml-10">个案管理</div></div>
        <div class="flex-n fac menu-btn-item" :class="$route.path.indexOf('datascreen')==1?'choose':''" @click="choosePage('datascreen')"><el-icon><HomeFilled /></el-icon><div class="ml-10">数据大屏</div></div>
     -->
      </div>
    </el-scrollbar>
    <!--    <select-option></select-option>-->
    <!--    <el-badge :value="1" class="ml-10">-->
    <!--      <el-icon :size="24"><Bell /></el-icon>-->
    <!--    </el-badge>-->
    <el-dropdown @command="handleCommand">
      <div class="flex-n fac" style="cursor: pointer">
        <img />
        <!--        {{ commMethods.getStorage('userInfo').userName||''}}-->
        <div class="ml-20" style="color: rgba(255, 255, 255, 0.65)">
          {{ commMethods.getStorage("userInfo").userName || "未命名" }}
        </div>
      </div>
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item command="reset" :icon="Edit"
            >修改密码</el-dropdown-item
          >
          <el-dropdown-item command="exit" :icon="SwitchButton" @click="exit"
            >退出登录</el-dropdown-item
          >
        </el-dropdown-menu>
      </template>
    </el-dropdown>
  </div>
  <ResetPassword v-model:visible="showDialog"></ResetPassword>
</template>

<script setup lang="ts">
import {
  HomeFilled,
  Monitor,
  Setting,
  FolderOpened,
  Edit,
  SwitchButton,
  Suitcase,
  DocumentChecked,
  DataAnalysis,
  DataLine,
} from "@element-plus/icons-vue";
import commMethods from "../../../assets/js/common";
import ResetPassword from "./component/ResetPassword.vue";
import { ref, reactive } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
const $store = useStore();
const dictIcon = reactive({
  Monitor: Monitor,
  Setting: Setting,
  Suitcase: Suitcase,
  DocumentChecked: DocumentChecked,
  DataAnalysis: DataAnalysis,
  DataLine: DataLine,
  FolderOpened: FolderOpened,
  HomeFilled: HomeFilled,
});
const router = useRouter();
const showDialog = ref<boolean>(false);
interface Props {
  list?: any;
}

const props = withDefaults(defineProps<Props>(), {
  list: [],
});
const exit = () => {
  localStorage.clear();
  router.replace("/");
};
const handleCommand = (command: string | number | object) => {
  if (command == "reset") {
    showDialog.value = true;
  }
};
const choosePage = (page: string) => {
  router.push(page);
};
</script>

<style scoped lang="scss">
.hl {
  height: 100%;
  color: white;
  .icon {
    width: 40px;
    height: 40px;
    background: #f1f1f1;
    margin-right: 10px;
  }
  .title {
    font-weight: 500;
    font-size: 18px;
    white-space: nowrap;
  }
  .menu-btn {
    height: 60px;
    margin: 0 20px;
    cursor: pointer;
    .menu-btn-item {
      margin: 0 2px;
      border: 1px solid transparent;
      padding: 10px 20px;
      border-radius: 30px;
      white-space: nowrap;
      &.choose {
        border: 1px solid #f1f1f133;
        background: #1683e6;
      }
      &:hover {
        border: 1px solid #f1f1f133;
        background: #2dacd740;
      }
    }
  }
}
</style>
