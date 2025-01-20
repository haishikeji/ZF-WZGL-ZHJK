<template>
  <select-option
    :list="list"
    :option="{ label: 'consultationName', value: 'consultationId' }"
    :value="value"
    :placeholder="placeholder"
    @getAllData="(data) => emit('getAllData', data)"
  ></select-option>
</template>
<script lang="ts" setup>
import SelectOption from "../../../components/SelectOption/SelectOption.vue";
import { reactive, ref, onMounted } from "vue";
import { sConsultationTypeApi } from "../../../utils/Api";
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
  getConsultationTypeList();
});
// 咨询类型下拉
const getConsultationTypeList = () => {
  sConsultationTypeApi
    .findAll({ consultationIdType: 1 })
    .then((res) => {
      list.value = res.data.list;
    })
    .catch((err) => {});
};
</script>
