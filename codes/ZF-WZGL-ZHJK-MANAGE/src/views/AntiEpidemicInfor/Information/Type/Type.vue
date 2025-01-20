<!-- 资讯类型 -->
<template>
  <info-card>
    <template #header>资讯类型</template>
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
          v-model="searchData.consultationName"
          clearable
          placeholder="资讯类型名称"
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
          @click="manageData(0)"
          :icon="Plus"
          >新建类型</el-button
        >
      </template>
      <template #item_consultationIdType="scope">
        <Tag
          state="success"
          v-if="scope.row.consultationIdType == 1"
          text="启用"
        ></Tag>
        <Tag state="warning" v-else text="禁用"></Tag>
        <!-- {{commMethods.getDicName('examineStatus',scope.row.examineStatus)}} -->
      </template>
      <template #operateContent="scope">
        <el-button
          type="primary"
          size="small"
          plain
          :icon="EditPen"
          @click="manageData(1, scope.row.id)"
          >编辑</el-button
        >
        <el-button
          type="success"
          size="small"
          plain
          v-if="scope.row.consultationIdType == 0"
          :icon="SuccessFilled"
          @click="handleOpen(true, scope.row.id)"
          >启用
        </el-button>
        <el-button
          type="warning"
          size="small"
          v-else
          plain
          :icon="CircleCloseFilled"
          @click="handleOpen(false, scope.row.id)"
          >禁用</el-button
        >
        <el-button
          type="danger"
          size="small"
          plain
          :icon="DeleteFilled"
          @click="delData(scope.row.id)"
          >删除</el-button
        >
      </template>
    </container-table>
  </info-card>
  <manage-info-type
    v-model:visible="editDialog"
    :id="searchData.id"
    @refresh="refreshList"
  ></manage-info-type>
</template>
<script lang="ts" setup>
import InfoCard from "../../../../components/InfoCard/InfoCard.vue";
import ManageInfoType from "./components/ManageInfoType.vue";
import ContainerTable from "../../../../components/ContainerTable/ContainerTable.vue";
import Tag from "../../../../components/Tag/Tag.vue";
import { ElMessage, ElMessageBox } from "element-plus";
import {
  DeleteFilled,
  SuccessFilled,
  CircleCloseFilled,
  EditPen,
  Search,
  RefreshLeft,
  Plus,
} from "@element-plus/icons-vue";
import commMethods from "../../../../assets/js/common";
import { onMounted, reactive, ref } from "vue";
import { sConsultationTypeApi } from "../../../../utils/Api";

const load = reactive(commMethods.loadData()); //加载所需数据
const editDialog = ref(false);
const searchData: any = reactive({
  id: "",
  consultationName: "",
});
const tableConfig = reactive([
  { label: "编号", prop: "consultationId", width: 180, align: "left" },
  { label: "类型名称", prop: "consultationName" },
  { label: "类型状态", prop: "consultationIdType", width: 120, slot: true },
  { label: "更新时间", prop: "createTime", width: 180 },
]);
onMounted(() => {
  initData();
  searchList();
});
const initData = () => {
  Object.assign(searchData, {
    id: "",
    consultationName: "",
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
  sConsultationTypeApi
    .findAll({
      consultationName: searchData.consultationName,
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
  editDialog.value = true;
};
// 启用停用
const handleOpen = (state: boolean, id: string | number) => {
  commMethods
    .getMessageBox(`确定要${state ? "启用" : "停用"}当前资讯类型?`)
    .then(() => {
      load.loading = true;
      sConsultationTypeApi
        .updateOne({
          consultationIdType: state ? "1" : "0",
          id: id,
        })
        .then((res) => {
          ElMessage({
            type: "success",
            message: `${state ? "启用" : "停用"}成功`,
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
  commMethods.getMessageBox(`确定要删除当前资讯类型?`).then(() => {
    load.loading = true;
    sConsultationTypeApi
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
