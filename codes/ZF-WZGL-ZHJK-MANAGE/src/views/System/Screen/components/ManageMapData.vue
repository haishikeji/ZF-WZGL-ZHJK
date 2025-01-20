<template>
  <Dialog
    v-model:visible="visible"
    :loading="loading"
    width="800px"
    :title="(id ? '编辑' : '新建') + '地图数据'"
    @cancel="cancelSubmit"
    @ok="submitForm"
  >
    <el-form
      ref="ruleForm"
      :model="form"
      :show-message="false"
      :rules="rules"
      label-width="90px"
    >
      <el-row :gutter="10">
        <el-col :span="12">
          <el-form-item label="检测类型" prop="checkTypeId">
            <check-type-list
              class="w-full"
              v-model="form.checkTypeId"
              placeholder="请选择检测类型"
            ></check-type-list>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="统计时间" prop="statisticalTime">
            <el-date-picker
              v-model="form.statisticalTime"
              type="date"
              class="w-full"
              format="YYYY-MM-DD"
              value-format="YYYY-MM-DD"
              placeholder="选择时间"
            />
          </el-form-item>
        </el-col>
      </el-row>
      <div class="map-info-grid">
        <div class="label">南关区</div>
        <div class="value flex-n fac">
          <el-form-item label="现有确诊" class="flex-1" prop="ngAreaNew">
            <el-input-number
              :min="0"
              :step="1"
              step-strictly
              :controls="false"
              class="flex-1"
              v-model="form.ngAreaNew"
              placeholder="请输入数量"
            />
          </el-form-item>
          <el-form-item class="flex-1" label="新增确诊" prop="ngAreaOld">
            <el-input-number
              :min="0"
              :step="1"
              step-strictly
              :controls="false"
              class="flex-1"
              v-model="form.ngAreaOld"
              placeholder="请输入数量"
            />
          </el-form-item>
        </div>
        <div class="label">宽城区</div>
        <div class="value flex-n fac">
          <el-form-item label="现有确诊" class="flex-1" prop="kcAreaNew">
            <el-input-number
              :min="0"
              :step="1"
              step-strictly
              :controls="false"
              class="flex-1"
              v-model="form.kcAreaNew"
              placeholder="请输入数量"
            />
          </el-form-item>
          <el-form-item class="flex-1" label="新增确诊" prop="kcAreaOld">
            <el-input-number
              :min="0"
              :step="1"
              step-strictly
              :controls="false"
              class="flex-1"
              v-model="form.kcAreaOld"
              placeholder="请输入数量"
            />
          </el-form-item>
        </div>
        <div class="label">朝阳区</div>
        <div class="value flex-n fac">
          <el-form-item label="现有确诊" class="flex-1" prop="cyAreaNew">
            <el-input-number
              :min="0"
              :step="1"
              step-strictly
              :controls="false"
              class="flex-1"
              v-model="form.cyAreaNew"
              placeholder="请输入数量"
            />
          </el-form-item>
          <el-form-item class="flex-1" label="新增确诊" prop="cyAreaOld">
            <el-input-number
              :min="0"
              :step="1"
              step-strictly
              :controls="false"
              class="flex-1"
              v-model="form.cyAreaOld"
              placeholder="请输入数量"
            />
          </el-form-item>
        </div>
        <div class="label">二道区</div>
        <div class="value flex-n fac">
          <el-form-item label="现有确诊" class="flex-1" prop="edAreaNew">
            <el-input-number
              :min="0"
              :step="1"
              step-strictly
              :controls="false"
              class="flex-1"
              v-model="form.edAreaNew"
              placeholder="请输入数量"
            />
          </el-form-item>
          <el-form-item class="flex-1" label="新增确诊" prop="edAreaOld">
            <el-input-number
              :min="0"
              :step="1"
              step-strictly
              :controls="false"
              class="flex-1"
              v-model="form.edAreaOld"
              placeholder="请输入数量"
            />
          </el-form-item>
        </div>
        <div class="label">绿园区</div>
        <div class="value flex-n fac">
          <el-form-item label="现有确诊" class="flex-1" prop="lyAreaNew">
            <el-input-number
              :min="0"
              :step="1"
              step-strictly
              :controls="false"
              class="flex-1"
              v-model="form.lyAreaNew"
              placeholder="请输入数量"
            />
          </el-form-item>
          <el-form-item class="flex-1" label="新增确诊" prop="lyAreaOld">
            <el-input-number
              :min="0"
              :step="1"
              step-strictly
              :controls="false"
              class="flex-1"
              v-model="form.lyAreaOld"
              placeholder="请输入数量"
            />
          </el-form-item>
        </div>
        <div class="label">双阳区</div>
        <div class="value flex-n fac">
          <el-form-item label="现有确诊" class="flex-1" prop="syAreaNew">
            <el-input-number
              :min="0"
              :step="1"
              step-strictly
              :controls="false"
              class="flex-1"
              v-model="form.syAreaNew"
              placeholder="请输入数量"
            />
          </el-form-item>
          <el-form-item class="flex-1" label="新增确诊" prop="syAreaOld">
            <el-input-number
              :min="0"
              :step="1"
              step-strictly
              :controls="false"
              class="flex-1"
              v-model="form.syAreaOld"
              placeholder="请输入数量"
            />
          </el-form-item>
        </div>
        <div class="label">九台区</div>
        <div class="value flex-n fac">
          <el-form-item label="现有确诊" class="flex-1" prop="jtAreaNew">
            <el-input-number
              :min="0"
              :step="1"
              step-strictly
              :controls="false"
              class="flex-1"
              v-model="form.jtAreaNew"
              placeholder="请输入数量"
            />
          </el-form-item>
          <el-form-item class="flex-1" label="新增确诊" prop="jtAreaOld">
            <el-input-number
              :min="0"
              :step="1"
              step-strictly
              :controls="false"
              class="flex-1"
              v-model="form.jtAreaOld"
              placeholder="请输入数量"
            />
          </el-form-item>
        </div>
        <div class="label">农安县</div>
        <div class="value flex-n fac">
          <el-form-item label="现有确诊" class="flex-1" prop="naAreaNew">
            <el-input-number
              :min="0"
              :step="1"
              step-strictly
              :controls="false"
              class="flex-1"
              v-model="form.naAreaNew"
              placeholder="请输入数量"
            />
          </el-form-item>
          <el-form-item class="flex-1" label="新增确诊" prop="naAreaOld">
            <el-input-number
              :min="0"
              :step="1"
              step-strictly
              :controls="false"
              class="flex-1"
              v-model="form.naAreaOld"
              placeholder="请输入数量"
            />
          </el-form-item>
        </div>
        <div class="label">榆树市</div>
        <div class="value flex-n fac">
          <el-form-item label="现有确诊" class="flex-1" prop="ysAreaNew">
            <el-input-number
              :min="0"
              :step="1"
              step-strictly
              :controls="false"
              class="flex-1"
              v-model="form.ysAreaNew"
              placeholder="请输入数量"
            />
          </el-form-item>
          <el-form-item class="flex-1" label="新增确诊" prop="ysAreaOld">
            <el-input-number
              :min="0"
              :step="1"
              step-strictly
              :controls="false"
              class="flex-1"
              v-model="form.ysAreaOld"
              placeholder="请输入数量"
            />
          </el-form-item>
        </div>
        <div class="label">德惠市</div>
        <div class="value flex-n fac">
          <el-form-item label="现有确诊" class="flex-1" prop="dhAreaNew">
            <el-input-number
              :min="0"
              :step="1"
              step-strictly
              :controls="false"
              class="flex-1"
              v-model="form.dhAreaNew"
              placeholder="请输入数量"
            />
          </el-form-item>
          <el-form-item class="flex-1" label="新增确诊" prop="dhAreaOld">
            <el-input-number
              :min="0"
              :step="1"
              step-strictly
              :controls="false"
              class="flex-1"
              v-model="form.dhAreaOld"
              placeholder="请输入数量"
            />
          </el-form-item>
        </div>
        <div class="label">公主岭市</div>
        <div class="value flex-n fac">
          <el-form-item label="现有确诊" class="flex-1" prop="gzlAreaNew">
            <el-input-number
              :min="0"
              :step="1"
              step-strictly
              :controls="false"
              class="flex-1"
              v-model="form.gzlAreaNew"
              placeholder="请输入数量"
            />
          </el-form-item>
          <el-form-item class="flex-1" label="新增确诊" prop="gzlAreaOld">
            <el-input-number
              :min="0"
              :step="1"
              step-strictly
              :controls="false"
              class="flex-1"
              v-model="form.gzlAreaOld"
              placeholder="请输入数量"
            />
          </el-form-item>
        </div>
      </div>
    </el-form>
  </Dialog>
</template>
<script lang="ts" setup>
import Dialog from "../../../../components/Dialog/Dialog.vue";
import SelectOption from "../../../../components/SelectOption/SelectOption.vue";
import { reactive, ref, watch } from "vue";
import { ElMessage, FormRules } from "element-plus";
import { mapDataApi } from "../../../../utils/Api";
import CheckTypeList from "../../../common/components/CheckTypeList.vue";
const loading = ref<boolean>(false);
const ruleForm = ref();
interface Props {
  id?: string;
  visible?: boolean;
}
const emit = defineEmits<{
  (e: "update:visible", visible: boolean): void;
  (e: "refresh"): void;
}>();
const props = withDefaults(defineProps<Props>(), {
  visible: false,
  id: "",
});
//表单数据
let form = ref();

const rules = reactive<FormRules>({
  checkTypeId: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
  statisticalTime: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
  ngAreaNew: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
  ngAreaOld: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
  kcAreaNew: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
  kcAreaOld: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
  cyAreaNew: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
  cyAreaOld: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
  edAreaNew: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
  edAreaOld: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
  lyAreaNew: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
  lyAreaOld: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
  syAreaNew: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
  syAreaOld: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
  jtAreaNew: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
  jtAreaOld: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
  naAreaNew: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
  naAreaOld: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
  ysAreaNew: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
  ysAreaOld: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
  dhAreaNew: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
  dhAreaOld: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
  gzlAreaNew: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
  gzlAreaOld: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
});
//监听visible变化，关闭弹窗
watch(
  () => props.visible,
  (newval) => {
    getInitValue();
    if (props.id) {
      loading.value = true;
      getData();
    }
    emit("update:visible", newval);
  }
);
const getInitValue = () => {
  form.value = {
    checkTypeId: "",
    statisticalTime: "",
    ngAreaNew: "",
    ngAreaOld: "",
    kcAreaNew: "",
    kcAreaOld: "",
    cyAreaNew: "",
    cyAreaOld: "",
    edAreaNew: "",
    edAreaOld: "",
    lyAreaNew: "",
    lyAreaOld: "",
    syAreaNew: "",
    syAreaOld: "",
    jtAreaNew: "",
    jtAreaOld: "",
    naAreaNew: "",
    naAreaOld: "",
    ysAreaNew: "",
    ysAreaOld: "",
    dhAreaNew: "",
    dhAreaOld: "",
    gzlAreaNew: "",
    gzlAreaOld: "",
  };
};
const getData = () => {
  mapDataApi
    .findById(props.id)
    .then((res) => {
      form.value = res.data;
    })
    .finally(() => {
      loading.value = false;
    });
};
// 表单验证
const submitForm = async () => {
  if (!ruleForm.value) return;
  await ruleForm.value.validate((valid: any, fields: any) => {
    if (valid) {
      loading.value = true;
      saveOrUpdate();
    } else {
      ElMessage({
        message: "请检查是否有必填项未填写",
        type: "warning",
      });
    }
  });
};
const saveOrUpdate = () => {
  mapDataApi
    .saveOrUpdate(props.id ? "update" : "save", form.value)
    .then((res) => {
      ElMessage({
        message: (props.id ? "修改" : "保存") + "成功",
        type: "success",
      });
      emit("refresh");
      cancelSubmit();
    })
    .finally(() => {
      loading.value = false;
    });
};
// 取消提交,重置表单
const cancelSubmit = () => {
  // ruleForm.value.resetFields();
  emit("update:visible", false);
};
</script>
<style scoped lang="scss">
.map-info-grid {
  display: grid;
  grid-template-columns: 100px 1fr;
  border: 1px solid #ebeef5;
  background: #ebeef5;
  grid-gap: 1px;

  ::v-deep(.el-form-item) {
    margin-bottom: 0;
  }

  > div {
    padding: 10px;
    font-size: 14px;
    display: flex;
    align-items: center;
  }

  .label {
    background: #f5f7fa;
    color: #606266;
    font-weight: 700;
  }

  .value {
    color: #303133;
    background: white;
  }
}
</style>
