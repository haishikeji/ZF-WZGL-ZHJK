<!-- 物资出库 -->
<template>
  <info-card>
    <template #header>
      <div class="flex-n fac fjb">
        <div>物资派发</div>
        <div style="margin: -10px 0">
          <el-button :icon="ArrowLeftBold" @click="back">返回</el-button>
          <el-button type="primary" @click="submitForm(0)" v-if="state == 1"
            >完成</el-button
          >
        </div>
      </div>
    </template>

    <container-table
      :tableData="load.tableData"
      :tableConfig="tableConfig"
      :loading="load.loading"
      :showPage="false"
      :show-operate="false"
      operateWidth="120px"
      :total="load.total"
      :showIndex="false"
      :currentPage="load.currentPage"
    >
      <!-- 搜索区域 -->
      <template #searchArea>
        <div class="w-full">
          <template v-if="state != 2">
            <ZTitle title="领取信息"> </ZTitle>
            <el-form
              ref="ruleForm"
              class="mt-20"
              :model="form"
              :show-message="false"
              :rules="rules"
              label-width="110px"
            >
              <div class="flex-n fac">
                <el-form-item
                  label="领取人"
                  prop="receiveUserCode"
                  class="flex-1"
                >
                  <PeopleList
                    class="w-full"
                    v-model="form.receiveUserCode"
                    placeholder="请选择申请人"
                    @getAllData="changeUser"
                  ></PeopleList>
                </el-form-item>
                <el-form-item
                  label="领取日期"
                  prop="receiveTime"
                  class="flex-1"
                >
                  <el-date-picker
                    v-model="form.receiveTime"
                    type="date"
                    class="w-full"
                    format="YYYY-MM-DD"
                    value-format="YYYY-MM-DD"
                    placeholder="选择日期"
                  />
                </el-form-item>
              </div>
              <el-form-item label="备注" prop="receiveRemarks">
                <el-input
                  v-model="form.receiveRemarks"
                  :rows="4"
                  resize="none"
                  type="textarea"
                  placeholder="请输入备注"
                />
              </el-form-item>
            </el-form>
          </template>
          <ZTitle title="物资信息"> </ZTitle>
        </div>
      </template>
      <template #item_has_receive_num="scope">
        {{ state == 2 ? scope.row.output_num : 0 }}
      </template>
      <template #item_this_receive_num="scope">
        <el-input
          v-if="state == 0"
          disabled
          v-model="scope.row.output_num"
          placeholder="数量"
        />
        <div v-else>{{ scope.row.output_num }}</div>
      </template>
      <template #operateContent="scope">
        <el-button
          type="primary"
          size="small"
          plain
          :icon="View"
          @click="receiveRecords(scope.index)"
          >领取记录
        </el-button>
      </template>
    </container-table>
  </info-card>
</template>
<script lang="ts" setup>
import InfoCard from "../../../../../components/InfoCard/InfoCard.vue";
import Tag from "../../../../../components/Tag/Tag.vue";
import SelectOption from "../../../../../components/SelectOption/SelectOption.vue";
import ContainerTable from "../../../../../components/ContainerTable/ContainerTable.vue";
import { ElMessage, ElMessageBox } from "element-plus";
import MaterialSelection from "../../../../common/components/MaterialSelection.vue";
import ZTitle from "../../../../../components/ZTitle/ZTitle.vue";
import { ArrowLeftBold, View } from "@element-plus/icons-vue";
import commMethods from "../../../../../assets/js/common";
import { onMounted, reactive, ref, watch } from "vue";
import DepartmentList from "../../../../common/components/DepartmentList.vue";
import PeopleList from "../../../../common/components/PeopleList.vue";
import {
  stockOutputApplyApi,
  stockOutputDistributeApi,
} from "../../../../../utils/Api";

interface Props {
  visible?: boolean;
  state?: string | number; //0 待领取 1已领取
  id?: string;
}
const props = withDefaults(defineProps<Props>(), {
  visible: false,
  id: "",
});
const load = reactive(commMethods.loadData()); //加载所需数据

let form: any = ref({});
const ruleForm = ref();
// //监听visible变化，关闭弹窗
// watch(
//   () => props.visible,
//   (newval) => {
//     emit("update:visible", newval);
//   }
// );
const rules = reactive<FormRules>({
  receiveRemarks: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
  receiveUserCode: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
  receiveTime: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
});
const tableConfig = reactive([
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
  { label: "申请数量", prop: "output_num", width: 110 },
  // { label: "已领取数量", prop: "has_receive_num", width: 110 , slot: true},
  // { label: "本次领取数量", prop: "this_receive_num", width: 120, slot: true, fixed: 'right' },
]);
const emit = defineEmits<{
  (e: "update:visible", visible: boolean): void;
  (e: "back"): void;
}>();
onMounted(() => {
  getInitValue();
  getDataInfo();
});
const getInitValue = () => {
  form.value = {
    receiveRemarks: "",
    receiveUserCode: "",
    receiveTime: "",
  };
};
const changeUser = (data: any) => {
  form.value.applyUserName = data.userName;
};
// 列表
const getDataInfo = () => {
  console.log(2131231);
  stockOutputDistributeApi
    .findAll({
      outputNo: props.id,
    })
    .then((res) => {
      load.tableData = res.data.list || [];
    })
    .finally(() => {
      load.loading = false;
    });
};

const receiveRecords = (idx: number) => {
  console.log(idx);
  commMethods.getMessageBox(`确定要删除当前物资?`).then(() => {
    load.tableData.splice(idx, 1);
    ElMessage({
      type: "success",
      message: "删除成功",
    });
  });
};
const back = () => {
  emit("back");
  emit("update:visible", false);
};
// 表单验证
const submitForm = async (state: number) => {
  if (!ruleForm.value) return;
  await ruleForm.value.validate((valid: any, fields: any) => {
    if (valid) {
      //   loading.value = true;
      saveOrUpdate(state);
    } else {
      ElMessage({
        message: "请检查是否有必填项未填写",
        type: "warning",
      });
    }
  });
};
const saveOrUpdate = (state: number) => {
  commMethods.getMessageBox(`确定已完成物资的派发?`).then(() => {
    load.loading = true;
    stockOutputDistributeApi
      .saveDistribute({
        outputNo: props.id,
        receiveUserCode: form.value.receiveUserCode,
        receiveTime: form.value.receiveTime,
        receiveRemarks: form.value.receiveRemarks,
      })
      .then((res) => {
        ElMessage({
          message: "保存成功",
          type: "success",
        });
        back();
      })
      .finally(() => {
        load.loading = false;
      });
    // console.log(load.tableData, form.value)
    // form.value.approveStatus = state;
    // let stockOutputModels = [];
    // for (let i in load.tableData) {
    //     let item = load.tableData[i]
    //     stockOutputModels.push({
    //         "goodCode": item.goodCode,
    //         "gridId": "1",
    //         "output_num": item.output_num,
    //     })
    // }
    // form.value.stockOutputModels = stockOutputModels;
    // stockOutputApplyApi.saveAndUpdateAndSubmit(form.value).then(res => {
    //     ElMessage({
    //         message: (state == 0 ? '临时' : '') + '保存成功',
    //         type: 'success',
    //     })
    //     back();
    // }).finally(() => {
    //     load.loading = false;
    // })
  });
};
</script>
<style scoped lang="scss">
::v-deep(.el-table) {
  margin-top: 0;
}
</style>
