<template>
  <div id="app">
    <header>
      <div class="header">
        <van-icon name="arrow-left" class="left" @click="back" />
        <!-- <span>智慧疾控物资管理</span> -->
        <span>{{ header }}</span>
      </div>
    </header>
  </div>
</template>
<script>
export default {
  name: "Header",
  data() {
    return {};
  },
  props: ["header"],
  methods: {
    back() {
      this.$router.go(-1);
    },
  },
};
</script>
<style lang="scss" scoped>
.header {
  width: 100%;
  height: 44px;
  display: flex;
  justify-content: center;
  align-items: center;
  font-family: "PingFang SC";
  font-style: normal;
  font-weight: 500;
  font-size: 18px;
  color: #000;
  border-bottom: 1px solid #eaebed;
  position: relative;
  background: #fff;
}
.left {
  position: absolute;
  font-size: 26px;
  top: 9px;
  left: 15px;
}
</style>
