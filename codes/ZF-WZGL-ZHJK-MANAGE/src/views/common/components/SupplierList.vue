<template>
  <select-option
    :list="list"
    :option="{ label: 'dicName', value: 'dicCode' }"
    v-model="value"
    :placeholder="placeholder"
    @getAllData="(data: any) => emit('getAllData', data)"
  ></select-option>
</template>
<script lang="ts" setup>
import SelectOption from "../../../components/SelectOption/SelectOption.vue";
import { reactive, ref, onMounted } from "vue";
import { supplierApi } from "../../../utils/Api";
const list = ref([]);
interface Props {
  size?: string;
  placeholder?: string;
  value?: string | number | number[] | string[];
  clearable?: boolean;
  disabled?: boolean;
  filterable?: boolean;
  multiple?: boolean;
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
});
onMounted(() => {
  getSupplierList();
});
// 获取公司列表
const getSupplierList = () => {
  supplierApi
    .supplierList({})
    .then((res) => {
      list.value = res.data.list;
    })
    .catch((err) => {});
};
</script>
