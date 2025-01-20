<!-- 预约信息 -->
<template>
  <transition name="fade">
    <info-card v-if="!editReserve">
      <template #header>预约信息</template>
      <container-table
        :tableData="load.tableData"
        :tableConfig="tableConfig"
        :loading="load.loading"
        :total="load.total"
        :showIndex="false"
        :operateWidth="250"
        :pageSize="load.pageSize"
        :currentPage="load.currentPage"
        @size-change="handleSizeChange"
        showSelect
        :selectable="selectable"
        @selectChange="selectChange"
        :rowState="rowState"
        @current-change="handleCurrentChange"
      >
        <!-- 搜索区域 -->
        <template #searchArea>
          <div>
            <div class="search_area">
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

              <CheckTypeList
                class="w-200"
                v-model="searchData.checkTypeId"
                placeholder="检测类型"
              ></CheckTypeList>
              <SelectOption
                class="w-200"
                :list="data.checkResultStatusList"
                v-model="searchData.checkResultStatus"
                placeholder="检测状态"
              >
              </SelectOption>
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
                @click="manageData(0)"
                :icon="Plus"
                >发布预约信息
              </el-button>
            </div>
            <div class="search_area mt-10">
              <!-- :disabled="checkList.length == 0 ? true : false" -->
              <el-button
                type="primary"
                :loading="load.loading"
                @click="multiExport"
                :icon="Download"
                >导出预约信息
              </el-button>
              <el-button
                type="warning"
                :disabled="checkList.length == 0 ? true : false"
                :icon="Share"
                @click="pushMessage()"
                >一键推送
              </el-button>
            </div>
          </div>
        </template>
        <template #item_reservationNum="scope">
          <div
            style="color: #4e9fd4; cursor: pointer"
            @click="manageData(2, scope.row.reservationId)"
          >
            {{ scope.row.reservationNum }}
          </div>
        </template>
        <template #item_checkResultStatus="scope">
          <Tag
            state="error"
            v-if="scope.row.checkResultStatus == 3"
            text="已过期"
          ></Tag>
          <Tag
            state="success"
            v-if="scope.row.checkResultStatus == 1"
            text="已检测"
          ></Tag>
          <Tag
            state="warning"
            v-if="scope.row.checkResultStatus == 2"
            text="未检测"
          ></Tag>

          <!-- {{commMethods.getDicName('examineStatus',scope.row.examineStatus)}} -->
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
            type="success"
            v-if="scope.row.checkResultStatus == 2"
            size="small"
            plain
            :icon="Notebook"
            @click="resultEntry(scope.row.reservationId)"
          >
            结果录入
          </el-button>
          <el-button
            type="warning"
            size="small"
            v-if="scope.row.checkResultStatus == 1 && scope.row.pushed == 2"
            plain
            :icon="Share"
            @click="pushMessage(scope.row.reservationId)"
            >一键推送
          </el-button>

          <el-button
            type="primary"
            v-if="['1', '2'].indexOf(scope.row.checkResultStatus) > -1"
            size="small"
            plain
            :icon="EditPen"
            @click="manageData(1, scope.row.reservationId)"
            >编辑
          </el-button>
          <el-button
            type="danger"
            size="small"
            plain
            :icon="DeleteFilled"
            @click="delData(scope.row.reservationId)"
            >删除
          </el-button>
        </template>
      </container-table>
    </info-card>
    <ManageReserveData
      v-else
      :state="reserveState"
      v-model:visible="editReserve"
      :id="searchData.id"
      @refresh="refreshList"
      @back="editReserve = false"
    ></ManageReserveData>
  </transition>
  <ManageReserve
    v-model:visible="editResult"
    :id="searchData.id"
    @refresh="refreshList"
  ></ManageReserve>
</template>
<script lang="ts" setup>
import InfoCard from "../../../components/InfoCard/InfoCard.vue";
import ManageReserve from "./components/ManageReserve.vue";
import Tag from "../../../components/Tag/Tag.vue";
import ManageReserveData from "./components/ManageReserveData.vue";
import ContainerTable from "../../../components/ContainerTable/ContainerTable.vue";
import SelectOption from "../../../components/SelectOption/SelectOption.vue";
import { ElMessage, ElMessageBox } from "element-plus";
import {
  DeleteFilled,
  Notebook,
  Share,
  Download,
  EditPen,
  Search,
  RefreshLeft,
  Plus,
} from "@element-plus/icons-vue";
import commMethods from "../../../assets/js/common";
import { reservationtApi } from "../../../utils/Api";
import { onMounted, reactive, ref } from "vue";
import CheckTypeList from "../../common/components/CheckTypeList.vue";

const load = reactive(commMethods.loadData()); //加载所需数据
const editReserve = ref(false);
const reserveState = ref<string | number>(0);
const editResult = ref(false);
const data: any = reactive({
  checkResultStatusList: [],
});
const searchData: any = reactive({
  id: "",
  personnelName: "",
  idnum: "",
  checkTypeId: "",
  checkResultStatus: "",
});
const tableConfig = reactive([
  { label: "检测类型", prop: "checkTypeName", minWidth: 140, align: "left" },
  { label: "预约流水号", prop: "reservationNum", width: 160, slot: true },
  { label: "就诊人姓名", prop: "personnelName", width: 130 },
  { label: "就诊人身份证号", prop: "idnum", width: 180 },
  { label: "就诊人电话", prop: "phone", width: 120 },
  { label: "检测负责人", prop: "checkPrincipal", width: 110 },
  { label: "预约时间", prop: "reservationTime", width: 180 },
  {
    label: "检测状态",
    prop: "checkResultStatus",
    width: 100,
    slot: true,
    fixed: "right",
  },
  {
    label: "显示结果",
    prop: "checkResultDisplay",
    width: 120,
    slot: true,
    fixed: "right",
  },
]);
onMounted(() => {
  initData();
  getDicList();
  searchList();
});
const initData = () => {
  Object.assign(searchData, {
    id: "",
    personnelName: "",
    idnum: "",
    checkTypeId: "",
    checkResultStatus: "",
  });
};
// 获取字典
const getDicList = () => {
  data.checkResultStatusList =
    commMethods.getDicByNameOrTypeList("check_result_status") || [];
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
    .findAll({
      personnelName: searchData.personnelName,
      idnum: searchData.idnum,
      checkTypeId: searchData.checkTypeId,
      checkResultStatus: searchData.checkResultStatus,
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
const manageData = (state: string | number, id?: string | number) => {
  searchData.id = id;
  reserveState.value = state;
  editReserve.value = true;
};
const resultEntry = (id: string | number) => {
  editResult.value = true;
  searchData.id = id;
};
const pushMessage = (id: string = "") => {
  console.log(id);
  commMethods
    .getMessageBox(
      id != ""
        ? `确定通过公众号推送给当前就诊人?`
        : `确定通过公众号推送给当前所选就诊人?`
    )
    .then(() => {
      let ids = [];
      if (id == "") {
        for (let i in checkList.value) {
          ids.push(checkList.value[i].reservationId);
        }
      }
      reservationtApi
        .pushed(id != "" ? id : ids.join(","))
        .then((res) => {
          ElMessage({
            type: "success",
            message: "推送成功",
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
  commMethods.getMessageBox(`确定要删除当前就诊人信息?`).then(() => {
    load.loading = true;
    reservationtApi
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
// 导出
const multiExport = () => {
  load.loading = true;
  reservationtApi
    .exportAll({
      personnelName: searchData.personnelName,
      idnum: searchData.idnum,
      checkTypeId: searchData.checkTypeId,
      checkResultStatus: searchData.checkResultStatus,
    })
    .then((res) => {
      console.log(res);
      commMethods.downloadWithName(res, "预约信息");
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
  if (arg.row.checkResultDisplay == 3) return "code-danger";
};
//哪些可选
const selectable = (data: any) => {
  console.log(data);
  if (data.checkResultStatus == 1 && data.pushed == 2) return true;
  else {
    return false;
  }
};
const checkList: any = ref([]);
//选择
const selectChange = (data: any) => {
  console.log(data);
  checkList.value = data;
};
// 分页跳转
const handleSizeChange = (val: number) => {
  Object.assign(load, commMethods.loadData(1, val));
  console.log(load);
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
// ::v-deep(.el-table__body-wrapper tr td.el-table-fixed-column--right) {
//     background: transparent !important;
// }
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
