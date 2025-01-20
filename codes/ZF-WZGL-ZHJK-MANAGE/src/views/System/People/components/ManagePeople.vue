<template>
  <Dialog
    v-model:visible="visible"
    :loading="loading"
    :title="(id ? '编辑' : '新建') + '人员管理'"
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
      <el-form-item label="所属机构" prop="orgId">
        <org-list
          class="w-full"
          v-model="form.orgId"
          placeholder="请选择所属机构"
        ></org-list>
      </el-form-item>
      <el-form-item label="所属部门" prop="deptId">
        <department-list
          class="w-full"
          v-model="form.deptId"
          placeholder="请选择所属部门"
        ></department-list>
      </el-form-item>
      <el-form-item label="关联社区" prop="communityIdList">
        <community-list
          class="w-full"
          multiple
          v-model="form.communityIdList"
          placeholder="请选择关联社区"
        ></community-list>
      </el-form-item>
      <el-form-item label="姓名" prop="userName">
        <el-input v-model="form.userName" placeholder="请输入姓名" />
      </el-form-item>
      <el-form-item label="年龄" prop="birthday">
        <el-input v-model="form.birthday" placeholder="请输入年龄" />
      </el-form-item>
      <el-form-item label="手机号" prop="phone">
        <el-input v-model="form.phone" placeholder="请输入手机号" />
      </el-form-item>
      <el-form-item label="人员账号" prop="loginName">
        <el-input v-model="form.loginName" placeholder="请输入人员账号" />
      </el-form-item>
      <el-form-item label="登录密码" prop="passWord" v-if="!id">
        <el-input
          v-model="form.passWord"
          type="password"
          placeholder="请输登录密码"
        />
      </el-form-item>
      <el-form-item label="绑定角色" prop="roleId">
        <role-list
          class="w-full"
          v-model="form.roleId"
          placeholder="请选择绑定权限"
        ></role-list>
      </el-form-item>
    </el-form>
  </Dialog>
</template>
<script lang="ts" setup>
import Dialog from "../../../../components/Dialog/Dialog.vue";
import SelectOption from "../../../../components/SelectOption/SelectOption.vue";
import { reactive, ref, watch } from "vue";
import { ElMessage, FormRules } from "element-plus";
import { userApi } from "../../../../utils/Api";
import OrgList from "../../../common/components/OrgList.vue";
import DepartmentList from "../../../common/components/DepartmentList.vue";
import CommunityList from "../../../common/components/CommunityList.vue";
import RoleList from "../../../common/components/RoleList.vue";
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
let form: any = ref({});

const rules = reactive<FormRules>({
  userName: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
  deptId: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
  communityIdList: [
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
  birthday: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
  phone: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
  loginName: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
  passWord: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
  roleId: [
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
    userName: "",
    realName: "",
    deptId: "",
    communityIdList: [],
    orgId: "",
    birthday: "",
    phone: "",
    loginName: "",
    passWord: "",
    roleId: "",
  };
};
const getData = () => {
  userApi
    .findByUserId(props.id)
    .then((res) => {
      form.value = res.data;
      form.value.communityIdList = res.data.communityIds.split(",");
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
  console.log(form.value);
  userApi
    .saveOrUpdate(props.id ? "updateUser" : "save", form.value)
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
