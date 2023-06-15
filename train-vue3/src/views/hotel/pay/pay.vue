<template>
  <div class="pay-big">

    <div class="title">安全支付</div>
    <div class="pay-detail">
      <span>订单金额</span>
      <span style="color:#FF7700;">￥</span>
      <span style="color:#FF7700;font-size: 24px;">{{ roomPrice }}</span>
    </div>
    <div class="pay-detail-hotel">
      <span>{{hotelName}}</span>
      <span>{{roomType}}&nbsp;{{num}}间</span>
      <span>入住时间：{{checkDate[0]}}至{{checkDate[1]}}</span>
      <span>住户：{{customer}}</span>
    </div>
    <div class="pay-way">
      <p>支付方式</p>
      <span @click="centerDialogVisible=true"><img src="../imgs/pay/zfb.png" alt=""><div style="text-align: center;">支付宝支付</div></span>
      <span @click="wxDialogVisible=true"><img src="../imgs/pay/wx.png" alt=""><div
          style="text-align:center;width: 130px;">微信支付</div></span>
    </div>

    <!--    弹出支付宝支付对话框-->
    <el-dialog v-model="centerDialogVisible" title="支付宝" width="30%" center>
    <span class="dialog-content">
      支付金额 <span style="color:#FF7700;font-size: 24px;font-weight: bolder;">￥{{ roomPrice }}</span>
    </span>
      <template #footer>
      <span class="dialog-footer">

        <el-button type="warning" color="#FF7700" @click="jump(roomPrice)">
         <span style="font-size: 20px;font-weight: bolder; color: white;">去支付</span>
        </el-button>

      </span>
      </template>
    </el-dialog>

    <!--    弹出微信支付对话框-->
    <el-dialog v-model="wxDialogVisible" title="微信支付" width="30%" center>
      <div class="dialog-content">
        支付金额 <span style="color:#FF7700;font-size: 24px;font-weight: bolder;">￥{{ roomPrice }}</span>
      </div>
      <div style="margin: 0 auto;">
        <p class="ewm"><img src="../imgs/pay/alipay.png" alt="微信支付二维码"></p>
        <div class="sao"><img src="../imgs/pay/saocode.png" alt=""><span>打开微信扫一扫</span></div>
      </div>
    </el-dialog>

  </div>
</template>

<script setup>
import {ref} from 'vue'
import {useRoute} from 'vue-router'
import {useRouter} from 'vue-router'

let centerDialogVisible = ref(false)
let wxDialogVisible = ref(false)

//接收路由传值
const route = useRoute()
// let roomType = route.query.roomType
// let roomPrice = route.query.roomPrice
// let hotelName=route.query.hotelName
const {roomType,roomPrice,hotelName,checkDate,num,customer}=route.query
console.log(route.query)



//路由跳转 支付宝扫码支付页面
const router = useRouter()
function jump(roomPrice) {
  router.push({
    path: '/index/alipay',
    query: {
      roomPrice:roomPrice
    }
  })
}

</script>

<style scoped>

.sao {
  /*border: #1890ff 2px solid;*/
  width: 70%;
  text-align: center;
  height: 30px;
  line-height: 30px;
  display: inline-block;
  position: relative;
}

.sao img {
  /*float: left;*/
}

.sao span {
  /*border: #1890ff 1px solid;*/
  display: inline-block;
  height: 30px;
  line-height: 30px;
  position: absolute;
  margin-left: 10px;
  font-size: 14px;
}

.ewm {
  width: 100%;
  height: 168px;
  line-height: 168px;
  text-align: center;
}

.dialog-content {
  display: inline-block;
  width: 100%;
  text-align: center;
}

.pay-big {
  width: 80%;
  height: auto;
  margin: 10px auto;
  background-color: white;
  /*border: black 1px solid;*/
  padding: 0 20px;
  display: block;
}

.title {
  height: 70px;
  line-height: 70px;
  border-bottom: lightgray 1px dashed;
  color: #0086f6;
  font-size: 22px;
  font-weight: bolder;
}

.pay-detail {
  height: auto;
  display: inline-block;
}

.pay-detail span {
  width: 80px;
  height: 50px;
  line-height: 50px;
  font-size: 18px;
  font-weight: bolder;
}

.pay-detail-hotel {
  font-size: 16px;
  font-weight: normal;
  padding-bottom: 20px;
  border-bottom: lightgray 1px dashed;
}

.pay-detail-hotel span {
  margin-right: 20px;
}

.pay-way {
  padding: 20px 0;
  height: auto;
  font-weight: bolder;
  font-size: 18px;

}

.pay-way span {
  margin: 0 20px;
  display: inline-block;
  width: 150px;
  height: 30px;
  line-height: 30px;
  font-weight: normal;
  /*border: #13ce66 1px solid;*/
  position: relative;
  cursor: pointer;
}

.pay-way img {
  width: 24px;
  height: 24px;
  position: absolute;
  bottom: 2px;
  /*border: blue 1px solid;*/
  /*display: block;*/
}
</style>
