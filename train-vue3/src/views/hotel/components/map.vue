<template>
  <div className="home">
    <h2>111</h2>
    <!--地图容器-->
<!--    <div id="container" className="allmap"></div>-->
  </div>
</template>

<script setup>
import {ref, onMounted} from 'vue'
import {BMPGL} from "../../../bdmap";

const ak = ref('XEBDRroG9bAGjS1Tomqz0pVg74MXZAfu');

function initMap() {
  BMPGL(ak.value).then((BMapGL) => {
    //地图实例
    let map = new BMapGL.Map("container");
    //  创建点坐标
    let point = new BMapGL.Point(121.47893,31.236926)
    //  初始化地图，设置中心点坐标和地图级别
    map.centerAndZoom(point, 14)
    map.enableScrollWheelZoom(true)//开启地图鼠标滚轮缩放
    map.setHeading(64.5)//地图旋转角度
    map.setTilt(73)//地图倾斜角度
  }).catch((err) => {
    console.log(err);
  })
}

onMounted()
{
  initMap()
}
</script>

<style scoped>
#container {
  width: 400px;
  margin: 0 auto;
  border: red 1px solid;
  height: 400px;
  position: relative;
  z-index: 2;
}
</style>
