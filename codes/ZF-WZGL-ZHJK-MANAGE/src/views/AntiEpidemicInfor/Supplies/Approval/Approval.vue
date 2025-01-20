<!-- 出入库审批 -->
<template>
  <transition name="fade">
    <info-card v-show="!lookInboundDetail && !lookDepotDetail">
      <template #header>出入库审批</template>
      <container-table
        :tableData="load.tableData"
        :tableConfig="tableConfig"
        :loading="load.loading"
        :total="load.total"
        :showIndex="false"
        :operateWidth="160"
        :pageSize="load.pageSize"
        :currentPage="load.currentPage"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      >
        <!-- 搜索区域 -->
        <template #searchArea>
          <DepartmentList
            class="w-200"
            v-model="searchData.applyDept"
            placeholder="申请人部门"
          ></DepartmentList>
          <el-input
            class="w-200"
            v-model="searchData.applyUserName"
            clearable
            placeholder="申请人姓名"
          />
          <SelectOption
            class="w-200"
            v-model="searchData.stockType"
            placeholder="出入库状态"
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
            >导出报表
          </el-button>
        </template>
        <template #item_approveStatus="scope">
          <template v-if="scope.row.stockType == 2">
            <Tag
              state="error"
              text="拒绝出库"
              v-if="scope.row.approveStatus == 3"
            ></Tag>
            <Tag
              state="success"
              text="已出库"
              v-if="scope.row.approveStatus == 2"
            ></Tag>
            <Tag
              state="warning"
              text="待审批"
              v-if="scope.row.approveStatus == 1"
            ></Tag>
          </template>
          <template v-else>
            <Tag
              state="error"
              text="拒绝入库"
              v-if="scope.row.approveStatus == 3"
            ></Tag>
            <Tag
              state="success"
              text="已入库"
              v-if="scope.row.approveStatus == 2"
            ></Tag>
            <Tag
              state="warning"
              text="待审批"
              v-if="scope.row.approveStatus == 1"
            ></Tag>
          </template>
          <!-- {{commMethods.getDicName('examineStatus',scope.row.examineStatus)}} -->
        </template>
        <template #operateContent="scope">
          <el-button
            type="primary"
            size="small"
            plain
            :icon="View"
            @click="
              manageData(
                2,
                scope.row.stockType,
                scope.row.approve_no,
                scope.row.id
              )
            "
          >
            查看
          </el-button>
          <el-button
            type="warning"
            size="small"
            plain
            :icon="CircleCheckFilled"
            v-if="scope.row.approveStatus == 1"
            @click="
              manageData(
                1,
                scope.row.stockType,
                scope.row.approve_no,
                scope.row.id
              )
            "
            >审批
          </el-button>
        </template>
      </container-table>
    </info-card>
  </transition>
  <InboundDetail
    :approval="editState == 1 ? true : false"
    v-show="lookInboundDetail"
    v-model:visible="lookInboundDetail"
    :no="searchData.no"
    :id="searchData.id"
    @back="refreshList"
  >
  </InboundDetail>
  <DepotDetail
    :approval="editState == 1 ? true : false"
    :no="searchData.no"
    v-show="lookDepotDetail"
    v-model:visible="lookDepotDetail"
    :id="searchData.id"
    @back="refreshList"
  >
  </DepotDetail>
</template>
<script lang="ts" setup>
import InfoCard from "../../../../components/InfoCard/InfoCard.vue";
// import ManageInfoType from "./components/ManageInfoType.vue";
import Tag from "../../../../components/Tag/Tag.vue";
import SelectOption from "../../../../components/SelectOption/SelectOption.vue";
import InboundDetail from "../Inbound/components/InboundDetail.vue";
import DepotDetail from "../Depot/components/DepotDetail.vue";
import ContainerTable from "../../../../components/ContainerTable/ContainerTable.vue";
import { ElMessage, ElMessageBox } from "element-plus";
import {
  View,
  Download,
  CircleCheckFilled,
  Search,
  RefreshLeft,
} from "@element-plus/icons-vue";
import commMethods from "../../../../assets/js/common";
import { onMounted, reactive, ref } from "vue";
import DepartmentList from "../../../common/components/DepartmentList.vue";
import { stockApproveApi } from "../../../../utils/Api";
const lookDepotDetail = ref(false);
const lookInboundDetail = ref(false);
const editState = ref(0);
const load = reactive(commMethods.loadData()); //加载所需数据

const searchData: any = reactive({
  id: "",
  no: "",
  applyDept: "",
  applyUserName: "",
  stockType: "",
});
const tableConfig = reactive([
  { label: "申请单号", prop: "approve_no", width: 180, align: "left" },
  { label: "申请人部门", prop: "applyDeptName", minWidth: 180 },
  { label: "申请人姓名", prop: "applyUserName", minWidth: 150 },
  { label: "申请人手机号", prop: "applyPhone", width: 120 },
  { label: "预约领取时间", prop: "applyAppointmentTime", width: 180 },
  { label: "类型", prop: "stockTypeName", width: 100 },
  { label: "出库/入库状态", prop: "approveStatus", width: 120, slot: true },
  { label: "出库/入库时间", prop: "approveTime", width: 180 },
]);
onMounted(() => {
  initData();
  searchList();
});
const initData = () => {
  Object.assign(searchData, {
    id: "",
    no: "",
    applyDept: "",
    applyUserName: "",
    stockType: "",
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
  stockApproveApi
    .findAll({
      applyDept: searchData.applyDept,
      applyUserName: searchData.applyUserName,
      stockType: searchData.stockType,
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
//type 入出库判断 0入1出
const manageData = (
  state: string | number,
  type: string,
  no?: string | number,
  id?: string | number
) => {
  searchData.id = id;
  searchData.no = no;
  if (type == "1") {
    lookInboundDetail.value = true;
  } else {
    lookDepotDetail.value = true;
  }
  editState.value = state;
};

// 导出
const multiExport = () => {
  load.loading = true;
  stockApproveApi
    .report({
      applyDept: searchData.applyDept,
      applyUserName: searchData.applyUserName,
      stockType: searchData.stockType,
    })
    .then((res) => {
      console.log(res);
      commMethods.downloadWithName(res, "出入库审批列表");
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
