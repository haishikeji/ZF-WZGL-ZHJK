<!-- 物资出库 -->
<template>
  <transition name="fade">
    <info-card v-show="!editDialog && !lookDetail">
      <template #header>物资出库</template>
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
            v-model="searchData.outputNo"
            clearable
            placeholder="出库单号"
          />
          <!-- 
                <el-button @click="resetData" :loading="load.loading" :icon="RefreshLeft">重置</el-button> -->

          <el-button
            type="primary"
            :icon="Search"
            :loading="load.loading"
            @click="searchList"
            >查询</el-button
          >
          <el-button
            type="primary"
            :loading="load.loading"
            @click="manageData(0)"
            :icon="Plus"
            >物资出库
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
          <!-- {{commMethods.getDicName('output_status',scope.row.approveStatus)}} -->
        </template>
        <template #operateContent="scope">
          <el-button
            type="primary"
            size="small"
            plain
            v-if="scope.row.approveStatus != 0"
            :icon="View"
            @click="manageData(2, scope.row.id, scope.row.outputNo)"
            >查看
          </el-button>
          <el-button
            type="primary"
            size="small"
            plain
            v-if="['0', '3', 0, 3].indexOf(scope.row.approveStatus) > -1"
            :icon="EditPen"
            @click="manageData(1, scope.row.id, scope.row.outputNo)"
            >编辑
          </el-button>
          <el-button
            type="danger"
            size="small"
            plain
            v-if="['0', '3', 0, 3].indexOf(scope.row.approveStatus) > -1"
            :icon="DeleteFilled"
            @click="delData(scope, scope.row.outputNo)"
            >删除
          </el-button>
        </template>
      </container-table>
    </info-card>
  </transition>
  <ManageDepot
    v-if="editDialog"
    :state="editState"
    v-model:visible="editDialog"
    :id="searchData.id"
    :no="searchData.no"
    @back="refreshList"
  ></ManageDepot>
  <DepotDetail
    v-show="lookDetail"
    v-model:visible="lookDetail"
    :no="searchData.no"
    @back="refreshList"
  >
  </DepotDetail>
</template>
<script lang="ts" setup>
import InfoCard from "../../../../components/InfoCard/InfoCard.vue";
import ManageDepot from "./components/ManageDepot.vue";
import DepotDetail from "./components/DepotDetail.vue";
import Tag from "../../../../components/Tag/Tag.vue";
import ContainerTable from "../../../../components/ContainerTable/ContainerTable.vue";
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
import { stockOutputApplyApi } from "../../../../utils/Api";

const load = reactive(commMethods.loadData()); //加载所需数据
const editDialog = ref(false);
const editState = ref(0);
const lookDetail = ref(false);
const searchData: any = reactive({
  id: "",
  no: "",
  outputNo: "",
});
const tableConfig = reactive([
  { label: "出库单号", prop: "outputNo", width: 180, align: "left" },
  { label: "申请人部门", prop: "applyDeptName" },
  { label: "申请人姓名", prop: "applyUserName", width: 130 },
  { label: "申请人手机号", prop: "applyPhone", width: 120 },
  { label: "出库申请时间", prop: "applyTime", width: 180 },
  { label: "状态", prop: "approveStatus", width: 100, slot: true },
]);
onMounted(() => {
  initData();
  searchList();
});
const initData = () => {
  Object.assign(searchData, {
    id: "",
    outputNo: "",
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
  stockOutputApplyApi
    .findAll({
      outputNo: searchData.outputNo,
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
      `确定要删除当前出库信息?出库信息删除会影响库存的计算，请慎重删除!`
    )
    .then(() => {
      stockOutputApplyApi
        .logicDeleteByOutputNo(id)
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
  stockOutputApplyApi
    .report({
      outputNo: searchData.outputNo,
    })
    .then((res) => {
      console.log(res);
      commMethods.downloadWithName(res, "物资出库列表");
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
