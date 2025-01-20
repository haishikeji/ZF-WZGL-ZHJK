<template>
  <div class="home">
    <Header :header='header' />

    <van-form @submit="onSubmit">

      <van-field class="bt" v-model="info.applyAppointmentTime" v-if="!readonly" readonly clickable name="预约领取时间" label="预约领取时间" label-width="100" input-align="right" placeholder="请选择拜访时间" @click="timePicker = true"/>
      <van-field class="bt" v-model="info.applyAppointmentTime" v-if="readonly" readonly name="预约领取时间" label="预约领取时间" label-width="100" input-align="right" />
      <van-field class="bt" v-model="info.applyDeptName" v-if="readonly" readonly name="所属部门" label="所属部门" label-width="100" input-align="right" />
      <van-field class="bt" v-model="info.applyUserName" v-if="readonly" readonly name="申请人姓名" label="申请人姓名" label-width="100" input-align="right" />
      <van-field class="bt" v-model="info.applyPhone" v-if="readonly" readonly name="申请人手机号" label="申请人手机号" label-width="100" input-align="right" />
      <van-field class="bt" readonly name="申请物资列表" label="申请物资列表" label-width="100" input-align="right">
        <template #button v-if="header == '物资申请'">
          <div @click="goodslist" class="van-button van-button--info van-button--small">
            <div data-v-30111bd5="" class="van-button__content">
              <span data-v-30111bd5="" class="van-button__text">选择物资</span>
            </div>
          </div>
        </template>
      </van-field>
      
      <div style="border-bottom: 1px solid #ccc">
        <div class="flex" v-for="(item,index) in stockOutputModels" :key="index">
          <van-icon v-if="header == '物资申请'" @click="clear(item.goodCode)" name="clear" style="margin-left: 10px;" />
          <van-field v-model="item.output_num" :name="item.goodName" :label="item.goodName" label-width="100" input-align="right"  placeholder="请输入数量"/>
        </div>
      </div>


      <div style="margin: 16px;">
        <van-button v-if="!readonly" block type="info" native-type="submit">提交</van-button>
      </div>
    </van-form>
    
    <van-popup v-model="show" position="top" duration="0" closeable :style="{ height: '100%',width: '100%' }" close-icon-position="top-left" >
      <div class="popheader">
        <div>选择物资</div>
        <div class="popbtn">
          <div @click="setgoodslist" class="van-button van-button--info van-button--small" style="width: 70px;">
            <div data-v-30111bd5="" class="van-button__content">
              <span data-v-30111bd5="" class="van-button__text">完成</span>
            </div>
          </div>
        </div>
      </div>
      <div class="poplist">
        <van-checkbox-group v-model="stockOutputModels">
          <van-checkbox v-for="(item,index) in stocklist" :key="index" :name="item" style="border-bottom: 1px solid #ccc;padding: 20px 0;">{{item.goodName}}</van-checkbox>
        </van-checkbox-group>
      </div>
    </van-popup>

    <van-popup v-model="timePicker" position="bottom">
      <van-datetime-picker type="datetime" title="选择预约时间" :formatter="formatter" :min-date="minDate" @confirm="onConfirmtime" @cancel="showPicker = false" />
    </van-popup>

    <!-- <Footer /> -->
  </div>
</template>

<script>
import Header from '../../components/header/index.vue';
import Footer from '../../components/footer/index.vue';
import {getAction,postAction} from '@/api/axios'
import {communitylist,stockOutputApply,saveAndUpdateAndSubmit,stocklist} from '@/api/api'
import { Dialog } from 'vant';
import { Toast } from 'vant';
import {formatdate,getY,getM,getD} from "../../static/index.js";
export default {
  name: "Login",
  components: {Footer,Header},
  data() {
    return {
      name: '',
      showPicker: false,
      timePicker: false,
      sexPicker: false,
      value: '',
      columns: [],
      sexcolumns:[
        {text: '男',value: '1',},
        {text: '女',value: '2',},
      ],
      info: {},
      sex: '',
      minDate: new Date(2020, 0, 1),
      maxDate: new Date(2025, 10, 1),
      header: '预约',
      readonly: false,
      communityName: '',
      show: false,
      goodTypeCode: '',
      goodName: '',
      stocklist: [],
      stockOutputModels: [],
    }
  },
  created () {
    console.log(this.$route.query.type)
    let type = this.$route.query.type
    if (type == 'add') {
      this.header = '物资申请'
    } else if (type == 'details') {
      this.header = '物资详情'
      this.readonly = true
      this.getdetails(this.$route.query.id)
    }
    // this.getcheckTypelist()
    this.getdate()
    this.getstocklist()
  },
  methods: {
    clear(goodCode) {
      for (let i = 0; i < this.stockOutputModels.length; i++) {
        if (this.stockOutputModels[i].goodCode == goodCode) {
          this.stockOutputModels.splice(i,1)
        }
      }
    },
    goodslist() {
      console.log('goodslist')
      this.show = true
    },
    setgoodslist() {
      console.log('goodslist')
      // this.getstocklist()
      console.log(this.stockOutputModels)
      this.show = false
    },
    getstocklist() {
      let parameter = {
        pageSize: 5000,
        pageNum: 1,
        goodName: this.goodName,
        goodTypeCode: this.goodTypeCode,
      }
      postAction(stocklist, parameter).then((res) => {
        this.stocklist = res.data.data.list
        // Toast('申请成功');
        // this.$router.go(-1)
      })
    },

    getdate() {
      let minY = getY()
      let minM = getM()
      let minD = getD()
      this.minDate = new Date(minY, minM, minD)
    },
    getdetails(e) {
      getAction(stockOutputApply,e).then((res) => {
        this.info = res.data.data
        this.stockOutputModels = res.data.data.stockOutputModels
      })
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
      this.info.communityId = value.communityId
      this.info.visitedOrg = value.communityName
      this.showPicker = false;
      this.communityName = ''
      this.getcheckTypelist()
    },
    onConfirmtime(value) {
      // console.log(formatdate(value))
      this.info.applyAppointmentTime = formatdate(value);
      this.timePicker = false;
    },
    onConfirmsex(value) {
      // console.log(value)
      this.info.sex = value.value;
      this.sex = value.text;
      this.sexPicker = false;
    },
    onSubmit() {
      if (!this.info.applyAppointmentTime) {
        Toast('请选择领取时间');
        return
      }
      if (this.stockOutputModels.length < 1) {
        Toast('请选择物资');
        return
      }
      for (let i = 0; i < this.stockOutputModels.length; i++) {
        if (!this.stockOutputModels[i].output_num) {
          Toast('请输入数量');
          return
        }
      }
      for (var i = 0; i < this.stockOutputModels.length; i++) {
        this.stockOutputModels[i].gridId = '1'
      }
      // console.log( this.stockOutputModels)
      // let stockOutputModels = [
      //   {
      //     goodCode: this.stockOutputModels[0].goodCode,
      //     gridId: this.stockOutputModels[0].gridId,
      //     output_num: this.stockOutputModels[0].output_num,
      //   }
      // ]
      let parameter = {
        applyAppointmentTime: this.info.applyAppointmentTime,
        stockOutputModels: this.stockOutputModels,
        approveStatus: "1",
      }
      postAction(saveAndUpdateAndSubmit, parameter).then((res) => {
        if (res.data.code == 'S507') {
          Toast('申请成功');
          this.$router.push({name:'apply'})
        } else {
          Toast(res.data.msg);
        }
      })
    },
    getcheckTypelist() {
      let parameter = {
        pageSize: 5000,
        pageNum: 1,
        communityStatus: 1,  //社区状态
        communityName: this.communityName,  //社区名称
        communityCode: '',  //社区编码
      }
      postAction(communitylist, parameter).then((res) => {
        let data = res.data.data
        this.columns = data.list
      })
    },
    cancelsq() {
      this.showPicker = false;
      this.communityName = ''
      this.getcheckTypelist()
    },
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
.popheader{
  width: 100%;
  position: absolute;
  top: 0;
  left: 0;
  height: 50px;
  line-height: 50px;
  text-align: center;
}
.popbtn{
  position: absolute;
  top: 0;
  right: 10px;
  display: flex;
  align-items: center;
  height: 50px;
}
.poplist{
  padding: 60px 15px 30px 15px;
}
.flex{
  display: flex;
  justify-content: space-between;
  border-top: 1px solid #ccc;
  align-items: center;
}
</style>