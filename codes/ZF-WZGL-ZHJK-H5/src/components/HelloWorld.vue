<template>
  <div class="hello">
    <div>
      
    </div>
  </div>
</template>

<script>
import {whlist} from '@/api/api'
import {getAction} from '@/api/axios'
export default {
  name: "HelloWorld",
  props: {
    
  },
  data() {
    return {
      
    }
  },
  created() {
    console.log('created')
    this.getlist()
  },
  methods: {
    getlist() {
      let parameter = this.qs.stringify({    //这里是发送给后台的数据
        token: '9pozDXXfUzFHJehwwrdETbTXtpnQuYLw/aA5AenYCTK+qPJu2DC4CWVc1BX3HPutX3nBr3O82MKvcSVad/ZZWQ==',
        time: 1602318909,
      })
      // parameter = new Object(parameter);

      getAction(whlist, parameter).then((res) => {
        console.log('res....', res.data)
      })
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
