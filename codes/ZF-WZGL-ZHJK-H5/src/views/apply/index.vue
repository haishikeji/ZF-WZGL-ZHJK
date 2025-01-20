<template>
  <div class="home">
      <div class="box yysq" @click="go(1)" v-if="type == '1'">
          <div class="left">
              <div class="title">预约申请</div>
              <div class="go" style="color: #736FF4;">进入</div>
          </div>
          <div>
              <img style="max-width: 100%;" src="../../assets/yuyue.png" alt="" srcset="">
          </div>
      </div>
      <div class="box mjdj" @click="go(2)" v-if="type == '1'">
          <div class="left">
              <div class="title">门禁登记</div>
              <div class="go" style="color: #FB9801;">进入</div>
          </div>
          <div>
              <img style="max-width: 100%;" src="../../assets/menjin.png" alt="" srcset="">
          </div>
      </div>
      <div class="box wzsq" @click="go(3)" v-if="type == '0'">
          <div class="left">
              <div class="title">物资申请</div>
              <div class="go" style="color: #00B5BA;">进入</div>
          </div>
          <div>
              <img style="max-width: 100%;" src="../../assets/wuzi.png" alt="" srcset="">
          </div>
      </div>
      <Footer />
  </div>
</template>

<script>
import Footer from '../../components/footer/index.vue';
import { useStore } from '@/pinia'
export default {
  name: "Login",
  components: {Footer},
  data() {
    return {
      type: '',
    }
  },
  created () {
    console.log('根组件：created')
    const store = useStore()
    this.type = store.info.type
  },
  methods: {
    go(e) {
        if (e == 1) {
            this.$router.push({name:'yuyueAdd',query: {type:'add'}})
        } else if (e == 2) {
            this.$router.push({name:'menjinAdd',query: {type:'add'}})
        } else if (e == 3) {
            this.$router.push({name:'wuziAdd',query: {type:'add'}})
        }
    }
  }
};
</script>
<style scoped lang="scss">
.home{
    padding: 30px 20px 0;
    .box{
        width: 100%;
        height: 100px;
        padding: 0 20px;
        border-radius: 10px;
        margin-bottom: 20px;
        display: flex;
        justify-content: space-between;
        align-items: center;
        .left{
            .title{
                font-family: 'PingFang SC';
                font-style: normal;
                font-weight: 500;
                font-size: 14px;
                line-height: 18px;
                color: #FFFFFF;
            }
            .go{
                font-family: 'PingFang SC';
                font-style: normal;
                font-weight: 500;
                font-size: 12px;
                line-height: 17px;
                background: #fff;
                height: 20px;
                display: flex;
                align-items: center;
                justify-content: center;
                padding: 0 8px;
                border-radius: 20px;
                margin-top: 15px;
            }
        }
    }
    .yysq{
        background: #736FF4;
    }
    .mjdj{
        background: #FB9801;
    }
    .wzsq{
        background: #00B5BA;
    }
}
</style>