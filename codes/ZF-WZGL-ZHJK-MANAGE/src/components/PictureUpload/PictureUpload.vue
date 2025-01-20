<template>
  <el-upload
    :class="{ disabled: uploadDisabled }"
    v-model:file-list="list"
    action="#"
    :limit="limit"
    :multiple="false"
    accept=".jpg,.png"
    :auto-upload="false"
    list-type="picture-card"
    :on-remove="handleRemove"
    :on-change="onChange"
    :on-preview="handlePictureCardPreview"
  >
    <!--      :on-change="getFile"-->
    <el-icon>
      <Plus />
    </el-icon>
  </el-upload>
  <el-dialog v-model="dialogVisible" width="400px" title="图片查看">
    <img class="w-360" :src="dialogImageUrl" alt="Preview Image" />
  </el-dialog>
</template>
<script lang="ts" setup>
import type { UploadProps, UploadUserFile } from "element-plus";
import { fileUploadApi } from "../../utils/Api";
import { Plus } from "@element-plus/icons-vue";
import { ref, computed } from "vue";
import { ElMessage } from "element-plus";
const dialogImageUrl: any = ref("");
const dialogVisible = ref(false);
interface Props {
  list?: any;
  limit?: number;
}
const props = withDefaults(defineProps<Props>(), {
  list: [],
  limit: 1,
});
// 向外抛值
const emit = defineEmits<{
  //勾选复选框
  (e: "uploadData", data: any): void;
  (e: "update:list", data: any): void;
}>();
const uploadDisabled = computed(() => props.list.length >= props.limit);

const getFile = (file: any, fileList: any) => {
  //上传头像
  console.log(file, fileList);
  // fileUploadApi.uploadToLocal({}).then((res)=>{
  //
  // }).finally(()=>{
  //
  // })
  // getBase64(file.raw).then(res => {
  //     console.log(res)
  //     if (res) {
  //         fileList.splice(-1, 1)
  //         fileList.push({
  //             name: file.name,
  //             url: res
  //         })
  //     }
  //     emit("update:list", fileList)
  //
  // })
};
const onChange = (file: { name: string }, fileList: any) => {
  console.log(file, fileList);

  let msg = file.name.substring(file.name.lastIndexOf(".") + 1).toLowerCase();
  console.log(msg);
  //判断文件格式
  let accept = props.accept && props.accept.split(",");
  if (accept && accept.indexOf("." + msg) == -1) {
    // props.list.splice(-1,1)
    emit("update:list", []);
    // this.$refs.fileUpload.clearFiles()
    // this.$notify.error({
    //   title: '错误',
    //   message: '上传文件只能是' + that.accept + '格式!',
    //   duration: 2000
    // })
    return;
  }

  // props.list = fileList;
  emit("update:list", fileList);
  uploadFile();
};
// 上传文件
const uploadFile = () => {
  let formData = new FormData();
  if (props.multiple) {
    for (var i in props.list) {
      console.log(props.list[i].raw);
      formData.append("files", props.list[i].raw);
    }
  } else {
    formData.append("file", props.list[0].raw);
  }
  formData.append("type", "1");
  console.log(
    "333",
    formData.getAll("files"),
    formData.getAll("file"),
    formData.getAll("type")
  );
  emit("uploadData", formData);
  console.log(formData.getAll("files"));
  fileUploadApi
    .uploadToLocal(formData)
    .then((res) => {
      console.log("成功上传", res);
      ElMessage({
        message: "上传成功",
        type: "success",
      });
      emit("uploadData", res.data);
    })
    .catch((err) => {});
};
const getBase64 = (file: any) => {
  //把图片转成base64编码
  return new Promise(function (resolve, reject) {
    let reader = new FileReader();
    let imgResult: any = "";
    reader.readAsDataURL(file);
    reader.onload = function () {
      imgResult = reader.result;
    };
    reader.onerror = function (error) {
      reject(error);
    };
    reader.onloadend = function () {
      resolve(imgResult);
    };
  });
};

const handleRemove: UploadProps["onRemove"] = (uploadFile, uploadFiles) => {
  console.log(uploadFile, uploadFiles);
  emit("update:list", props.list);
};

const handlePictureCardPreview: UploadProps["onPreview"] = (uploadFile) => {
  console.log(uploadFile);

  dialogImageUrl.value = uploadFile.url;
  dialogVisible.value = true;
};
</script>
<style lang="scss">
.disabled .el-upload--picture-card {
  display: none;
}

::v-deep(.disabled .el-upload.el-upload--picture-card) {
  display: none;
}
</style>
