<template>
  <div id="left">
<!--    <div class="map"><hd_map/></div>-->
    <div class="map"><test/></div>
    <div class="bottom"><span>Spa&nbsp;|&nbsp;接机服务&nbsp;|&nbsp;24小时前台&nbsp;|&nbsp;会议厅&nbsp;|&nbsp;棋牌室</span></div>
  </div>
</template>

<script>
import Hotel_map from "./hd_map";
import Hd_map from "./hd_map";
import Test from "../components/hotelDetailMap";
export default {
  name: "choice",
  components: {Test, Hd_map, Hotel_map}
}
</script>

<style scoped>
#left{
  height: 360px;
  background: white;
  /*width: 25%;*/
  /*border: black 1px solid;*/
}

.map{
  width: 100%;
  height: auto;
  line-height:303px;
  font-size: 20px;
  padding-right: 20px;
  /*border: #1890ff 1px solid;*/
}
.bottom{
  width: 100%;
  /*height: 9%;*/
  /*padding-top: 30px;*/
  line-height: 35px;
  font-size: 18px;
  /*border: #1890ff 1px solid;*/
}
</style>
