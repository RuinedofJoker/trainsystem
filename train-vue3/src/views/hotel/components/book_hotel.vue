<template>
  <div class="big">
    <div class="top"><span id="topLeft">预订酒店</span> <span id="topRight">安心订<el-icon><HomeFilled/></el-icon>放心住</span></div>
    <div class="destination">
    <destination/>
    </div>
    <div class="room">
<!--      <housing/>-->
    </div>
  </div>
</template>

<script>
import Destination from "./bookHotel/destination";
import Housing from "./bookHotel/housing";
export default {
  name: "book_hotel",
  components: {Housing, Destination},
  data(){
    return{

    }
  }
}
</script>

<style scoped>
.big{
  width: 740px;
  height: 280px;
  background: linear-gradient(to right, #76BDFA, #FDFEFF);
  border: #D6ECFF 1px solid;
  border-radius: 15px;
}
.top{
  height: 50px;
  /*border: #1890ff 1px solid;*/
}
.top span{
  display: inline-block;
  margin:0px 20px;
  height: 50px;
  line-height: 50px;
  /*border: black 1px solid;*/
}
#topLeft{
  font-size: 22px;
  font-weight: bold;
  color: white;
}
#topRight{
  float: right;
  font-size: 20px;
  font-weight: bolder;
  font-family: 幼圆;
  color: dodgerblue;
}
.destination{
  margin: 0 auto;
  /*border: black 1px solid;*/
  width: 100%;
  height: 100px;
  padding: 0px 20px;
}
.room{
  margin: 0 auto;
  width: 100%;
  height: 100px;
  /*padding: 10px 10px;*/
  /*border: black 1px solid;*/
}
</style>
