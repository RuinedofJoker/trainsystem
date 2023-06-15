<template>
  <div>
    <div>
      <span v-for="(item,index) in arrList" :key="index" @click="show(index)">{{ item }}</span>
    </div>
    <!--  show-->
    <div class="son">
      <div v-show="test===0">
        No.1
        <guang-zhou/>
      </div>
      <div v-show="test===1">
        No.2
        <shang-hai/>
      </div>
      <div v-show="test===2">
        No.3
        <shen-zhen/>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref} from 'vue'

import Hotel_card from "./components/recommend/hotel_card";
import ShangHai from "./components/recommend/hotRecommend/shangHai";
import ShenZhen from "./components/recommend/hotRecommend/shenZhen";
import GuangZhou from "./components/recommend/hotRecommend/guangZhou";

let test = ref()
const arrList = ref(['11', '22', '33'])
function show(i) {
  test.value = i;
  console.log("index:",test.value)
}

</script>

<style scoped>

span {
  text-align: center;
  display: inline-block;
  width: 80px;
  height: 40px;
  line-height: 40px;
  border: #1890ff 1px solid;
}

span:hover {
  cursor: pointer;
  background-color: #8CD0FA;
}

.son {
  background-color: #8CD0FA;
  margin: 10px;
  width: 400px;
  height: auto;
}
.son-test{
  z-index: 2;
}
</style>
