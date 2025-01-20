<template>
  <div class="home">
      <Header :header='header' />
      <van-form class="info">
        <van-field readonly v-model="username" name="就诊人：" label="就诊人：" label-width="130" input-align="right" />
        <van-field readonly v-model="phone" name="就诊人手机号：" label="就诊人手机号：" label-width="130" input-align="right" />
      </van-form>  
      <van-pull-refresh v-if="list.length > 0" v-model="refreshing" @refresh="onRefresh" style="background: #E5E5E5;padding: 20px 20px 0;">
        <van-list v-model="loading" :finished="finished" :finished-text="finishedtext" loading-text="正在加载" @load="onLoad" offset="30" :immediate-check="false">
            <van-cell v-for="(item,index) in list" :key="index"  @click="godetails(item)">
                <div class="listtop">
                    <div class="listtitle">{{item.checkTypeName}}</div>
                    <div v-if="item.checkResultStatus == '1'" class="listbtn">已出报告</div>
                    <div v-if="item.checkResultStatus != '1'" class="listbtn listbtn1">未出报告</div>
                </div>
                <div class="listtime">
                    检测时间：{{item.reservationTime}}
                </div>
            </van-cell>
        </van-list>
      </van-pull-refresh>
      <div v-if="list.length < 1" style="height: 100%;width: 100%;text-align: center;padding-top: 100px;">
        <img style="width: 40%" src="@/assets/no.png" alt="">
        <div style="margin-top: 20px;">暂时没有数据</div>
      </div>
  </div>
</template>

<script>
import Header from '../../components/header/index.vue';
import {getAction,postAction} from '@/api/axios'
import {subscribe,subscribeqx,pushedRead,getrole} from '@/api/api'
import { Dialog } from 'vant';
import { Toast } from 'vant';
import { useStore } from '@/pinia'
export default {
  name: "Login",
  components: {Header},
  data() {
    return {
      list: [],
      loading: false,
      finished: false,
      refreshing: false,
      active: 2,
      pageNum: 1,
      header: '报告查询',
      phone: '',
      userinfo: {},
      username: '',
      finishedtext: '没有更多了',
    }
  },
  created () {
    console.log('根组件：created')
    const store = useStore()
    this.phone = store.phone
    this.userinfo = store.user
    this.username = this.userinfo.userName ? this.userinfo.userName : '游客'
    this.list = []
    this.active = 2
    this.getlist()
  },
  methods: {
    godetails(item) {
      this.$router.push({name:'baogaoAdd',query: {id: item.reservationId,}})
    },
    getnav(e) {
      if (this.active == e) {
        return
      }
      this.list = []
      this.active = e 
      this.pageNum = 1
      this.loading = true
      this.finished = false
      this.getlist()
    },
    getlist() {
      let parameter = {    //这里是发送给后台的数据
        pageSize: 10,
        pageNum: this.pageNum,
        phone: this.phone,  //登陆用户手机号
      }
      postAction(subscribe, parameter).then((res) => {
        // console.log('res....', res.data)
        let list = res.data.data.list
        for (let i = 0; i < list.length; i++) {
          this.list.push(list[i]);
        }
        this.loading = false
        this.refreshing = false;
        if (this.list.length > 0 && list.length < 10) {
          this.finished = true;
          this.finishedtext = '没有更多了';
        } else {
          this.finished = true;
          this.finishedtext = '';
        }
        
      })
    },
    
    onLoad() {
      this.pageNum = this.pageNum + 1
      this.loading = true
      this.getlist() 
    },
    onRefresh() {
      // 清空列表数据
      this.list = []
      this.finished = false;
      this.pageNum = 1
      // 重新加载数据
      // 将 loading 设置为 true，表示处于加载状态
      this.loading = true;
      this.getlist();
    },
    quxiao(e) {
      Dialog.confirm({
        title: '提示',
        message: '是否确定取消预约',
      }).then(() => {
        let parameter = {    //这里是发送给后台的数据
          checkResultStatus: '4',
          reservationId: e.reservationId,
        }
        postAction(subscribeqx, parameter).then((res) => {
          Toast('取消成功');
          this.onRefresh();
        })
      }).catch(() => {
        // on cancel
      });
    },
  }
};
</script>
<style scoped lang="scss">
.home{
  background: #E5E5E5;
  min-height: 100vh;
}
.tabnav{
    width: 100%;
    height: 40px;
    display: flex;
    justify-content: flex-start;
    align-items: center;
    .tablist{
        width: 50%;
        // text-align: center;
        justify-content: center;
        height: 20px;
        display: flex;
        align-items: center;
        font-family: 'PingFang SC';
        font-style: normal;
        font-weight: 400;
        font-size: 14px;
        color: #000;
    }
    .active{
        color: #0041C4;
    }
    .tablist ::after{
        content: '';
        display: block;
        height: 0;
        border: 1px solid #fff;
        margin-top: 2px;
    }
    .active ::after{
        content: '';
        display: block;
        height: 0;
        border: 1px solid #0041C4;
        margin-top: 2px;
    }
}
.info{
  padding-top: 10px;
  background: #fff;
  .van-cell{
    margin-bottom: 0;
    padding-top: 0;
    padding-bottom: 10px;
  }
}
.van-cell{
    background: #fff;
    margin-bottom: 20px;
    border-radius: 10px;
    padding-top: 10px;
    padding-bottom: 20px;
    .listtop{
        display: flex;
        justify-content: space-between;
        align-items: center;
        .listtitle{
            font-family: 'PingFang SC';
            font-style: normal;
            font-weight: 700;
            font-size: 14px;
            line-height: 18px;
            display: flex;
            align-items: center;
            color: #091F44;
        }
        .listbtn{
            display: flex;
            justify-content: center;
            align-items: center;
            height: 30px;
            padding: 0 20px;
            color: #fff;
            border-radius: 30px;
            background: #0041C4;
            font-size: 12px;
        }
        .listbtn1{
            color: #8D8D8D;
            background: #E7E7E7;
        }
    }
    .listtime{
        font-family: 'PingFang SC';
        font-style: normal;
        font-weight: 400;
        font-size: 12px;
        line-height: 115%;
        display: flex;
        align-items: center;
        text-transform: capitalize;
        color: #091F44;
        margin-top: 10px;
    }
    .listname{
        font-family: 'PingFang SC';
        font-style: normal;
        font-weight: 400;
        font-size: 12px;
        line-height: 115%;
        display: flex;
        align-items: center;
        text-transform: capitalize;
        color: #091F44;
        margin-top: 10px;
    }
    .listedit{
        display: flex;
        justify-content: flex-end;
        .listbtn{
            display: flex;
            justify-content: center;
            align-items: center;
            height: 30px;
            padding: 0 20px;
            color: #fff;
            border-radius: 30px;
            background: #0041C4;
            font-size: 12px;
        }
    }
}
</style>