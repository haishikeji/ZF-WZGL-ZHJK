<template>
  <Dialog
    width="600px"
    v-model:visible="visible"
    :loading="loading"
    :title="(id ? '编辑' : '新建') + '物资信息'"
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
      <el-form-item label="物资类别" prop="goodTypeCodes">
        <category-list
          class="w-full"
          v-model="form.goodTypeCodes"
          placeholder="请选择物资类别"
        ></category-list>
      </el-form-item>
      <el-form-item label="物资名称" prop="goodName">
        <el-input v-model="form.goodName" placeholder="请输入物资名称" />
      </el-form-item>
      <el-form-item label="物资图片" prop="goodPicList">
        <picture-upload
          v-model:list="form.goodPicList"
          @uploadData="uploadData"
        ></picture-upload>
      </el-form-item>
      <el-form-item label="规格型号" prop="specsModelName">
        <el-input v-model="form.specsModelName" placeholder="请输入规格型号" />
      </el-form-item>
      <el-form-item label="计量单位" prop="measureUnitId">
        <SelectOption
          class="w-full"
          :list="data.measureUnitList"
          v-model="form.measureUnitId"
          @getAllData="changeMeasure"
          placeholder="请选择计量单位"
        ></SelectOption>
      </el-form-item>
      <div class="flex-n">
        <el-form-item class="flex-1" label="库存上限" prop="stockUpper">
          <el-input-number
            class="w-full"
            :controls="false"
            v-model="form.stockUpper"
            placeholder="请输入库存上限"
          />
        </el-form-item>
        <el-form-item class="flex-1 ml-10" label="库存下限" prop="stockLower">
          <el-input-number
            class="w-full"
            :controls="false"
            v-model="form.stockLower"
            placeholder="请输入库存下限"
          />
        </el-form-item>
      </div>
    </el-form>
  </Dialog>
</template>
<script lang="ts" setup>
import Dialog from "../../../../../components/Dialog/Dialog.vue";
import SelectOption from "../../../../../components/SelectOption/SelectOption.vue";
import { reactive, ref, watch } from "vue";
import { ElMessage, FormRules } from "element-plus";
import { Plus } from "@element-plus/icons-vue";
import { goodApi } from "../../../../../utils/Api";
import type { UploadProps, UploadUserFile } from "element-plus";
import commMethods from "../../../../../assets/js/common";
import CategoryList from "../../../../common/components/CategoryList.vue";
import PictureUpload from "../../../../../components/PictureUpload/PictureUpload.vue";

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
//   const form = reactive(reloadAssessmentData())
let form: any = ref();

const rules = reactive<FormRules>({
  goodTypeCodes: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
  goodName: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
  goodPicList: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
  specsModelName: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
  measureUnitId: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
  stockUpper: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
  stockLower: [
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
    getDicList();
    getInitValue();
    if (props.id) {
      loading.value = true;
      getData();
    }
    emit("update:visible", newval);
  }
);
const data: any = reactive({
  measureUnitList: [],
});
// 获取字典
const getDicList = () => {
  data.measureUnitList =
    commMethods.getDicByNameOrTypeList("measure_unit") || [];
};
const getInitValue = () => {
  form.value = {
    goodTypeCodes: "",
    goodName: "",
    goodPic: "",
    goodPicList: [],
    specsModelName: "",
    measureUnitId: "",
    measureUnit: "",
    stockUpper: "",
    stockLower: "",
  };
};
const uploadData = (data: string) => {
  console.log("上传的图片地址", data);
  form.value.goodPic = data;
  let goodPicList = [];
  goodPicList.push({
    name: "",
    url: data,
  });
  form.value.goodPicList = goodPicList;
};
const changeMeasure = (data: any) => {
  console.log(data);
  form.value.measureUnit = data.label;
};
const getData = () => {
  goodApi
    .findById(props.id)
    .then((res) => {
      form.value = res.data;

      form.value.goodTypeCodes = form.value.goodTypeCodeList.split(",");
      let goodPicList = [];
      goodPicList.push({
        name: "",
        url: res.data.goodPic,
      });
      form.value.goodPicList = goodPicList;
      console.log(form.value);
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
  if (form.value.stockUpper < form.value.stockLower) {
    loading.value = false;
    return ElMessage({
      message: "库存下限不能大于库存上限",
      type: "error",
    });
  }
  form.value.goodPic = form.value.goodPicList[0].url;
  form.value.goodTypeCode =
    form.value.goodTypeCodes[form.value.goodTypeCodes.length - 1];
  form.value.goodTypeCodeList = form.value.goodTypeCodes.join(",");
  goodApi
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
