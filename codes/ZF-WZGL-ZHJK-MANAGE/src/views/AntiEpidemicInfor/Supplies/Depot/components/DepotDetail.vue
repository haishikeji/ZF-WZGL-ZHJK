<!-- 物资出库 -->
<template>
  <info-card>
    <template #header>
      <div class="flex-n fac fjb">
        <div>物资出库</div>
        <div style="margin: -10px 0">
          <el-button :icon="ArrowLeftBold" @click="back">返回</el-button>
        </div>
      </div>
    </template>
    <ZTitle title="审批信息"></ZTitle>
    <div v-if="approval" style="margin: 20px 100px">
      <el-form
        ref="ruleForm"
        :model="form"
        :show-message="false"
        :rules="rules"
        label-width="80px"
      >
        <el-form-item label="审批备注" prop="approveContent">
          <el-input
            v-model="form.approveContent"
            :rows="4"
            resize="none"
            type="textarea"
            placeholder="请输入审批信息"
          />
        </el-form-item>
        <div class="flex-n fje">
          <el-button type="danger" @click="depotOperations(3)"
            >拒绝出库</el-button
          >
          <el-button type="primary" @click="depotOperations(2)">出库</el-button>
        </div>
      </el-form>
    </div>
    <div
      v-else
      class="info-grid-content"
      style="margin: 20px 100px; grid-template-columns: repeat(2, 120px 1fr)"
    >
      <div class="label">审批结果</div>
      <div class="value" style="grid-column: span 3">
        <el-tag type="warning" v-if="form.approveStatus == 1" size="small">{{
          commMethods.getDicName("output_status", form.approveStatus)
        }}</el-tag>
        <el-tag type="error" v-if="form.approveStatus == 3" size="small">{{
          commMethods.getDicName("output_status", form.approveStatus)
        }}</el-tag>
        <el-tag type="success" v-if="form.approveStatus == 2" size="small">{{
          commMethods.getDicName("output_status", form.approveStatus)
        }}</el-tag>
      </div>
      <div class="label">审批备注</div>
      <div class="value" style="grid-column: span 3">
        {{ form.approveContent }}
      </div>
      <div class="label">审批人</div>
      <div class="value">{{ form.approveUserName }}</div>
      <div class="label">审批时间</div>
      <div class="value">{{ form.approveTime }}</div>
    </div>
    <ZTitle title="出库信息"></ZTitle>
    <div
      class="info-grid-content"
      style="margin: 20px 100px; grid-template-columns: repeat(2, 120px 1fr)"
    >
      <div class="label">申请单号</div>
      <div class="value">{{ form.outputNo }}</div>
      <div class="label">申请人部门</div>
      <div class="value">{{ form.applyDeptName }}</div>
      <div class="label">申请人姓名</div>
      <div class="value">{{ form.applyUserName }}</div>
      <div class="label">申请人手机号</div>
      <div class="value">{{ form.applyPhone }}</div>
      <div class="label">预约领取时间</div>
      <div class="value" style="grid-column: span 3">
        {{ form.applyAppointmentTime }}
      </div>
    </div>
    <container-table
      :tableData="load.tableData"
      :tableConfig="tableConfig"
      :loading="load.loading"
      :showPage="false"
      height="60vh"
      :show-operate="false"
      operateWidth="90px"
      :total="load.total"
      :showIndex="false"
      :pageSize="load.pageSize"
      :currentPage="load.currentPage"
    >
      <!-- 搜索区域 -->
      <template #searchArea>
        <div class="w-full">
          <ZTitle :title="approval ? '物资申请信息' : '物资信息'">
            <!-- <MaterialSelection @getCheckList="getCheckList" v-if="!approval">
                        </MaterialSelection> -->
          </ZTitle>
        </div>
      </template>
      <template #item_output_num="scope">
        <el-input v-model="scope.row.output_num" placeholder="数量" />
      </template>
      <template #operateContent="scope">
        <el-button
          type="danger"
          size="small"
          plain
          :icon="DeleteFilled"
          @click="delData(scope.index)"
          >删除
        </el-button>
      </template>
    </container-table>
  </info-card>
</template>
<script lang="ts" setup>
import InfoCard from "../../../../../components/InfoCard/InfoCard.vue";
import Tag from "../../../../../components/Tag/Tag.vue";
import SelectOption from "../../../../../components/SelectOption/SelectOption.vue";
import MaterialSelection from "../../../../common/components/MaterialSelection.vue";

import ContainerTable from "../../../../../components/ContainerTable/ContainerTable.vue";
import { ElMessage, ElMessageBox, FormRules } from "element-plus";
import ZTitle from "../../../../../components/ZTitle/ZTitle.vue";
import { ArrowLeftBold, DeleteFilled } from "@element-plus/icons-vue";
import commMethods from "../../../../../assets/js/common";
import { onMounted, reactive, ref, watch } from "vue";
import { stockOutputApplyApi } from "../../../../../utils/Api";

interface Props {
  visible?: boolean;
  approval?: boolean;
  id?: string;
  no?: string;
}
const props = withDefaults(defineProps<Props>(), {
  visible: false,
  approval: false,
  id: "",
  no: "",
});
const load = reactive(commMethods.loadData()); //加载所需数据
let form = ref({
  applyDeptName: "",
  applyPhone: "",
  applyUserName: "",
  outputNo: "",
  approveContent: "",
  approveStatus: "",
  approveUserName: "",
  approveTime: "",
  applyAppointmentTime: "",
});

//监听visible变化，关闭弹窗
watch(
  () => props.visible,
  (newval) => {
    if (newval) {
      getDataInfo();
    }
    emit("update:visible", newval);
  }
);

const rules = reactive<FormRules>({
  approveContent: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
});
let tableConfig = reactive([
  {
    label: "物资编号",
    prop: "goodCode",
    width: 160,
    align: "left",
    fixed: "left",
  },
  { label: "物资名称", prop: "goodName", minWidth: 180, fixed: "left" },
  { label: "规格型号", prop: "specsModelName", width: 140 },
  { label: "计量单位", prop: "measureUnit", width: 110 },
  { label: "数量", prop: "output_num", width: 120, fixed: "right" },
  { label: "仓库名称", prop: "storeNo", width: 160, fixed: "right" },
  { label: "库位", prop: "gridId", width: 160, fixed: "right" },
]);

const emit = defineEmits<{
  (e: "update:visible", visible: boolean): void;
  (e: "back"): void;
}>();
// 列表
const getDataInfo = () => {
  stockOutputApplyApi
    .findStockOutputApplyByOutputNo(props.no)
    .then((res) => {
      form.value = res.data;
      load.tableData = res.data.stockOutputModels || [];
    })
    .finally(() => {
      load.loading = false;
    });
};
//选中的物资
const getCheckList = (data: any) => {
  console.log(data);
};
// 搜索列表
const searchList = () => {
  Object.assign(load, commMethods.loadData());
  getDataList();
};
const depotOperations = (approveStatus: number) => {
  commMethods
    .getMessageBox(
      approveStatus == 2
        ? `确定物资申请单没有问题，允许出库?`
        : `确定物资申请单存在问题，拒绝出库?`
    )
    .then(() => {
      stockOutputApplyApi
        .saveAndUpdateAndSubmit({
          id: props.id,
          inputNo: props.no,
          approveContent: form.value.approveContent,
          approveStatus: approveStatus,
        })
        .then((res) => {
          ElMessage({
            type: "success",
            message: approveStatus == 2 ? "入库成功" : "拒绝成功",
          });
          back();
        });
    });
};
// 获取列表数据
const getDataList = () => {
  load.loading = false;
};

const back = () => {
  emit("back");
  emit("update:visible", false);
};
</script>
<style scoped lang="scss">
::v-deep(.el-table) {
  margin-top: 0;
}
</style>
