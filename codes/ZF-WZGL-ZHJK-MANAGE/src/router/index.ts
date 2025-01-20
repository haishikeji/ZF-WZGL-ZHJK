import {
  createRouter,
  createWebHistory,
  RouteRecordRaw,
  createWebHashHistory,
} from "vue-router";

const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    name: "login",
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/Login/Login.vue"),
  },
  //系统首页
  {
    path: "/index",
    name: "index",
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/Index/Index.vue"),
  },
  //防疫信息
  {
    path: "/antiepidemicinfor",
    name: "antiepidemicinfor",
    redirect: "/antiepidemicinfor/infor/type",
    component: () =>
      import(
        /* webpackChunkName: "about" */ "../views/AntiEpidemicInfor/AntiEpidemicInfor.vue"
      ),

    children: [
      // 资讯管理
      {
        path: "/antiepidemicinfor/infor",
        name: "antiepidemicinformation",
        redirect: "/antiepidemicinfor/infor/type",
        component: () =>
          import(
            /* webpackChunkName: "about" */ "../views/AntiEpidemicInfor/Information/Information.vue"
          ),
        children: [
          {
            path: "/antiepidemicinfor/infor/type",
            name: "antiepidemicinfortype",
            component: () =>
              import(
                /* webpackChunkName: "about" */ "../views/AntiEpidemicInfor/Information/Type/Type.vue"
              ),
          },
          {
            path: "/antiepidemicinfor/infor/manage",
            name: "antiepidemicinformanage",
            component: () =>
              import(
                /* webpackChunkName: "about" */ "../views/AntiEpidemicInfor/Information/Manage/Manage.vue"
              ),
          },
        ],
      },
      //工作安排
      {
        path: "/antiepidemicinfor/work",
        name: "antiepidemicinforwork",
        redirect: "/antiepidemicinfor/work/dutylist",
        component: () =>
          import(
            /* webpackChunkName: "about" */ "../views/AntiEpidemicInfor/Work/Work.vue"
          ),
        children: [
          {
            path: "/antiepidemicinfor/work/dutylist",
            name: "antiepidemicinfordutylist",
            component: () =>
              import(
                /* webpackChunkName: "about" */ "../views/AntiEpidemicInfor/Work/DutyList/DutyList.vue"
              ),
          },
          {
            path: "/antiepidemicinfor/work/scheduling",
            name: "antiepidemicinforscheduling",
            component: () =>
              import(
                /* webpackChunkName: "about" */ "../views/AntiEpidemicInfor/Work/Scheduling/Scheduling.vue"
              ),
          },
          {
            path: "/antiepidemicinfor/work/frequency",
            name: "antiepidemicinforfrequency",
            component: () =>
              import(
                /* webpackChunkName: "about" */ "../views/AntiEpidemicInfor/Work/Frequency/Frequency.vue"
              ),
          },
          {
            path: "/antiepidemicinfor/work/netdisk",
            name: "antiepidemicinfornetdisk",
            component: () =>
              import(
                /* webpackChunkName: "about" */ "../views/AntiEpidemicInfor/Work/Netdisk/Netdisk.vue"
              ),
          },
        ],
      },
      //物资管理
      {
        path: "/antiepidemicinfor/supplies",
        name: "antiepidemicinforsupplies",
        redirect: "/antiepidemicinfor/supplies/category",
        component: () =>
          import(
            /* webpackChunkName: "about" */ "../views/AntiEpidemicInfor/Supplies/Supplies.vue"
          ),
        children: [
          {
            path: "/antiepidemicinfor/supplies/category",
            name: "antiepidemicinforsuppliescategory",
            component: () =>
              import(
                /* webpackChunkName: "about" */ "../views/AntiEpidemicInfor/Supplies/Category/Category.vue"
              ),
          },
          {
            path: "/antiepidemicinfor/supplies/baseinfo",
            name: "antiepidemicinforsuppliesbaseinfo",
            component: () =>
              import(
                /* webpackChunkName: "about" */ "../views/AntiEpidemicInfor/Supplies/BaseInfo/BaseInfo.vue"
              ),
          },
          {
            path: "/antiepidemicinfor/supplies/suppliers",
            name: "antiepidemicinforsuppliers",
            component: () =>
              import(
                /* webpackChunkName: "about" */ "../views/AntiEpidemicInfor/Supplies/Suppliers/Suppliers.vue"
              ),
          },
          {
            path: "/antiepidemicinfor/supplies/inbound",
            name: "antiepidemicinforinbound",
            component: () =>
              import(
                /* webpackChunkName: "about" */ "../views/AntiEpidemicInfor/Supplies/Inbound/Inbound.vue"
              ),
          },
          {
            path: "/antiepidemicinfor/supplies/depot",
            name: "antiepidemicinfordepot",
            component: () =>
              import(
                /* webpackChunkName: "about" */ "../views/AntiEpidemicInfor/Supplies/Depot/Depot.vue"
              ),
          },
          {
            path: "/antiepidemicinfor/supplies/approval",
            name: "antiepidemicinforsuppliesapproval",
            component: () =>
              import(
                /* webpackChunkName: "about" */ "../views/AntiEpidemicInfor/Supplies/Approval/Approval.vue"
              ),
          },
          {
            path: "/antiepidemicinfor/supplies/aggregate",
            name: "antiepidemicinforsuppliesaggregate",
            component: () =>
              import(
                /* webpackChunkName: "about" */ "../views/AntiEpidemicInfor/Supplies/Aggregate/Aggregate.vue"
              ),
          },
          {
            path: "/antiepidemicinfor/supplies/inventory",
            name: "antiepidemicinforsuppliesinventory",
            component: () =>
              import(
                /* webpackChunkName: "about" */ "../views/AntiEpidemicInfor/Supplies/Inventory/Inventory.vue"
              ),
          },
          {
            path: "/antiepidemicinfor/supplies/distribute",
            name: "antiepidemicinforsuppliesdistribute",
            component: () =>
              import(
                /* webpackChunkName: "about" */ "../views/AntiEpidemicInfor/Supplies/Distribute/Distribute.vue"
              ),
          },
        ],
      },
    ],
  },
  //防疫检测
  {
    path: "/antiepidemictesting",
    name: "antiepidemictesting",
    redirect: "/antiepidemictesting/testtype",
    component: () =>
      import(
        /* webpackChunkName: "about" */ "../views/AntiEpidemicTesting/AntiEpidemicTesting.vue"
      ),
    children: [
      {
        path: "/antiepidemictesting/testtype",
        name: "antiepidemictestingtesttype",
        component: () =>
          import(
            /* webpackChunkName: "about" */ "../views/AntiEpidemicTesting/TestType/TestType.vue"
          ),
      },
      {
        path: "/antiepidemictesting/reserve",
        name: "antiepidemictestingreserve",
        component: () =>
          import(
            /* webpackChunkName: "about" */ "../views/AntiEpidemicTesting/Reserve/Reserve.vue"
          ),
      },
      {
        path: "/antiepidemictesting/archives",
        name: "antiepidemictestingarchives",
        component: () =>
          import(
            /* webpackChunkName: "about" */ "../views/AntiEpidemicTesting/Archives/Archives.vue"
          ),
      },
    ],
  },
  //进出监控
  {
    path: "/accessmonitoring",
    name: "accessmonitoring",
    redirect: "/accessmonitoring/approval",
    component: () =>
      import(
        /* webpackChunkName: "about" */ "../views/AccessMonitoring/AccessMonitoring.vue"
      ),
    children: [
      {
        path: "/accessmonitoring/approval",
        name: "accessmonitoringapproval",
        component: () =>
          import(
            /* webpackChunkName: "about" */ "../views/AccessMonitoring/Approval/Approval.vue"
          ),
      },
      {
        path: "/accessmonitoring/approvalresult",
        name: "accessmonitoringapprovalresult",
        component: () =>
          import(
            /* webpackChunkName: "about" */ "../views/AccessMonitoring/ApprovalResult/ApprovalResult.vue"
          ),
      },
      {
        path: "/accessmonitoring/record",
        name: "accessmonitoringrecord",
        component: () =>
          import(
            /* webpackChunkName: "about" */ "../views/AccessMonitoring/Record/Record.vue"
          ),
      },
    ],
  },
  //系统管理
  {
    path: "/system",
    name: "system",
    redirect: "/system/organ",
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/System/System.vue"),
    children: [
      {
        path: "/system/organ",
        name: "systemorgan",
        component: () =>
          import(
            /* webpackChunkName: "about" */ "../views/System/Organ/Organ.vue"
          ),
      },
      {
        path: "/system/community",
        name: "systemcommunity",
        component: () =>
          import(
            /* webpackChunkName: "about" */ "../views/System/Community/Community.vue"
          ),
      },
      {
        path: "/system/department",
        name: "systemdepartment",
        component: () =>
          import(
            /* webpackChunkName: "about" */ "../views/System/Department/Department.vue"
          ),
      },
      {
        path: "/system/role",
        name: "systemrole",
        component: () =>
          import(
            /* webpackChunkName: "about" */ "../views/System/Role/Role.vue"
          ),
      },
      {
        path: "/system/people",
        name: "systempeople",
        component: () =>
          import(
            /* webpackChunkName: "about" */ "../views/System/People/People.vue"
          ),
      },
      {
        path: "/system/applications",
        name: "systemapplications",
        component: () =>
          import(
            /* webpackChunkName: "about" */ "../views/System/Applications/Applications.vue"
          ),
      },
      {
        path: "/system/screen",
        name: "systemscreen",
        component: () =>
          import(
            /* webpackChunkName: "about" */ "../views/System/Screen/Screen.vue"
          ),
      },
      {
        path: "/system/operationlog",
        name: "systemoperationlog",
        component: () =>
          import(
            /* webpackChunkName: "about" */ "../views/System/OperationLog/OperationLog.vue"
          ),
      },
      {
        path: "/system/message",
        name: "systemmessage",
        component: () =>
          import(
            /* webpackChunkName: "about" */ "../views/System/Message/Message.vue"
          ),
      },
    ],
  },
  //报表管理
  {
    path: "/reportform",
    name: "reportform",
    redirect: "/reportform/statistics",
    component: () =>
      import(
        /* webpackChunkName: "about" */ "../views/ReportForm/ReportForm.vue"
      ),
    children: [
      {
        path: "/reportform/statistics",
        name: "reportformstatistics",
        component: () =>
          import(
            /* webpackChunkName: "about" */ "../views/ReportForm/Statistics/Statistics.vue"
          ),
      },
      {
        path: "/reportform/supplies",
        name: "reportformsupplies",
        component: () =>
          import(
            /* webpackChunkName: "about" */ "../views/ReportForm/Supplies/Supplies.vue"
          ),
      },
      {
        path: "/reportform/testing",
        name: "reportformtesting",
        component: () =>
          import(
            /* webpackChunkName: "about" */ "../views/ReportForm/Testing/Testing.vue"
          ),
      },
      {
        path: "/reportform/visitors",
        name: "reportformvisitors",
        component: () =>
          import(
            /* webpackChunkName: "about" */ "../views/ReportForm/Visitors/Visitors.vue"
          ),
      },
    ],
  },
  //个案管理
  {
    path: "/case",
    name: "case",
    redirect: "/case/casemanagement",
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/Case/Case.vue"),
    children: [
      {
        path: "/case/casemanagement",
        name: "casemanagement",
        component: () =>
          import(
            /* webpackChunkName: "about" */ "../views/Case/CaseManagement/CaseManagement.vue"
          ),
      },
    ],
  },
  //数据大屏
  {
    path: "/datascreen",
    name: "datascreen",
    component: () =>
      import(
        /* webpackChunkName: "about" */ "../views/DataScreen/DataScreen.vue"
      ),
  },
];

const router = createRouter({
  history: createWebHashHistory(),
  routes,
});

export default router;
