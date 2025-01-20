<template>
  <el-cascader
    :show-all-levels="showAllLevel"
    :options="list"
    :props="props1"
    :clearable="clearable"
    :placeholder="placeholder"
  />
</template>
<script lang="ts" setup>
import { reactive, ref, onMounted } from "vue";
import { departmentApi, goodTypeApi } from "../../../utils/Api";
const list = ref([]);
const props1 = {
  checkStrictly: true,
  label: "typeName",
  value: "typeCode",
  multiple: false,
};

interface Props {
  size?: string;
  placeholder?: string;
  value?: string | number | number[] | string[];
  clearable?: boolean;
  disabled?: boolean;
  filterable?: boolean;
  multiple?: boolean;
  showAllLevel?: boolean;
}
// 向外抛值
const emit = defineEmits<{
  //勾选复选框
  (e: "getAllData", data: any): void;
}>();
const props = withDefaults(defineProps<Props>(), {
  placeholder: "请选择",
  clearable: true,
  disabled: false,
  filterable: false,
  showAllLevel: false,
});
onMounted(() => {
  getgoodTypeList();
});
// 获取物资类别列表
const getgoodTypeList = () => {
  goodTypeApi
    .findAll({
      status: 1,
    })
    .then((res) => {
      list.value = [res.data];
    })
    .catch((err) => {});
};
</script>
