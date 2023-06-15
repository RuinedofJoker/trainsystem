<template>
  <div>
    <!--  头部导航-->
    <div id="head">
      <div id="left">酒店<span style="color: #ff8800;">推荐</span></div>
      <div class="right">
        <span v-for="(item,index) in cityList" :key="index" @click="activeCity(index)">{{item}}</span>
      </div>
      <span @click.prevent="show" class="more">更多</span>
    </div>


    <div class="ht">
      <!--酒店卡片子组件-->
      <div v-show="activeNum===0">
        <hotel_card/>
      </div>
      <div v-show="activeNum===1">
        <shang-hai/>
      </div>
      <div v-show="activeNum===2">
        <guang-zhou/>
      </div>
      <div v-show="activeNum===3">
        <shen-zhen/>
      </div>

      <!--下拉城市名-->
      <div v-show="isShow" class="cityTable">
        <city/>
      </div>

    </div>

  </div>
</template>

<script setup>

import Hotel_card from "./recommend/hotel_card";
import City from "./recommend/city";
import {ref} from "vue";
import {useRouter} from 'vue-router';
import {getHotCity} from "../../../api/HotCity/hotCity";
import ShangHai from "./recommend/hotRecommend/shangHai";
import GuangZhou from "./recommend/hotRecommend/guangZhou";
import ShenZhen from "./recommend/hotRecommend/shenZhen";

let activeNum = ref(0);
    let isShow = ref(false);
    let cityList = ref(['北京','上海','广州', '深圳'])
//点击城市显示卡片
    function activeCity(i)
      {
        activeNum.value = i
        console.log('点击城市的index:',activeNum.value);
      }
      //更多面板的显示
    function show(event)
      {
        isShow.value = !isShow.value;
      }

    // const router = useRouter()
    // function jump(cityId,cityName){
    //   router.push({
    //     path: '/index/hotel/hotel_card',
    //     query:{
    //       cityId: cityId,
    //       cityName:cityName
    //     }
    //   })
    // }


</script>

<style scoped>
#head {
  position: relative;
}

#left {
  width: 370px;
  height: 30px;
  line-height: 30px;
  margin: 0px;
  padding-left: 10px;
  float: left;
  font-size: 20px;

}

.right {

  background-color: white;
  width: 370px;
  height: 30px;
  line-height: 30px;
  float: right;
  display: inline;
  position: relative;
  top: 0px;
  margin: 0px;
  right:60px ;
  text-align: right;
  z-index: 2;
}

.right span {
  margin: 0 auto;
  display: inline-block;
  width: 60px;
  height: 30px;
  line-height: 30px;
  text-align: center;
  /*border: #34bfa3 1px solid;*/
  z-index: 3;

}

.right span:hover {
  cursor: pointer;
  background-color: #E6F3FE;
  border-radius: 5px;
}


/*.active {*/
/*  background-color: #E6F3FE;*/
/*  border-radius: 5px;*/
/*}*/

.ht {
  position: relative;
}
.more{
  float: right;
  top: 0px;
  right: 0px;
  width: 60px;
  height: 30px;
  line-height: 30px;
  display: block;
  text-align: center;
  position: absolute;
  z-index: 5;
}
.more:hover{
  cursor: pointer;
}
.cityTable{
  z-index: 6;
}
</style>
