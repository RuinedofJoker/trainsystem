<template>
  <div class="train-city-query">
    <div class="city-div div-right-border">
      <city-box label="出发城市" placeholder="出发城市" v-model="fromCity"/>
    </div>

    <div class="switch-btn" @click="handleChangeBtn">
      <el-icon><Switch /></el-icon>
    </div>
    
    <div class="city-div">
      <city-box label="到达城市" placeholder="到达城市" v-model="toCity"/>
    </div>
  </div>
</template>

<script setup>
import useTrainStore from '@/store/modules/train.js';
import CityBox from './city-box.vue'

const fromCity = ref('')
const toCity = ref('')
const cityArr = []

const trainStore = useTrainStore()
watch([fromCity,toCity],(newValue) => {
  cityArr[0] = newValue[0]
  cityArr[1] = newValue[1]
  trainStore.saveCityFn(newValue)
  // emit('cityFn',cityArr)
})

// 交换往返城市
function handleChangeBtn() {
  let temp = fromCity.value;
  fromCity.value = toCity.value;
  toCity.value = temp;
}

</script>

<style scoped>
.train-city-query {
  position: relative;
  background-color: #F8F8F8;
  width: 440px;
  height: 80px;
  display: flex;
  justify-content: space-between;
  color: #999;
  border-radius: 15px;
  font-size: 14px;
}
.switch-btn {
  width: 24px;
  height: 24px;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%,-50%);
  border: 1px solid #999;
  background-color: #fff;
  border-radius:12px;
  text-align: center;
  padding-top: 3px;
}

.city-div {
  padding: 14px 24px;
}
.div-right-border {
  border-right: 1px solid #ddd;
}
</style>
