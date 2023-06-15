<template>
  <div class="h_big">
    <div class="d_big">
      <div class="leftBox">
        <span class="smallText">房间数量</span>
        <div @click="" style="height: 50px; margin-top: 15px;">
          <el-input-number style="float: right;width: 98%;height: 40px;"
                           v-model="num" :min="1" :max="10" @change="handleChange"/>
        </div>
      </div>

      <div class="aside"></div>

      <div class="rightBox">
        <span class="smallText">关键词（选填）</span>
        <p><input type="text" placeholder="机场/火车站/酒店名称..."></p>
      </div>
    </div>
    <div class="search">
      <span @click=""><el-icon><Search/></el-icon>搜索</span>
    </div>

  </div>
</template>

<script>
import {ref} from "vue";

const num = ref(1)
const handleChange = (number) => {
  console.log(value)
}

export default {
  name: "housing1",
  data() {
    return {
      num: 1,

    }
  },
  methods: {
    handleChange(value) {
      console.log(value);
    }
  }
}
</script>

<style scoped>
.h_big {
  /*display: block;*/
}

.d_big {
  width: 80%;
  margin: 0 auto;
  /*padding: 10px;*/
  padding: 0 10px 10px 10px;
  background-color: white;
  /*border-radius: 15px;*/
  display: inline-block;
  border: #D6ECFF 1px solid;
  /*border: black 1px solid;*/
  position: relative;
}

.smallText {
  /*border: black 1px solid;*/
  font-size: 13px;
  color: gray;
}

.leftBox {
  float: left;
  width: 50%;
  height: 80px;
  /*border-right: #D6ECFF 1px solid;*/
  /*border: #D6ECFF 1px solid;*/
  overflow: hidden;
  padding-right: 7.5px;
  /*border: #b4bccc 1px solid;*/
}

.rightBox input {
  margin-top: 10px;
  border: none;
  /*margin: 0;*/
  width: 100%;
  height: 30px;
  /*border: black 1px solid;*/
}

::-webkit-input-placeholder {
  color: #DADFE6;
  font-size: 15px;
  font-weight: bolder;
}


.rightBox {
  padding-left: 10px;
  float: right;
  width: 50%;
  height: 80px;
  /*border: red 1px solid;*/
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
  width: 20%;
  height: 92px;
  line-height: 90px;
  text-align: center;
  border: #D6ECFF 1px solid;
  cursor: pointer;
}

.aside {
  width: 1px;
  height: 90px;
  background: #D6ECFF;
  z-index: 2;
  position: absolute;
  bottom: 0;
  left: 240px;

}
</style>
