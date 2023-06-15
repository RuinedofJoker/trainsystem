<template>
<div class="alipay-big">
<div class="alipay-content">
  <p style="color: #909399;font-size: 13px;">微信扫一扫付款</p>
  <span style="color:#FF7700;font-size: 24px;font-weight: bolder;">￥317.00</span>
  <p><img src="../imgs/pay/alipay.png" alt="支付二维码"></p>
  <router-link to="hotel"><p style="padding-top: 0;margin-top: 0;"><span style="color: #1890ff;font-weight: bolder;font-size: 13px;">完成支付</span></p></router-link>
</div>
</div>
</template>

<script>
export default {
  name: "alipay"
}
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
