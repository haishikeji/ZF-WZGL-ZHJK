<!-- 人员管理 -->
<template>
  <info-card>
    <template #header>人员管理</template>
    <container-table
      :tableData="load.tableData"
      :tableConfig="tableConfig"
      :loading="load.loading"
      :total="load.total"
      :showIndex="false"
      :operateWidth="320"
      :pageSize="load.pageSize"
      :currentPage="load.currentPage"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    >
      <!-- 搜索区域 -->
      <template #searchArea>
        <el-input
          class="w-200"
          v-model="searchData.userName"
          clearable
          placeholder="姓名"
        />
        <org-list
          class="w-200"
          v-model="searchData.orgId"
          placeholder="所属机构"
        ></org-list>
        <department-list
          class="w-200"
          v-model="searchData.deptId"
          placeholder="所属部门"
        ></department-list>
        <el-button
          type="primary"
          :icon="Search"
          :loading="load.loading"
          @click="searchList"
          >查询</el-button
        >
        <el-button
          @click="resetData"
          :loading="load.loading"
          :icon="RefreshLeft"
          >重置</el-button
        >
        <el-button
          type="primary"
          :loading="load.loading"
          @click="manageData(0)"
          :icon="Plus"
          >新建人员</el-button
        >
      </template>
      <template #item_status="scope">
        <Tag state="success" v-if="scope.row.status == 1" text="复职"></Tag>
        <Tag state="warning" v-else text="离职"></Tag>
        <!-- {{commMethods.getDicName('examineStatus',scope.row.examineStatus)}} -->
      </template>
      <template #operateContent="scope">
        <el-button
          type="primary"
          size="small"
          plain
          :icon="Lock"
          @click="resetPass(scope.row.id)"
          >重置密码
        </el-button>
        <el-button
          type="primary"
          size="small"
          plain
          :icon="EditPen"
          @click="manageData(1, scope.row.id)"
          >编辑
        </el-button>
        <el-button
          type="success"
          size="small"
          v-if="scope.row.status == 0"
          plain
          :icon="SuccessFilled"
          @click="handleOpen(true, scope.row.id)"
          >复职
        </el-button>
        <el-button
          type="warning"
          size="small"
          v-else
          plain
          :icon="CircleCloseFilled"
          @click="handleOpen(false, scope.row.id)"
          >离职</el-button
        >
        <el-button
          type="danger"
          size="small"
          plain
          :icon="DeleteFilled"
          @click="delData(scope.row.id)"
          >删除
        </el-button>
      </template>
    </container-table>
  </info-card>
  <ManagePeople
    v-model:visible="editDialog"
    :id="searchData.id"
    @refresh="refreshList"
  ></ManagePeople>
</template>
<script lang="ts" setup>
import InfoCard from "../../../components/InfoCard/InfoCard.vue";
import ManagePeople from "./components/ManagePeople.vue";
import Tag from "../../../components/Tag/Tag.vue";
import ContainerTable from "../../../components/ContainerTable/ContainerTable.vue";
import SelectOption from "../../../components/SelectOption/SelectOption.vue";
import { ElMessage, ElMessageBox } from "element-plus";
import {
  Lock,
  DeleteFilled,
  SuccessFilled,
  CircleCloseFilled,
  EditPen,
  Search,
  RefreshLeft,
  Plus,
} from "@element-plus/icons-vue";
import commMethods from "../../../assets/js/common";
import { onMounted, reactive, ref } from "vue";
import { userApi } from "../../../utils/Api";
import OrgList from "../../common/components/OrgList.vue";
import DepartmentList from "../../common/components/DepartmentList.vue";
const load = reactive(commMethods.loadData()); //加载所需数据
const editDialog = ref(false);
const searchData: any = reactive({
  id: "",
  userName: "",
  orgId: "",
  deptId: "",
});
const tableConfig = reactive([
  { label: "人员账号", prop: "loginName", width: 160, align: "left" },
  { label: "姓名", prop: "userName", width: 120 },
  { label: "年龄", prop: "birthday", width: 60 },
  { label: "手机号", prop: "roleName", width: 110 },
  { label: "角色名称", prop: "roleName", minWidth: 160 },
  { label: "所属部门", prop: "deptName", minWidth: 160 },
  { label: "所属机构", prop: "orgName", minWidth: 160 },
  { label: "人员状态", prop: "status", width: 80, slot: true },
  { label: "关联社区", prop: "communityNames", width: 180 },
]);
onMounted(() => {
  initData();
  searchList();
});
const initData = () => {
  Object.assign(searchData, {
    id: "",
    userName: "",
    orgId: "",
    deptId: "",
  });
};
// 刷新列表
const refreshList = () => {
  //编辑时，刷新当前页面
  if (searchData.id) {
    load.loading = true;
    getDataList();
  } else {
    searchList();
  }
};
// 搜索列表
const searchList = () => {
  Object.assign(load, commMethods.loadData());
  getDataList();
};
// 获取列表数据
const getDataList = () => {
  userApi
    .findAll({
      userName: searchData.userName,
      orgId: searchData.orgId,
      deptId: searchData.deptId,
      pageSize: load.pageSize,
      pageNum: load.currentPage,
    })
    .then((res) => {
      load.tableData = res.data.list;
      load.total = res.data.total;
    })
    .finally(() => {
      load.loading = false;
    });
};
const manageData = (state: string | number, id?: string) => {
  searchData.id = id;
  editDialog.value = true;
};
// 重置密码
const resetPass = (id: string | number) => {
  searchData.id = id;
  commMethods
    .getMessageBox(`确定要重置当前账号的密码？重置之后的密码为123456`)
    .then((res) => {
      userApi.resetPassword({
        passWord: "123456",
        id: id,
      });
    })
    .then((res) => {
      ElMessage({
        type: "success",
        message: `账号密码重置成功`,
      });
      refreshList();
    })
    .finally(() => {
      load.loading = false;
    });
};
// 复职停用
const handleOpen = (state: boolean, id: string) => {
  searchData.id = id;
  commMethods
    .getMessageBox(`确定要${state ? "复职" : "离职"}当前人员?`)
    .then(() => {
      load.loading = true;
      userApi
        .updateStatus({
          status: state ? "1" : "0",
          id: id,
        })
        .then((res) => {
          ElMessage({
            type: "success",
            message: `${state ? "复职" : "离职"}成功`,
          });
          refreshList();
        })
        .finally(() => {
          load.loading = false;
        });
    });
};
// 删除
const delData = (id: string) => {
  searchData.id = id;
  commMethods.getMessageBox(`确定要删除当前人员?`).then(() => {
    load.loading = true;
    userApi
      .deleteByIds(id)
      .then((res) => {
        ElMessage({
          type: "success",
          message: "删除成功",
        });
        refreshList();
      })
      .finally(() => {
        load.loading = false;
      });
  });
};
// 重置数据
const resetData = () => {
  initData();
  searchList();
};
// 分页跳转
const handleSizeChange = (val: number) => {
  Object.assign(load, commMethods.loadData(1, val));
  getDataList();
  console.log("pageSize", val);
};
const handleCurrentChange = (val: number) => {
  load.currentPage = val;
  getDataList();
  console.log("currentPage", val);
};
</script>
<style scoped lang="scss"></style>
