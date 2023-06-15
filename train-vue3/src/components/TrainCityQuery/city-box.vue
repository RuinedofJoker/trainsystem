<template>
  <div class="city" v-clickOutside>
    <span>{{ label }}</span>
    <input type="text" @input="handleInput" v-model="inputText" :placeholder="placeholder">
    <div class="cityArea" v-if="isShow">
      <table cellpadding="10" width="100%">
        <!--                    动态获取后台城市名数据-->
        <tr v-for="(item,index) in cities" :key="index">
          <td v-for="x in item" @click="setCityName(x.cityName)">{{x.cityName}}</td>
        </tr>
      </table>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import useTrainStore from "@/store/modules/train";
import { storeToRefs } from "pinia";
import useDebounce from '@/hooks/useDebounce'

const store = useTrainStore()
const { cities } = storeToRefs(store)

const isShow = ref(false)
const inputText = ref('')

const props = defineProps({
  label: String,
  placeholder: {
    type: String,
    default: '请输入'
  },
  modelValue: String
})
const emits = defineEmits(['update:modelValue'])
const VClickOutside = {
  beforeMount(el) {
    let handler = (e) => {
      // 控制点击显示的
      if (el.contains(e.target) && e.target.className.indexOf('item-li') == -1) {
        isShow.value = !isShow.value
      } else {
        isShow.value = false
      }
    }
    document.addEventListener('click', handler)
  }
}

const handleInput = (e) => {
  inputText.value = e.target.value
  emits('update:modelValue', inputText.value)
  useDebounce(store.getCity(e.target.value), 1000)
}

const setCityName = (cityName) => {
  emits('update:modelValue', cityName)
  inputText.value = cityName
}

onMounted(() => {
  store.getOriginCities()
})
</script>

<style>
input {
  width: 170px;
  height: 30px;
  border: 0px;
  background-color: #F8F8F8;
  font-size: 18px;
  font-weight: 700;
}
.cityArea {
  position: absolute;
  z-index: 9999;
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