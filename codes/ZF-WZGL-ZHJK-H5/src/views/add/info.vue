<template>
  <div class="home">
    <Header :header='header' />

    <van-form @submit="onSubmit">





      <van-field v-model="info.reportUnit" class="bt" name="信息上报单位" label="信息上报单位" label-width="100" input-align="right" placeholder="请输入信息上报单位" />
      <van-field v-model="info.peopleName" class="bt" name="姓名" label="姓名" label-width="100" input-align="right" placeholder="请输入姓名" />
      <van-field v-model="info.phone" class="bt" name="电话" label="电话" label-width="100" input-align="right" placeholder="请输入电话" />
      <van-field v-model="info.identityname" readonly name="身份" label="身份" label-width="100" input-align="right" placeholder="请选择身份"  @click="showPicker = true" />
      <van-field v-model="info.nationality" name="国籍" label="国籍" label-width="100" input-align="right" placeholder="请输入国籍" />
      <van-field v-model="info.idnum" class="bt" name="有效证件号" label="有效证件号" label-width="100" input-align="right" placeholder="请输入有效证件号" />
      <van-field v-model="info.sexname" readonly name="性别" label="性别" label-width="100" input-align="right" placeholder="请选择性别"  @click="sexPicker = true" />
      <van-field v-model="info.birth" readonly clickable name="出生日期" label="出生日期" label-width="100" input-align="right" placeholder="请选择出生日期" @click="time1Picker = true"/>
      <van-field v-model="info.age" name="年龄" label="年龄" label-width="100" input-align="right" placeholder="请输入年龄" />
      <van-field v-model="info.nation" name="民族" label="民族" label-width="100" input-align="right" placeholder="请输入民族" />
      <van-field v-model="info.nativePlace" name="籍贯" label="籍贯" label-width="100" input-align="right" placeholder="请输入籍贯" />
      <van-field v-model="info.peopleAdd" class="bt" name="现住详细地" label="现住详细地" label-width="100" input-align="right" placeholder="请输入现住详细地" />
      <van-field v-model="info.occupationname" readonly name="职业" label="职业" label-width="100" input-align="right" placeholder="请选择职业"  @click="occPicker = true" />
      <van-field v-model="info.company" name="单位" label="单位" label-width="100" input-align="right" placeholder="请输入单位" />
      <van-field v-model="info.travelRecent" class="bt" name="近期旅行地" label="近期旅行地" label-width="100" input-align="right" placeholder="请输入近期旅行地" />
      <van-field v-model="info.caseStatusname" readonly name="个案状态" label="个案状态" label-width="100" input-align="right" placeholder="请选择个案状态"  @click="casePicker = true" />
      <van-field v-model="info.arrivalDate" readonly clickable name="到达日期" label="到达日期" label-width="100" input-align="right" placeholder="请选择到达日期" @click="time2Picker = true"/>
      <!-- <van-field v-model="name" name="就诊人姓名" label="就诊人姓名" label-width="100" input-align="right" placeholder="请输入就诊人姓名" /> -->





      <div style="margin: 16px;">
        <van-button block type="info" native-type="submit">提交</van-button>
      </div>
    </van-form>

    <van-popup v-model="showPicker" position="bottom">
      <van-picker show-toolbar :columns="columns" @confirm="onConfirm" title="选择身份" @cancel="showPicker = false" />
    </van-popup>
    <van-popup v-model="sexPicker" position="bottom">
      <van-picker show-toolbar :columns="sexcolumns" @confirm="onConfirmsex" title="选择性别" @cancel="sexPicker = false" />
    </van-popup>
    <van-popup v-model="time1Picker" position="bottom">
      <van-datetime-picker type="date" title="选择出生日期" :formatter="formatter" :min-date="minDate1" @confirm="onConfirmtime1" @cancel="time1Picker = false" />
    </van-popup>
    <van-popup v-model="time2Picker" position="bottom">
      <van-datetime-picker type="date" title="选择到达日期" :formatter="formatter" :min-date="minDate" @confirm="onConfirmtime2" @cancel="time2Picker = false" />
    </van-popup>
    <van-popup v-model="occPicker" position="bottom">
      <van-picker show-toolbar :columns="columnsocc" @confirm="onConfirmocc" title="选择身份" @cancel="occPicker = false" />
    </van-popup>
    <van-popup v-model="casePicker" position="bottom">
      <van-picker show-toolbar :columns="columnscase" @confirm="onConfirmcase" title="选择个案状态" @cancel="casePicker = false" />
    </van-popup>

    <!-- <Footer /> -->
  </div>
</template>

<script>
import Header from '../../components/header/index.vue';
import Footer from '../../components/footer/index.vue';
import {formatdate,getY,getM,getD} from "../../static/index.js";
import {peopleAdd} from '@/api/api'
import {getAction,postAction} from '@/api/axios'
import { Dialog } from 'vant';
import { Toast } from 'vant';
export default {
  name: "Login",
  components: {Footer,Header},
  data() {
    return {
      name: '',
      header: '个案录入',
      showPicker: false,
      sexPicker: false,
      time1Picker: false,
      time2Picker: false,
      occPicker: false,
      casePicker: false,
      info: {},
      columns:[
        {text: '普通公民',value: '1',},
        {text: '华侨',value: '2',},
        {text: '台胞',value: '3',},
      ],
      sexcolumns:[
        {text: '男',value: '1',},
        {text: '女',value: '2',},
      ],
      columnsocc:[
        {text: '其他',value: '1',},
        {text: '销售',value: '2',},
        {text: '网络',value: '3',},
        {text: '金融',value: '4',},
        {text: '教育',value: '5',},
        {text: '医疗',value: '6',},
        {text: '建筑',value: '7',},
        {text: '行政管理',value: '8',},
        {text: '专业技术',value: '9',},
      ],
      columnscase:[
        {text: '隔离中(居家)',value: '1',},
        {text: '隔离中(隔离酒店)',value: '2',},
        {text: '已解除隔离',value: '3',},
        {text: '未隔离',value: '4',},
      ],
      minDate1: new Date(1970, 0, 1),
      minDate: new Date(2020, 0, 1),
      maxDate: new Date(2025, 10, 1),
    }
  },
  created () {
    console.log('根组件：created')
    this.getdate()
  },
  methods: {
    getdate() {
      let minY = getY()
      let minM = getM()
      let minD = getD()
      this.minDate = new Date(minY, minM, minD)
    },
    formatter(type, val) {
      if (type === 'year') {
        return `${val}年`;
      } else if (type === 'month') {
        return `${val}月`;
      } else if (type === 'day') {
        return `${val}日`;
      }
      return val;
    },
    onConfirm(value) {
      console.log(value)
      this.info.identityname = value.text;
      this.info.identity = value.value;
      // this.sex = value.text;
      this.showPicker = false;
    },
    onConfirmsex(value) {
      // console.log(value)
      this.info.sexname = value.text;
      this.info.sex = value.value;
      // this.sex = value.text;
      this.sexPicker = false;
    },
    onConfirmtime1(value) {
      // console.log(formatdate(value))
      this.info.birth = formatdate(value);
      this.time1Picker = false;
    },
    onConfirmtime2(value) {
      // console.log(formatdate(value))
      this.info.arrivalDate = formatdate(value);
      this.time2Picker = false;
    },
    onConfirmocc(value) {
      // console.log(formatdate(value))
      this.info.occupationname = value.text;
      this.info.occupation = value.value;
      this.occPicker = false;
    },
    onConfirmcase(value) {
      // console.log(formatdate(value))
      this.info.caseStatusname = value.text;
      this.info.caseStatus = value.value;
      this.casePicker = false;
    },
    onSubmit() {
      let parameter = {
        reportUnit: this.info.reportUnit,
        peopleName: this.info.peopleName,
        phone: this.info.phone,
        identity: this.info.identity,
        nationality: this.info.nationality,
        idnum: this.info.idnum,
        sex: this.info.sex,
        birth: this.info.birth,
        age: this.info.age,
        nation: this.info.nation,
        nativePlace: this.info.nativePlace,
        peopleAdd: this.info.peopleAdd,
        occupation: this.info.occupation,
        company: this.info.company,
        travelRecent: this.info.travelRecent,
        caseStatus: this.info.caseStatus,
        arrivalDate: this.info.arrivalDate,
      }
      console.log(parameter)
      postAction(peopleAdd, parameter).then((res) => {
        Toast('录入成功');
        this.$router.push({name:'apply'})
      })
    }
  }
};
</script>
<style scoped lang="scss">
.home{
    // padding: 30px 20px 0;
}
.bt.van-cell::after{
  content: '*';
  display: block;
  font-size: 16px;
  color: #ee0a24;
  position: absolute;
  top: 10px;
  left: 10px;
  bottom: auto;
  border-bottom: 0px solid #ebedf0;
}
</style>