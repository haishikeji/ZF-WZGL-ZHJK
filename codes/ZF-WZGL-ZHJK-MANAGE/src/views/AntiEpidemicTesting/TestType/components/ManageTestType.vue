<template>
  <Dialog
    v-model:visible="visible"
    :loading="loading"
    :title="(id ? '编辑' : '新建') + '类型信息'"
    @cancel="cancelSubmit"
    @ok="submitForm"
  >
    <el-form
      ref="ruleForm"
      :model="form"
      :show-message="false"
      :rules="rules"
      label-width="110px"
    >
      <el-form-item label="类型名称" prop="checkTypeName">
        <el-input v-model="form.checkTypeName" placeholder="请输入类型名称" />
      </el-form-item>
      <el-form-item label="类型编号" prop="code">
        <el-input v-model="form.code" placeholder="请输入类型编号" />
      </el-form-item>
      <!-- <el-form-item label="支持预约时间" prop="startTime">
        <div class="flex-n">
          <el-form-item label="" prop="startTime">
            <el-time-picker v-model="form.startTime" class="w-120" format="HH:mm" value-format="HH:mm"
              placeholder="开始时间" />
          </el-form-item>
          <div class="ml-10 mr-10">-</div>
          <el-form-item label="" prop="endTime">
            <el-time-picker :disabled="form.startTime ? false : true" :disabled-hours="disabledHours"
              :disabled-minutes="disabledMinutes" v-model="form.endTime" class="w-120" format="HH:mm"
              value-format="HH:mm" placeholder="结束时间" />
          </el-form-item>
        </div>
      </el-form-item> -->
    </el-form>
  </Dialog>
</template>
<script lang="ts" setup>
import Dialog from "../../../../components/Dialog/Dialog.vue";
import { reactive, ref, watch } from "vue";
import { ElMessage } from "element-plus";
import { checkTypeApi } from "../../../../utils/Api";
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
  checkTypeName: [{ required: true, trigger: ["change", "blur"] }],
  code: [{ required: true, trigger: ["change", "blur"] }],
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
    checkTypeName: "",
    code: "",
  };
};
const getData = () => {
  checkTypeApi
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
  checkTypeApi
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
