<template>
  <div class="home">
    <div class="loginimg">
      <img src="../../assets/login.png" alt="" srcset="">
    </div>
    <van-cell-group style="border: 1px solid #ccc,">
      <van-field type="number" v-model="phone" placeholder="请输入账号(手机号)" />
    </van-cell-group>
    <van-cell-group style="border: 1px solid #ccc,">
      <van-field type="password" v-model="code" placeholder="请输入密码" />
    </van-cell-group>
    <!-- <van-cell-group style="border: 1px solid #ccc,">
      <van-field type="number" v-model="code" placeholder="请输入短信验证码" />
      <span v-if="!disadle" @click="getcode" class="getcode">{{codetitle}}</span>
      <span v-if="disadle" class="getcode">{{codetitle}}</span>
    </van-cell-group> -->

    <div class="flex" style="justify-content: space-between;margin-bottom: 20px;">
      <div @click="edit" style="color: #1648CE;font-size: 14px;">修改密码</div>
      <div @click="register" style="color: #1648CE;font-size: 14px;">注册</div>
    </div>
    
    <div class="flex">
      <van-checkbox v-model="checkbox" shape="square" />
      <div class="fwxy">用户服务协议</div>
    </div>

    <van-button @click="login" style="width: 100%;margin-top: 30px;" color="#1648CE" type="info">登录</van-button>

  </div>
</template>

<script>
import {getToken, setToken, removeToken,setUser,setPhone} from '@/utils/authorize'
import {getAction,postAction,loginAction} from '@/api/axios'
import {login,findByPhone} from '@/api/api'
import { Toast,Dialog } from 'vant';
export default {
  name: "Login",
  components: {},
  data() {
    return {
      phone: '',
      code: '',
      codetitle: '发送验证码',
      checkbox: false,
      store: {},
      count: 59,
      disadle: false,
    }
  },
  created () {
    console.log('根组件：created',this.$pinia)
    if (this.$route.query.type) {
      this.reload()
    }
    // this.$router.go(0)
  },
  methods: {
    register() {
      this.$router.push({name:'register'})
    },
    edit() {
      this.$router.push({name:'edit'})
    },
    getcode() {
      // this.codetitle = Number(60)
      Toast('验证码为123456');
      return
      if (!this.phone) {
        Toast('请输入手机号');
        return
      }
      this.codetitle = "重新获取(" + this.count + ")";
      this.count--;
      if (this.count <= 0) {
        this.count = 60;
        this.codetitle = "获取验证码";
        this.disadle = false;
      } else {
        this.disadle = true;
        setTimeout(() => {
          this.getcode();
        }, 1000);
      }
    },
    chkIt(value){
      var isRight = /^\w+$/.test(value);
      if (!isRight) {
          return false;
      }else{
          return true
      }
    },
    login(e) {
      if (!this.phone) {
        Toast('请输入账号');
        return
      }
      if (this.phone.length != 11) {
        Toast('账号是手机号');
        return
      }
      if (!this.code) {
        Toast('请输入密码');
        return
      }
      if (!this.checkbox) {
        Toast('请选择用户服务协议');
        return
      }
      let parameter = {    //这里是发送给后台的数据
        loginName: this.phone,
        passWord: this.code,
      }
      // console.log(parameter)
      loginAction(login, parameter).then((res) => {
        // this.store.SetInfo(res.data.data)
        if (res.data.code == 'success') {
          let token = res.data.data
          setToken(token)
          setUser(res.data.data.userInfo)
          setPhone(res.data.data.userInfo.phone)
          Toast('登录成功');
          // return
          this.$nextTick(function(){
            this.$router.push({name:'Home'})
            // this.$router.go(-2)
          });
        } else {
          Dialog.alert({
            title: '提示',
            message: res.data.msg + ',请重新登录',
          }).then(() => {
          });
        }
      })
    },
    reload() {
      this.$router.push({name:'login'})
      location.reload()
    },
  }
};
</script>
<style scoped lang="scss">
.home{
    padding: 0 20px;
    .loginimg{
        width: 100%;
        text-align: center;
        margin-top: 15px;
        img{
            max-width: 100%;
            margin-bottom: 0;
        }
    }
}
.van-cell-group{
    border: 1px solid #E2E5E7;
    border-radius: 5px;
    height: 45px;
    margin-bottom: 24px;
    overflow: hidden;
    position: relative;
}
.getcode{
    position: absolute;
    display: flex;
    align-items: center;
    height: 100%;
    right: 15px;
    top: 0px;
    font-family: 'Roboto';
    font-style: normal;
    font-weight: 400;
    font-size: 14px;
    line-height: 16px;
    color: #1648CE;
}
.flex{
    display: flex;
    align-items: center;
    justify-content: flex-start;
}
.fwxy{
    font-family: 'Roboto';
    font-style: normal;
    font-weight: 400;
    font-size: 14px;
    line-height: 16px;
    color: #1648CE;
    margin-left: 5px;
}
</style>