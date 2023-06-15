<template>
  <div class="tourism">
    <!--  动态读取旅游团购-->
    <!--  <div v-for="">-->
    <!--    <div></div>-->
    <!--  </div>-->
    <div class="tourism-top">
      <div><img src="../../imgs/tourism/group.png" alt="图片" class="group"></div>
      <div class="left-head"><span>当季热卖·跟团游</span></div>
    </div>

    <!--  旅游推荐-->
    <div class="tourism-bottom">
      <!--    <div v-for="">-->
      <!--&lt;!&ndash;      多个旅游团推荐&ndash;&gt;-->
      <!--      <div></div>-->
      <!--    </div>-->
     <router-link to="/undeveloped"> <div class="tourism-box">
        <div class="l-img"><img src="../../imgs/right/h2.jpg" alt="图片"></div>
        <div class="title">杭州+乌镇+西塘3日两晚跟团游
          <div>
            <span class="score">4.9分</span><span class="person">|</span><span class="person">5342人出游</span>
          </div>
        </div>
        <div class="price"><span class="rmb">￥</span>692起</div>
      </div></router-link>
      <router-link to="/undeveloped"> <div class="tourism-box">
        <div class="l-img"><img src="../../imgs/right/h2.jpg" alt="图片"></div>
        <div class="title">杭州+乌镇+西塘3日两晚跟团游
          <div>
            <span class="score">4.9分</span><span class="person">|</span><span class="person">5342人出游</span>
          </div>
        </div>
        <div class="price"><span class="rmb">￥</span>692起</div>
      </div></router-link>
      <router-link to="/undeveloped"> <div class="tourism-box">
        <div class="l-img"><img src="../../imgs/right/h2.jpg" alt="图片"></div>
        <div class="title">杭州+乌镇+西塘3日两晚跟团游
          <div>
            <span class="score">4.9分</span><span class="person">|</span><span class="person">5342人出游</span>
          </div>
        </div>
        <div class="price"><span class="rmb">￥</span>692起</div>
      </div></router-link>
      <router-link to="/undeveloped"> <div class="tourism-box">
        <div class="l-img"><img src="../../imgs/right/h2.jpg" alt="图片"></div>
        <div class="title">杭州+乌镇+西塘3日两晚跟团游
          <div>
            <span class="score">4.9分</span><span class="person">|</span><span class="person">5342人出游</span>
          </div>
        </div>
        <div class="price"><span class="rmb">￥</span>692起</div>
      </div></router-link>
      <router-link to="/undeveloped"> <div class="tourism-box">
        <div class="l-img"><img src="../../imgs/right/h2.jpg" alt="图片"></div>
        <div class="title">杭州+乌镇+西塘3日两晚跟团游
          <div>
            <span class="score">4.9分</span><span class="person">|</span><span class="person">5342人出游</span>
          </div>
        </div>
        <div class="price"><span class="rmb">￥</span>692起</div>
      </div></router-link>
    </div>
  </div>
</template>

<script>
export default {
  name: "tourism"
}
</script>

<style scoped>
.tourism {
  background: white;
  width: 100%;
  height: 510px;
  border-bottom: #E3F2FB 3px solid;
  border-left: #E3F2FB 3px solid;
  border-right: #E3F2FB 3px solid;
  border-radius: 10px;
  /*display: inline-block;*/
}

.tourism-top {
  border-top: #8CD0FA 2px solid;
  background: url("../../imgs/tourism/groupBorder.png");
  background: rgba(92,144,186,0.1);
  display: inline-block;
  width: 101%;
  height: 50px;
  border-radius: 5px;
  margin-bottom: 8px;
}

.group {
  width: 80px;
  height: 50px;
  float: left;
  padding-bottom: 2px;
}

.left-head {
  /*border: #7a6df0 1px solid;*/
  height: 50px;
  line-height: 50px;
  font-size: 16px;
  font-weight: bolder;
  float: left;
}

.tourism-bottom {
  /*background: palegoldenrod;*/
  width: 100%;
  margin: 0 auto;
  height: 300px;
  padding:0px 10px;
}

.tourism-box {
  /*margin-top: 10px;*/
  width: 345px;
  margin: 2px auto;
  background-color: white;
  display: inline-block;
  border-radius: 5px;
}

.tourism-box:hover {
  box-shadow: 3px 5px 3px #EDEDED;
  /*7px 5px 5px #EDEDED;*/
}

.l-img {
  border-radius: 5px;
  width: 80px;
  height: 80px;
  float: left;
}

.l-img img {
  width: 79px;
  height: 79px;
  /*padding-bottom: 1px;*/
  border-radius: 5px;
}

.title {
  float: left;
  background: white;
  /*border: black 1px solid;*/
  width: 200px;
  height: 80px;
  padding: 10px;
  font-size: 14px;
  font-weight: bolder;
  display: block;
}

.score {
  color: #007FEf;
  font-size: 12px;
  font-weight: normal;
  padding-right: 5px;
}

.person {
  color: gray;
  font-size: 13px;
  font-weight: normal;
  padding-right: 5px;
}

.price {
  margin-top: 10px;
  /*border: black 1px solid;*/
  float: left;
  color: #007FEf;
  font-weight: bold;
}

.rmb {
  font-size: 13px;
}
</style>
