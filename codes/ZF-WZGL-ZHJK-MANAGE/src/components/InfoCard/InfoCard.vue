<template>
  <el-card class="auto-card">
    <template #header>
      <slot name="header"></slot>
    </template>
    <slot></slot>
  </el-card>
</template>
<script lang="ts" setup>

</script>
<style lang="scss" scoped>
.el-card{
  border-radius: 10px;
}
::v-deep(.el-card__header){
  // background-image: linear-gradient(to right, #e8f4ff, #dfdfdf);
  background: #f2f2f2;
  color: #666;
  font-weight: 600;
}
</style>