<template>
  <div class="train-city-date">
    <div class="dep-date">
      <span>出发日期</span>
      <el-form>
        <el-form-item>
            <el-date-picker
              v-model="depDate"
              style="width: 245px"
              value-format="YYYY-MM-DD"
              type="date"
            />
          </el-form-item>
      </el-form>
    </div>
    <div class="return-date" :class="{ hidden: isHidden }">
      <span>返回日期</span>
      <el-form>
        <el-form-item>
            <el-date-picker
              v-model="returnDate"
              style="width: 245px"
              value-format="YYYY-MM-DD"
              type="date"
            />
          </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import useTimeNow from '@/hooks/useTimeNow.js';
import { ref } from 'vue';
import useTrainStore from '@/store/modules/train.js'

const props = defineProps({
  // 根据父组件传来的单程或往返，决定是否显示返回日期
  isHidden: {
    type: Boolean,
    default: true
  }
})

// const depDate = ref(useTimeNow().nowTime)
// const returnDate = ref(useTimeNow().nextTime)
const depDate = ref("2023-04-05")
const returnDate = ref("")
if (!props.isHidden) {
  returnDate.value = "2023-04-05"
} else {
  returnDate.value = ""
}

const dateArr = [depDate,returnDate]
// const emit = defineEmits(['dateFn'])
// emit('dateFn',dateArr)

const trainStore = useTrainStore()
trainStore.saveDateArr(dateArr)

</script>

<style scoped>
.train-city-date {
  display: flex;
  justify-content: space-between;
  width: 550px;
  height: 80px;
  background-color: #F8F8F8;
  color: #999;
  border-radius: 15px;
  font-size: 14px;
  padding: 14px 24px;
}
.hidden {
  display: none;
}

</style>
