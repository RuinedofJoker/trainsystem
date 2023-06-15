<template>
  <div class="city">
    <div class="cityName">
      <span>热门城市</span>
      <table cellpadding="5" width="100%">


        <!--                    动态获取后台城市名数据-->
        <tr v-for="(item,index) in cityList" :key="index">
          <td v-for="x in cityList[index]" @click="setCityName(x.cityName)">{{ x.cityName }}</td>
        </tr>

      </table>
    </div>
  </div>
</template>

<script setup>
import {getHotCity} from "../../../../api/HotCity/hotCity";
import {ref} from 'vue'
import {useRouter} from 'vue-router'

let cityList = ref([])
const emit = defineEmits(['getCityName'])

function getHotCityList() {
  getHotCity().then((res) => {
    let rowNum = 0
    let cNum = 0
    for (let i = 0; i < res.data.length; i++) {
      if (cNum == 0) {
        cityList.value[rowNum] = new Array()
      }
      cityList.value[rowNum][cNum] = res.data[i]
      if (cNum == 4) {
        cNum = 0
        rowNum++
        continue
      }
      cNum++
    }
    console.log(cityList.value.cityId);
  })
}

let cityId = ref('')
const router = useRouter()
//子组件传值给父组件
function setCityName(e) {
  // test.value = '{{x.cityName}}'
  // console.log(test.value);
  emit('getCityName', e)
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

  overflow: auto;
  border: #E6F3FE 1px solid;
  border-radius: 5px;
  display: block;
}

span {
  color: #8492a6;
  font-weight: bolder;
  font-size: 14px;
}

td {
  /*border: #1890ff 1px solid;*/
  text-align: left;
  height: 20px;
  padding: 5px;
  color: #333333;
}

td:hover {
  background-color: #E6F3FE;
  cursor: pointer;
}
</style>
