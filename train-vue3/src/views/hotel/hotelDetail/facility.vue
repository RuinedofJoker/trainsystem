<template>
<div class="big-facility">
  <h2>酒店设施</h2>
  <div v-html="facilityList.hotelPolicies">

  </div>
</div>
</template>

<script setup>
import {getFacility} from "../../../api/hotel/facility";
import {useRouter} from 'vue-router'
import {ref} from 'vue'
const router=useRouter()
//获取推荐酒店
let facilityList=ref([])
function getFacilityList(){
  getFacility().then((res)=>{
    facilityList.value=res.data;
    // console.log('设施信息');
    // console.log(facilityList.value);
  })
}
getFacilityList()
</script>

<style scoped>
.big-facility{
  width: 100%;
  height: auto;
  /*background-color: pink;*/
}
</style>
