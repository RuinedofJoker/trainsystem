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
    let point=new BMapGL.Point(116.403414,39.924091)
  //  初始化地图，设置中心点坐标和地图级别
    map.centerAndZoom(point,7)
    //创建标注
    let marker = new BMapGL.Marker(point)
    //将标注添加到地图中
    map.addOverlay(marker)
    let opts = {
      width:100,
      height:80,
      color:'#ff8800',
      title:"故宫博物院" , // 信息窗口标题
      message:"这里是故宫博物院"
    }
    let infoWindow = new BMapGL.InfoWindow("地址：北京市东城区景山前街4号\n" +
        "电话：4009501925\n", opts);  // 创建信息窗口对象
    marker.addEventListener("click",function (){
      map.openInfoWindow(infoWindow,point);
    })
    map.enableScrollWheelZoom(true)//开启地图鼠标滚轮缩放
     map.setHeading(0)//地图旋转角度
     map.setTilt(0)//地图倾斜角度
  }).catch((err)=>{
    console.log(err);
  })
}


  initMap()

</script>

<style scoped>
#container {
  width: 400px;
  margin: 0 auto;
  border: lightgray 1px solid;
  height: 400px;
  position: relative;
  z-index: 2;
}
</style>
