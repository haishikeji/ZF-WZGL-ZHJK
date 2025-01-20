<template>
  <div class="home">
    <Header :header='header' />

    <van-form @submit="onSubmit">

      <van-field v-model="loginName" class="bt" name="帐号" label="帐号" label-width="100" input-align="right" placeholder="请输入帐号(手机号)" />
      <van-field v-model="oldPassWord" type="password" class="bt" name="原密码" label="原密码" label-width="100" input-align="right" placeholder="请输入原密码" />
      <van-field v-model="passWord" type="password" class="bt" name="新密码" label="新密码" label-width="100" input-align="right" placeholder="请输入新密码" />
      <van-field v-model="passWord2" type="password" class="bt" name="确认密码" label="确认密码" label-width="100" input-align="right" placeholder="请输入确认密码" />
      


      <div style="margin: 16px;">
        <van-button block type="info" native-type="submit">提交</van-button>
      </div>
    </van-form>


    
    <!-- <Footer /> -->
  </div>
</template>

<script>
import Header from '../../components/header/index.vue';
import Footer from '../../components/footer/index.vue';
import {formatdate,getY,getM,getD} from "../../static/index.js";
import {edit} from '@/api/api'
import {getAction,postAction} from '@/api/axios'
import { Dialog } from 'vant';
import { Toast } from 'vant';
export default {
  name: "Login",
  components: {Footer,Header},
  data() {
    return {
      name: '',
      header: '修改密码',
      loginName: '',
      nickname: '',
      realName: '',
      userName: '',
      passWord: '',
      passWord2: '',
      phone: '',
    }
  },
  created () {
  },
  methods: {
    login() {
      this.$router.push({name:'login'})
    },
    chkIt(value){
      var isRight = /^\w+$/.test(value);
      if (!isRight) {
          return false;
      }else{
          return true
      }
    },
    onSubmit() {
      if (!this.loginName) {
        Toast('请输入账号');
        return
      }
      if (this.loginName.length != 11) {
        Toast('账号是手机号');
        return
      }
      if (!this.oldPassWord) {
        Toast('请输入原密码');
        return
      }
      if (!this.passWord) {
        Toast('请输入密码');
        return
      }
      if (!this.passWord2) {
        Toast('请输入确认密码');
        return
      }
      if (this.passWord != this.passWord2) {
        Toast('两次输入密码不一致');
        return
      }
      

      
      let parameter = {
        passWord: this.passWord,
        loginName: this.loginName,
        oldPassWord: this.oldPassWord,
      }
      postAction(edit, parameter).then((res) => {
        if (res.data.code == 'S507') {
          Toast('注册成功');
          this.$router.push({name:'login'})
        } else {
          Toast(res.data.msg);
        }
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
.flex{
  display: flex;
  align-items: center;
  justify-content: flex-start;
}
</style>