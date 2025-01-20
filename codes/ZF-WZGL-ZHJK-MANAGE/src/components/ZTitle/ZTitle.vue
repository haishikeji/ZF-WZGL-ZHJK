<template>
 <div class="title flex-n fac fjb">
  <div>{{title}}</div>
  <slot></slot>
 </div>
</template>
<script lang="ts" setup>
interface Props {
    title?:string
}
const props = withDefaults(defineProps<Props>(), {
    title:''
})
</script>
<style lang="scss" scoped>
.title{
  border-left: 4px solid #4cb3f8;
  padding: 10px;
  background: #f5f7fa;
  font-weight: 700;
  font-size: 14px;
  color: #666;
}
</style>