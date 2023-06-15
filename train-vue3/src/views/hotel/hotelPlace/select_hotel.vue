<template>
  <div class="hotel-big">
    <!--  酒店卡片-->
    <div class="hotel-detail" v-for="item in arr" :key="item.hotelId">
      <!--    酒店封面图-->
      <div class="cover"><img :src="item.hotelPicture"></div>
      <!--    酒店名字/位置-->
      <div class="hotel-name">
        <span class="hotel-name-title">{{ cityName }}{{ item.hotelName }}</span>
        <p style="color: #1890ff">
          {{ item.summary }}
          <el-icon>
            <StarFilled/>
          </el-icon>
          <el-icon>
            <StarFilled/>
          </el-icon>
          <el-icon>
            <StarFilled/>
          </el-icon>
        </p>
        <span class="hotel-name-place"><el-icon><Location/></el-icon>{{ item.address }}</span>
      </div>
      <!--    酒店价格/评分-->
      <div class="hotel-price">
        <div class="hotel-price-content">
          <span class="remark">超多好评<el-button type="primary" color="#4978CE" size="small">{{
              item.appraiseScore
            }}.0</el-button>
          </span>
          <span class="price">￥{{ item.price }}起</span>


          <span class="button">
           <el-button type="primary" color="#287DFA" size="large"
                      @click="jump(item.hotelId,item.hotelName,item.userId)">查看详情</el-button></span>
        </div>
      </div>
    </div>

    <el-pagination background layout="prev, pager, next" :total="40" @current-change="handpage"/>

  </div>
</template>

<script setup>
import {getRecommend} from "../../../api/hotel/getRecommend";
import {ref, reactive} from "vue";
import {computed} from 'vue'
import {useRouter} from 'vue-router'
import {useRoute} from 'vue-router'
import {getAllHotel} from "../../../api/hotel/allHotel";

//接收查询过来的
const route = useRoute()
let cityId = route.query.cityId
let cityName = route.query.cityName

//跳转酒店详情页
const router = useRouter()
function jump(hotelId,hotelName,userId) {
  router.push({
    path: `/index/hotel_detail/${hotelId}/${hotelName}/${userId}`,
    params: {
      hotelId: hotelId,
      hotelName: hotelName,
      userId:userId,
    }
  })
}


//获取推荐酒店
let total = ref(0)
let hotelList = ref([])
function getHotelList() {
  getRecommend().then((res) => {
    hotelList.value = res.data.data;
  })
}

getHotelList()

//分页按钮
let arr = ref([])
let page = ref(1)//当前页

//请求需要的参数
const parms = reactive({
  cursor: page,
  size: 10,
  testTypeId: 0,
  subjectId: 0,
  clazzLevel: 0,
  classType: 0,
  className: "",
})

let no = ref(0)

//发送请求
function listarr(no) {
  getAllHotel(no).then((res) => {
    arr.value = res.data.data;
    total.value = res.data.data.length;
  })
}

//分页点击
function handpage(e) {
//  改变分页
  if (e > 0) {
    e = e - 1;
  }
  page.value = e;

//  再次获取数据
  listarr(page.value)

}


//调用方法
handpage(0)
</script>

<style scoped>
.hotel-big {
  width: 100%;
  height: auto;
  padding-top: 10px;
  /*background-color: peachpuff;*/
}

.hotel-detail {
  background-color: white;
  width: 100%;
  height: 200px;
  display: inline-block;
}

.hotel-detail:hover {
  box-shadow: 1px 3px 2px lightgray;
}

.cover {
  width: 33.33%;
  height: 200px;
  padding: 10px 10px 20px 10px;
  float: left;
}

.cover img {
  width: 100%;
  height: 100%;
}

.hotel-name {
  width: 33.33%;
  height: 200px;
  float: left;
  /*display: inline-block;*/
}

.hotel-name span {
  padding: 10px 0;
  display: inline-block;
}

.hotel-name-title {
  font-weight: bold;
  font-size: 16px;
}

p {
  margin: 0 auto;
}

.hotel-name-place {
  color: #909399;
}

.hotel-price {
  width: 33.33%;
  height: 200px;
  float: left;
  /*border: #1890ff 1px solid;*/
  padding-right: 10px;
}

.hotel-price-content {
  /*width: 100%;*/
  float: right;
  padding-bottom: 10px;
}

.hotel-price-content span {
  display: block;
  width: 100%;
  height: 60px;
  /*border: #1890ff 1px solid;*/
}

.remark {
  padding-top: 10px;
  font-size: 13px;
  color: darkgray;
}

.price {
  color: #007FEf;
  font-weight: bold;
  font-size: 18px;
  line-height: 60px;
  text-align: right;
}

.button {
  padding-top: 20px;
  /*border: hotpink 1px solid;*/
  text-align: right;
  font-size: 18px;
  font-weight: bolder;
}
</style>
