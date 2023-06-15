<template>
  <div id="big-room">
    <div class="room" v-for="item in roomArr" :key="item.roomTypeId">
      <!--房间图片+简要-->
      <div class="room-name">
        <div class="room-img"><img src="../imgs/roomImages/r1.jpg" alt="房间内景图"></div>
        <div class="room-title">
          <p>{{ item.roomType }}</p>
          <span>1张1.8米大床 | 28m² | 无窗 | 禁烟</span>

        </div>
      </div>
      <!--房间细节+价格-->
      <div class="room-detail">
        <div>
          <span style="text-align: left;font-size: 20px; color: #455873;"><el-icon><UserFilled/></el-icon><el-icon><UserFilled/></el-icon></span>
          <span>无早餐</span>
          <span @click="jump(item.roomType,item.typePrice)">30分钟内免费取消</span>
          <span style="color: #0086F6;
                font-size: 22px;
                font-weight: bolder;
                text-decoration: none;">￥{{ item.typePrice }}</span>

          <span style="border-right: none;">
            <el-button type="warning" size="large"><p style="font-size: 20px;font-weight: bold; display: block;" @click="jump(item.roomType,item.typePrice)">
            预订</p></el-button>
          </span>
        </div>
      </div>
    </div>


  </div>
</template>

<script setup>
import {ref} from 'vue'
import {getRoom} from "../../../api/hotel/roomType";
import {useRouter} from 'vue-router'
import {useRoute} from 'vue-router'
//接收路由传值
const route = useRoute()
const cityName = route.query.cityName
const { hotelId, hotelName } = route.params

//根据cityId获取酒店房型
const roomArr = ref([])

function getRoomArr() {
  getRoom(hotelId).then((res) => {
    roomArr.value = res.data;
  })
}

//路由传值

const router = useRouter()

function jump(roomType, roomPrice) {
  router.push({
    path: `/index/book`,
    query: {
      roomPrice: roomPrice,
      roomType: roomType,
      hotelName
    }
  })
}

getRoomArr()
</script>

<style scoped>
#big-room {
  /*border: #1890ff 2px solid;*/
}

.room {
  /*border-top: lightgray 2px solid;*/
  background: white;
  display: inline-block;
  width: 100%;
  margin: 0 auto;
}

.room-name {
  border-right: lightgray 1px solid;
  float: left;
  width: 20%;
  padding: 20px;
}

.room-name p {
  margin: 10px auto;
  font-size: 16px;
  font-weight: bolder;
}

.room-name span {
  font-size: 14px;
  color: gray;
}

.room-img {
  width: 140px;
  height: 90px;
}

.room-img img {
  width: 100%;
  height: 100%;
}

.room-detail {
  /*border: blue solid 1px;*/
  border-bottom: lightgray 1px solid;
  float: left;
  width: 80%;
  height: 211px;
  display: inline-block;
}

.room-detail span {
  display: inline-block;
  width: 20%;
  height: 200px;
  line-height: 200px;
  text-align: center;
  /*border: #1890ff 1px solid;*/
  /*border-right: darkgray 1px dashed;*/
  /*border-bottom: darkgray 1px dashed;*/
  font-size: 16px;
  text-decoration: underline;
  color: #06AECC;
}


</style>
