<!-- 进入审批 -->
<template>
  <info-card>
    <template #header>进入审批</template>
    <container-table
      :tableData="load.tableData"
      :tableConfig="tableConfig"
      :loading="load.loading"
      :total="load.total"
      :showIndex="false"
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
        <div class="occupy_seat"></div>
        <div class="flex-n fac w-400" style="font-size: 12px">
          <el-switch v-model="isApproval" @change="changeApprovalAuto" />
          <span class="ml-10">
            开启自动审批，<span style="color: #cb1a1a"
              >请注意：开启自动审批之后，用户提交进入申请，则自动审批通过</span
            >
          </span>
        </div>
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
      <template #operateContent="scope">
        <el-button
          v-if="scope.row.visitStatus == 3"
          type="warning"
          size="small"
          plain
          :icon="CircleCheckFilled"
          @click="manageData(1, scope.row)"
          >审批
        </el-button>
      </template>
    </container-table>
  </info-card>
  <ApprovalInfo
    v-model:visible="editDialog"
    :info="info"
    @refresh="refreshList"
  ></ApprovalInfo>
</template>
<script lang="ts" setup>
import InfoCard from "../../../components/InfoCard/InfoCard.vue";
import ApprovalInfo from "./components/ApprovalInfo.vue";
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
import { onMounted, reactive, ref, watch } from "vue";
import { approvalApi } from "../../../utils/Api";
const isApproval = ref(false);
const load = reactive(commMethods.loadData()); //加载所需数据
const editDialog = ref(false);
const info = ref({});
const searchData: any = reactive({
  id: "",
  personnelName: "",
  idnum: "",
  visitStatus: "",
});
const data: any = reactive({
  visitStatusList: [],
});
const tableConfig = reactive([
  { label: "被访人机构", prop: "visitedOrg", width: 180, align: "left" },
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
const changeApprovalAuto = () => {
  approvalApi
    .approvalAuto({
      isApproval: isApproval.value ? 1 : 0,
    })
    .then((res) => {
      ElMessage({
        type: "success",
        message: `自动审批${isApproval.value ? "开启" : "关闭"}成功`,
      });
      searchList();
    })
    .catch(() => {
      isApproval.value = !isApproval;
    });
};
onMounted(() => {
  getDicList();
  initData();
  getApprovalAutoStatus();
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
//获取自动审批状态
const getApprovalAutoStatus = () => {
  approvalApi.approvalAutoStatus().then((res) => {
    console.log("获取自动审批状态", res);
    isApproval.value = res.data.isApproval == 1 ? true : false;
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
const manageData = (state: string | number, data?: any) => {
  info.value = data;
  editDialog.value = true;
};

// 导出
const multiExport = () => {
  load.loading = true;
  approvalApi
    .exportAll({
      personnelName: searchData.personnelName,
      idnum: searchData.idnum,
      visitStatus: searchData.visitStatus,
    })
    .then((res) => {
      console.log(res);
      commMethods.downloadWithName(res, "审核列表");
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
