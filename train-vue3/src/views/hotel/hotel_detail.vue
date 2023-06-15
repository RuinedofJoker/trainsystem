<template>
  <div id="big-box">
    <!--搜索栏-->
    <div class="search">
      <!--      <search/>-->
    </div>
    <!--酒店图片/评分-->
    <div class="brief">
      <div class="hotel-brief">
        <!--酒店名字-->
        <span class="hotel-name">{{ hotelData.hotelName || 'aaa'}}</span>
        <!--        {{hotelId}}-->
        <p>
          <el-icon>
            <Location/>
          </el-icon>
          <!-- 酒店位置-->
          {{ hotelData.address || 'aaa'}}
        </p>
        <!--        简介弹窗-->
        <p>
          <el-icon>
            <Document/>
          </el-icon>
          <a @click="dialogVisible=true">点击查看简介</a>

          <el-dialog
              v-model="dialogVisible"
              title="酒店简介"
              width="30%"
              :before-close="handleClose"
          >
            <span>
              黄山悦榕庄位于中国东部的安徽省内，从这一风景如画的度假胜地出发，便可到达世界文化遗产古村落——宏村和西递。
黄山悦榕庄环绕在美不胜收的风光之中，汲取了黄山浪漫迷人的气息和精华，借鉴徽派建筑、中国水墨画、书法学院，独具现代的优雅韵味。
温馨别致的套房和别墅，配上大地色系的内饰令人倍感温馨；玉色的点缀和时尚的家具让人眼前一亮；木雕和竹雕刻画着古老的故事，工艺品呈现着黄山摄人心魄的壮丽。
            </span>
            <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="dialogVisible = false">
          确认
        </el-button>
      </span>
            </template>
          </el-dialog>

        </p>

        <div style="float: right;position: absolute;right: 20px;bottom: 5px;">
          <a href="#checkroom">
            <el-button type="primary" size="large" color="#287DFA"><span
                style="color: white;font-size: 16px;font-weight: bolder;">选择房间</span></el-button>
          </a>
        </div>
      </div>
      <!--      酒店图片-->
      <div class="hotel-img">
        <brief/>
      </div>
      <!--选择房间和地图-->
      <div class="choice">
        <choice/>
      </div>
    </div>
    <!--房间/评价/政策 导航-->
    <div class="room-nav">
      <span><a href="#checkroom">房间</a></span>
      <span><a href="#checkcomment">点评</a></span>
      <span><a href="#checkfacility">政策</a></span>
    </div>
    <!--房间-->
    <div id="checkroom">
      <div class="room">
        <!--      <a name="room"></a>-->
        <room/>
      </div>
    </div>

    <!--评分-->
    <div class="score">
      <avg_score @getCommentData="getCommentData"/>
    </div>
    <!--评价-->
    <div id="checkcomment">
      <div class="remark">
        <remark :comment="commentData"/>
      </div>
    </div>
    <!--政策-->
    <div id="checkfacility">
      <div class="facility">
        <facility/>
      </div>
    </div>
    <!--地图-->
    <div class="map">
    </div>

  </div>
</template>

<script setup>
import Brief from "./hotelDetail/hotel_img";
import Choice from "./hotelDetail/map";
import Book_hotel from "./components/book_hotel";
import Search from "./hotelDetail/search";
import Room from "./hotelDetail/room";
import Remark from "./hotelDetail/remark";
import Hotel_map from "./hotelPlace/hotel_map";
import Avg_score from "./hotelDetail/avg_score";
import Facility from "./hotelDetail/facility";
import {ref, onMounted} from 'vue'
import {useRoute, useRouter } from 'vue-router'
import {getHotelDetail} from "../../api/hotel/hotelDetail";

//接收路由数据
const route = useRoute();
const { hotelId, hotelName } = route.params
const dialogVisible = ref(false)


//请求接口
const router = useRouter();
const hotelData = ref({
  hotelName: ' ',
  address: ' '
})

function getHotelData(hotelId) {
  getHotelDetail(hotelId).then((res) => {
    hotelData.value = res.data;
  })
}

//接收用户评论组件传来的data
const commentData = ref({comment:null})

function getCommentData(msg) {
  commentData.value.comment = msg
}
onMounted(() => {
  console.log('router:',route.params)
  getHotelData(hotelId)
})

getHotelData(hotelId)

</script>

<style scoped>
#big-box {
  margin: 10px auto;
  width: 1200px;
  height:auto;
  display: block;
  background: white;
}

.search {
  width: 100%;
  /*height: 110px;*/
  background-color: whitesmoke;
}

.brief {
  padding-top: 15px;
  /*background: #8CD0FA;*/
  height: 500px;
  width: 100%;
  padding-left: 10px;
  position: relative;
}

.room-nav {
  /*border: #b4bccc 2px solid;*/
  height: 100px;
  line-height: 100px;
  font-size: 20px;
  padding: 10px 0;
  width: 100%;
  display: inline-block;
}

.room-nav span {
  padding: 0 10px;
  /*border: #1890ff 1px solid;*/
  width: 80px;
  height: 85px;
}

.room-nav span:hover {
  border-bottom: #1890ff 3px solid;
}

.room {
  width: 100%;
  height: auto;
  background: floralwhite;
}

.score {
  width: 100%;
  height: auto;
  /*border: #1890ff 2px solid;*/
}

.remark {
  width: 100%;
  height: auto;
  background: #909399;

}

.facility {
  width: 100%;
  height: auto;
  padding: 15px;
  /*background: papayawhip;*/
}

.hotel-brief {
  background: white;
  width: 100%;
  height: 100px;
  font-size: 15px;
  color: gray;
  position: relative;
}

.hotel-img {
  width: 60%;
}

span {
  display: inline-block;
}

.hotel-name {
  font-weight: bolder;
  font-size: 18px;
  color: black;
}

a:hover {
  color: #1890ff;
}

.choice {
  width: 45%;
  /*background: wheat;*/
  /*padding: 20px;*/
  float: right;
  height: 380px;
  position: absolute;
  top: 120px;
  right: 0px;
}

.map {
  width: 400px;
}
</style>
