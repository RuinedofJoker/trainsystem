<template>
<div class="alipay-big">
<div class="alipay-content">
  <p style="color: #909399;font-size: 13px;">支付宝扫一扫付款</p>
  <span style="color:#FF7700;font-size: 24px;font-weight: bolder;">￥{{roomPrice}}</span>
  <p><img src="../imgs/pay/alipay.png" alt="支付二维码"></p>
  <router-link to="hotel"><p style="padding-top: 0;margin-top: 0;"><span style="color: #1890ff;font-weight: bolder;font-size: 13px;">完成支付</span></p></router-link>
</div>
</div>
</template>

<script setup>
import {ref} from 'vue'
import {useRoute} from 'vue-router'

//接收路由传值 问题：路由没有跳转过来
const route = useRoute()
// console.log(route.query)
const roomPrice = route.query.roomPrice
</script>

<style scoped>
.alipay-big{
  margin: 0 auto;
  background-color: whitesmoke;
  width: 800px;
  height: auto;
  text-align: center;
}
.alipay-content{
  margin: 10px auto;
  padding: 20px 0;
}
p{
  margin-top: 0;
  padding-top: 10px;
}

</style>
