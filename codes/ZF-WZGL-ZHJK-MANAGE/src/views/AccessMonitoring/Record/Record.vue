<!-- 人员备案 -->
<template>
  <transition name="fade">
    <info-card v-if="!editDialog">
      <template #header>人员备案</template>
      <container-table
        :tableData="load.tableData"
        :tableConfig="tableConfig"
        :loading="load.loading"
        :total="load.total"
        :showIndex="false"
        :operateWidth="120"
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
        <template #operateContent="scope">
          <el-button
            type="primary"
            size="small"
            plain
            :icon="Reading"
            @click="manageData(1, scope.row)"
            >拜访记录
          </el-button>
        </template>
      </container-table>
    </info-card>
    <visiting-record
      v-else
      @back="editDialog = false"
      :info="info"
    ></visiting-record>
  </transition>
</template>
<script lang="ts" setup>
import InfoCard from "../../../components/InfoCard/InfoCard.vue";
import VisitingRecord from "./components/VisitingRecord.vue";
import Tag from "../../../components/Tag/Tag.vue";
import ContainerTable from "../../../components/ContainerTable/ContainerTable.vue";
import { ElMessage, ElMessageBox } from "element-plus";
import {
  Download,
  Reading,
  Search,
  RefreshLeft,
} from "@element-plus/icons-vue";
import commMethods from "../../../assets/js/common";
import { onMounted, reactive, ref } from "vue";
import { approvalApi } from "../../../utils/Api";
const load = reactive(commMethods.loadData()); //加载所需数据
const editDialog = ref(false);
const info = ref({});
const searchData = reactive({
  id: "",
  personnelName: "",
  idnum: "",
});
const tableConfig = reactive([
  { label: "拜访人姓名", prop: "personnelName", align: "left" },
  { label: "身份证号", prop: "idnum", minWidth: 180 },
  { label: "手机号", prop: "phone", minWidth: 120 },
  { label: "拜访人车牌号", prop: "carnum", minWidth: 110 },
  { label: "前端用户账号", prop: "loginName", minWidth: 110 },
]);
onMounted(() => {
  initData();
  searchList();
});
const initData = () => {
  Object.assign(searchData, {
    id: "",
    personnelName: "",
    idnum: "",
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
  approvalApi
    .findPersonnelList({
      personnelName: searchData.personnelName,
      idnum: searchData.idnum,
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
const manageData = (state: string | number, data?: any) => {
  info.value = data;
  editDialog.value = true;
};

// 导出
const multiExport = () => {
  load.loading = true;
  approvalApi
    .exportPersonnelList({
      personnelName: searchData.personnelName,
      idnum: searchData.idnum,
    })
    .then((res) => {
      console.log(res);
      commMethods.downloadWithName(res, "人员列表");
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
