<!-- 资讯详情 -->
<template>
  <info-card>
    <template #header>
      <div class="flex-n fac fjb">
        <div v-if="state != 2">{{ (id ? "编辑" : "新建") + "预约" }}</div>
        <div v-else>预约详情</div>
        <div style="margin: -10px 0">
          <el-button :icon="ArrowLeftBold" @click="emit('back')"
            >返回</el-button
          >
          <el-button type="primary" @click="submitForm">保存</el-button>
        </div>
      </div>
    </template>
    <el-scrollbar>
      <!-- 新增得时候都是未检测 需要录入信息  录入结果后自动变成已检测？ 然后编辑页面可以编辑已检测得数据 -->
      <el-form
        ref="ruleForm"
        :model="form"
        :disabled="state == 2"
        :show-message="false"
        :rules="rules"
        label-width="140px"
      >
        <div class="grid-content">
          <div class="label">
            <div class="label-content">预约信息</div>
          </div>
          <div class="content">
            <el-form-item label="所属社区" prop="communityId">
              <CommunityList
                class="w-full"
                v-model="form.communityId"
                placeholder="请选择所属社区"
              ></CommunityList>
            </el-form-item>
            <el-form-item label="检测类型" prop="checkTypeId">
              <CheckTypeList
                class="w-full"
                v-model="form.checkTypeId"
                @getAllData="getCheckTypeInfo"
                placeholder="请选择检测类型"
              ></CheckTypeList>
            </el-form-item>

            <el-form-item label="预约时间" prop="reservationTime">
              <el-date-picker
                v-model="form.reservationTime"
                type="datetime"
                class="w-full"
                format="YYYY-MM-DD HH:mm:ss"
                value-format="YYYY-MM-DD HH:mm:ss"
                placeholder="选择时间"
              />
            </el-form-item>

            <el-form-item label="检测状态" prop="idnum" v-if="state == 2">
              <el-radio-group v-model="form.idnum">
                <el-radio label="2">未检测</el-radio>
                <el-radio label="1">已检测</el-radio>
              </el-radio-group>
            </el-form-item>
          </div>
          <div class="label">
            <div class="label-content">就诊人信息</div>
          </div>
          <div class="content">
            <el-form-item label="就诊人姓名" prop="personnelName">
              <el-input
                v-model="form.personnelName"
                placeholder="请输入就诊人姓名"
              />
            </el-form-item>
            <el-form-item label="就诊人身份证号" prop="idnum">
              <el-input
                v-model="form.idnum"
                maxlength="18"
                placeholder="请输入就诊人身份证号"
              />
            </el-form-item>
            <el-form-item label="就诊人性别" prop="sex">
              <el-radio-group v-model="form.sex">
                <el-radio label="1">男</el-radio>
                <el-radio label="2">女</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="就诊人电话" prop="phone">
              <el-input v-model="form.phone" placeholder="请输入就诊人电话" />
            </el-form-item>
            <el-form-item label="就诊人地址" prop="personnelAdd">
              <el-input
                v-model="form.personnelAdd"
                placeholder="请输入就诊人地址"
              />
            </el-form-item>
          </div>
          <template v-if="id && form.checkResultStatus == 1">
            <div class="label">
              <div class="label-content">检测结果信息</div>
            </div>

            <div class="content">
              <el-form-item label="检测时间" prop="checkTime">
                <el-date-picker
                  v-model="form.checkTime"
                  type="datetime"
                  class="w-full"
                  format="YYYY-MM-DD HH:mm:ss"
                  value-format="YYYY-MM-DD HH:mm:ss"
                  placeholder="选择时间"
                />
              </el-form-item>
              <el-form-item label="检测负责人" prop="checkPrincipal">
                <el-input
                  v-model="form.checkPrincipal"
                  placeholder="请输入检测负责人"
                />
              </el-form-item>
              <el-form-item label="检测内容" prop="checkContent">
                <el-input
                  v-model="form.checkContent"
                  :rows="4"
                  resize="none"
                  type="textarea"
                  placeholder="请输入检测内容"
                />
              </el-form-item>
              <el-form-item label="显示结果" prop="checkResultDisplay">
                <el-radio-group v-model="form.checkResultDisplay">
                  <el-radio label="1">绿色无症状</el-radio>
                  <el-radio label="2">黄色预警</el-radio>
                  <el-radio label="3">红色预警</el-radio>
                </el-radio-group>
              </el-form-item>
              <el-form-item label="检测结果" prop="checkResult">
                <el-input
                  v-model="form.checkResult"
                  :rows="4"
                  resize="none"
                  type="textarea"
                  placeholder="请输入检测结果"
                />
              </el-form-item>
              <el-form-item label="检测报告" prop="checkResultUrl">
                <BatchUpload
                  title="报告上传"
                  accept=".xlsx,.xls,.doc,.docx,.zip,.rar,.pdf,.png,.jpg,.jpeg"
                  v-if="!form.checkResultUrl"
                  @uploadData="uploadData"
                ></BatchUpload>
                <div v-else class="flex-n">
                  <div>{{ form.checkResultFileName }}</div>
                  <el-button type="text" class="ml-10" @click="delFile"
                    >删除</el-button
                  >
                </div>
              </el-form-item>
              <el-form-item label="疫苗类别" prop="vaccineCategory">
                <el-input
                  v-model="form.vaccineCategory"
                  placeholder="请输入疫苗类别"
                />
              </el-form-item>
            </div>
          </template>
        </div>
      </el-form>
    </el-scrollbar>
  </info-card>
</template>
<script lang="ts" setup>
import InfoCard from "../../../../components/InfoCard/InfoCard.vue";
import { ElMessage, ElMessageBox, FormRules } from "element-plus";
import BatchUpload from "../../../../components/BatchUpload/BatchUpload.vue";
import SelectOption from "../../../../components/SelectOption/SelectOption.vue";
import { ArrowLeftBold } from "@element-plus/icons-vue";
import { onMounted, ref, reactive } from "vue";
import commMethods from "../../../../assets/js/common";
import { reservationtApi } from "../../../../utils/Api";
import CheckTypeList from "../../../common/components/CheckTypeList.vue";
import CommunityList from "../../../common/components/CommunityList.vue";

const ruleForm = ref();
const loading = ref<boolean>(false);

interface Props {
  id?: string;
  visible?: boolean;
  state?: string | number;
}

const props = withDefaults(defineProps<Props>(), {
  visible: false,
  state: 0,
  id: "",
});
let form: any = ref({});

const rules = reactive<FormRules>({
  communityId: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
  checkTypeId: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
  reservationTime: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
  personnelName: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
  idnum: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
  sex: [
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
  personnelAdd: [
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
  checkPrincipal: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
  checkContent: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
  checkResult: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
  checkResultDisplay: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
  checkResultUrl: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
  vaccineCategory: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
});
const emit = defineEmits<{
  (e: "back"): void;
  (e: "update:visible", visible: boolean): void;
  (e: "refresh"): void;
}>();
onMounted(() => {
  getInitValue();
  if (props.id) {
    loading.value = true;
    getData();
  }
});
const getInitValue = () => {
  form.value = {
    communityId: "",
    checkTypeId: "",
    checkTypeName: "",
    reservationTime: "",
    personnelName: "",
    sex: "",
    idnum: "",
    phone: "",
    personnelAdd: "",
    checkResultStatus: "",
    checkTime: "",
    checkPrincipal: "",
    checkContent: "",
    checkResult: "",
    checkResultDisplay: "",
    checkResultUrl: "",
    vaccineCategory: "",
  };
};
const uploadData = (data: any) => {
  form.value.checkResultUrl = data.url;
  form.value.checkResultFileName = data.name;
};
const getData = () => {
  reservationtApi
    .findById(props.id)
    .then((res) => {
      form.value = res.data;
    })
    .finally(() => {
      loading.value = false;
    });
};
const delFile = () => {
  form.value.checkResultUrl = "";
  form.value.checkResultFileName = "";
};
const getCheckTypeInfo = (data: any) => {
  form.value.checkTypeName = data.checkTypeName;
};
// 表单验证
const submitForm = async () => {
  if (!ruleForm.value) return;
  await ruleForm.value.validate((valid: any, fields: any) => {
    if (valid) {
      loading.value = true;
      saveOrUpdate();
    } else {
      // console.error(fields)
      ElMessage({
        message: "请检查是否有必填项未填写",
        type: "warning",
      });
    }
  });
};
const saveOrUpdate = () => {
  commMethods.getMessageBox(`确定要保存当前预约信息？`).then(() => {
    reservationtApi
      .saveOrUpdate(
        props.id ? "updateReservationt" : "saveReservationt",
        form.value
      )
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
  });
};
// 取消提交,重置表单
const cancelSubmit = () => {
  // ruleForm.value.resetFields();
  emit("update:visible", false);
};
</script>
<style scoped lang="scss">
.grid-content {
  display: grid;
  margin: 0 100px;
  grid-template-columns: 150px 1fr;
  grid-gap: 0 1px;
  background: #e1e1e1;
  border: 1px solid #e1e1e1;

  .label {
    background: #f9f9f9;

    .label-content {
      margin-top: 20px;
      background: #808e99;
      padding: 0 20px;
      height: 40px;
      line-height: 40px;
      font-size: 14px;
      color: white;
      flex: 1;
      position: relative;
      margin-left: -1px;

      &::before {
        content: "";
        display: block;
        position: absolute;
        width: 0;
        height: 0;
        right: -20px;
        border-left: 20px solid #808e99;
        border-top: 20px solid transparent;
        border-bottom: 20px solid transparent;
      }
    }
  }

  .content {
    background: white;
    padding: 20px 80px;
  }
}
</style>
