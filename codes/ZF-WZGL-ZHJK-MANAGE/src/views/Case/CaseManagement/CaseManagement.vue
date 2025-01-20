<!-- 人员档案 -->
<template>
  <info-card>
    <template #header>人员档案</template>
    <container-table
      :tableData="load.tableData"
      :tableConfig="tableConfig"
      :loading="load.loading"
      :total="load.total"
      :showIndex="false"
      :show-operate="false"
      :operateWidth="90"
      :pageSize="load.pageSize"
      :currentPage="load.currentPage"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    >
      <!-- 搜索区域 -->
      <template #searchArea>
        <el-input
          class="w-200"
          v-model="searchData.peopleName"
          clearable
          placeholder="姓名"
        />
        <el-input
          class="w-200"
          v-model="searchData.idnum"
          clearable
          placeholder="有效证件号"
        />
        <SelectOption
          class="w-200"
          :list="data.identityList"
          v-model="searchData.identity"
          placeholder="身份"
        >
        </SelectOption>
        <SelectOption
          class="w-200"
          :list="data.sexList"
          v-model="searchData.sex"
          placeholder="性别"
        ></SelectOption>
        <SelectOption
          class="w-200"
          :list="data.caseStatusList"
          v-model="searchData.caseStatus"
          placeholder="个案状态"
        >
        </SelectOption>

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
          @click="multiExport"
          :icon="Download"
          >导出报表</el-button
        >
      </template>

      <template #item_peopleId="scope">
        <el-button type="text" @click="manageData(scope.row.peopleId)">{{
          scope.row.peopleId
        }}</el-button>
      </template>
      <template #item_identity="scope">
        {{ commMethods.getDicName("identity", scope.row.identity) }}
      </template>
      <template #item_sex="scope">
        {{ commMethods.getDicName("sex", scope.row.sex) }}
      </template>
      <template #item_caseStatus="scope">
        {{ commMethods.getDicName("case_status", scope.row.caseStatus) }}
      </template>
    </container-table>
  </info-card>
  <CaseManageInfo
    v-model:visible="editDialog"
    :id="searchData.id"
    @refresh="refreshList"
  ></CaseManageInfo>
</template>
<script lang="ts" setup>
import InfoCard from "../../../components/InfoCard/InfoCard.vue";
import CaseManageInfo from "./components/CaseManageInfo.vue";
import Tag from "../../../components/Tag/Tag.vue";
import SelectOption from "../../../components/SelectOption/SelectOption.vue";

import ContainerTable from "../../../components/ContainerTable/ContainerTable.vue";
import { ElMessage, ElMessageBox } from "element-plus";
import {
  View,
  Download,
  CircleCheckFilled,
  Search,
  RefreshLeft,
} from "@element-plus/icons-vue";
import commMethods from "../../../assets/js/common";
import { onMounted, reactive, ref } from "vue";
import { peopleApi } from "../../../utils/Api";

const load = reactive(commMethods.loadData()); //加载所需数据
const editDialog = ref(false);

const searchData: any = reactive({
  id: "",
  peopleName: "",
  idnum: "",
  identity: "",
  sex: "",
  caseStatus: "",
});
const data: any = reactive({
  caseStatusList: [],
  identityList: [],
  sexList: [],
});
const tableConfig = reactive([
  { label: "ID", prop: "peopleId", width: 180, align: "left", slot: true },
  { label: "姓名", prop: "peopleName", width: 130 },
  { label: "有效证件号", prop: "idnum", minWidth: 130 },
  { label: "电话", prop: "phone", width: 120 },
  { label: "身份", prop: "identity", width: 120, slot: true },
  { label: "性别", prop: "sex", width: 80, slot: true },
  { label: "现住详细地", prop: "peopleAdd", minWidth: 160 },
  { label: "个案状态", prop: "caseStatus", width: 140, slot: true },
  { label: "到达日期", prop: "arrivalDate", width: 140 },
]);
onMounted(() => {
  getDicList();
  initData();
  searchList();
});
const initData = () => {
  Object.assign(searchData, {
    id: "",
    peopleName: "",
    idnum: "",
    identity: "",
    sex: "",
    caseStatus: "",
  });
};
// 获取字典
const getDicList = () => {
  data.caseStatusList = commMethods.getDicByNameOrTypeList("case_status") || [];
  data.identityList = commMethods.getDicByNameOrTypeList("identity") || [];
  data.sexList = commMethods.getDicByNameOrTypeList("sex") || [];
};
// 搜索列表
const searchList = () => {
  Object.assign(load, commMethods.loadData());
  getDataList();
};
// 获取列表数据
const getDataList = () => {
  peopleApi
    .findAll({
      peopleName: searchData.peopleName,
      idnum: searchData.idnum,
      identity: searchData.identity,
      sex: searchData.sex,
      caseStatus: searchData.caseStatus,
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

const manageData = (id: string) => {
  searchData.id = id;
  editDialog.value = true;
};
// 导出
const multiExport = () => {
  load.loading = true;
  peopleApi
    .exportAll({
      peopleName: searchData.peopleName,
      idnum: searchData.idnum,
      identity: searchData.identity,
      sex: searchData.sex,
      caseStatus: searchData.caseStatus,
    })
    .then((res) => {
      console.log(res);
      commMethods.downloadWithName(res, "人员档案");
      // allData.value = res.data.list;
      // nextTick(() => {
      //     const allTable = [
      //         {
      //             eleName: '#table', //要导出的表格id
      //             //   title: '表1'
      //         }
      //     ]
      //     exportExcel(allTable, '访客出入信息报表') //导出的excel的名称
      // })
    })
    .finally(() => {
      load.loading = false;
    });
};
// 重置数据
const resetData = () => {
  initData();
  // Object.assign(searchData, commMethods.loadData())
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
