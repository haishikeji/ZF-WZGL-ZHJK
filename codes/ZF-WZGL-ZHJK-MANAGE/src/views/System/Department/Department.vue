<!-- 部门管理 -->
<template>
  <info-card>
    <template #header>部门管理</template>
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
        <el-input
          class="w-200"
          v-model="searchData.departName"
          clearable
          placeholder="部门名称"
        />
        <org-list
          class="w-200"
          v-model="searchData.orgId"
          placeholder="所属机构"
        ></org-list>
        <SelectOption
          class="w-200"
          v-model="searchData.departStatus"
          :list="data.departStatusList"
          placeholder="部门状态"
        ></SelectOption>

        <el-button
          type="primary"
          :icon="Search"
          :loading="load.loading"
          @click="searchList"
          >查询</el-button
        >
        <el-button
          @click="resetData"
          :loading="load.loading"
          :icon="RefreshLeft"
          >重置</el-button
        >
        <el-button
          type="primary"
          :loading="load.loading"
          @click="manageData(0)"
          :icon="Plus"
          >新建部门</el-button
        >
      </template>
      <template #item_departStatus="scope">
        <Tag
          state="success"
          v-if="scope.row.departStatus == 1"
          text="启用"
        ></Tag>
        <Tag state="warning" v-else text="禁用"></Tag>
        <!-- {{commMethods.getDicName('examineStatus',scope.row.examineStatus)}} -->
      </template>
      <template #operateContent="scope">
        <el-button
          type="primary"
          size="small"
          plain
          :icon="EditPen"
          @click="manageData(1, scope.row.departId)"
          >编辑
        </el-button>
        <el-button
          type="success"
          size="small"
          plain
          v-if="scope.row.departStatus == 0"
          :icon="SuccessFilled"
          @click="handleOpen(true, scope.row.departId)"
          >启用
        </el-button>
        <el-button
          type="warning"
          size="small"
          plain
          v-else
          :icon="CircleCloseFilled"
          @click="handleOpen(false, scope.row.departId)"
          >禁用
        </el-button>
        <el-button
          type="danger"
          size="small"
          plain
          :icon="DeleteFilled"
          @click="delData(scope.row.departId)"
          >删除
        </el-button>
      </template>
    </container-table>
  </info-card>
  <ManageDepartment
    v-model:visible="editDialog"
    :id="searchData.id"
    @refresh="refreshList"
  ></ManageDepartment>
</template>
<script lang="ts" setup>
import InfoCard from "../../../components/InfoCard/InfoCard.vue";
import ManageDepartment from "./components/ManageDepartment.vue";
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
import { departmentApi } from "../../../utils/Api";
import { onMounted, reactive, ref } from "vue";
import OrgList from "../../common/components/OrgList.vue";

const load = reactive(commMethods.loadData()); //加载所需数据
const editDialog = ref(false);
const data: any = reactive({
  departStatusList: [],
});
const searchData: any = reactive({
  id: "",
  departName: "",
  orgId: "",
  departStatus: "",
});
const tableConfig = reactive([
  { label: "部门名称", prop: "departName", minWidth: 160, align: "left" },
  { label: "所属机构", prop: "orgName", minWidth: 160 },
  { label: "职能描述", prop: "departFunction", minWidth: 240 },
  { label: "部门状态", prop: "departStatus", width: 80, slot: true },
]);
onMounted(() => {
  initData();
  getDicList();
  searchList();
});
const initData = () => {
  Object.assign(searchData, {
    id: "",
    departName: "",
    orgId: "",
    departStatus: "",
  });
};
// 获取字典
const getDicList = () => {
  data.departStatusList =
    commMethods.getDicByNameOrTypeList("depart_status") || [];
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
  departmentApi
    .findAll({
      departName: searchData.departName,
      orgId: searchData.orgId,
      departStatus: searchData.departStatus,
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
  commMethods
    .getMessageBox(`确定要${state ? "启用" : "禁用"}当前部门?`)
    .then(() => {
      load.loading = true;
      departmentApi
        .saveOrUpdate("update", {
          departStatus: state ? "1" : "0",
          departId: id,
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
  commMethods.getMessageBox(`确定要删除当前部门?`).then(() => {
    load.loading = true;
    departmentApi
      .deleteByIds(id)
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
