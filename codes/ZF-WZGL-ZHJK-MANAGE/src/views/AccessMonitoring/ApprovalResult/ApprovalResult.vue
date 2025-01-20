<!-- 审批结果 -->
<template>
  <info-card>
    <template #header>审批结果</template>
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
          v-model="searchData.personnelName"
          clearable
          placeholder="拜访人姓名"
        />
        <el-input
          class="w-200"
          v-model="searchData.idnum"
          clearable
          placeholder="拜访人身份证号"
        />
        <SelectOption
          class="w-200"
          :list="data.visitStatusList"
          v-model="searchData.visitStatus"
          placeholder="进入状态"
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
          @click="multiExport"
          :icon="Download"
          >导出报表</el-button
        >
      </template>
      <template #item_visitStatus="scope">
        <Tag
          v-if="scope.row.visitStatus == 2"
          state="error"
          text="拒绝进入"
        ></Tag>
        <Tag
          v-if="scope.row.visitStatus == 1"
          state="success"
          text="允许进入"
        ></Tag>
        <Tag
          v-if="scope.row.visitStatus == 3"
          state="warning"
          text="待审批"
        ></Tag>
        <!-- {{commMethods.getDicName('examineStatus',scope.row.examineStatus)}} -->
      </template>
    </container-table>
  </info-card>
  <!-- <manage-info-type v-model:visible="editDialog" :id="searchData.id" @refresh="refreshList"></manage-info-type> -->
</template>
<script lang="ts" setup>
import InfoCard from "../../../components/InfoCard/InfoCard.vue";
// import ManageInfoType from "./components/ManageInfoType.vue";
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
import { approvalApi } from "../../../utils/Api";
const value1 = ref("1");
const load = reactive(commMethods.loadData()); //加载所需数据
const editDialog = ref(false);

const searchData = reactive({
  id: "",
  personnelName: "",
  idnum: "",
  visitStatus: "",
});
const data: any = reactive({
  visitStatusList: [],
});
const tableConfig = reactive([
  { label: "拜访区域", prop: "visitedOrg", width: 180, align: "left" },
  { label: "被访人姓名", prop: "visitedName", width: 130 },
  { label: "拜访人姓名", prop: "personnelName", minWidth: 130 },
  { label: "身份证号", prop: "idnum", width: 180 },
  { label: "手机号", prop: "phone", width: 120 },
  { label: "拜访人车牌号", prop: "carnum", width: 110 },
  { label: "拜访事由", prop: "visitReason", width: 160 },
  { label: "拜访时间", prop: "visitTime", width: 180 },
  { label: "进入状态", prop: "visitStatus", width: 100, slot: true },
  { label: "审批人", prop: "approver", width: 120 },
  { label: "审批时间", prop: "approvalTime", width: 180 },
  { label: "审批备注", prop: "approvalRemark", width: 200 },
]);
onMounted(() => {
  getDicList();
  initData();
  searchList();
});
const initData = () => {
  Object.assign(searchData, {
    id: "",
    personnelName: "",
    idnum: "",
    visitStatus: "",
  });
};
// 获取字典
const getDicList = () => {
  data.visitStatusList =
    commMethods.getDicByNameOrTypeList("visit_status") || [];
};
// 搜索列表
const searchList = () => {
  Object.assign(load, commMethods.loadData());
  getDataList();
};
// 获取列表数据
const getDataList = () => {
  approvalApi
    .findAll({
      personnelName: searchData.personnelName,
      idnum: searchData.idnum,
      visitStatus: searchData.visitStatus,
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

// 导出
const multiExport = () => {
  load.loading = true;
  approvalApi
    .exportResultAll({
      personnelName: searchData.personnelName,
      idnum: searchData.idnum,
      visitStatus: searchData.visitStatus,
    })
    .then((res) => {
      console.log(res);
      commMethods.downloadWithName(res, "审批结果");
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
