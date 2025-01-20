<!-- 大屏配置 -->
<template>
  <info-card>
    <template #header>
      <el-radio-group v-model="radio" size="large" style="margin: -10px 0">
        <el-radio-button label="0">疫情数据</el-radio-button>
        <el-radio-button label="1">地图数据</el-radio-button>
      </el-radio-group>
    </template>
    <container-table
      v-if="radio == '0'"
      :tableData="load.tableData"
      :tableConfig="tableConfig"
      :loading="load.loading"
      :total="load.total"
      :showIndex="false"
      :operateWidth="160"
      :currentPage="load.currentPage"
      @sizeChange="handleSizeChange"
      @currentChange="handleCurrentChange"
    >
      <!-- 搜索区域 -->
      <template #searchArea>
        <!-- <el-button type="primary" :icon="Search" :loading="load.loading" @click="searchList">查询</el-button>
                <el-button @click="resetData" :loading="load.loading" :icon="RefreshLeft">重置</el-button> -->
        <el-button
          type="primary"
          :loading="load.loading"
          @click="manageData(0)"
          :icon="Plus"
          >配置数据</el-button
        >
      </template>

      <template #operateContent="scope">
        <el-button
          type="primary"
          size="small"
          plain
          :icon="EditPen"
          @click="manageData(1, scope.row.id)"
          >编辑
        </el-button>
        <!-- <el-button type="success" size="small" plain :icon="SuccessFilled" @click="handleOpen(true,scope.row.id)">启用
                </el-button> -->
        <!-- <el-button type="warning" size="small" plain :icon="CircleCloseFilled"  @click="handleOpen(false,scope.row.id)">禁用</el-button></el-button></el-button> -->
        <el-button
          type="danger"
          size="small"
          plain
          :icon="DeleteFilled"
          @click="delData(scope.row.id)"
          >删除
        </el-button>
      </template>
    </container-table>
    <container-table
      v-else
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
        <!-- <el-button type="primary" :icon="Search" :loading="load.loading" @click="searchList">查询</el-button>
                <el-button @click="resetData" :loading="load.loading" :icon="RefreshLeft">重置</el-button> -->
        <el-button
          type="primary"
          :loading="load.loading"
          @click="manageMapData(0)"
          :icon="Plus"
          >配置数据
        </el-button>
      </template>
      <template #item_c="scope">
        <el-table-column label="新增" prop="ngAreaNew" align="center">
        </el-table-column>
        <el-table-column label="现有" prop="ngAreaOld" align="center">
        </el-table-column>
      </template>
      <template #item_e="scope">
        <el-table-column label="新增" prop="kcAreaNew" align="center">
        </el-table-column>
        <el-table-column label="现有" prop="kcAreaOld" align="center">
        </el-table-column>
      </template>
      <template #item_f="scope">
        <el-table-column label="新增" prop="cyAreaNew" align="center">
        </el-table-column>
        <el-table-column label="现有" prop="cyAreaOld" align="center">
        </el-table-column>
      </template>
      <template #item_g="scope">
        <el-table-column label="新增" prop="edAreaNew" align="center">
        </el-table-column>
        <el-table-column label="现有" prop="edAreaOld" align="center">
        </el-table-column>
      </template>
      <template #item_h="scope">
        <el-table-column label="新增" prop="lyAreaNew" align="center">
        </el-table-column>
        <el-table-column label="现有" prop="lyAreaOld" align="center">
        </el-table-column>
      </template>
      <template #item_i="scope">
        <el-table-column label="新增" prop="syAreaNew" align="center">
        </el-table-column>
        <el-table-column label="现有" prop="syAreaOld" align="center">
        </el-table-column>
      </template>
      <template #item_j="scope">
        <el-table-column label="新增" prop="jtAreaNew" align="center">
        </el-table-column>
        <el-table-column label="现有" prop="jtAreaOld" align="center">
        </el-table-column>
      </template>
      <template #item_k="scope">
        <el-table-column label="新增" prop="naAreaNew" align="center">
        </el-table-column>
        <el-table-column label="现有" prop="naAreaOld" align="center">
        </el-table-column>
      </template>
      <template #item_l="scope">
        <el-table-column label="新增" prop="ysAreaNew" align="center">
        </el-table-column>
        <el-table-column label="现有" prop="ysAreaOld" align="center">
        </el-table-column>
      </template>
      <template #item_m="scope">
        <el-table-column label="新增" prop="dhAreaNew" align="center">
        </el-table-column>
        <el-table-column label="现有" prop="dhAreaOld" align="center">
        </el-table-column>
      </template>
      <template #item_n="scope">
        <el-table-column label="新增" prop="gzlAreaNew" align="center">
        </el-table-column>
        <el-table-column label="现有" prop="gzlAreaOld" align="center">
        </el-table-column>
      </template>
      <template #operateContent="scope">
        <el-button
          type="primary"
          size="small"
          plain
          :icon="EditPen"
          @click="manageMapData(1, scope.row.id)"
          >编辑
        </el-button>
        <!-- <el-button type="success" size="small" plain :icon="SuccessFilled" @click="handleOpen(true,scope.row.id)">启用
                </el-button> -->
        <!-- <el-button type="warning" size="small" plain :icon="CircleCloseFilled"  @click="handleOpen(false,scope.row.id)">禁用</el-button></el-button></el-button> -->
        <el-button
          type="danger"
          size="small"
          plain
          :icon="DeleteFilled"
          @click="delMapData(scope.row.id)"
          >删除
        </el-button>
      </template>
    </container-table>
    <!-- 疫情数据 -->
    <ManageEpidemicData
      v-model:visible="editEpidemicData"
      :id="searchData.id"
      @refresh="refreshList"
    >
    </ManageEpidemicData>
    <!-- 地图数据 -->
    <ManageMapData
      v-model:visible="editMapData"
      :id="searchData.id"
      @refresh="refreshList"
    >
    </ManageMapData>
  </info-card>
</template>
<script lang="ts" setup>
import InfoCard from "../../../components/InfoCard/InfoCard.vue";
import { ElMessage, ElMessageBox } from "element-plus";
import ManageEpidemicData from "./components/ManageEpidemicData.vue";
import ManageMapData from "./components/ManageMapData.vue";
import {
  DeleteFilled,
  SuccessFilled,
  CircleCloseFilled,
  Notification,
  User,
  EditPen,
  Search,
  RefreshLeft,
  Plus,
} from "@element-plus/icons-vue";
import commMethods from "../../../assets/js/common";
import { onMounted, reactive, ref, watch } from "vue";
import ContainerTable from "../../../components/ContainerTable/ContainerTable.vue";
import { mapDataApi, sepidemicDataApi } from "../../../utils/Api";
const load = reactive(commMethods.loadData()); //加载所需数据
const radio = ref("0");
const editEpidemicData = ref(false);
const editMapData = ref(false);
onMounted(() => {
  searchList();
});
// 搜索列表
const searchList = () => {
  Object.assign(load, commMethods.loadData());
  getDataList();
};
const searchData: any = reactive({
  id: "",
});
const tableConfig = reactive([
  { label: "检测类型", prop: "checkTypeName", minWidth: 180, align: "left" },
  { label: "统计时间", prop: "statisticalTime", width: 180 },
  { label: "本土新增", prop: "localNew", width: 120 },
  { label: "新增疑似", prop: "newSuspected", width: 120 },
  { label: "国内新增", prop: "domesticNew", width: 120 },
  { label: "本土现有确诊", prop: "localExistingDiagnosis", width: 120 },
  { label: "国内现有确诊", prop: "currentDiagnosisInChina", width: 120 },
  { label: "本土新增治愈", prop: "localNewCure", width: 120 },
  { label: "本土累计治愈", prop: "localCumulativeCure", width: 120 },
  { label: "国内新增治愈", prop: "domesticNewCure", width: 120 },
  { label: "国内累计治愈", prop: "cumulativeCureInChina", width: 120 },
]);
watch(
  () => radio.value,
  (newval) => {
    tableConfig.length = 0;
    if (newval == "0") {
      tableConfig.push(
        ...[
          {
            label: "检测类型",
            prop: "checkTypeName",
            minWidth: 180,
            align: "left",
          },
          { label: "统计时间", prop: "statisticalTime", width: 180 },
          { label: "本土新增", prop: "localNew", width: 120 },
          { label: "新增疑似", prop: "newSuspected", width: 120 },
          { label: "国内新增", prop: "domesticNew", width: 120 },
          { label: "本土现有确诊", prop: "localExistingDiagnosis", width: 120 },
          {
            label: "国内现有确诊",
            prop: "currentDiagnosisInChina",
            width: 120,
          },
          { label: "本土新增治愈", prop: "localNewCure", width: 120 },
          { label: "本土累计治愈", prop: "localCumulativeCure", width: 120 },
          { label: "国内新增治愈", prop: "domesticNewCure", width: 120 },
          { label: "国内累计治愈", prop: "cumulativeCureInChina", width: 120 },
        ]
      );
    } else {
      tableConfig.push(
        ...[
          {
            label: "检测类型",
            prop: "checkTypeName",
            minWidth: 180,
            align: "left",
          },
          { label: "统计时间", prop: "statisticalTime", width: 180 },
          { label: "南关区", prop: "c", width: 120, slot: true },
          { label: "宽城区", prop: "e", width: 120, slot: true },
          { label: "朝阳区", prop: "f", width: 120, slot: true },
          { label: "二道区", prop: "g", width: 120, slot: true },
          { label: "绿园区", prop: "h", width: 120, slot: true },
          { label: "双阳区", prop: "i", width: 120, slot: true },
          { label: "九台区", prop: "j", width: 120, slot: true },
          { label: "农安县", prop: "k", width: 120, slot: true },
          { label: "榆树市", prop: "l", width: 120, slot: true },
          { label: "德惠市", prop: "m", width: 120, slot: true },
          { label: "公主岭市", prop: "n", width: 120, slot: true },
        ]
      );
    }
    searchList();
  }
);
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
// 获取列表数据
const getDataList = () => {
  load.loading = false;
  if (radio.value == "0") {
    sepidemicDataApi
      .findAll({
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
  } else {
    mapDataApi
      .findAll({
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
  }
};
const manageData = (state: string | number, id?: string) => {
  searchData.id = id;
  editEpidemicData.value = true;
};
const manageMapData = (state: string | number, id?: string) => {
  searchData.id = id;
  editMapData.value = true;
};
// 删除
const delData = (id: string) => {
  searchData.id = id;
  commMethods.getMessageBox(`是否确定删除当前统计?`).then(() => {
    load.loading = true;
    sepidemicDataApi
      .logicDeleteByIds(id)
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
// 删除
const delMapData = (id: string) => {
  searchData.id = id;
  commMethods.getMessageBox(`是否确定删除当前统计?`).then(() => {
    load.loading = true;
    mapDataApi
      .logicDeleteByIds(id)
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
// 重置数据
const resetData = () => {
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
