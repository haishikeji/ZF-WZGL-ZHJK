<!-- 检测类型 -->
<template>
  <info-card>
    <template #header>检测类型</template>
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
          v-model="searchData.checkTypeName"
          clearable
          placeholder="检测类型名称"
        />
        <SelectOption
          :list="data.checkTypeList"
          class="w-200"
          v-model="searchData.checkTypeStatus"
          placeholder="检测类型状态"
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
          >新建类型</el-button
        >
      </template>
      <template #item_checkTypeStatus="scope">
        <Tag
          state="success"
          v-if="scope.row.checkTypeStatus == 1"
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
          @click="manageData(1, scope.row.checkTypeId)"
          >编辑
        </el-button>
        <el-button
          type="success"
          size="small"
          v-if="scope.row.checkTypeStatus == 0"
          plain
          :icon="SuccessFilled"
          @click="handleOpen(true, scope.row.checkTypeId)"
          >启用
        </el-button>
        <el-button
          type="warning"
          size="small"
          v-else
          plain
          :icon="CircleCloseFilled"
          @click="handleOpen(false, scope.row.checkTypeId)"
          >禁用</el-button
        >
        <el-button
          type="danger"
          size="small"
          plain
          :icon="DeleteFilled"
          @click="delData(scope.row.checkTypeId)"
          >删除
        </el-button>
      </template>
    </container-table>
  </info-card>
  <ManageTestType
    v-model:visible="editDialog"
    :id="searchData.id"
    @refresh="refreshList"
  ></ManageTestType>
</template>
<script lang="ts" setup>
import InfoCard from "../../../components/InfoCard/InfoCard.vue";
import ManageTestType from "./components/ManageTestType.vue";
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
import { checkTypeApi } from "../../../utils/Api";

const load = reactive(commMethods.loadData()); //加载所需数据
const editDialog = ref(false);
const data: any = reactive({
  checkTypeList: [],
});
let searchData: any = reactive({
  id: "",
  checkTypeName: "",
  checkTypeStatus: "",
});
const tableConfig = reactive([
  { label: "编号", prop: "code", width: 180, align: "left" },
  { label: "类型名称", prop: "checkTypeName" },
  { label: "类型状态", prop: "checkTypeStatus", width: 100, slot: true },
  { label: "更新时间", prop: "updateTime", width: 180 },
]);
onMounted(() => {
  initData();
  getDicList();
  searchList();
});
const initData = () => {
  Object.assign(searchData, {
    id: "",
    checkTypeName: "",
    checkTypeStatus: "",
  });
};
// 获取字典
const getDicList = () => {
  data.checkTypeList =
    commMethods.getDicByNameOrTypeList("check_type_status") || [];
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
  checkTypeApi
    .findAll({
      checkTypeName: searchData.checkTypeName,
      checkTypeStatus: searchData.checkTypeStatus,
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
    .getMessageBox(`确定要${state ? "启用" : "禁用"}当前检测类型?`)
    .then(() => {
      load.loading = true;
      checkTypeApi
        .updateCheckTypeStatus({
          checkTypeId: id,
          checkTypeStatus: state ? 1 : 0,
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
  commMethods.getMessageBox(`确定要删除当前检测类型?`).then(() => {
    load.loading = true;
    checkTypeApi
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
  // Object.assign(searchData, commMethods.loadData())
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
