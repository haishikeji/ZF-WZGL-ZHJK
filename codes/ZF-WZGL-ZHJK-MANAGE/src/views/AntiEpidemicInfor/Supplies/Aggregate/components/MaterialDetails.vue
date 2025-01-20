<!-- 物资明细 -->
<template>
  <info-card>
    <template #header>
      <div class="flex-n fac fjb">
        <div>物资明细</div>
        <el-button
          style="margin: -10px 0"
          :icon="ArrowLeftBold"
          @click="emit('back')"
          >返回</el-button
        >
      </div>
    </template>
    <container-table
      :tableData="load.tableData"
      :tableConfig="tableConfig"
      :loading="load.loading"
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
        <div class="w-full">
          <ZTitle title="物资信息"></ZTitle>
          <div
            class="info-grid-content mt-10"
            style="grid-template-columns: repeat(2, 100px 1fr)"
          >
            <div class="label">物资编号</div>
            <div class="value">{{ info.goodCode }}</div>
            <div class="label">物资名称</div>
            <div class="value">{{ info.goodName }}</div>
            <div class="label">规格型号</div>
            <div class="value">{{ info.specsModelName }}</div>
            <div class="label">计量单位</div>
            <div class="value">{{ info.measureUnit }}</div>
          </div>
        </div>
      </template>
      <template #item_gridId="scope"> 1 </template>
    </container-table>
  </info-card>
</template>
<script lang="ts" setup>
import InfoCard from "../../../../../components/InfoCard/InfoCard.vue";
import Tag from "../../../../../components/Tag/Tag.vue";
import ContainerTable from "../../../../../components/ContainerTable/ContainerTable.vue";
import { ElMessage, ElMessageBox } from "element-plus";
import { ArrowLeftBold } from "@element-plus/icons-vue";
import commMethods from "../../../../../assets/js/common";
import { onMounted, reactive, ref } from "vue";
import ZTitle from "../../../../../components/ZTitle/ZTitle.vue";
import { stockApi } from "../../../../../utils/Api";
interface Props {
  visible?: boolean;
  state?: string | number;
  id?: string;
  info?: any;
}
const props = withDefaults(defineProps<Props>(), {
  visible: false,
  id: "",
});
const load = reactive(commMethods.loadData()); //加载所需数据
let form = ref({});
const tableConfig = reactive([
  { label: "仓库名称", prop: "storeName", minWidth: 180 },
  { label: "仓位名称", prop: "gridId", slot: true, minWidth: 180 },
  { label: "数量", prop: "stockNum", width: 140 },
]);
const emit = defineEmits<{
  (e: "back"): void;
}>();
onMounted(() => {
  searchList();
});
// 搜索列表
const searchList = () => {
  Object.assign(load, commMethods.loadData());
  getDataList();
};
// 获取列表数据
const getDataList = () => {
  stockApi
    .findDetailAll({
      stockNo: props.id,
      pageSize: load.pageSize,
      pageNum: load.currentPage,
    })
    .then((res) => {
      // form.value = res.data;
      load.tableData = res.data.list || [];
    })
    .finally(() => {
      load.loading = false;
    });
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
