<template>
  <div class="city">
    <div class="cityName">
      <table cellpadding="10" width="100%">
        <!--                    动态获取后台城市名数据-->
        <tr v-for="(item,index) in cityList" :key="index">
          <td v-for="x in cityList[index]" @click="setCityName(x.cityName)">{{x.cityName}}</td>
        </tr>
      </table>
    </div>
  </div>
</template>

<script setup>
import {getHotCity} from "@/api/HotCity/hotCity";
import { onMounted, computed } from "vue";
import useTrainStore from "@/store/modules/train";
import { storeToRefs } from "pinia";
const cityList=ref([])
const store = useTrainStore()
const { cities } = storeToRefs(store)

const emit = defineEmits(['getFromCityName','getToCityName'])

const list = computed(() => {
  return cities.length === 0 ? cityList : cities
})
console.log("list:",list);
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
  })
}


//子组件传值给父组件
function setCityName(e) {
  emit('getFromCityName', e)
  emit('getToCityName', e)
}

onMounted(() => {
  getHotCityList()
})

</script>

<style scoped>

.city {
  background-color: white;
  top: 80px;
  right: 10px;
  width: 450px;
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
