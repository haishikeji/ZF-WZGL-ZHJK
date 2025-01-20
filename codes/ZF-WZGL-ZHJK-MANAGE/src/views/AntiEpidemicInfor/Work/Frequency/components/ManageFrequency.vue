<template>
  <Dialog
    v-model:visible="visible"
    :loading="loading"
    :title="(id ? '编辑' : '新建') + '班次信息'"
    @cancel="cancelSubmit"
    @ok="submitForm"
  >
    <el-form
      ref="ruleForm"
      :model="form"
      :show-message="false"
      :rules="rules"
      label-width="100px"
    >
      <el-form-item label="班次编号" prop="scheduleType">
        <el-input v-model="form.scheduleType" placeholder="请输入" />
      </el-form-item>
      <el-form-item label="班次名称" prop="scheduleTypeName">
        <el-input v-model="form.scheduleTypeName" placeholder="请输入" />
      </el-form-item>

      <el-form-item label="上下班时间" prop="startTime">
        <div class="flex-n">
          <el-form-item label="" prop="startTime">
            <el-time-picker
              v-model="form.startTime"
              class="w-120"
              format="HH:mm"
              value-format="HH:mm"
              placeholder="上班时间"
            />
          </el-form-item>
          <div class="ml-10 mr-10">-</div>
          <el-form-item label="" prop="endTime">
            <el-time-picker
              :disabled="form.startTime ? false : true"
              :disabled-hours="disabledHours"
              :disabled-minutes="disabledMinutes"
              v-model="form.endTime"
              class="w-120"
              format="HH:mm"
              value-format="HH:mm"
              placeholder="下班时间"
            />
          </el-form-item>
        </div>
      </el-form-item>
    </el-form>
  </Dialog>
</template>
<script lang="ts" setup>
import Dialog from "../../../../../components/Dialog/Dialog.vue";
import { reactive, ref, watch } from "vue";
import { ElMessage, FormRules } from "element-plus";
import { scheduleTypeApi } from "../../../../../utils/Api";
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
let form: any = ref();

const rules = reactive<FormRules>({
  scheduleTypeName: [{ required: true, trigger: ["change", "blur"] }],
  scheduleType: [{ required: true, trigger: ["change", "blur"] }],
  startTime: [{ required: true, trigger: ["change", "blur"] }],
  endTime: [{ required: true, trigger: ["change", "blur"] }],
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
    scheduleTypeName: "",
    scheduleType: "",
    startTime: "",
    endTime: "",
  };
};
watch(
  () => form.startTime,
  (newval, oldval) => {
    form.endTime = "";
  }
);
const disabledHours = () => {
  let startTime = (form.startTime || "00:00").split(":");
  let result = [];
  for (
    let i = 0;
    i <
    (Number(startTime[1]) != 59
      ? Number(startTime[0])
      : Number(startTime[0]) + 1);
    i++
  ) {
    result.push(i);
  }
  return result;
};
const disabledMinutes = (hour: number) => {
  let startTime = (form.startTime || "00:00").split(":");
  let result = [];
  if (Number(startTime[0]) == hour) {
    for (let i = 0; i <= Number(startTime[1]); i++) {
      result.push(i);
    }
  } else if (Number(startTime[0]) > hour) {
    for (let i = 0; i < 59; i++) {
      result.push(i);
    }
  }

  return result;
};
const getData = () => {
  scheduleTypeApi
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
  scheduleTypeApi
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
  ruleForm.value.resetFields();
  emit("update:visible", false);
};
</script>
<style scoped lang="scss"></style>
