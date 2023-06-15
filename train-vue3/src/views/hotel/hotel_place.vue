<template>
<div class="search-big">
<!--搜索栏-->
  <div class="search">
<!--    <search/>-->
  </div>
<!--筛选栏-->
  <div class="selection">
<!--    <selection/>-->
  </div>
<!--查询到的酒店以及地图 外层-->
  <div class="outer">
<!--左边酒店-->
    <div class="left">
      <select_hotel/>
    </div>
<!--右边地图-->
    <!--固定不动-->
    <div class="map">
        <hotel_map/>
    </div>
  </div>
</div>
</template>

<script setup>
import Search from "./hotelDetail/search";
import Selection from "./hotelPlace/selection";
import Select_hotel from "./hotelPlace/select_hotel";
import Map from "./components/map";
import Hotel_map from "./hotelPlace/hotel_map";
import {getRecommend} from "../../api/hotel/getRecommend";
import {ref} from "vue";
let hotelList=ref([])
function getHotelList(){
  getRecommend().then((res)=>{
    hotelList.value=res.data.data;
    // console.log(hotelList.value);
  })
}
getHotelList()
</script>

<style scoped>

.search-big{
  margin: 10px auto;
  width: 1200px;
  height:auto;
  /*border: #13ce66 2px solid;*/
  background: #F5F7FA;
  position: relative;
}
.search{
  /*position: fixed;*/
}

.selection{
background: white;
  width: 100%;
  height: auto;
}
.outer{
  width: 100%;
  position: relative;
  display: inline-block;
}
.left{
  float: left;
  width: 65%;
  height: auto;
}
.map{
  padding-top: 10px;
  width: 400px;
  height: 400px;
  float: right;
}
</style>
