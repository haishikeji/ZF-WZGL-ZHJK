<template>
  <Dialog
    width="1200px"
    v-model:visible="visible"
    :loading="loading"
    :title="(id ? '编辑' : '新增') + '排班'"
    @cancel="cancelSubmit"
    @ok="submitForm"
  >
    <el-form
      ref="ruleForm"
      :model="form"
      :show-message="false"
      :rules="rules"
      label-width="100px"
    >
      <div class="flex-n fac">
        <el-form-item label="排班名称" class="flex-1" prop="scheduleName">
          <el-input v-model="form.scheduleName" placeholder="请输入" />
        </el-form-item>
        <el-form-item label="初始班次" class="flex-1" prop="scheduleType">
          <ScheduleTypeList
            class="w-full"
            :disabled="initState"
            v-model="form.scheduleType"
            placeholder="请选择初始班次"
            @getAllData="getScheduleTypeInfo"
          ></ScheduleTypeList>
        </el-form-item>
      </div>
      <el-form-item label="设置工作日" class="flex-1" prop="scheduleWorkDay">
        <el-checkbox-group v-model="form.scheduleWorkDay" :disabled="initState">
          <el-checkbox
            :label="item.value"
            v-for="(item, index) in workingDayList"
            :key="item.value"
          >
            {{ item.label }}
          </el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <el-form-item label="备注" class="flex-1" prop="remark">
        <el-input v-model="form.remark" placeholder="请输入备注" />
      </el-form-item>
      <div class="flex-n fjb">
        <el-form-item label="排班周期" prop="scheduleCycleDate">
          <el-date-picker
            v-model="form.scheduleCycleDate"
            :disabled="initState"
            class="w-140"
            type="month"
            format="YYYY-MM"
            value-format="YYYY-MM"
            placeholder="请选择月份"
            :disabled-date="(time: any) => time.getTime() < Date.now()"
          />
          <!-- <el-checkbox class="ml-10" v-model="checked" :true-label="1" :false-label="0">隐藏往期时间</el-checkbox> -->
        </el-form-item>
        <!-- id 还是code 确认一下
        <el-form-item label="所属公司" class="flex-1" prop="companyCode">
          <company-list class="w-full" multiple v-model="form.companyCode" placeholder="请选择(可多选)"></company-list>
        </el-form-item> -->
        <el-button
          class="ml-10"
          type="primary"
          v-if="!initState"
          @click="initialScheduling"
          >初始化排班</el-button
        >
        <el-button class="ml-10" type="primary" v-else @click="resetScheduling"
          >重新选择排班</el-button
        >
      </div>
      <el-form
        ref="ruleForm"
        :model="form2"
        :show-message="false"
        :rules="rules2"
        label-width="100px"
      >
        <container-table
          :tableData="form2.listmodel"
          :tableConfig="tableConfig"
          :loading="loading"
          height="300px"
          :showIndex="false"
          :showPage="false"
          :operateWidth="90"
          :operateFilters="statusList"
          :operateFilterMethod="operateFilterMethod"
        >
          <template #item_scheduleDate="scope">
            <el-form-item
              label-width="0"
              :key="scope.index"
              class="w-full"
              style="margin-bottom: 0"
              :prop="
                'listmodel.' +
                scope.index +
                '.dateListModel.' +
                scope.rowinfo?.idx +
                '.timeRange'
              "
              :rules="{
                required: scope.row.schedleSStatus == '1' ? true : false,
                trigger: ['change', 'blur'],
              }"
            >
              <el-time-picker
                class="w-full"
                v-if="form2.listmodel[scope.index]"
                :disabled="scope.row.schedleSStatus == '1' ? false : true"
                v-model="
                  form2.listmodel[scope.index].dateListModel[scope.rowinfo.idx]
                    .timeRange
                "
                is-range
                range-separator="-"
                format="HH:mm"
                value-format="HH:mm"
                start-placeholder="-"
                end-placeholder="-"
              />
            </el-form-item>
          </template>
          <template #operateContent="scope">
            <el-popconfirm
              :title="`是否确认${
                scope.row.schedleSStatus == '1' ? '移除' : '加入'
              }排班`"
              @confirm="manageData(scope.index, scope.row.schedleSStatus)"
            >
              <template #reference>
                <el-button
                  type="text"
                  style="color: #f56c6c"
                  v-if="scope.row.schedleSStatus == '1'"
                  >移除排班</el-button
                >
                <el-button type="text" style="color: #3b973e" v-else
                  >加入排班</el-button
                >
              </template>
            </el-popconfirm>
          </template>
        </container-table>
      </el-form>
    </el-form>
  </Dialog>
</template>
<script lang="ts" setup>
import Dialog from "../../../../../components/Dialog/Dialog.vue";
import ContainerTable from "../../../../../components/ContainerTable/ContainerTable.vue";

import { ElMessage, FormRules } from "element-plus";
import { reactive, ref, watch } from "vue";
import { managementApi, userApi } from "../../../../../utils/Api";
import DT from "../../../../../assets/js/date";
import ScheduleTypeList from "../../../../common/components/ScheduleTypeList.vue";
import commMethods from "../../../../../assets/js/common";

const statusList = [
  {
    text: "已排班",
    value: "1",
  },
  {
    text: "未排班",
    value: "0",
  },
];
const ruleForm = ref();
const loading = ref<boolean>(false);
const checked = ref(1);
const initState = ref(false);
let tableConfig = reactive([
  {
    label: "姓名",
    prop: "username",
    minWidth: 160,
  },
  {
    label: "手机号",
    prop: "phone",
    width: 160,
  },
  { label: "04-01(一)", prop: "scheduleDate", minWidth: 180, slot: true },
]);
interface Props {
  id?: string;
  visible?: boolean;
}
const emit = defineEmits<{
  (e: "update:visible", visible: boolean): void;
  (e: "refresh"): void;
}>();
const props = withDefaults(defineProps<Props>(), {
  id: "",
});
const workingDayList = [
  {
    value: 1,
    label: "星期一",
  },
  {
    value: 2,
    label: "星期二",
  },
  {
    value: 3,
    label: "星期三",
  },
  {
    value: 4,
    label: "星期四",
  },
  {
    value: 5,
    label: "星期五",
  },
  {
    value: 6,
    label: "星期六",
  },
  {
    value: 7,
    label: "星期日",
  },
];
//表单数据
const operateFilterMethod = (value: string, row: any) => {
  console.log(row);
  return row.schedleSStatus === value;
};

let form = ref();
let form2: any = ref({
  listmodel: [],
});
const rules = reactive<FormRules>({
  scheduleName: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
  scheduleType: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
  scheduleWorkDay: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
  remark: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
  scheduleCycleDate: [
    {
      required: true,
      trigger: ["change", "blur"],
    },
  ],
});
const rules2 = reactive<FormRules>({});
//监听visible变化，关闭弹窗
watch(
  () => props.visible,
  (newval, oldval) => {
    getInitValue();
    if (newval) {
      if (props.id) {
        getData();
      }
    }
  }
);
// watch(
//   () => checked.value,
//   (newval, oldval) => {
//     if (newval)
//       if (new Date(form.value.scheduleCycleDate).getTime() < Date.now()) {
//         form.value.scheduleCycleDate = ''
//       }
//   }
// )
const getInitValue = () => {
  form.value = {
    scheduleName: "",
  };
  form2.value = {
    listmodel: [],
  };
};
//获取默认时间
const getScheduleTypeInfo = (data: { startTime: any; endTime: any }) => {
  console.log("获取默认时间", data);
  form.value.startTime = data.startTime;
  form.value.endTime = data.endTime;
};
//重新选择排班
const resetScheduling = () => {
  commMethods
    .getMessageBox("重新选择排班，会清除下面已填写数据，是否确认清除重置")
    .then((res) => {
      form2.value.listmodel = [];
      initState.value = false;
    })
    .finally(() => {});
};
//初始化排班
const initialScheduling = async () => {
  if (!ruleForm.value) return;
  await ruleForm.value.validate((valid: any, fields: any) => {
    if (valid) {
      userApi
        .finduser({})
        .then((res) => {
          let listmodel = [];
          let list = getDataTime();
          for (let i in res.data.list) {
            let item = res.data.list[i];
            let dateListModel = []; //根据工作日和排班周期计算
            for (let j in list) {
              console.log(list[j]);
              dateListModel.push({
                scheduleDate: list[j].date,
                idx: list[j].idx,
                timeRange:
                  form.value.startTime && form.value.endTime
                    ? [form.value.startTime, form.value.endTime]
                    : [],
                startTime: form.value.startTime,
                endTime: form.value.endTime,
              });
            }
            listmodel.push({
              username: item.userName,
              usercode: item.id,
              departId: item.departId,
              userId: item.id,
              schedleSStatus: "1",
              phone: item.phone,
              dateListModel: dateListModel,
            });
          }
          form2.value.listmodel = listmodel;
          initState.value = true;
        })
        .finally(() => {});
    } else {
      console.log(fields);
      ElMessage({
        message: "请检查是否有必填项未填写",
        type: "warning",
      });
    }
  });
};

const getDataTime = () => {
  let t = form.value.scheduleCycleDate.split("-");
  if (Number(t[1]) + 1 > 12) {
    t[0] = Number(t[0]) + 1;
  } else {
    t[1] = Number(t[1]) + 1;
  }
  console.log("234234", t);
  console.log(
    "啊",
    DT.getDate(1, new Date(t[0] + "-" + t[1] + "-01")),
    t[0] + "-" + t[1] + "-01"
  );

  let dt = DT.getDate(1, new Date(t[0] + "-" + t[1] + "-01"));
  let beginTime = new Date(dt[0]).getTime(),
    endTime = new Date(
      new Date(dt[1]).getTime() - 24 * 60 * 60 * 1000
    ).getTime();
  let scheduleWorkDay = [],
    list = [],
    xingqi = ["日", "一", "二", "三", "四", "五", "六", "日"];
  for (let i in form.value.scheduleWorkDay) {
    if (form.value.scheduleWorkDay[i] == 6) {
      scheduleWorkDay.push(0);
    } else {
      scheduleWorkDay.push(form.value.scheduleWorkDay[i]);
    }
  }
  console.log("啊", scheduleWorkDay, beginTime + 24 * 60 * 60 * 1000, endTime);
  let idx = 0;
  for (let i = beginTime; i <= endTime; i = i + 24 * 60 * 60 * 1000) {
    console.log(i);
    let date = new Date(i);
    if (scheduleWorkDay.indexOf(date.getDay()) > -1) {
      list.push({
        label:
          DT.computedDate(date).substring(5) +
          "(" +
          xingqi[date.getDay()] +
          ")",
        date: DT.computedDate(date),
        idx: idx,
        prop: "scheduleDate",
        minWidth: 180,
        slot: true,
      });
      idx++;
    }
  }
  console.log("啊", list);
  tableConfig.length = 0;
  tableConfig.push(
    {
      label: "姓名",
      prop: "username",
      minWidth: 160,
    },
    {
      label: "手机号",
      prop: "phone",
      width: 160,
    },
    ...list
  );
  return list;
};
const manageData = (idx: number, status: string) => {
  form2.value.listmodel[idx].schedleSStatus = status == "1" ? "0" : "1";

  //排序open 前 close 后
};
const getData = () => {
  managementApi
    .findById(props.id)
    .then((res) => {
      Object.assign(form.value, res.data);
      form2.value.listmodel = res.data.listmodel || [];
      form.value.scheduleWorkDay = form.value.scheduleWorkDay?.split(",") || [];
      for (let i in form.value.scheduleWorkDay) {
        form.value.scheduleWorkDay[i] = Number(form.value.scheduleWorkDay[i]);
      }
      for (let i in form2.value.listmodel) {
        for (let j in form2.value.listmodel[i].dateListModel) {
          let item = form2.value.listmodel[i].dateListModel[j];
          if (item.startTime && item.endTime)
            form2.value.listmodel[i].dateListModel[j].timeRange = [
              item.startTime,
              item.endTime,
            ];
        }
      }
      getDataTime();
      console.warn(form);
      loading.value = false;
    })
    .catch((err) => {
      loading.value = false;
    });
};
// 表单验证
const submitForm = async () => {
  console.log(form2);
  if (form2.value.listmodel.length == 0) {
    return ElMessage({
      message: "请初始化排班，选择人员进行排班，再进行保存",
      type: "warning",
    });
  }
  if (!ruleForm.value) return;
  await ruleForm.value.validate((valid: any, fields: any) => {
    if (valid) {
      saveOrUpdate();
    } else {
      console.log(fields);
      ElMessage({
        message: "请检查是否有必填项未填写",
        type: "warning",
      });
    }
  });
};
const saveOrUpdate = () => {
  loading.value = true;
  for (let i in form2.value.listmodel) {
    if (form2.value.listmodel[i].schedleSStatus == "1") {
      for (let j in form2.value.listmodel[i].dateListModel) {
        form2.value.listmodel[i].dateListModel[j].startTime =
          form2.value.listmodel[i].dateListModel[j].timeRange[0] || "";
        form2.value.listmodel[i].dateListModel[j].endTime =
          form2.value.listmodel[i].dateListModel[j].timeRange[1] || "";
      }
    }
  }
  managementApi
    .saveOrUpdate(props.id ? "update" : "save", {
      ...form2.value,
      orgId: commMethods.getStorage("userInfo").orgId,
      id: props.id,
      scheduleName: form.value.scheduleName,
      scheduleType: form.value.scheduleType,
      startTime: form.value.startTime,
      endTime: form.value.endTime,
      remark: form.value.remark,
      scheduleCycleDate: form.value.scheduleCycleDate,
      scheduleWorkDay: form.value.scheduleWorkDay.join(","),
    })
    .then((res) => {
      ElMessage({
        message: "排班成功",
        type: "success",
      });
      cancelSubmit();
      emit("refresh");
    })
    .finally(() => {
      loading.value = false;
    });
};
// 取消提交,重置表单
const cancelSubmit = () => {
  ruleForm.value.resetFields();
  form2.value.listmodel = [];
  initState.value = false;
  tableConfig.length = 0;
  tableConfig.push(
    {
      label: "姓名",
      prop: "username",
      minWidth: 160,
    },
    {
      label: "手机号",
      prop: "phone",
      width: 160,
    }
  );

  emit("update:visible", false);
};
</script>
<style scoped lang="scss">
.attachment-grid {
  display: grid;
  grid-template-columns: 50px 110px repeat(7, 1fr) 60px;

  > div {
    padding: 10px 5px;
    text-align: center;
    display: flex;
    align-items: center;
    justify-content: center;

    border-bottom: 1px solid #ebeef5;
  }

  .title {
    border-top: 1px solid #ebeef5;
    background: #f1f1f1;
    // color: #909399;
    font-weight: bold;
  }

  .el-form-item {
    margin-bottom: 0px;
  }
}
</style>
