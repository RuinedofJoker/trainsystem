<template>
  <div class="selection-big">
    <!--星级价格-->
    <div class="level-price">
      <!--左侧标题-->
      <div class="title"><span>星级价格</span></div>
      <!--右侧具体-->
      <div class="detail">
        <span>五星</span><span>四星</span><span>三星</span><span>二星及以下</span>
        <span>150以下</span><span>150-300</span><span>300-500</span><span>500以上</span>
      </div>
    </div>

    <!--高级筛选-->
    <div class="advanced-selection">
      <!--左侧标题-->
      <div class="title"><span>高级筛选</span></div>
      <!--右侧具体-->
      <div class="detail">
        <span>评分</span><span>房型</span><span>品牌</span><span>早餐</span>
        <span>支付方式</span><span>酒店设施</span><span>特色主题</span><span>适用人群</span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "selection"
}
</script>

<style scoped>
.selection-big {
  padding: 0;
  /*border-bottom: hotpink 1px solid;*/
  width: 100%;
  height: 105px;
  box-shadow: 1px 3px 2px lightgray,
  -1px 2px 2px lightgray,
  1px -1px 2px lightgray;
  /*border: lightgray 1px solid;*/
}

.level-price {
  width: 100%;
  display: inline-block;
  border-bottom: lightgray 1px solid;
}

.title {
  float: left;
  background-color: #E3E5E8;
  font-size: 17px;
  font-weight: bold;
  width: 100px;
  height: 50px;
  line-height: 50px;
  text-align: center;
}

.detail {
  margin: 0 20px;
  float: left;
  display: inline-block;
}

.detail span {
  display: inline-block;
  width: 100px;
  height: 50px;
  line-height: 50px;
  text-align: center;
  /*border: #1890ff 1px solid;*/
}

.advanced-selection {
  width: 100%;
  /*margin-top: 0px;*/
  display: inline-block;
  /*border-bottom: lightgray 1px solid;*/
}
</style>
