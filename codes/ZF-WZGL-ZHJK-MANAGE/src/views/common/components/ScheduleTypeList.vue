<template>
  <select-option
    :list="list"
    :option="{ label: 'scheduleTypeName', value: 'id' }"
    v-model:value="value"
    :placeholder="placeholder"
    :disabled="disabled"
    @getAllData="(data) => emit('getAllData', data)"
    @change="(data) => emit('change', data)"
  ></select-option>
</template>
<script lang="ts" setup>
import SelectOption from "../../../components/SelectOption/SelectOption.vue";
import { reactive, ref, onMounted } from "vue";
import { scheduleTypeApi } from "../../../utils/Api";
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
  (e: "change", data: any): void;
}>();
const props = withDefaults(defineProps<Props>(), {
  placeholder: "请选择",
  clearable: true,
  disabled: false,
  filterable: false,
});
onMounted(() => {
  getScheduleTypeList();
});
// 检测类型下拉
const getScheduleTypeList = () => {
  scheduleTypeApi
    .findAll({ genreStatus: 1 })
    .then((res) => {
      list.value = res.data.list;
    })
    .catch((err) => {});
};
</script>
