<!-- 人员档案 -->
<template>
  <transition name="fade">
    <info-card v-if="!editDialog">
      <template #header>人员档案</template>
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
        :rowState="rowState"
        @current-change="handleCurrentChange"
      >
        <!-- 搜索区域 -->
        <template #searchArea>
          <el-input
            class="w-200"
            v-model="searchData.personnelName"
            clearable
            placeholder="就诊人姓名"
          />
          <el-input
            class="w-200"
            v-model="searchData.idnum"
            clearable
            placeholder="就诊人身份证号"
          />
          <el-button
            type="primary"
            :icon="Search"
            :loading="load.loading"
            @click="searchList"
            >查询
          </el-button>
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
          <div class="occupy_seat"></div>
          <el-button type="text" @click="showCode = true"
            >生成采集链接</el-button
          >
        </template>
        <template #item_checkResultDisplay="scope">
          <Tag
            state="error"
            v-if="scope.row.checkResultDisplay == 3"
            text="红色预警"
          ></Tag>
          <Tag
            state="warning"
            v-if="scope.row.checkResultDisplay == 2"
            text="黄色预警"
          ></Tag>
          <Tag
            state="success"
            v-if="scope.row.checkResultDisplay == 1"
            text="绿色无症状"
          ></Tag>
        </template>
        <template #operateContent="scope">
          <el-button
            type="primary"
            size="small"
            plain
            :icon="Notebook"
            @click="manageData(1, scope.row)"
          >
            检测记录
          </el-button>
        </template>
      </container-table>
    </info-card>
    <TestingRecord
      v-else
      @back="editDialog = false"
      :info="info"
    ></TestingRecord>
  </transition>
  <CollectionLinks v-model:visible="showCode"></CollectionLinks>
</template>
<script lang="ts" setup>
import InfoCard from "../../../components/InfoCard/InfoCard.vue";
import TestingRecord from "./components/TestingRecord.vue";
import CollectionLinks from "./components/CollectionLinks.vue";
import Tag from "../../../components/Tag/Tag.vue";
import ContainerTable from "../../../components/ContainerTable/ContainerTable.vue";
import { ElMessage, ElMessageBox } from "element-plus";
import {
  Notebook,
  Download,
  Search,
  RefreshLeft,
} from "@element-plus/icons-vue";
import commMethods from "../../../assets/js/common";
import { onMounted, reactive, ref } from "vue";
import { reservationtApi } from "../../../utils/Api";
const load = reactive(commMethods.loadData()); //加载所需数据
const editDialog = ref(false);
const showCode = ref(false);
const info = ref({});
const searchData: any = reactive({
  id: "",
  personnelName: "",
  idnum: "",
});
const tableConfig = reactive([
  { label: "就诊人姓名", prop: "personnelName", minWidth: 140, align: "left" },
  { label: "就诊人身份证号", prop: "idnum", width: 180 },
  { label: "就诊人电话", prop: "phone", width: 120 },
  { label: "就诊人地址", prop: "personnelAdd", minWidth: 180 },
  { label: "检测次数", prop: "checkTimes", width: 80 },
  { label: "最新检测类型", prop: "checkTypeName", width: 110 },
  { label: "最新检测时间", prop: "checkTime", width: 180 },
  { label: "前端用户账号", prop: "loginName", width: 120 },
  {
    label: "最新检测结果显示",
    prop: "checkResultDisplay",
    width: 140,
    slot: true,
    fixed: "right",
  },
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
  reservationtApi
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
  reservationtApi
    .exportPersonnelList({
      personnelName: searchData.personnelName,
      idnum: searchData.idnum,
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
  // Object.assign(searchData, commMethods.loadData())
  initData();
  searchList();
};
//设置行的颜色
const rowState = (arg: any) => {
  console.log(arg);
  if (arg.row.checkResultDisplay == 3) return "code-danger";
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
<style scoped lang="scss">
//::v-deep(.el-table__body-wrapper tr td.el-table-fixed-column--right) {
//    background: transparent !important;
//}
::v-deep(.code-danger) {
  background-color: #a23232 !important;
  color: white;

  tr,
  td {
    background-color: #a23232 !important;
    color: white;
  }
}

::v-deep(.el-table__body tr.hover-row > td.el-table__cell) {
  background: #f7feff !important;
  color: #333;
}

::v-deep(.hover-row) {
  background: #f7feff !important;
  color: #333;
}
</style>
