<template>
  <div class="page-query">
      <div>
        <el-form :inline="true">
          <el-form-item>
            <el-radio-group v-model="isOneWay" @change="change">
              <el-radio label="单程" />
              <el-radio label="往返" />
            </el-radio-group>
          </el-form-item>
        </el-form>
      </div>

      <div class="page-main">
        <train-city-query @cityFn="saveCityFn" />
        <train-city-date :isHidden="isHidden" @dateFn="saveDateArr" />
        <el-form :inline="true">
          <el-form-item>
            <el-button class="query-btn" type="primary"  icon="Search" size="large" @click="handleQuery">
              搜索
            </el-button>
          </el-form-item>
        </el-form>
      </div>
      
  </div>
</template>

<script setup>
import {useRouter} from 'vue-router'
import TrainCityQuery from '@/components/TrainCityQuery/index.vue'
import TrainCityDate from '@/components/TrainCityDate/index.vue'
import { getTrainTripList } from '@/api/train/train'
import useTrainStore from '@/store/modules/train.js';
import { storeToRefs } from 'pinia'

let isHidden = ref(true)
const isOneWay = ref('单程')

const router = useRouter()
const from = ref({
  'fromCity': '',
  'toCity': '',
  'fromDate': '',
  'toDate': '',
  'pageNo': 0,
  'pageSize': 15,
  'trainType': [
    'G',
    'T',
    'K'
  ],
  'departureTime': '06:00-24:00'
})

// 改变单程或往返的值
function change() {
  isHidden.value = !isHidden.value
}

// 保存从子组件传来的值
const store = useTrainStore()
const { fromDate, toDate, fromCity, toCity } = storeToRefs(store)
from.value.fromDate = fromDate
from.value.toDate = toDate
from.value.fromCity = fromCity
from.value.toCity = toCity

// function saveDateArr(arr) {
//   from.value.fromDate = arr[0].value;
//   from.value.toDate = arr[1].value;
// }
// function saveCityFn(arr) {
//   from.value.fromCity = arr[0];
//   from.value.toCity = arr[1];
// }

// 搜索按钮
function handleQuery() {
  console.log(from.value);
  router.push({
    path:'/index/train/trips',
    query:from.value
  })
}

</script>

<style scoped>
.page-query {
  position: absolute;
  top: 100px;
  left: 50%;
  transform: translateX(-600px);
  width: 1200px;
  /* margin: 0 auto; */
  padding: 30px 30px;
  background-color: #fff;
  border-radius:10px;
  box-shadow: 0 4px 16px rgba(69,88,115,.2);
}
.query-btn {
  width: 120px;
  height: 80px;
  border-radius: 12px;
  font-size: 18px;
  font-weight: 700;
}
.page-main {
  display: flex;
  justify-content: space-between;
}
</style>
