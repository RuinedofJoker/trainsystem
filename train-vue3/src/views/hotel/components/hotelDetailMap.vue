<template>
  <div class="home">

    <div id="container" class="allmap">

    </div>
  </div>
</template>

<script setup>
import {ref} from "vue"
import {BMPGL} from "../../../bdmap";

const ak = ref('XEBDRroG9bAGjS1Tomqz0pVg74MXZAfu');
const lon = ref(121.48054)
const lat = ref(31.237905)

function initMap() {
  BMPGL(ak.value).then((BMapGL) => {
    //创建地图实例
    var map = new BMapGL.Map("container");
    ////  初始化地图，设置中心点坐标和地图级别
    map.centerAndZoom(new BMapGL.Point(116.403414,39.924091), 15);
    map.enableScrollWheelZoom(true)//开启地图鼠标滚轮缩放
    //起点和终点坐标
    var from = new BMapGL.Point(112.968035,28.192746);//长沙橘子洲
    var to = new BMapGL.Point(116.403619,39.919721);//故宫博物院
    //绘制驾驶路线
    var driving = new BMapGL.DrivingRoute(map, {renderOptions: {map: map, autoViewport: true}});
    driving.search(from, to);

    //  定位当前位置
    // var geolocation = new BMapGL.Geolocation();
    // geolocation.getCurrentPosition(function(r){
    //   if(this.getStatus() == BMAP_STATUS_SUCCESS){
    //     var mk = new BMapGL.Marker(r.point);
    //     map.addOverlay(mk);
    //     map.panTo(r.point);
    //     alert('您的位置：' + r.point.lng + ',' + r.point.lat);
    //   }
    //   else {
    //     alert('failed' + this.getStatus());
    //   }
    // });
  })
}


initMap()
</script>

<style scoped>
.home {
  width: 100%;
  height: 100%;
}

#container {
  width: 100%;
  height: 360px;
}
</style>
