<template>

  <div>
    <destination/>
    <div class="d_big">
      <div class="leftBox">
        <div :class="{'input-box-focus':inputBox}">
          <span class="smallText">目的地/酒店名称</span>
          <p><input type="text" :value="cityName"  @click="showCity" @focus="focusInputBox" @blur="blurInputBox"></p>
          <div class="HotCity" v-if="flag">
            <hot-city></hot-city>
            <!--          <hot-city/>-->
          </div>

        </div>
      </div>

      <div class="rightBox">
        <div class="test">
          <span class="smallText">入住</span>
          <span class="check">退房</span></div>
        <p style="height: 50px;line-height: 54px;"><span>
  <el-date-picker
      style="width: 100%;"
      type="daterange"
      format="YYYY/MM/DD"
      value-format="YYYY-MM-DD"
      v-model="date_value"
      @input="changeTime"
      range-separator="至"
      start-placeholder="开始日期"
      end-placeholder="结束日期">
  </el-date-picker>
    </span></p>

      </div>
    </div>

  </div>

</template>

<script setup>

import {ref} from "vue";
import HotCity from "../components/bookHotel/hotCity";
import Destination from "../components/bookHotel/destination";

const inputBox=ref()
const flag=ref(false)
const date_value=ref('');
const cityName=ref('')
function showCity() {
  flag.value = ! flag.value;
  console.log("输入框被点击")
}
function focusInputBox() {
  inputBox.value = true;
  // flag.value= true;
  console.log('输入框获得焦点,显示热点城市列表');
}
// function blurInputBox() {
//    inputBox.value = false;
//   flag.value = false;
//   console.log('输入框失去焦点,关闭热点城市列表');
// }
function  changeTime(e) {
  this.$forceUpdate()
}

function getCityName(msg){
  cityName.value=msg
  console.log(cityName.value);
}
</script>

<style scoped>
.test {
  /*border: blue 1px solid;*/
}

.d_big {
  width: 100%;
  margin-bottom: 15px;
  /*padding: 10px 10px 0 10px;*/
  background-color: white;
  padding: 0 10px;
  border-radius: 15px;
  display: inline-block;
  border: #D6ECFF 1px solid;
}

.smallText {
  font-size: 13px;
  color: gray;
}

.leftBox {
  /*border: black 1px solid;*/
  float: left;
  width: 50%;
  height: 90px;
  box-sizing: border-box;
  border-right: #DADFE6 1px solid;
  position: relative;
}

.input-box-focus {
  float: left;
  width: 100%;
  height: 100%;
  border-bottom: #0083F1 2px solid;
  position: relative;
}

.HotCity {
  z-index: 3;
  position: absolute;
  left: 499px;
  top: 56px;

}

p {
  display: block;
}

input {
  border: none;
  margin: 0;
  height: 54px;
  line-height: 54px;
  width: 100%;
  font-size: 16px;
  font-weight: bolder;
}

input:focus {
  outline: none;
  /*border-bottom: #0083F1 2px solid;*/
  /*border: #0083F1 2px solid;*/
}

.rightBox {
  float: right;
  padding-left: 10px;
  /*border: black 1px solid;*/
  width: 50%;
  height: 90px;
  box-sizing: border-box;
}

.check {
  font-size: 13px;
  color: gray;
  float: right;
}
</style>
