<template>
  <div class="home">
    <!--地图容器-->
    <div id="container" class="allmap"></div>
  </div>
</template>

<script setup>
import {ref, onMounted} from 'vue'
import {BMPGL} from "../../../bdmap";
const ak=ref('XEBDRroG9bAGjS1Tomqz0pVg74MXZAfu');
function initMap() {
  BMPGL(ak.value).then((BMapGL) => {
    //地图实例
    let map =new BMapGL.Map("container");
  //  创建点坐标
    let point=new BMapGL.Point(121.475588,31.222992)
  //  初始化地图，设置中心点坐标和地图级别
    map.centerAndZoom(point,14)

    map.enableScrollWheelZoom(true)//开启地图鼠标滚轮缩放

  }).catch((err)=>{
    console.log(err);
  })
}

  initMap()

</script>

<style scoped>
#container {
  width: 100%;
  margin: 0 auto;
  border: lightgray 1px solid;
  height: 360px;
  position: relative;
  z-index: 2;
}
</style>
