<!-- 物资入库 -->
<template>
  <info-card>
    <template #header>
      <div class="flex-n fac fjb">
        <div>物资入库</div>
        <div style="margin: -10px 0">
          <el-button :icon="ArrowLeftBold" @click="back">返回</el-button>
          <el-button type="warning" @click="submitForm(1)"
            >提交入库申请单</el-button
          >

          <el-button type="primary" @click="submitForm(0)">保存</el-button>
        </div>
      </div>
    </template>
    <el-form
      ref="ruleForm"
      :model="form"
      :show-message="false"
      :rules="rules"
      label-width="100px"
    >
      <container-table
        :tableData="form.stockInputModels"
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
              <!-- 仓库目前默认为1个，所以新增/编辑都是默认,以后若仓库不唯一，新增/编辑支持仓库的修改 -->
              <el-form-item label="仓库名称" prop="storeNo" class="flex-1">
                <StoreList
                  class="w-full"
                  :disabled="state == 2"
                  v-model="form.storeNo"
                  placeholder="请选择仓库"
                ></StoreList>
              </el-form-item>
              <el-form-item label="供应商名称" prop="supplierNo" class="flex-1">
                <SupplierList
                  class="w-full"
                  v-model="form.supplierNo"
                  placeholder="请选择供应商"
                >
                </SupplierList>
              </el-form-item>
            </div>

            <ZTitle title="物资信息">
              <MaterialSelection
                :check-list="form.stockInputModels"
                @getCheckList="getCheckList"
              >
              </MaterialSelection>
            </ZTitle>
          </div>
        </template>
        <template #item_input_num="scope">
          <el-form-item
            v-if="scope.index >= 0"
            label=""
            :prop="'stockInputModels.' + scope.index + '.input_num'"
            required
            label-width="0"
          >
            <el-input-number
              class="w-full"
              :controls="false"
              :min="0"
              v-model="scope.row.input_num"
              placeholder="数量"
            />
          </el-form-item>
        </template>
        <!--        <template #item_gridId="scope">-->
        <!--          1-->
        <!--        </template>-->
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
import SelectOption from "../../../../../components/SelectOption/SelectOption.vue";
import ContainerTable from "../../../../../components/ContainerTable/ContainerTable.vue";
import { ElMessage, ElMessageBox, FormRules } from "element-plus";
import MaterialSelection from "../../../../common/components/MaterialSelection.vue";
import ZTitle from "../../../../../components/ZTitle/ZTitle.vue";
import { ArrowLeftBold, DeleteFilled } from "@element-plus/icons-vue";
import commMethods from "../../../../../assets/js/common";
import { onMounted, reactive, ref, watch } from "vue";
import StoreList from "../../../../common/components/StoreList.vue";
import SupplierList from "../../../../common/components/SupplierList.vue";
import { stockInputApplyApi } from "../../../../../utils/Api";

interface Props {
  visible?: boolean;
  state?: string | number;
  id?: string;
  no?: string;
}

const props = withDefaults(defineProps<Props>(), {
  visible: false,
  id: "",
  no: "",
});
const load = reactive(commMethods.loadData()); //加载所需数据

let form: any = ref({});
// //监听visible变化，关闭弹窗
// watch(
//   () => props.visible,
//   (newval) => {
//     emit("update:visible", newval);
//   }
// );
const ruleForm = ref();
const rules = reactive<FormRules>({
  storeNo: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
  supplierNo: [
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
  { label: "数量", prop: "input_num", width: 120, slot: true, fixed: "right" },
  // {label: "库位", prop: "gridId", width: 160, slot: true, fixed: 'right'},
]);
const emit = defineEmits<{
  (e: "update:visible", visible: boolean): void;
  (e: "back"): void;
}>();
onMounted(() => {
  console.log(props.state);
  getInitValue();
  if (props.state == 1) {
    getDataInfo();
  } else {
    load.loading = false;
  }
});

const getInitValue = () => {
  form.value = {
    storeNo: "",
    supplierNo: "",
    stockInputModels: [],
  };
};
// 列表
const getDataInfo = () => {
  stockInputApplyApi
    .findStockInputApplyByInputNo(props.no)
    .then((res) => {
      form.value = res.data;
      form.value.stockInputModels = res.data.stockInputModels || [];
      console.log(form.value);
    })
    .finally(() => {
      load.loading = false;
    });
};
//选中的物资
const getCheckList = (data: any) => {
  let noData = [];
  //原先没有了就删除
  for (let i = 0; i < form.value.stockInputModels.length; i++) {
    let k = -1;
    for (let j = 0; j < data.length; j++) {
      if (data[j].goodCode == form.value.stockInputModels[i].goodCode) {
        k = j;
        break;
      }
    }
    if (k == -1) {
      form.value.stockInputModels.splice(i, 1);
      i--;
    }
  }
  //原先没有的加进来
  for (let i = 0; i < data.length; i++) {
    let k = -1;
    for (let j = 0; j < form.value.stockInputModels.length; j++) {
      if (data[i].goodCode == form.value.stockInputModels[j].goodCode) {
        k = j;
        break;
      }
    }
    if (k == -1) {
      noData.push(data[i]);
    }
  }
  form.value.stockInputModels = form.value.stockInputModels.concat(noData);
};
const delData = (idx: number) => {
  console.log(idx);
  commMethods.getMessageBox(`确定要删除当前物资?`).then(() => {
    form.value.stockInputModels.splice(idx, 1);
    ElMessage({
      type: "success",
      message: "删除成功",
    });
  });
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
  if (form.value.stockInputModels.length == 0) {
    return ElMessage.warning("请选择需要入库的物资!");
  }
  commMethods
    .getMessageBox(
      state == 1
        ? `确定已完成物资的录入，提交入库申请单?`
        : `确定临时保存当前的入库申请单?`
    )
    .then(() => {
      console.log(form.value.stockInputModels, form.value);
      form.value.approveStatus = state;
      let stockInputModels = [];
      for (let i in form.value.stockInputModels) {
        let item = form.value.stockInputModels[i];
        stockInputModels.push({
          goodCode: item.goodCode,
          gridId: "1",
          input_num: item.input_num,
        });
      }
      form.value.stockInputModels = stockInputModels;
      stockInputApplyApi
        .saveAndUpdateAndSubmit(form.value)
        .then((res) => {
          ElMessage({
            message: (state == 0 ? "临时" : "") + "保存成功",
            type: "success",
          });
          back();
        })
        .finally(() => {
          load.loading = false;
        });
    });
};

const back = () => {
  emit("back");
  emit("update:visible", false);
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
