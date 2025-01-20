<template>
  <Dialog
    v-model:visible="visible"
    :loading="load.loading"
    title="绑定人员信息"
    width="800px"
    @cancel="cancelSubmit"
    @ok="submitForm"
  >
    <container-table
      ref="table"
      showSelect
      :showOperate="false"
      :showIndex="false"
      :tableData="load.tableData"
      :tableConfig="tableConfig"
      :loading="load.loading"
      :total="load.total"
      :currentPage="load.currentPage"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      @selectChange="selectChange"
    >
      <template #searchArea>
        <select-option
          class="w-200"
          v-model="searchData.cb"
          clearable
          placeholder="所属部门选择"
        ></select-option>
        <el-input
          class="w-200"
          v-model="searchData.ca"
          clearable
          placeholder="人员姓名"
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
      </template>
    </container-table>
  </Dialog>
</template>
<script lang="ts" setup>
import Dialog from "../../../../components/Dialog/Dialog.vue";
import SelectOption from "../../../../components/SelectOption/SelectOption.vue";
import { reactive, ref, watch, nextTick } from "vue";
import { ElMessage } from "element-plus";
import { Search, RefreshLeft, Plus } from "@element-plus/icons-vue";
import ContainerTable from "../../../../components/ContainerTable/ContainerTable.vue";
import commMethods from "../../../../assets/js/common";
const load: any = reactive(commMethods.loadData()); //加载所需数据
const loading = ref<boolean>(false);
const selectedIdList = ref<any>([]);
const ruleForm = ref();
const table = ref();
interface Props {
  id?: string | number;
  visible?: boolean;
}

const emit = defineEmits<{
  (e: "update:visible", visible: boolean): void;
  (e: "refresh"): void;
}>();
const props = withDefaults(defineProps<Props>(), {
  visible: false,
});
const searchData = reactive({
  id: "",
  ca: "",
  cb: "",
});
const tableConfig = reactive([
  { label: "人员姓名", prop: "a", align: "left" },
  { label: "人员账号", prop: "b" },
  { label: "身份证号", prop: "c", width: 180 },
  { label: "所属部门", prop: "d" },
]);
//监听visible变化，关闭弹窗
watch(
  () => props.visible,
  (newval) => {
    //[]替换成选中的list
    selectedIdList.value = JSON.parse(JSON.stringify([]));
    searchList();
    emit("update:visible", newval);
  }
);

// 表单验证
const submitForm = () => {};

// 取消提交
const cancelSubmit = () => {
  //清除选中表格
  table.value.table.clearSelection();
  selectedIdList.value = [];
  Object.assign(load, commMethods.loadData());
  emit("update:visible", false);
};
// 重置数据
const resetData = () => {
  // Object.assign(searchData, commMethods.loadData())
  searchList();
};
// 搜索列表
const searchList = () => {
  Object.assign(load, commMethods.loadData());
  getDataList();
};
// 获取列表数据
const getDataList = () => {
  console.log(123131);
  getMultiple();
  load.loading = false;
};

// 选中
const selectChange = (val: any) => {
  console.log(val);
  let addList: any = [];
  let oldData = new Set(selectedIdList.value);
  //先删除当页数据
  for (let i in load.tableData) {
    oldData.delete(load.tableData[i].id);
  }
  //再加入当页选中数据
  for (let i in val) {
    addList.push(val[i].id);
  }
  selectedIdList.value = [...oldData].concat(addList);
  console.log(selectedIdList.value);
};
//多选
const getMultiple = () => {
  // this.$nextTick(() => {
  //   for (let i in load.tableData) {
  //     if (selectedIdList.value.indexOf(load.tableData[i].id) > -1) {
  //       ref.value.table.toggleRowSelection(load.tableData[i], true);
  //     }
  //   }
  // })
  nextTick(() => {
    for (let i in load.tableData) {
      if (selectedIdList.value.indexOf(load.tableData[i].id) > -1) {
        table.value.table.toggleRowSelection(load.tableData[i], true);
      }
    }
  });
};
//s 分页跳转
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
