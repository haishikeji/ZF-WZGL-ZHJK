<template>
  <Dialog
    width="600px"
    v-model:visible="visible"
    :title="'当前职位：' + name"
    @cancel="cancel"
    @ok="submit"
  >
    <el-scrollbar class="scroll" height="500px">
      <el-tree
        ref="tree"
        v-loading="loading"
        :data="data"
        node-key="id"
        :props="defaultProps"
        show-checkbox
      />
    </el-scrollbar>
  </Dialog>
</template>
<script lang="ts" setup>
import Dialog from "../../../../components/Dialog/Dialog.vue";
import { nextTick, reactive, ref, watch } from "vue";
import { menuApi, roleApi } from "../../../../utils/Api";
import { ElMessage } from "element-plus";
const loading = ref<boolean>(false);
const ruleForm = ref(null);
const defaultProps = {
  children: "children",
  label: "menuName",
  disabled: "disabled",
  isLeaf: "isLeaf",
};
const data = ref([]);
const tree = ref();
// const data = [
//   {
//     id: 1,
//     label: 'Level one 1',
//     children: [
//       {
//         id: 3,
//         label: 'Level two 2-1',
//         children: [
//           {
//             id: 4,
//             label: 'Level three 3-1-1',
//           },
//           {
//             id: 5,
//             label: 'Level three 3-1-2',
//             disabled: true,
//           },
//         ],
//       },
//       {
//         id: 2,
//         label: 'Level two 2-2',
//         disabled: true,
//         children: [
//           {
//             id: 6,
//             label: 'Level three 3-2-1',
//           },
//           {
//             id: 7,
//             label: 'Level three 3-2-2',
//             disabled: true,
//           },
//         ],
//       },
//     ],
//   },
// ]
interface Props {
  id?: string;
  visible?: boolean;

  name?: string;
}
const emit = defineEmits<{
  (e: "update:visible", visible: boolean): void;
  (e: "refresh"): void;
}>();
const props = withDefaults(defineProps<Props>(), {
  id: "",
});
watch(
  () => props.visible,
  (newval, oldval) => {
    if (newval) {
      getDataList();
    }
  }
);

const getInitValue = () => {
  Object.assign(form, {
    orgName: "",
    orgCode: "",
    orgFoundedTime: "",
    orgAdd: "",
  });
};
// 获取数据
const getDataList = () => {
  console.log("获取数据");
  loading.value = true;
  menuApi
    .menuRecurrenceAll()
    .then((res) => {
      console.log(res);
      data.value = res.data.children;
      getData();
    })
    .finally(() => {
      loading.value = false;
    });
};
const getData = () => {
  menuApi
    .findMenuByRoleId(props.id)
    .then((res) => {
      console.log("当前用户菜单", res);
      let list = [];
      for (let i in res.data.list) {
        if (tree.value.getNode(res.data.list[i].menuid)?.isLeaf) {
          list.push(res.data.list[i].menuid);
          console.log(
            res.data.list[i].menuid,
            tree.value.getNode(res.data.list[i].menuid).isLeaf
          );
          tree.value.setChecked(res.data.list[i].menuid, true);
        }
      }
    })
    .finally(() => {
      loading.value = false;
    });
};
// 提交数据
const submit = () => {
  // emit('refresh');
  loading.value = true;
  console.log(tree.value.getCheckedKeys(), tree.value.getHalfCheckedKeys());
  let menuData = [];
  for (let i in tree.value.getCheckedKeys()) {
    menuData.push({
      menuId: tree.value.getCheckedKeys()[i],
    });
  }
  for (let i in tree.value.getHalfCheckedKeys()) {
    menuData.push({
      menuId: tree.value.getHalfCheckedKeys()[i],
    });
  }
  menuData.push({
    menuId: "1",
  });
  roleApi
    .auth({
      menuData: menuData,
      type: 0,
      id: props.id,
    })
    .then((res) => {
      console.log(res);
      ElMessage.success("权限分配成功");
      cancel();
    })
    .finally(() => {
      loading.value = false;
    });
};
// 取消提交,重置表单
const cancel = () => {
  emit("update:visible", false);
};
</script>
<style scoped lang="scss">
.scroll {
  border: 1px solid #f1f1f1;
  padding: 10px;
}
</style>
