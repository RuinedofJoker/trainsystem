<template>
  <div class="book-big">
    <div class="content">
      <!--  左边订单详细信息-->
      <div class="book-left">
        <!--    所选酒店名字/房型/人数-->
        <div class="roomInfo">
          <div class="hotel-name">{{ hotelName }}</div>
          <div class="address">
            <el-icon>
              <Star/>
            </el-icon>
            <el-icon>
              <Star/>
            </el-icon>
            <el-icon>
              <Star/>
            </el-icon>
            <el-icon>
              <Star/>
            </el-icon>
            <!--            <el-icon>-->
            <!--              <Location/>-->
            <!--            </el-icon>-->
            <!--            中国，安徽，黟县，宏村镇悦榕路1号-->
          </div>
          <div class="room-type">{{ rType }}</div>
          <div>
            <el-icon>
              <User/>
            </el-icon>
            2人
            <el-icon>
              <House/>
            </el-icon>
            1.8米大床
            <el-icon>
              <ForkSpoon/>
            </el-icon>
            不含早餐
          </div>
        </div>
        <!--    入住客户信息/电话/姓名-->
        <div class="userInfo">
          <!--入住日期和房间数量-->
          <div class="date-room">
            <!--日期-->
            <div>
              <div class="demo-date-picker">
                <div class="block">
                  住房时间
                  <el-date-picker
                      v-model="date_value"
                      type="daterange"
                      range-separator="至"
                      start-placeholder="入住时间"
                      end-placeholder="退房时间"
                      size="large"
                      format="YYYY/MM/DD"
                      value-format="YYYY-MM-DD"

                  />
                </div>
              </div>
            </div>

            <!--房间数-->
            <div class="roomNum">
              房间数量
              <el-input-number v-model="num" :min="1" :max="10" @change="handleChange" size="large"/>
            </div>

          </div>
          <!--住客资料-->
          <div class="userInfo-name">
            <div>
              <span style="font-weight: bolder;font-size: 18px;">住客资料</span>
              <p style="color: #909399">请按实际入住人数填写，姓名与证件保持一致
                <el-icon>
                  <Warning/>
                </el-icon>
              </p>
            </div>

            <div class="user">
              <p>住户姓名</p>
              <input v-model="customer" placeholder="每间只需填一人"/>
              <p>电子邮件（选填）</p>
              <input type="email">
              <p>电话号码</p>
              <input type="text">
            </div>
          </div>
        </div>
        <!--   预计到店-->
        <div class="checkin-time">
          <div class="room-type">预计到店</div>
          <div>
            <p class="small-text">到达时间</p>
            <div>
              <el-time-select
                  v-model="time"
                  start="08:30"
                  step="00:30"
                  end="23:30"
                  placeholder="请选择时间"
                  size="large"
              />
            </div>
            <div>
              <p class="small-text" style="color: #313131;margin: 0;">房间整晚保留</p>
            </div>
          </div>
        </div>

        <div style="height: 30px;line-height: 30px;">预订条款，个人信息授权协议，前往下一步即代表已阅读并同意上述条件</div>

        <!--    付款按钮-->
        <div class="payment">
          <div class="pay-left">到店付款</div>
          <div class="pay-price">￥{{ rPrice }}</div>
          <div class="pay-right">

            <el-button type="warning" color="#FF7700" @click="jumpPay(rPrice,rType,hotelName,checkDate,num,customer)">
              <span style="font-size: 20px;font-weight: bolder; color: white;">去支付</span>
            </el-button>

          </div>
        </div>
      </div>
      <!--  右侧注意事项，固定-->
      <div class="book-right">
        <div class="payment" style="border-bottom: dashed lightgray 1px;">
          <span class="pay-left" style="font-size: 24px;">应付总金额</span>
          <span class="pay-price" style="float: right;font-size: 24px;">￥{{ rPrice }}</span>
        </div>
        <div class="notice">
          <span style="color: #06aebd;font-weight: bolder;">30分钟内免费取消</span>
          <span>订单确认后30分钟内可免费取消。逾期不可取消/修改，若未入住将收取您全额房费（如用优惠券则以券后支付价为准）。
            订单需等酒店或供应商确认后生效，订单确认结果以短信、邮件或app通知为准，如订单不确认将全额退款至您的付款账户。</span>
        </div>
        <div class="notice">
          <span style="color: black;font-weight: bolder;">说明</span>
          <span>预订服务由携程旗下上海赫程国际旅行社有限公司及其分公司提供、住宿服务由酒店提供，交易款项由商家委托携程旗下子公司统一收取。</span>
        </div>
      </div>
    </div>
  </div>

</template>

<script setup>
import {ref} from 'vue'
import {useRoute} from "vue-router"
import {useRouter} from "vue-router"

//接收路由传值
const route = useRoute()
let rType = route.query.roomType
let rPrice = route.query.roomPrice
let hotelName = route.query.hotelName

const time = ref('')
const date_value = ref('')
let checkDate = ref(date_value)
const num = ref(1)
const customer = ref('')
const handleChange = (value) => {
  console.log(value)
}
//获取 酒店价格/房型/酒店名/入住日期/房间数量/住客姓名 传递订单
//路由跳转支付界面
const router = useRouter()
function jumpPay(rPrice, rType, hotelName,checkDate,num,customer) {
  console.log(checkDate)
  router.push({
    path: '/index/pay',
    query: {
      roomPrice: rPrice,
      roomType: rType,
      hotelName: hotelName,
      checkDate:checkDate,
      num:num,
      customer:customer
    }
  })
}


</script>

<style scoped>
.book-big {
  width: 100%;
  height: auto;
  padding: 20px 0;
  background-color: whitesmoke;
  /*border: hotpink 2px solid;*/
  display: inline-block;
}

.content {
  width: 80%;
  height: auto;
  margin: 0 auto;
  position: relative;
}

.book-left {
  width: 65%;
  height: auto;
  /*border: dodgerblue solid 2px;*/
}

.roomInfo {
  width: 100%;
  height: 150px;
  padding: 0 20px;
  background-color: white;
}

.hotel-name {
  padding-top: 10px;
  font-size: 22px;
  font-weight: bolder;
  height: 50px;
  line-height: 40px;
}

.address {
  font-size: 14px;
  height: 30px;
  line-height: 30px;
}

.room-type {
  font-size: 18px;
  font-weight: bolder;
  height: 30px;
  line-height: 30px;
}

.book-right {
  width: 34%;
  background-color: white;
  height: auto;
  float: right;
  /*border: #287DFA solid 2px;*/
  position: absolute;
  top: 0px;
  right: 0px;
}

.userInfo {
  margin: 10px 0;
}

.date-room {
  width: 100%;
  height: 80px;
  padding: 20px 0px 10px 20px;
  border-bottom: lightgray dashed 1px;
  background-color: white;
  display: inline-block;
  color: gray;
}

.demo-date-picker {
  float: left;
}

.roomNum {
  float: right;
}

.userInfo-name {
  background-color: white;
  padding: 0 20px;
}

.small-text {
  color: #595B5D;
  font-size: 13px;
}

.user p {
  color: #595B5D;
  font-size: 13px;
}

input {
  width: 200px;
  height: 40px;
  border: none;
  padding: 0;
  border-bottom: black 2px solid;
  font-size: 18px;
  font-weight: bolder;
  margin-bottom: 10px;
}

input:focus {
  outline: none;
}

.checkin-time {
  background-color: white;
  padding: 0 20px;
  height: 150px;
}

.payment {
  /*margin-top: 10px;*/
  width: 100%;
  height: 130px;
  line-height: 110px;
  padding: 20px;
  background-color: white;
  font-size: 18px;
  font-weight: bolder;
  display: inline-block;
}

.pay-left {
  float: left;
}

.pay-price {
  float: left;
  color: #287DFA;
  font-size: 22px;
}

.pay-right {
  float: right;
}

.notice {
  padding: 15px 20px;
  width: 100%;
  height: auto;
  border-bottom: lightgray 1px dashed;
}

.notice span {
  color: #909399;
  font-size: 16px;
  display: inline-block;
  margin-bottom: 5px;
}

</style>
