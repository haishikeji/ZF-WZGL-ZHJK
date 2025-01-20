<template>
  <Dialog
    v-model:visible="visible"
    :loading="loading"
    :title="(id ? '编辑' : '新建') + '部门信息'"
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
      <el-form-item label="部门名称" prop="departName">
        <el-input v-model="form.departName" placeholder="请输入部门名称" />
      </el-form-item>
      <el-form-item label="所属机构" prop="orgId">
        <org-list
          class="w-full"
          v-model="form.orgId"
          placeholder="选择所属机构"
          @getAllData="getOrgData"
        ></org-list>
      </el-form-item>
      <el-form-item label="职能描述" prop="departFunction">
        <el-input
          v-model="form.departFunction"
          :rows="8"
          resize="none"
          type="textarea"
          placeholder="请输入职能描述"
        />
      </el-form-item>
    </el-form>
  </Dialog>
</template>
<script lang="ts" setup>
import Dialog from "../../../../components/Dialog/Dialog.vue";
import SelectOption from "../../../../components/SelectOption/SelectOption.vue";
import { reactive, ref, watch } from "vue";
import { ElMessage, FormRules } from "element-plus";
import { departmentApi } from "../../../../utils/Api";
import OrgList from "../../../common/components/OrgList.vue";
const loading = ref<boolean>(false);
const ruleForm = ref();
interface Props {
  id?: string;
  visible?: boolean;
}
const props = withDefaults(defineProps<Props>(), {
  visible: false,
  id: "",
});
const emit = defineEmits<{
  (e: "update:visible", visible: boolean): void;
  (e: "refresh"): void;
}>();

//表单数据
let form: any = ref({
  outputNo: "",
  applyDept: "",
  applyUserName: "",
  applyPhone: "",
});

const rules = reactive<FormRules>({
  departName: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
  orgId: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
  departFunction: [
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
    departName: "",
    orgId: "",
    departFunction: "",
    orgName: "",
  };
};
const getOrgData = (data: any) => {
  console.log(data);
  form.value.orgName = data.orgName;
};
const getData = () => {
  departmentApi
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
  departmentApi
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
<style scoped lang="scss"></style>
