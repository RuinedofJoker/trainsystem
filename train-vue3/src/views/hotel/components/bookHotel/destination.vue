<template>

  <div class="big-box">
    <div class="d_big">
      <div class="d-leftBox">
        <div :class="{'input-box-focus':inputBox}">
          <span class="smallText">目的地/酒店名称</span>
          <p><input type="text" :value="cityName" @click="showCity" @focus="focusInputBox" @blur="blurInputBox"></p>
          <div class="HotCity" v-if="flag">
            <hot-city @getCityName="getCityName"></hot-city>
          </div>

        </div>
      </div>

      <div class="d-rightBox">
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

      <!--    订房搜索子组件-->
    </div>

    <div class="h_big">
      <div class="hh_big">
        <div class="leftBox">
          <span class="smallText">房间数量</span>
          <div @click="" style="height: 50px; margin-top: 9px;">
            <el-input-number style="float: right;width: 98%;height: 41px;"
                             v-model="num" :min="1" :max="10" @change="handleChange"/>
          </div>
        </div>

        <div class="middle">
          <span class="smallText">酒店级别</span>
          <div style="margin-top: 10px;">
            <el-select v-model="value" class="sel" placeholder="请选择" size="large">
              <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </div>
        </div>

        <div class="rightBox">
          <span class="smallText">关键词（选填）</span>
          <p><input type="text" placeholder="机场/火车站/酒店名称..."></p>
        </div>
      </div>


      <div class="search" @click="jump(cityName)">
        <span><el-icon size="25"><Search/></el-icon>搜索</span>
      </div>


    </div>
    <!--    测试-->
    <!--    <housing/>-->
  </div>

</template>

<script setup>

import {ref} from "vue";
import {useRouter} from 'vue-router';
import HotCity from "./hotCity";
import Housing from "./housing";
import {param} from "../../../../utils";

const inputBox = ref()
const flag = ref(false)
const date_value = ref('');
const cityName = ref('北京')

function showCity() {
  flag.value = !flag.value;
  console.log("输入框被点击")
}

function focusInputBox() {
  // inputBox.value = true;
  // flag.value= true;
  console.log('输入框获得焦点,显示热点城市列表');
}

// function blurInputBox() {
//    inputBox.value = false;
//   flag.value = false;
//   console.log('输入框失去焦点,关闭热点城市列表');
// }
function changeTime(e) {
  this.$forceUpdate()
}

//获取hotCity组件传上来的cityName值
function getCityName(msg) {
  cityName.value = msg
  console.log(cityName.value);
} 

//点击城市名路由 跳转 城市酒店推荐页（城市id）
const router = useRouter()
function jump(cityName) {
  router.push({
    path: `/index/hotel_place/${cityName}`,
    query: { //query查询式跳转 地址‘/index/hotel_place/运城?cityId=101100801&cityName=运城’
      cityName: cityName,
    }
    //params 拼接方式 地址变成‘/index/hotel_place/运城’
  })
}


const num = ref(1)
const handleChange = (number) => {
  console.log(value)
}
const value = ref('')
const options = ref([
  {
    value: '不限',
  },
  {
    value: '五星',
  },
  {
    value: '四星',
  },
  {
    value: '三星',
  },
  {
    value: '二星及以下',
  },])


</script>

<style scoped>
.big-box {
  height: 220px;
}

.d_big {
  width: 100%;
  margin-bottom: 15px;
  /*padding: 10px 10px 0 10px;*/
  background-color: white;
  padding: 0 10px;
  height: 91px;
  /*border-radius: 15px;*/
  display: inline-block;
  border: #D6ECFF 1px solid;
}

.smallText {
  font-size: 13px;
  color: gray;
}

.d-leftBox {
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

.d-rightBox {
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


/*测试*/
.h_big {
  margin-top: 5px;
  width: 100%;
  height: 92px;
  display: inline-block;
}

.hh_big {
  width: 80%;
  margin: 0 auto;
  padding: 10px;
  background-color: white;
  /*border-radius: 15px;*/
  display: inline-block;
  border: #D6ECFF 1px solid;
}

.leftBox {
  float: left;
  width: 33.33%;
  height: 70px;
  border-right: #DADFE6 1px solid;
  overflow: hidden;
  padding-right: 7.5px;
  /*border: #b4bccc 1px solid;*/
}

.rightBox input {
  border: none;
  margin: 0;
  width: 100%;
  height: 30px;
}

::-webkit-input-placeholder {
  color: #DADFE6;
  font-size: 15px;
  font-weight: bolder;
}

.middle {
  float: left;
  width: 33.33%;
  height: 70px;
  /*border: black 1px solid;*/
  padding-left: 7.5px;
  padding-right: 7.5px;
  border-right: #DADFE6 1px solid;
  overflow: hidden;
}


.rightBox {
  padding-left: 10px;
  float: right;
  width: 33.33%;
  height: 70px;
  /*border: black 1px solid;*/
}


input:focus {
  outline: none;
}

select:focus {
  outline: none;
}

.search {
  float: right;
  background: linear-gradient(to right, #00A6FA, #0076F5);
  color: white;
  font-size: 22px;
  width: 130px;
  height: 90px;
  line-height: 90px;
  text-align: center;
  /*border-radius: 15px;*/
  margin-top: 1px;
  cursor: pointer;
}

.sel {
  width: 100%;
  height: 50px;
  --el-input-hover-border: 0px;
  box-shadow: 0 0 0px;
  --el-input-border-color: white;
}
</style>
