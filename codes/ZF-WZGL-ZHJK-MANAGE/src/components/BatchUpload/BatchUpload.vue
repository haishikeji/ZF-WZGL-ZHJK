<template>
  <el-button type="primary" @click="showUpload = true">{{ title }}</el-button>
  <Dialog
    width="600px"
    v-model:visible="showUpload"
    hideBtn
    :title="multiple ? title : '文件上传'"
  >
    <div v-if="accept" style="margin-top: -10px; margin-bottom: 10px">
      允许上传的文件格式有{{ accept }}
    </div>
    <el-scrollbar
      class="scroll"
      :height="multiple ? '500px' : '40px'"
      v-loading="loading"
    >
      <div
        class="flex-n fac fjb scroll_item"
        v-for="(item, index) in fileData"
        :key="index"
      >
        <div>{{ item.name }}</div>
        <!--<div v-if="errList.indexOf(index)>-1" style="color: #dd6161">{{item.name}}</div>-->
        <!--<div v-else>{{item.name}}</div>-->
        <el-icon @click="delFile(index)"><delete /></el-icon>
      </div>
    </el-scrollbar>
    <div class="flex-n fje mt-10" style="padding-bottom: 20px">
      <el-upload
        class="upload-demo"
        ref="fileUpload"
        enctype="multipart/form-data"
        action="#"
        :limit="limit"
        :accept="accept"
        :multiple="multiple"
        :auto-upload="false"
        :show-file-list="false"
        :file-list="fileData"
        :on-change="onChange"
      >
        <el-button
          type="primary"
          :loading="loading"
          :disabled="limit == fileData.length ? true : false"
          >选择文件</el-button
        >
      </el-upload>
      <el-button
        class="ml-10"
        type="primary"
        @click="uploadFile"
        :loading="loading"
        >上传</el-button
      >
    </div>
  </Dialog>
</template>
<script lang="ts" setup>
import Dialog from "../Dialog/Dialog.vue";
import { Delete } from "@element-plus/icons-vue";
import { reactive, ref, watch } from "vue";
import { fileUploadApi } from "../../utils/Api";
import { ElMessage } from "element-plus";
const ruleForm = ref(null);
const showUpload = ref<boolean>(false);
const loading = ref<boolean>(false);
interface Props {
  id?: string | number;
  title?: string;
  accept?: string;
  limit?: number;
  multiple?: boolean;
}
let fileData: any = ref([]);
const emit = defineEmits<{
  (e: "uploadData", data: any): void;
}>();
const props = withDefaults(defineProps<Props>(), {
  title: "批量导入",
  accept: "", //允许上传的文件格式.xlsx,.xls,.doc,.docx,.zip,.rar,.pdf
  limit: 1,
});
// 语法糖内需要使用此方式暴露想要被外部修改得数据
defineExpose({
  showUpload,
});
//监听visible变化，关闭弹窗
watch(
  () => showUpload.value,
  (newval, oldval) => {
    if (!newval) {
      loading.value = false;
      fileData.value = [];
    }
  }
);

const onChange = (file: { name: string }, fileList: any) => {
  console.log(file, fileList);

  loading.value = true;
  let msg = file.name.substring(file.name.lastIndexOf(".") + 1).toLowerCase();
  console.log(msg);
  //判断文件格式
  let accept = props.accept && props.accept.split(",");
  if (accept && accept.indexOf("." + msg) == -1) {
    fileData.value.splice(-1, 1);
    // this.$refs.fileUpload.clearFiles()
    // this.$notify.error({
    //   title: '错误',
    //   message: '上传文件只能是' + that.accept + '格式!',
    //   duration: 2000
    // })
    loading.value = false;
    return;
  }

  fileData.value = fileList;
  loading.value = false;
};

//  console.log(file, fileList)
//     let that = this;
//     this.loading = true;
//     var msg = file.name.substring(file.name.lastIndexOf('.') + 1).toLowerCase()
//     console.log(msg,fileList)
//     //判断文件格式
//     let accept = this.accept && this.accept.split(',');
//     const isLt20M = file.size / 1024 / 1024 < 20;
//     if (accept && accept.indexOf('.' + msg) == -1) {
//       fileList.splice(-1,1)
//       this.loading = false;
//       return this.$notify.error({
//         title: '错误',
//         message: '上传文件只能是' + that.accept + '格式!',
//         duration: 2000
//       })

//     } else if (!isLt20M) {
//       fileList.splice(-1,1)
//       this.loading = false;
//       return this.$notify.error({
//         title: '错误',
//         message: '上传文件大小不能超过 20MB!',
//         duration: 2000
//       });
//     }else{
//       this.fileList = fileList;
//       this.loading = false;
//     }

// 上传文件
const uploadFile = () => {
  loading.value = true;
  let formData = new FormData();
  // if(props.multiple){
  //    for (var i in fileData.value) {
  //    console.log(fileData.value[i].raw)
  //    formData.append('files', fileData.value[i].raw);
  //    }
  // }
  // else{
  formData.append("file", fileData.value[0].raw);
  // }

  fileUploadApi
    .uploadToLocal(formData)
    .then((res) => {
      console.log("成功上传", res);
      ElMessage({
        message: "上传成功",
        type: "success",
      });
      emit("uploadData", {
        url: res.data,
        name: fileData.value[0].name,
      });
      showUpload.value = false;
    })
    .finally(() => {
      loading.value = false;
    });
};
const delFile = (index: number) => {
  fileData.value.splice(index, 1);
};
</script>
<style scoped lang="scss">
.scroll {
  border: 1px solid #f1f1f1;
  padding: 10px;
  .scroll_item {
    margin-bottom: 10px;
    padding: 5px 10px;
    &:hover {
      background: #d6d6d6;
      cursor: pointer;
    }
  }
}
</style>
