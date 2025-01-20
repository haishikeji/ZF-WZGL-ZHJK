<!-- 应用管理 -->
<template>
  <info-card>
    <template #header>应用管理</template>
    <container-table
      :tableData="load.tableData"
      :tableConfig="tableConfig"
      :loading="load.loading"
      :total="load.total"
      :showIndex="false"
      :operateWidth="230"
      :pageSize="load.pageSize"
      :currentPage="load.currentPage"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    >
      <!-- 搜索区域 -->
      <template #searchArea>
        <el-button
          type="primary"
          :icon="Search"
          :loading="load.loading"
          @click="searchList"
          >查询</el-button
        >
        <!--  <el-button @click="resetData" :loading="load.loading" :icon="RefreshLeft">重置</el-button> -->
        <el-button
          type="primary"
          :loading="load.loading"
          @click="manageData(0)"
          :icon="Plus"
          >新建应用</el-button
        >
      </template>
      <template #item_appType="scope">
        {{ commMethods.getDicName("app_type", scope.row.appType) }}
      </template>
      <template #item_appStatus="scope">
        <Tag state="success" v-if="scope.row.appStatus == 1" text="启用"></Tag>
        <Tag state="warning" v-else text="禁用"></Tag>
        <!-- {{commMethods.getDicName('examineStatus',scope.row.examineStatus)}} -->
      </template>
      <template #operateContent="scope">
        <el-button
          type="primary"
          size="small"
          plain
          :icon="EditPen"
          @click="manageData(1, scope.row.appId)"
          >编辑
        </el-button>
        <el-button
          type="success"
          v-if="scope.row.appStatus == 0"
          size="small"
          plain
          :icon="SuccessFilled"
          @click="handleOpen(true, scope.row.appId)"
          >启用
        </el-button>
        <el-button
          type="warning"
          v-else
          size="small"
          plain
          :icon="CircleCloseFilled"
          @click="handleOpen(false, scope.row.appId)"
          >禁用</el-button
        >
        <el-button
          type="danger"
          size="small"
          plain
          :icon="DeleteFilled"
          @click="delData(scope.row.appId)"
          >删除
        </el-button>
      </template>
    </container-table>
  </info-card>
  <ManageApplications
    v-model:visible="editDialog"
    :id="searchData.id"
    @refresh="refreshList"
  ></ManageApplications>
</template>
<script lang="ts" setup>
import InfoCard from "../../../components/InfoCard/InfoCard.vue";
import ManageApplications from "./components/ManageApplications.vue";
import Tag from "../../../components/Tag/Tag.vue";
import ContainerTable from "../../../components/ContainerTable/ContainerTable.vue";
import SelectOption from "../../../components/SelectOption/SelectOption.vue";
import { ElMessage, ElMessageBox } from "element-plus";
import {
  DeleteFilled,
  SuccessFilled,
  CircleCloseFilled,
  EditPen,
  Search,
  RefreshLeft,
  Plus,
} from "@element-plus/icons-vue";
import commMethods from "../../../assets/js/common";
import { onMounted, reactive, ref } from "vue";
import { appApi } from "../../../utils/Api";

const load = reactive(commMethods.loadData()); //加载所需数据
const editDialog = ref(false);
const searchData: any = reactive({
  id: "",
});
const tableConfig = reactive([
  {
    label: "应用类型",
    prop: "appType",
    minWidth: 180,
    align: "left",
    slot: true,
  },
  { label: "应用名称", prop: "appName", minWidth: 180 },
  { label: "应用链接", prop: "appUrl", minWidth: 180 },
  { label: "应用描述", prop: "appDescription", minWidth: 180 },
  { label: "应用状态", prop: "appStatus", width: 80, slot: true },
]);
onMounted(() => {
  initData();
  searchList();
});
const initData = () => {
  Object.assign(searchData, {
    id: "",
  });
};
// 刷新列表
const refreshList = () => {
  //编辑时，刷新当前页面
  if (searchData.id) {
    load.loading = true;
    getDataList();
  } else {
    searchList();
  }
};
// 搜索列表
const searchList = () => {
  Object.assign(load, commMethods.loadData());
  getDataList();
};
// 获取列表数据
const getDataList = () => {
  appApi
    .findAll({
      pageSize: load.pageSize,
      pageNum: load.currentPage,
    })
    .then((res) => {
      load.tableData = res.data.list;
      load.total = res.data.total;
    })
    .finally(() => {
      load.loading = false;
    });
};
const manageData = (state: string | number, id?: string) => {
  searchData.id = id;
  editDialog.value = true;
};
// 启用停用
const handleOpen = (state: boolean, id: string) => {
  searchData.id = id;
  commMethods
    .getMessageBox(`确定要${state ? "启用" : "禁用"}当前应用?`)
    .then(() => {
      load.loading = true;
      appApi
        .saveOrUpdate("update", {
          appId: id,
          appStatus: state ? "1" : "0",
        })
        .then((res) => {
          ElMessage({
            type: "success",
            message: `${state ? "启用" : "禁用"}成功`,
          });
          refreshList();
        })
        .finally(() => {
          load.loading = false;
        });
    });
};
// 删除
const delData = (id: string) => {
  searchData.id = id;
  commMethods.getMessageBox(`确定要删除当前应用?`).then(() => {
    load.loading = true;
    appApi
      .logicDeleteByIds(id)
      .then((res) => {
        ElMessage({
          type: "success",
          message: "删除成功",
        });
        refreshList();
      })
      .finally(() => {
        load.loading = false;
      });
  });
};
// 重置数据
const resetData = () => {
  initData();
  searchList();
};
// 分页跳转
const handleSizeChange = (val: number) => {
  Object.assign(load, commMethods.loadData(1, val));
  getDataList();
  console.log("pageSize", val);
};
const handleCurrentChange = (val: number) => {
  load.currentPage = val;
  getDataList();
  console.log("currentPage", val);
};
</script>
<style scoped lang="scss"></style>
