<template>
  <div class="hotel">
    <div class="hotelContent" v-for="item in hotelList" :key="item.hotelId" @click="jump(item.hotelId,item.hotelName,item.userId)">
      <div class="imgs"><img :src="item.hotelPicture"></div>
      <p class="hotelName">{{cityName}}{{item.hotelName}}</p>
      <p class="remark"><span class="ex">{{item.appraiseScore}}.0</span><span>/5</span></p>
      <span class="good">{{item.summary}}</span>
      <p class="rp"><span class="comments">1018人点评</span><span class="price">￥<span>{{item.price}}</span></span></p>
    </div>
  </div>

  </template>

<script setup>

import {useRouter,useRoute} from 'vue-router'
import {getRecommend} from "../../../../api/hotel/getRecommend";

import {ref} from 'vue'
const hotelId=ref('')


//点击卡片跳转酒店详情页
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
const hotelList=ref([])
function getHotelList(){
  getRecommend().then((res)=>{
    hotelList.value=res.data.data;
  })
}

//固定几个热点城市的酒店推荐列表


getHotelList()

</script>

<style scoped>
.hotel {
  /*border: #1ab394 2px solid;*/
  width: 740px;
  height: 320px;
  background-color: white;
  /*overflow: hidden;*/
  overflow-x: auto;
  white-space: nowrap;
}

.imgs{
  overflow: hidden;
  width: 230px;
  height: 160px;
}
.imgs img {
  margin: 0;
  width: 230px;
  height: 160px;
  overflow: hidden;
}

.hotel > div {
  float: left;
  margin-top: 20px;
  margin-left: 10px;
  /*border: #00afff 1px solid;*/
  border-radius: 15px;
  overflow: hidden;
}

.hotel p {
  display: block;
  margin: 0;
}

.hotelContent{
  height: 280px;
  position: relative;
  display: inline-block;
}

.hotelName {
  width: 230px;
  height: 28px;
  line-height: 28px;
  font-weight: bolder;
  font-size: 16px;
  padding-left: 5px;
  /*background-color: pink;*/
}

.imgs img:hover {
  transform: scale(1.1);
}

.imgs img{
  transition:all 1.3s;
}

.remark {
  width: 50px;
  height: 20px;
  line-height: 20px;
  font-size: 14px;
  padding-left: 5px;
  background-color: #007FE9;
  color: rgba(255, 255, 255, 0.5);
  border-radius: 5px 5px 0px 5px;
  float: left;
}
.remark .ex {
  font-size: 16px;
  color: white;
}
.good{
  padding-left: 2px;
  padding-bottom: 0px;
  color: #007FE9;
  font-size: 14px;
  float: left;
}
.rp{
  clear: left;
  position: absolute;
  bottom: 10px;
  width: 230px;
  height: 30px;
  line-height: 30px;

}
.comments{
  color: #8B8B8B;
  font-size: 12px;
}
.price{
  color: #007FEf;
  font-weight: bold;
  font-size: 13px;
  float: right;
}
.price span{
  font-size: 18px;
  margin: 0;
  font-weight: bolder;
}

.hotelContent:hover{
  box-shadow:
    7px 5px 5px #EDEDED,
    -7px 5px 5px #EDEDED,
    7px 5px 5px #EDEDED;
  cursor: pointer;
}
.hotelContent:hover img{
  transform: scale(1.1);
}
</style>
