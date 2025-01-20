<template>
  <el-button :icon="Plus" type="primary" @click="editMaterial = true"
    >选择物资</el-button
  >
  <Dialog
    v-model:visible="editMaterial"
    :loading="load.loading"
    title="物资列表"
    width="1000px"
    okBtnText="选择"
    @cancel="cancelSubmit"
    @ok="submitForm"
  >
    <container-table
      ref="ctable"
      height="45vh"
      :tableData="load.tableData"
      :tableConfig="tableConfig"
      showSelect
      @selectChange="selectChange"
      :total="load.total"
      :showIndex="false"
      :show-operate="false"
      :pageSize="load.pageSize"
      :currentPage="load.currentPage"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    >
      <!-- 搜索区域 -->
      <template #searchArea>
        <div class="search_area mb-10">
          <el-input
            class="w-200"
            clearable
            v-model="searchData.goodName"
            placeholder="请输入物资名称"
          />

          <category-list
            class="w-200"
            v-model="searchData.goodTypeCode"
            clearable
            placeholder="请选择物资类别"
          >
          </category-list>

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
        </div>
      </template>
    </container-table>
  </Dialog>
</template>
<script lang="ts" setup>
import { Search, Plus, RefreshLeft } from "@element-plus/icons-vue";
import Dialog from "../../../components/Dialog/Dialog.vue";
import commMethods from "../../../assets/js/common";
import ContainerTable from "../../../components/ContainerTable/ContainerTable.vue";
import SelectOption from "../../../components/SelectOption/SelectOption.vue";
import { nextTick, onMounted, reactive, ref, watch } from "vue";
import { goodApi, stockApi } from "../../../utils/Api";
import CategoryList from "./CategoryList.vue";
const load = reactive(commMethods.loadData()); //加载所需数据
const tableConfig = reactive([
  { label: "编号", prop: "goodCode", width: 160, align: "left" },
  { label: "物资名称", prop: "goodName", minWidth: 180 },
  { label: "所属类别", prop: "goodTypeName", width: 140 },
  { label: "规格型号", prop: "specsModelName", width: 160 },
  { label: "计量单位", prop: "measureUnit", width: 100 },
  { label: "库存余额", prop: "stockNum", width: 160 },
]);
interface Props {
  checkList?: any; //选中数据
  type?: number;
}
const selectData = ref([]);
const ctable = ref();
const props = withDefaults(defineProps<Props>(), {
  checkList: [],
  type: 0,
});
const emit = defineEmits<{
  (e: "getCheckList", data: any): void;
}>();
const searchData = reactive({
  id: "",
  goodTypeCode: "",
  goodName: "",
});
const editMaterial = ref(false);
//监听visible变化，关闭弹窗
watch(
  () => editMaterial.value,
  (newval) => {
    selectData.value = JSON.parse(JSON.stringify(props.checkList));
    initData();
    searchList();
  }
);
const initData = () => {
  Object.assign(searchData, {
    id: "",
    goodName: "",
    goodTypeCode: "",
  });
};
const submitForm = () => {
  emit("getCheckList", selectData.value);
  cancelSubmit();
};
const cancelSubmit = () => {
  editMaterial.value = false;
};
// 搜索列表
const searchList = () => {
  Object.assign(load, commMethods.loadData());
  getDataList();
};
// 获取列表数据
const getDataList = () => {
  if (props.type == 0)
    goodApi
      .findGoodStockAll({
        goodName: searchData.goodName,
        goodTypeCode:
          searchData.goodTypeCode?.length > 0
            ? searchData.goodTypeCode[searchData.goodTypeCode.length - 1]
            : "",
        pageSize: load.pageSize,
        pageNum: load.currentPage,
      })
      .then((res) => {
        load.tableData = res.data.list;
        load.total = res.data.total;
        toggleSelection();
      })
      .finally(() => {
        load.loading = false;
      });
  else {
    stockApi
      .findAll({
        goodName: searchData.goodName,
        goodTypeCode:
          searchData.goodTypeCode?.length > 0
            ? searchData.goodTypeCode[searchData.goodTypeCode.length - 1]
            : "",
        pageSize: load.pageSize,
        pageNum: load.currentPage,
      })
      .then((res) => {
        load.tableData = res.data.list;
        load.total = res.data.total;
        toggleSelection();
      })
      .finally(() => {
        load.loading = false;
      });
  }
};
const toggleSelection = () => {
  nextTick(() => {
    for (let i in load.tableData) {
      for (let j in selectData.value) {
        if (selectData.value[j].goodCode == load.tableData[i].goodCode) {
          ctable.value.table.toggleRowSelection(load.tableData[i], true);
        }
      }
    }
  });
};
// 选中的物资
const selectChange = (data: any) => {
  console.log(data);
  let oldData = new Set(selectData.value);
  for (let i in load.tableData) {
    for (let j in selectData.value) {
      if (selectData.value[j].goodCode == load.tableData[i].goodCode) {
        oldData.delete(selectData.value[j]);
      }
    }
  }
  selectData.value = [...oldData].concat(data);
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
<style scoped lang="scss">
::v-deep(.info-dialog) {
  .el-dialog__body {
    border-bottom: 1px solid #f1f1f1 !important;
  }
}
</style>
