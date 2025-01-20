<template>
 <div class="box-content">
    <div class="header">
        <div class="title flex-n fac">
            <img src='../../../assets/img/screen/icon.png'/>
        {{title}}
        </div>
        <div class="bottom-line"></div>

    </div>
   <slot></slot>
 </div>
</template>
<script lang="ts" setup>
interface Props{
    title?:string
}
const props = withDefaults(defineProps<Props>(), {
  title: "",
});
</script>
<style lang="scss" scoped>
.box-content{
    .header{
        .title{
          background: rgba(1, 102, 254, 0.4);
          color: white;
          width: fit-content;
          padding-right: 20px;
          font-size: 16px;
          font-weight: 600;
          img{
            border-bottom: 1px solid white;
            margin-right: 10px;
          }
        }
        .bottom-line{
            border-bottom: 1px solid #666;
            height: 5px;
            border-right: 1px solid #666;
            transform: skewX(-45deg);
            margin-top: -5px;
            width: calc(100% - 20px);
            position: relative;
            &::before{
                content:'';
                display: block;
                width: 16px;
                height: 4px;
                position: absolute;
                right: -20px;
                bottom:-1px;
                background: rgb(245, 203, 64);
            }
        }
    }
}
</style>