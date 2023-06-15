<template>
<div>
  <!--  头部导航-->
  <div id="head">
    <div id="left">当季<span style="color: #ff8800;">热推</span></div>
    <div>
      <span class="smallText">出发地</span>
    </div>
    <div class="search">
      <el-select filterable="true" placeholder="上海" style="width: 70%;">
        <el-option v-for="item in provinceList" :key="item.provinceId">{{item.provinceName}}</el-option>

      </el-select>
    </div>
  </div>
<!--  旅游团和特价机票-->
  <div class="season-bottom-box">
<!--    旅游团-->
    <div class="tour">
      <tourism/>
    </div>
<!--    特价机票-->
    <div class="fare">
      <special_fares/>
    </div>
  </div>

</div>
</template>

<script setup>
import {getProvince} from "../../../api/hotel/province";
import Tourism from "./season/tourism.vue";
import Special_fares from "./season/special_fares.vue";
import {ref} from "vue";
let a=ref('666')
let provinceList=ref([])
function getProvinceList(){
  getProvince().then((res)=>{
    provinceList.value=res.data
    console.log(provinceList.value);
  })
}
getProvinceList()
</script>

<style scoped>
#head {
  padding-top: 10px;
  width: 100%;
  height: 50px;
  /*background: #f8ac59;*/
  position: relative;
  display: inline-block;
}

#left {
  width: 100px;
  height: 30px;
  line-height: 30px;
  padding-left: 10px;
  margin-right: 10px;
  float: left;
  font-size: 20px;
  background-color: white;
}
.smallText{
  /*background-color: #13ce66;*/
  height: 30px;
  line-height: 30px;
  font-size: 13px;
  color: gray;
  margin-right: 10px;
  float: left;
}
.search{
  float: left;
}
.season-bottom-box{
  display: inline-block;
}
.tour{
  float: left;
  /*border: #7a6df0 1px solid;*/
  width: 49%;
}
.fare{
  float: right;
  /*border: black 1px solid;*/
  width: 49%;
}
</style>
