<!-- 首页 -->
<template>
  <div class="index-grid">
    <div class="flexcolumn">
      <el-card class="chartcard">
        <div class="flex-n fac fjb">
          <div class="header">实时汇总数据</div>
          <div class="subtitle">截止至{{ DT.computedDate(new Date(), '/') }}</div>
        </div>
        <div class="content1">
          <div>

            <div class="value">{{ data.localNew || 0 }}</div>
            <div class="label">新增</div>
          </div>
          <div>
            <div class="value">{{ data.newSuspected || 0 }}</div>
            <div class="label">疑似</div>
          </div>
          <div>
            <div class="value">{{ data.localExistingDiagnosis || 0 }}</div>
            <div class="label">确诊</div>
          </div>
          <div>
            <div class="value">{{ data.localCumulativeCure || 0 }}</div>
            <div class="label">累计确诊</div>
          </div>
          <div>
            <div class="value">{{ data.localNewCure || 0 }}</div>
            <div class="label">新增治愈</div>
          </div>
          <div>
            <div class="value">{{ data.localCumulativeCure || 0 }}</div>
            <div class="label">累计治愈</div>
          </div>
        </div>
      </el-card>
      <el-card class="chartcard mt-20 flex-1 flexcolumn">
        <div class="flex-n fac fjb">
          <div class="header">信息发布</div>
          <div class="more" @click="router.push('/antiepidemicinfor/infor/manage')">查看全部></div>
        </div>
        <!--        <div >-->
        <VerticalRolling ref="autoPlaying2" class="content2 flex-1">
          <div class="item flex-n fac mt-10" v-for="(item,index) in infoList" :key="index"  @click="router.push({name:'antiepidemicinformanage',params:{id:item.id}})">
            <el-tag size="small">{{item.consultationName}}</el-tag>
            <div class="text line-1 ml-10">{{item.consultationIitle}}</div>
          </div>
        </VerticalRolling>
        <!--        </div>-->
      </el-card>
    </div>
    <el-card class="chartcard  mapchart">
      <div class="flexcolumn" style="height:100%">
        <div class="header">疫情区域分布</div>
        <MapEchart ref="echart4" class="flex-1"></MapEchart>
      </div>
    </el-card>
    <div class="flexcolumn">
      <el-card class="chartcard flex-1">
        <div class="header">监控预警</div>
        <!-- @click=""/antiepidemictesting/reserve -->
        <VerticalRolling ref="autoPlaying" class="content3 " :time="10">
<!--          <div v-for="i in 4">-->
          <div class="item flex-n fac fjb mt-10"  v-for="(item,index) in monitorList" :key="index">

            <div class="text flex-n fac flex-1"><img class="mr-10" src="../../assets/img/index/tongzhi.png"/>
              <div class="line-1">{{ item.checkResult }}</div>
            </div>
            <div class="time ml-10">{{ item.checkTime }}</div>
          </div>
<!--          </div>-->
        </VerticalRolling>
      </el-card>
      <el-card class="chartcard mt-20 " style="height: 350px">
        <div class="header">检测类型TOP5</div>
        <Echarts ref="echart3" style="height:300px"></Echarts>

      </el-card>
    </div>
    <el-card class="chartcard">
      <div class="header">检测数据</div>
      <div class="content5 mt-10">
        <el-date-picker v-model="dateRange" type="daterange" class="w-280 " range-separator="-" start-placeholder="开始时间"
                        end-placeholder="结束时间"/>
        <div class="flex-n fac mt-10">
          <div class="left-content">
            <div class="label">防疫检测预约</div>
            <div class="value">{{ data.alreadyCheck }}</div>
          </div>
          <Echarts ref="echart1" class="flex-1" style="height:100px"></Echarts>
        </div>
        <div class="flex-n fac">
          <div class="left-content">
            <div class="label">防疫实际检测</div>
            <div class="value">{{ data.reservationt }}</div>
          </div>
          <Echarts ref="echart2" class="flex-1" style="height:100px"></Echarts>

        </div>
      </div>
    </el-card>
    <el-card class="chartcard">
      <div class="header">社区数据排行</div>
      <div class="content4">
        <div class="flex-n fac" v-for="(item,index) in communityList">
          {{item.communityName}}
          <el-progress :stroke-width="12" class="flex-1 ml-10" :percentage="item.communityCaseCount/maxNum*100" :format="format"/>
        </div>
      </div>
    </el-card>
    <el-card class="chartcard">
      <div class="header">高发病症</div>
      <div class="content8">
        <div class="label1">麻疹</div>
        <div class="label2">新冠</div>
        <div class="label3">狂犬病</div>
        <div class="label4">百日咳</div>
        <div class="label5">肺结核</div>
      </div>
    </el-card>
  </div>
</template>
<script lang="ts" setup>
import * as echarts from "echarts";
import {onMounted, reactive, ref, watch} from "vue";
import {reservationtApi, sepidemicDataApi, mapDataApi, sConsultationApi} from "../../utils/Api";
import Echarts from "../../components/Echarts/Echarts.vue";
import MapEchart from "../../components/MapEchart/MapEchart.vue";
import VerticalRolling from "../../components/VerticalRolling/VerticalRolling.vue";
import DT from "../../assets/js/date";
import { useRouter } from "vue-router";
const router=useRouter();
const dateRange = ref();
const monitorList=ref([])
const infoList=ref([])
const communityList=ref([]);
const maxNum=ref(0);
const mapKey = {
  "公主岭市": 'gzl',
  "榆树市": 'ys',
  "南关区": 'ng',
  "宽城区": 'kc',
  "朝阳区": 'cy',
  "二道区": 'ed',
  "绿园区": 'ly',
  "双阳区": 'sy',
  "九台区": 'jt',
  "农安县": 'na',
  "德惠市": 'dh',
}
const autoPlaying=ref();
const autoPlaying2=ref();
const data = reactive({
  alreadyCheck: '',//防疫检测预约人数
  reservationt: '',//防疫实际检测人数
})
const echart1 = ref();
const echart2 = ref();
const echart3 = ref();
const echart4 = ref();
const format = (percentage: any) => `${percentage}例`
onMounted(() => {
  dateRange.value = [DT.computedDate(), DT.computedDate()]
  checkTypeTop();//检测类型TOP5
  checkTotal();//检测数据统计
  checkTotalCurveByDay();//监测数据统计曲线
  sepidemicDataAll();//首页疫情数据今日
  mapDataAll();//首页地图数据今日
  monitorWarning();//监控预警
  informationRelease();//信息发布
  countCommunityCase();//社区数据排行
})

const getEchartOne = (data:any) => {
  let list=[],dateList=[];
  for(let i in data){
    list.push({
      name:data[i].dat.replace(/\-/g,"/").substring(5),
      value:data[i].reservationt
    })
    dateList.push(data[i].dat.replace(/\-/g,"/").substring(5))
  }
  echart1.value.setEchart({
    grid: {
      containLabel: true,
      bottom: 0,
      left: 0,
      right: 20,
      top: 0
    },
    xAxis: {
      type: 'category',
      boundaryGap: false,
      axisLine: {
        show: false,
      },
      axisTick: {
        show: false,
      },
      axisLabel: {
        color: '#aaa',
        fontSize: 10
      },
      data: dateList
    },
    yAxis: {
      type: 'value',
      show: false
    },
    series: [
      {
        data: list,
        type: 'line',
        smooth: 0.6,
        symbol: 'none',
        lineStyle: {
          color: new echarts.graphic.LinearGradient(0, 1, 1, 1, [
            {
              offset: 0,
              color: 'rgba(102, 153, 255, 0.01)'
            },
            {
              offset: 0.2,
              color: 'rgba(102, 153, 255, 1)'
            },
            {
              offset: 1,
              color: 'rgba(24, 109, 245, 1)'
            }
          ])
        },
        areaStyle: {
          color: new echarts.graphic.LinearGradient(0, 1, 1, 1, [
            {
              offset: 0,
              color: 'rgba(102, 153, 255, 0)'
            },
            {
              offset: 0.2,
              color: 'rgba(102, 153, 255, 0.48)'
            },
            {
              offset: 1,
              color: 'rgba(102, 153, 255, 0.8)'
            }
          ])
        }

      }
    ]
  })
}
const getEchartTwo = (data:any) => {
  let list=[],dateList=[];
  for(let i in data){
    list.push({
      name:data[i].dat.replace(/\-/g,"/").substring(5),
      value:data[i].alreadyCheck
    })
    dateList.push(data[i].dat.replace(/\-/g,"/").substring(5))
  }
  echart2.value.setEchart({
    grid: {
      containLabel: true,
      bottom: 0,
      left: 0,
      right: 20,
      top: 0
    },
    xAxis: {
      type: 'category',
      boundaryGap: false,
      axisLine: {
        show: false,
      },
      axisTick: {
        show: false,
      },
      axisLabel: {
        color: '#aaa',
        fontSize: 10
      },
      data: dateList
    },
    yAxis: {
      type: 'value',
      show: false
    },
    series: [
      {
        data:list,
        type: 'line',
        smooth: 0.6,
        symbol: 'none',
        lineStyle: {
          color: new echarts.graphic.LinearGradient(0, 1, 1, 1, [
            {
              offset: 0,
              color: 'rgba(102, 255, 173, 0.01)'
            },
            {
              offset: 0.2,
              color: 'rgba(0, 222, 4, 1)'
            },
            {
              offset: 1,
              color: 'rgba(0, 207, 96, 1)'
            }
          ])
        },
        areaStyle: {
          color: new echarts.graphic.LinearGradient(0, 1, 1, 1, [
            {
              offset: 0,
              color: 'rgba(145, 255, 102, 0)'
            },
            {
              offset: 0.2,
              color: 'rgba(72, 208, 162, 0.48) '
            },
            {
              offset: 1,
              color: 'rgba(57, 227, 170, 0.8)'
            }
          ])
        }
      }
    ]
  })
}
const getEchartThree = (list: any) => {
  let color = ['#FFC700', '#99c937', '#950AEF', '#00A0E9', '#CB5B66'];
  let seriesData = [], yname = [];
  for (let i = 0; i < list.length; i++) {
    seriesData.push({
      name: list[i].checkTypeName,
      value: Number(list[i].checkTypeCount),
      itemStyle: {
        color: color[i],
      }
    })
    yname.push(list[i].checkTypeName)
  }
  echart3.value.setEchart({
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow'
      }
    },
    grid: {
      top: 10,
      left: 0,
      bottom: 0,
      right: 10,
      containLabel: true
    },
    xAxis: {
      type: 'value',
      boundaryGap: [0, 0.01],

    },
    yAxis: {
      type: 'category',
      axisTick: {
        show: false,
      },
      splitLine: {
        lineStyle: {
          type: 'dashed'
        }
      },
      axisLine: {
        lineStyle: {
          color: '#aaa'
        }
      },
      data: yname
    },
    series: [
      {
        type: 'bar',
        barWidth: 20,
        itemStyle: {
          borderRadius: [0, 10, 10, 0],
          shadowColor: '#ddd',
          shadowBlur: 10
        },
        data: seriesData
      }
    ]
  })
}
const setEchartFour = (data:any) => {
  echart4.value.setEchart({
    tooltip: {
      formatter: function (params:any, ticket:any, callback:any) {
        // console.log(params.name)

        return   (data[mapKey[params.name]+'AreaOld']||0)+'例'
      }
    },
    series: [
      {
        type: 'map',
        zoom: 1.25,
        aspectScale: 0.8,
        label: {
          emphasis: {
            show: false,
          }
        },
        select: {label: false},
        itemStyle: {
          shadowColor: '#409eff',
          shadowOffsetX: 5,
          shadowOffsetY: 5,
          shadowBlur: 2,
          // opacity: 1,
          borderColor: 'white',
          areaColor: {
            type: 'linear',
            x: 0,
            y: 0,
            x2: 0,
            y2: 1,
            colorStops: [{
              offset: 0, color: '#2f8bdb' // 0% 处的颜色
            }, {
              offset: 1, color: '#2f8bdb99' // 100% 处的颜色
            }],
            global: false // 缺省为 false
          }
        },
      },
      {
        type: 'effectScatter',
        coordinateSystem: 'geo',
        symbolSize: 8,
        rippleEffect: { //坐标点动画
          period: 3,
          scale: 5,
          brushType: 'fill'
        },
        label: {
          normal: {
            show: true,
            position: 'right',
            formatter: '{b}',
            color: 'white',
            shadowBlur: 20,
            shadowOffsetX: 0,
            shadowOffsetY: 10,
            fontSize: 14
          }
        },

        data: [{
          name: "公主岭市",
          value: [124.674107, 43.786324],
        }, {
          name: "榆树市",
          value: [126.595959, 44.873204]
        },
          {
            name: "南关区",
            value: [125.412301, 43.725267]
          },
          {
            name: "宽城区",
            value: [125.326657, 44.088569]
          },
          {
            name: "朝阳区",
            value: [125.318042, 43.86491]
          },
          {
            name: "二道区",
            value: [125.606605, 43.866861]
          },
          {
            name: "绿园区",
            value: [125.175055, 43.890116]
          },
          {
            name: "双阳区",
            value: [125.70623, 43.52608]
          },
          {
            name: "九台区",
            value: [125.979165, 44.204262]
          },
          {
            name: "农安县",
            value: [125.08508, 44.472531]
          },
          {
            name: "德惠市",
            value: [125.810373, 44.554404]
          },
        ],
        itemStyle: { //坐标点颜色
          normal: {
            show: true,
            color: 'white',
            shadowBlur: 20,
            shadowColor: '#fff'
          },
          emphasis: {
            areaColor: '#f00'
          }
        },
      },
    ]
  });
}
const checkTypeTop = () => {
  reservationtApi.checkTypeTop().then(res => {
    console.log('检测类型TOP5', res)
    res.data.list = res.data.list.sort((a, b) => Number(a.checkTypeCount) - Number(b.checkTypeCount));
    getEchartThree(res.data.list);
  }).finally(() => {

  })
}
watch(
    () => dateRange.value,
    (newval: any) => {
      checkTotal();
      checkTotalCurveByDay();
    }
)
const checkTotal = () => {
  reservationtApi.checkTotal({
    dateStart: dateRange.value?.[0] || '',
    dateEnd:  dateRange.value?.[1] || '',
  }).then(res => {
    console.log('检测数据', res)
    Object.assign(data, res.data);
    console.log(data)
  }).finally(() => {

  })
}
const checkTotalCurveByDay=()=>{
  reservationtApi.checkTotalCurveByDay({
    dateStart: dateRange.value?.[0] || '',
    dateEnd:  dateRange.value?.[1] || '',
  }).then(res=>{
    console.log('检测数据曲线', res)

    getEchartOne(res.data.list);
    getEchartTwo(res.data.list);
  }).finally(err=>{

  })
}
const sepidemicDataAll = () => {
  sepidemicDataApi.findAll({
    statisticalTime: DT.computedDate()
  }).then(res => {
    console.log('实时数据', res)
    Object.assign(data, res.data.list[0] || {});
  }).finally(() => {

  })
}
const mapDataAll = () => {
  mapDataApi.findAll({
    statisticalTime: DT.computedDate()
  }).then(res => {
    console.log('地图数据', res)

    setEchartFour(res.data.list[0]||{});
  }).finally(() => {

  })
}
const monitorWarning=()=>{
  reservationtApi.monitorWarning({}).then(res=>{
    console.log('监控预警', res)
    monitorList.value=res.data.list||[];
    autoPlaying.value.setAutoPlay();
  }).finally(()=>{

  })
}
const informationRelease=()=>{
  sConsultationApi.findAll({
     pageSize: 20,
        pageNum: 1
  }).then(res => {
     console.log('信息发布', res)
        infoList.value=res.data.list||[];
    autoPlaying2.value.setAutoPlay();
    }).finally(() => {
    })
}
const countCommunityCase=()=>{
  reservationtApi.countCommunityCase({}).then(res=>{
console.log('社区数据排行', res)
        communityList.value=res.data.list||[];
        let max=0;
        for(let i in communityList.value){
          if(max<Number(communityList.value[i].communityCaseCount)){
            max=Number(communityList.value[i].communityCaseCount);
          }
        }
       
        maxNum.value=Math.pow(10, max.toString().length||1);
  }).finally(() => {
    })
}
</script>
<style scoped lang="scss">
.index-grid {
  display: grid;
  height: 100%;
  grid-template-columns: minmax(330px, 1fr)  minmax(330px, 2fr) minmax(330px, 1fr);
  grid-template-rows:  minmax(500px, calc(100vh - 420px)) 300px;
  grid-gap: 20px;

  ::v-deep(.chartcard) {
    .el-card__body {
      height: 100%;
      display: flex;
      flex-direction: column;
    }
  }

  .content1 {
    background: #f2f7fb;
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    margin-top: 20px;
    border-radius: 4px;

    > div {
      text-align: center;
      padding: 20px 10px;
    }

    .value {
      font-size: 22px;
      font-weight: 600;
    }

    .label {
      font-size: 14px;
    }
  }

  .content2 {
    .item {
      // cursor: pointer;
      font-size: 14px;

      // .text {
      //   &:hover {
      //     color: rgb(75, 176, 235);
      //   }
      // }
    }

  }

  .content3 {
    .item {
      .text {
        color: rgb(75, 176, 235);
        font-size: 14px;
        overflow: hidden;
      }

      .time {
        font-size: 12px;
        color: #aaa;
        white-space: nowrap;
      }
    }
  }

  .content4 {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    height: 230px;
    margin-top: 10px;
    grid-gap: 10px;
    grid-template-rows: repeat(5, 1fr);

    > div {
      width: 100%;
    }
  }

  .content5 {
    .left-content {
      text-align: center;

      .label {
        font-size: 14px;
        white-space: nowrap;
      }

      .value {
        font-size: 28px;
        white-space: nowrap;
      }
    }
  }

  .content8 {
    position: relative;

    > [class^='label'] {
      color: white;
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 22px;
      opacity: 0.67;
      position: absolute;
      border-radius: 50%;
    }

    .label1 {
      width: 100px;
      height: 100px;
      top: 30px;
      left: 0px;
      background: linear-gradient(89.54deg, #27B1FE 0.46%, #1DC560 118.27%);
      box-shadow: 0px 39.2317px 56.0453px rgba(104, 208, 215, 0.3);
    }

    .label2 {
      width: 130px;
      height: 130px;
      right: 10px;
      background: linear-gradient(195.26deg, #B600F1 23.55%, #FF3CEB 90.31%);
      box-shadow: 0px 25px 60px rgba(192, 0, 240, 0.55)
    }

    .label3 {
      width: 82px;
      height: 82px;
      left: 34%;
      top: 100px;
      background: linear-gradient(329.04deg, #FF753D 34.42%, rgba(255, 219, 128, 0.76) 99.58%);
      box-shadow: 0px 25px 35px rgba(198, 107, 0, 0.25);
    }

    .label4 {
      width: 82px;
      height: 82px;
      left: 10%;
      top: 160px;
      background: linear-gradient(315.24deg, #FFC700 14.64%, rgba(255, 199, 0, 0.51) 87.51%);
      opacity: 0.67;
      box-shadow: 0px 25px 35px rgba(198, 107, 0, 0.25);
    }

    .label5 {
      width: 84px;
      height: 84px;
      right: 10%;
      top: 140px;
      background: linear-gradient(327.69deg, #4671F6 15.56%, #7C93FF 84.98%);
      box-shadow: 0px 20px 30px rgba(89, 105, 233, 0.26);
    }
  }
}

.chartcard {
  .header {
    font-size: 16px;
    font-weight: bold;
  }

  .subtitle {
    font-size: 12px;
    font-weight: bold;
  }

  .more {
    font-size: 12px;
    color: #202020;
    cursor: pointer;
  }

}

</style>
<style lang="scss">
::v-deep(.mapchart) {
  .el-card__body {
    height: 100% !important;
  }
}
</style>