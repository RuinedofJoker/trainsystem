<template>
  <div class="city">
    <div class="cityName">
      <table cellpadding="10" width="100%">
        <!--                    动态获取后台城市名数据-->
        <tr v-for="(item,index) in cityList" :key="index">
          <td v-for="x in cityList[index]" @click="jump(x.cityId,x.cityName)">{{x.cityName}}</td>
        </tr>

      </table>
    </div>
  </div>
</template>

<script setup>
import {getHotCity} from "../../../../api/HotCity/hotCity";
import {useRouter} from 'vue-router'

let cityList=ref([])
let cityName=ref('')
function getHotCityList(){
  getHotCity().then((res)=>{
    let rowNum = 0
    let cNum = 0
    for (let i=0;i<res.data.length;i++) {
      if (cNum == 0){
        cityList.value[rowNum] = new Array()
      }
      cityList.value[rowNum][cNum] = res.data[i]
      if (cNum == 4){
        cNum = 0
        rowNum++
        continue
      }
      cNum++
    }
    // console.log(cityList);
    // console.log(res.data)
  })
}

const router=useRouter()
//点击城市名路由 跳转 城市酒店推荐页（城市id）
function jump(cityId,cityName){
  console.log("城市酒店")
  console.log(cityId,cityName);
  // console.log(cityName)
  router.push({
    path:`/index/hotel_place/${cityName}`,
    query:{ //query查询式跳转 地址‘/index/hotel_place/运城?cityId=101100801&cityName=运城’
      cityId:cityId,
      cityName:cityName,
    }
    //params 拼接方式 地址变成‘/index/hotel_place/运城’
  })
}

getHotCityList()
</script>

<style scoped>

.city {
  background-color: white;
  top: 35px;
  right: 10px;
  width: 500px;
  height: 255px;
  padding: 32px;
  float: right;
  position: absolute;
  box-shadow: 7px 5px 5px #EDEDED,
  7px 5px 5px #EDEDED;
  overflow: auto;
  border: #E6F3FE 1px solid;
  border-radius: 5px;
  display: block;
}

td {
  text-align: center;
  height: 20px;
  padding: 5px;
}

td:hover {
  background-color: #E6F3FE;
  cursor: pointer;
}
</style>
