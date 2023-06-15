<template>
  <div class="h_big">
    <div class="d_big">
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


      <div class="search"  @click="jump()">
        <span><el-icon size="25"><Search /></el-icon>搜索</span>
      </div>


  </div>
</template>

<script setup>
import {ref} from "vue";
import {useRouter} from 'vue-router'

const router = useRouter()

function jump(){
  router.push({
    path: '/index/hotel_place'
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
.h_big {
  /*display: block;*/
}

.d_big {
  width: 70%;
  margin: 0 auto;
  padding: 10px;
  background-color: white;
  /*border-radius: 15px;*/
  display: inline-block;
  border: #D6ECFF 1px solid;
}

.smallText {
  font-size: 13px;
  color: gray;
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
  width: 180px;
  height: 90px;
  line-height: 90px;
  text-align: center;
  /*border-radius: 15px;*/
  margin-top: 2px;
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
