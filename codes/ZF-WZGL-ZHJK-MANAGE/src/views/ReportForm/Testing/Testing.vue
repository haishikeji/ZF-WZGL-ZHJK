<!-- 检测信息报表 -->
<template>
  <info-card>
    <template #header>检测信息报表</template>
    <container-table
      :tableData="load.tableData"
      :tableConfig="tableConfig"
      :loading="load.loading"
      :total="load.total"
      :showIndex="false"
      :showOperate="false"
      :pageSize="load.pageSize"
      :currentPage="load.currentPage"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    >
      <!-- 搜索区域 -->
      <template #searchArea>
        <el-date-picker
          v-model="searchData.dateRange"
          type="daterange"
          class="w-300"
          range-separator="至"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
          start-placeholder="开始时间"
          end-placeholder="结束时间"
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
    </container-table>
    <div v-show="false">
      <container-table
        tableId="table"
        :showIndex="false"
        :showOperate="false"
        :tableData="allData"
        :tableConfig="tableConfig"
      ></container-table>
    </div>
  </info-card>
  <!-- <manage-info-type v-model:visible="editDialog" :id="searchData.id" @refresh="refreshList"></manage-info-type> -->
</template>
<script lang="ts" setup>
import InfoCard from "../../../components/InfoCard/InfoCard.vue";
// import ManageInfoType from "./components/ManageInfoType.vue";
import ContainerTable from "../../../components/ContainerTable/ContainerTable.vue";
import { ElMessage } from "element-plus";
import { Search, RefreshLeft, Download } from "@element-plus/icons-vue";
import commMethods from "../../../assets/js/common";
import { nextTick, onMounted, reactive, ref } from "vue";
import { checkResultApi } from "../../../utils/Api";
import { exportExcel } from "../../../assets/js/exportExcel";
const load = reactive(commMethods.loadData()); //加载所需数据
const searchData = reactive({
  id: "",
  dateRange: [],
});
const allData = ref([]);
const tableConfig = reactive([
  { label: "检测时间", prop: "dat", width: 180, align: "left" },
  { label: "检测人数", prop: "personnelNumTotal" },
  { label: "红色预警人数", prop: "redPersonnel" },
  { label: "黄色预警人数", prop: "yellowPersonnel" },
  { label: "绿色无症状人数", prop: "greenPersonnel" },
  { label: "红色预警类型", prop: "redType" },
  { label: "黄色预警类型", prop: "yellowType" },
]);
onMounted(() => {
  initData();
  searchList();
});
const initData = () => {
  allData.value = [];
  Object.assign(searchData, {
    id: "",
    dateRange: [],
  });
};
// 搜索列表
const searchList = () => {
  Object.assign(load, commMethods.loadData());
  getDataList();
};
// 获取列表数据
const getDataList = () => {
  checkResultApi
    .checkInfoTotal({
      dateStart: searchData.dateRange?.[0] || "",
      dateEnd: searchData.dateRange?.[1] || "",
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
  checkResultApi
    .exportCheckInfoTotal({
      dateStart: searchData.dateRange?.[0] || "",
      dateEnd: searchData.dateRange?.[1] || "",
    })
    .then((res) => {
      console.log(res);
      commMethods.downloadWithName(res, "检测信息报表");
      // allData.value = res.data.list;
      //  nextTick(() => {
      // const allTable = [
      //     {
      //         eleName: '#table', //要导出的表格id
      //     }
      // ]
      // exportExcel(allTable, '检测信息报表') //导出的excel的名称
      //  })
    })
    .finally(() => {
      load.loading = false;
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
