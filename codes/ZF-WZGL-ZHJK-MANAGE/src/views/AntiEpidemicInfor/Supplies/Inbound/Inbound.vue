<!-- 物资入库 -->
<template>
  <transition name="fade">
    <info-card v-show="!editDialog && !lookDetail">
      <template #header>物资入库</template>
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
            v-model="searchData.inputNo"
            clearable
            placeholder="入库单号"
          />
          <SupplierList
            class="w-200"
            v-model="searchData.supplierNo"
            placeholder="供应商名称"
          ></SupplierList>
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
            >物资入库
          </el-button>
          <el-button
            type="primary"
            :loading="load.loading"
            @click="multiExport"
            :icon="Download"
            >导出报表
          </el-button>
        </template>
        <template #item_approveStatus="scope">
          <Tag
            state="default"
            v-if="scope.row.approveStatus == 0"
            :text="scope.row.approveStatusName"
          ></Tag>
          <Tag
            state="error"
            v-if="scope.row.approveStatus == 3"
            :text="scope.row.approveStatusName"
          ></Tag>
          <Tag
            state="success"
            v-if="scope.row.approveStatus == 2"
            :text="scope.row.approveStatusName"
          ></Tag>
          <Tag
            state="warning"
            v-if="scope.row.approveStatus == 1"
            :text="scope.row.approveStatusName"
          ></Tag>
          <!--          commMethods.getDicName('input_status',scope.row.approveStatus)-->
        </template>
        <template #operateContent="scope">
          <el-button
            type="primary"
            size="small"
            v-if="scope.row.approveStatus != 0"
            plain
            :icon="View"
            @click="manageData(2, scope.row.id, scope.row.inputNo)"
            >查看
          </el-button>
          <el-button
            type="primary"
            v-if="['0', '3', 0, 3].indexOf(scope.row.approveStatus) > -1"
            size="small"
            plain
            :icon="EditPen"
            @click="manageData(1, scope.row.id, scope.row.inputNo)"
            >编辑
          </el-button>
          <el-button
            type="danger"
            size="small"
            v-if="['0', '3', 0, 3].indexOf(scope.row.approveStatus) > -1"
            plain
            :icon="DeleteFilled"
            @click="delData(scope.row.inputNo)"
            >删除
          </el-button>
        </template>
      </container-table>
    </info-card>
  </transition>
  <ManageInbound
    v-if="editDialog"
    :state="editState"
    v-model:visible="editDialog"
    :id="searchData.id"
    :no="searchData.no"
    @back="refreshList"
  ></ManageInbound>
  <InboundDetail
    v-show="lookDetail"
    v-model:visible="lookDetail"
    :no="searchData.no"
    @back="refreshList"
  >
  </InboundDetail>
</template>
<script lang="ts" setup>
import InfoCard from "../../../../components/InfoCard/InfoCard.vue";
import ManageInbound from "./components/ManageInbound.vue";
import InboundDetail from "./components/InboundDetail.vue";
// import ManageInfoType from "./components/ManageInfoType.vue";
import Tag from "../../../../components/Tag/Tag.vue";
import ContainerTable from "../../../../components/ContainerTable/ContainerTable.vue";
import SelectOption from "../../../../components/SelectOption/SelectOption.vue";
import { ElMessage, ElMessageBox } from "element-plus";
import {
  DeleteFilled,
  View,
  Download,
  EditPen,
  Search,
  RefreshLeft,
  Plus,
} from "@element-plus/icons-vue";
import commMethods from "../../../../assets/js/common";
import { onMounted, reactive, ref } from "vue";
import { stockInputApplyApi } from "../../../../utils/Api";
import SupplierList from "../../../common/components/SupplierList.vue";

const load = reactive(commMethods.loadData()); //加载所需数据
const editDialog = ref(false);
const editState = ref(0);
const lookDetail = ref(false);
const searchData: any = reactive({
  id: "",
  goodName: "",
  supplierNo: "",
  inputNo: "",
  no: "",
});
const tableConfig = reactive([
  { label: "入库单号", prop: "inputNo", width: 180, align: "left" },
  { label: "仓库名称", prop: "storeName" },
  { label: "供应商名称", prop: "supplierName" },
  { label: "入库申请人", prop: "applyUserName", width: 130 },
  { label: "入库申请提交时间", prop: "applyTime", width: 180 },
  { label: "状态", prop: "approveStatus", width: 100, slot: true },
]);
onMounted(() => {
  initData();
  searchList();
});
const initData = () => {
  Object.assign(searchData, {
    id: "",
    goodName: "",
    supplierNo: "",
    inputNo: "",
    no: "",
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
  stockInputApplyApi
    .findAll({
      inputNo: searchData.inputNo,
      goodName: searchData.goodName,
      supplierNo: searchData.supplierNo,
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
const manageData = (state: number, id?: string, no?: string) => {
  searchData.id = id;
  searchData.no = no;
  if (state == 2) {
    lookDetail.value = true;
  } else {
    editDialog.value = true;
  }
  editState.value = state;
};

// 删除
const delData = (id: string) => {
  commMethods
    .getMessageBox(
      `确定要删除当前入库信息?入库信息删除会影响库存的计算，请慎重删除!`
    )
    .then(() => {
      stockInputApplyApi
        .logicDeleteByInputNo(id)
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
// 导出
const multiExport = () => {
  load.loading = true;
  stockInputApplyApi
    .report({
      inputNo: searchData.inputNo,
      goodName: searchData.goodName,
      supplierNo: searchData.supplierNo,
    })
    .then((res) => {
      console.log(res);
      commMethods.downloadWithName(res, "物资入库列表");
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
