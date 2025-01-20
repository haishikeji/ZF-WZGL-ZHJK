<!-- 物资盘点 -->
<template>
  <info-card>
    <template #header>
      <div class="flex-n fac fjb">
        <div>物资盘点</div>
        <div style="margin: -10px 0">
          <el-button :icon="ArrowLeftBold" @click="back">返回</el-button>
          <template v-if="state != 2">
            <el-button type="primary" @click="submitForm(0)">保存</el-button>
            <el-button type="warning" @click="submitForm(1)">提交</el-button>
          </template>
        </div>
      </div>
    </template>
    <el-form
      ref="ruleForm"
      :model="form"
      :disabled="state == 2"
      :show-message="false"
      :rules="rules"
      label-width="80px"
    >
      <container-table
        :tableData="form.checkDetailModels"
        :tableConfig="tableConfig"
        :loading="load.loading"
        :showPage="false"
        :show-operate="state == 2 ? false : true"
        operateWidth="90px"
        :total="load.total"
        :showIndex="false"
        :currentPage="load.currentPage"
      >
        <!-- 搜索区域 -->
        <template #searchArea>
          <div class="w-full">
            <div class="flex-n fac">
              <el-form-item label="仓库名称" prop="storeNo" class="flex-1">
                <StoreList
                  class="w-full"
                  :disabled="state == 2"
                  v-model="form.storeNo"
                  placeholder="请选择仓库"
                ></StoreList>
              </el-form-item>
              <el-form-item
                label="盘点日期"
                prop="checkTime"
                class="flex-1 ml-20"
              >
                <el-date-picker
                  v-model="form.checkTime"
                  type="date"
                  class="w-full"
                  format="YYYY-MM-DD"
                  value-format="YYYY-MM-DD"
                  placeholder="选择日期"
                />
              </el-form-item>
            </div>
            <el-form-item label="备注" prop="remarks">
              <el-input
                v-model="form.remarks"
                :rows="4"
                resize="none"
                type="textarea"
                placeholder="请输入备注信息"
              />
            </el-form-item>

            <ZTitle title="物资信息">
              <MaterialSelection
                :check-list="form.checkDetailModels"
                :type="1"
                v-if="state != 2"
                @getCheckList="getCheckList"
              ></MaterialSelection>
            </ZTitle>
          </div>
        </template>
        <template #item_gridId="scope"> 1 </template>
        <template #item_num="scope">
          <template v-if="state != 2">
            <el-form-item
              v-if="scope.index >= 0"
              label=""
              :prop="'checkDetailModels.' + scope.index + '.num'"
              required
              label-width="0"
            >
              <el-input-number
                class="w-full"
                :controls="false"
                :min="0"
                v-model="scope.row.num"
                placeholder="数量"
              />
            </el-form-item>
          </template>
          <!-- <el-input v-if="state != 2" v-model="scope.row.num" placeholder="数量" /> -->
          <template v-else>{{ scope.row.num }}</template>
        </template>
        <template #item_profit_loss_nun_status="scope">
          {{
            scope.row.num != "" &&
            Number(scope.row.stockNum) == Number(scope.row.num)
              ? "无"
              : ""
          }}
          {{
            scope.row.num != "" &&
            Number(scope.row.stockNum) < Number(scope.row.num)
              ? "盈利"
              : ""
          }}
          {{
            scope.row.num != "" &&
            Number(scope.row.stockNum) > Number(scope.row.num)
              ? "亏损"
              : ""
          }}
        </template>
        <template #item_profit_loss_nun="scope">
          {{ (scope.row.num || 0) - scope.row.stockNum }}
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
    </el-form>
  </info-card>
</template>
<script lang="ts" setup>
import InfoCard from "../../../../../components/InfoCard/InfoCard.vue";
import Tag from "../../../../../components/Tag/Tag.vue";
import ContainerTable from "../../../../../components/ContainerTable/ContainerTable.vue";
import { ElMessage, ElMessageBox, FormRules } from "element-plus";
import MaterialSelection from "../../../../common/components/MaterialSelection.vue";
import ZTitle from "../../../../../components/ZTitle/ZTitle.vue";
import { ArrowLeftBold, DeleteFilled } from "@element-plus/icons-vue";
import commMethods from "../../../../../assets/js/common";
import { onMounted, reactive, ref, watch } from "vue";
import { checkApi } from "../../../../../utils/Api";
import StoreList from "../../../../common/components/StoreList.vue";
interface Props {
  visible?: boolean;
  state?: string | number;
  id?: string;
}
const props = withDefaults(defineProps<Props>(), {
  visible: false,
  id: "",
});
const value1 = ref("1");
const load = reactive(commMethods.loadData()); //加载所需数据

let form: any = ref({});
const ruleForm = ref();
const rules = reactive<FormRules>({
  storeNo: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
  checkTime: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
  remarks: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
});
//监听visible变化，关闭弹窗
// watch(
//   () => props.visible,
//   (newval) => {
//     emit("update:visible", newval);
//   }
// );
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
  { label: "仓位名称", prop: "gridId", slot: true, width: 160 },
  { label: "数量", prop: "stockNum", width: 100, fixed: "right" },
  { label: "盘点数量", prop: "num", width: 100, slot: true, fixed: "right" },
  {
    label: "盈亏",
    prop: "profit_loss_nun_status",
    width: 100,
    slot: true,
    fixed: "right",
  },
  {
    label: "盈亏数量",
    prop: "profit_loss_nun",
    width: 100,
    slot: true,
    fixed: "right",
  },
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
    storeNo: "",
    checkTime: "",
    remarks: "",
    checkDetailModels: [],
  };
};
// 列表
const getDataInfo = () => {
  checkApi
    .findCheckDetailByCheckNo(props.id)
    .then((res) => {
      form.value = res.data;
      form.value.checkDetailModels = res.data.checkDetailModels || [];
    })
    .finally(() => {
      load.loading = false;
    });
};

//选中的物资
const getCheckList = (data: any) => {
  let noData = [];
  //原先没有了就删除
  for (let i = 0; i < form.value.checkDetailModels.length; i++) {
    let k = -1;
    for (let j = 0; j < data.length; j++) {
      if (data[j].goodCode == form.value.checkDetailModels[i].goodCode) {
        k = j;
        break;
      }
    }
    if (k == -1) {
      form.value.checkDetailModels.splice(i, 1);
      i--;
    }
  }
  //原先没有的加进来
  for (let i = 0; i < data.length; i++) {
    let k = -1;
    for (let j = 0; j < form.value.checkDetailModels.length; j++) {
      if (data[i].goodCode == form.value.checkDetailModels[j].goodCode) {
        k = j;
        break;
      }
    }
    if (k == -1) {
      noData.push(data[i]);
    }
  }
  form.value.checkDetailModels = form.value.checkDetailModels.concat(noData);
};
const delData = (idx) => {
  console.log(idx);
  commMethods.getMessageBox(`确定要删除当前物资?`).then(() => {
    form.value.checkDetailModels.splice(idx, 1);
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
      console.log(fields);
      ElMessage({
        message: "请检查是否有必填项未填写",
        type: "warning",
      });
    }
  });
};
const saveOrUpdate = (state: number) => {
  if (form.value.checkDetailModels.length == 0) {
    return ElMessage.warning("请选择需要盘点的物资!");
  }
  commMethods
    .getMessageBox(
      state == 1
        ? `确定要提交当前盘点(提交之后的盘点会自动存档，不支持修改)?`
        : `确定要保存当前盘点(保存的盘点支持修改)?`
    )
    .then(() => {
      form.value.checkStatus = state;
      let checkDetailModels = [];
      for (let i in form.value.checkDetailModels) {
        let item = form.value.checkDetailModels[i];
        checkDetailModels.push({
          goodCode: item.goodCode,
          stockNum: item.stockNum,
          profit_loss_nun_status:
            item.num - item.stockNum > 0
              ? 1
              : item.num - item.stockNum == 0
              ? 0
              : 2,
          num: item.num,
          input_num: item.input_num,
        });
      }
      form.value.checkDetailModels = checkDetailModels;
      checkApi
        .saveAndUpdateAndSubmit(form.value)
        .then((res) => {
          ElMessage({
            message: (state == 0 ? "保存" : "提交") + "成功",
            type: "success",
          });
          back();
        })
        .finally(() => {
          load.loading = false;
        });
    });
};
</script>
<style scoped lang="scss">
::v-deep(.el-table) {
  margin-top: 0;
  .el-form-item {
    margin-bottom: 0;
  }
}
</style>
