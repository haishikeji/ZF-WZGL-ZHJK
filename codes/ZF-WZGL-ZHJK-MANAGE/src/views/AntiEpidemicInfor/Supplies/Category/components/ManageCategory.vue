<template>
  <Dialog
    v-model:visible="visible"
    :loading="loading"
    :title="(id ? '编辑' : '新建') + '类别信息'"
    @cancel="cancelSubmit"
    @ok="submitForm"
  >
    <el-form
      ref="ruleForm"
      :model="form"
      :show-message="false"
      :rules="rules"
      label-width="80px"
    >
      <el-form-item label="类别名称" prop="typeName">
        <el-input v-model="form.typeName" placeholder="请输入" />
      </el-form-item>
    </el-form>
  </Dialog>
</template>
<script lang="ts" setup>
import Dialog from "../../../../../components/Dialog/Dialog.vue";
import { reactive, ref, watch } from "vue";
import { ElMessage, FormRules } from "element-plus";
import { goodTypeApi } from "../../../../../utils/Api";
const loading = ref<boolean>(false);
const ruleForm = ref();
interface Props {
  id?: string;
  state?: number; //0新增1编辑
  visible?: boolean;
}
const emit = defineEmits<{
  (e: "update:visible", visible: boolean): void;
  (e: "refresh"): void;
}>();
const props = withDefaults(defineProps<Props>(), {
  visible: false,
  id: "",
  number: 0,
});
//表单数据
let form = ref();
let parentForm: any = {};
const rules = reactive<FormRules>({
  typeName: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
  b: [
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
    typeName: "",
  };
};
const getData = () => {
  goodTypeApi
    .findById(props.id)
    .then((res) => {
      if (props.state == 1) form.value = res.data;
      else {
        parentForm = res.data;
      }
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
  if (props.state == 0) {
    Object.assign(form.value, {
      parentId: parentForm.id,
      typeLevel: Number(parentForm.typeLevel) + 1,
      isLeaf: "0",
    });
  }
  goodTypeApi
    .saveOrUpdate(props.state == 1 ? "update" : "save", form.value)
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
