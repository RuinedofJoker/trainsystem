<template>
  <!-- 景点/项目/游记页面 -->
  <div class="page">
    <div class="at" >
      <h1>{{cityName}}</h1>
      <span>{{englishName}}</span>
      <div class="at-tab">
        <a ><img src="../../assets/icons/myicon/景点icon.png" style="height: 45px;"></a><p style="margin-right: 20px;">景点</p>
        <a style="margin-right: 20px;"><img src="../../assets/icons/myicon/住宿icon.png" ><p>住宿</p></a>
        <a style="margin-right: 20px;"><img src="../../assets/icons/myicon/美食icon.png" ><p>美食</p></a>
        <a style="margin-right: 20px;"><img src="../../assets/icons/myicon/购物icon.png" ><p>购物</p></a>
        <a style="margin-right: 20px;"><img src="../../assets/icons/myicon/游记icon.png" ><p>游记</p></a>
      </div>
      <div class="at-pc"><img :src="cityImg"></div>
    </div>
    <div class="at-pro">
      <el-tabs>
        <el-tab-pane label="必玩">
          <div class="play" >
            <!-- 动态调转；所以需要动态绑定href ；然后拼接地址-->
            <a :href="'/attractions/detail/' + attractions.attractionsId" target="_blank" v-for="attractions in intraduceAttractionsList" :key="attractions.attractionsId">
              <div class="play1">
                <div class="play-img"><img :src="`${serviceIp}/picture/${attractions.fileId}`"></div>
                <div class="play-word">
                  <h3>{{ attractions.attractionsName }}</h3>
                  <p><strong>{{ attractions.appraiseScore }}分</strong> {{ attractions.comments }}条点评</p>
                  <p>{{ attractions.summary }}</p>
                </div>
              </div>
            </a>
          </div>
        </el-tab-pane>
        <el-tab-pane label="必吃">
          <div class="play">
            <a href="/attractions/detail/景点id" target="_blank">
              <div class="play1">
                <div class="play-img"><img src="../../assets/images/myImage/迪士尼.jpg"></div>
                <div class="play-word">
                  <h3>上海迪士尼度假区</h3>
                  <p><strong>4.6分</strong> 22.1w条点评</p>
                  <p>充满欢乐童真的梦幻乐园</p>
                </div>
              </div>
            </a>
          </div>
        </el-tab-pane>
        <el-tab-pane label="必逛">
          <div class="play">
            <a href="/attractions/detail/景点id" target="_blank">
              <div class="play1">
                <div class="play-img"><img src="../../assets/images/myImage/迪士尼.jpg"></div>
                <div class="play-word">
                  <h3>上海迪士尼度假区</h3>
                  <p><strong>4.6分</strong> 22.1w条点评</p>
                  <p>充满欢乐童真的梦幻乐园</p>
                </div>
              </div>
            </a>
          </div>
        </el-tab-pane>
      </el-tabs>
      <a href="" style="float: right;">  查看更多<el-icon><CaretRight /></el-icon></a>
    </div>
    <div class="at-yj">
            <div class="yj-tab">
                <h3><span>{{ cityName }}</span>游记</h3>
                <span class="yj-tab-word">什么是优质游记？申请必看</span>
                <el-button type="primary" plain class="toutaio">头条</el-button>
            </div>
            <div v-for="essay in essayItems" :key="essay.essayId" @click="jumpYouji(essay.essayId)">
                <div class="yj1">
                    <img :src="`${serviceIp}/picture/${essay.coverPictureId}`" class="yj-img">
                    <h2>{{ essay.essayTitle }}</h2>
                    <div class="user">
                        <viewUser :userId="essay.userId" style="display:inline-block;"/>
                    </div>               
                    <span>{{ essay.summary }}</span>
                    <div class="icon">
                        <el-icon color="gray"><View /></el-icon><p style="display: inline;color: gray;margin-right: 10px;">{{ essay.lookNum }}</p>
                    </div>
                </div>
                <el-divider></el-divider>
            </div>
        </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import {Picture,ChatDotRound,View} from '@element-plus/icons-vue'
import {useRoute, useRouter,} from 'vue-router'
// 2.导入接口
import {getIntraduceAttraction,getEssay} from '@/api/city/index.js'
import viewUser from '@/components/viewUser/index.vue' 

const serviceIp = import.meta.env.VITE_APP_BASE_API
let cityImg = ref("")
const route = useRoute();
const router = useRouter()
const intraduceAttractionsList = ref([])  //5.必须要有attractionId，然后传递给子组件Detail；attractionsName、attractionsId、fileId
const essayItems = ref([]);
const essayList = ref([]);


//3.获取到传过来的englishName
let englishName = route.query.englishName;
let cityName = route.query.cityName

function jumpYouji(essayId){
    router.push({
        path:`/attractionsYouji/${essayId}`,
        params:{
            essayId :essayId
        }
    })
}

//4.调用接口；通过传过来的englishName获取城市的推荐景点
function getIntraduceAttractionList(){
  getIntraduceAttraction(englishName).then((res)=>{
    cityImg.value = `${serviceIp}/picture/${res.data.cityPictureFileId}`
    // console.log(res.data);
    for(let i =0;i<res.data.attractionsRecommendDetail.length;i++){
      // if(res.data.attractionsRecommendDetail[i].fileId === null || res.data.attractionsRecommendDetail[i].fileId===""){
      //   res.data.attractionsRecommendDetail[i].fileId = 
      // }
      intraduceAttractionsList.value.push(res.data.attractionsRecommendDetail[i]);
    }
  })
  console.log(intraduceAttractionsList);
}
getIntraduceAttractionList()

//调用接口，接收游记
function getEssayList(){
  getEssay(5).then((res)=>{
    for(let i=0,j=0;i<res.data.length;i++,j++){
      if(j == 3) {
        j = 0;
      }
      if(j == 0) {
        essayList.value.push(new Array())
      }
      essayList.value[essayList.value.length - 1].push(res.data[i])
      essayItems.value.push(res.data[i])
    }
    console.log(essayItems.value);
  })
}
getEssayList();
</script>

<style scoped>
/* 整体样式设计 */
.page{
    width: 1200px;
    height:3000px;
    margin-left: 30px;
    margin-top: 40px;
    /* background-color: aqua; */
}

.at{
  width: 1200px;
  height: 600px;
  /* background-color: aqua; */
}
.at-pro{
  width: 1200px;
  height: 950px;

  /* background-color: yellowgreen; */
}
.at-yj{
  width: 1200px;
}


/* 景点设计 */
.at h1{
  font-size: 35px;
  display: inline;
  margin-right: 10px;
}
.at span{
  font-size: 35px;
  color: gray;
}
.at-tab{
  width: 1200;
  height: 50px;
  margin-top: 10px;
  /* background-color: aqua; */
}

.play-img img{
  width: 100%;
  height: 100%;
}
.at-tab p{
  display: inline;

}
.at-pc{
  width:1200px;
  height: 400px;
  margin-top: 30px;
}
.at-pc img{
  border-radius: 15px;
  height: 100%;
  width: 100%;
}

/* 景点项目介绍样式 */
.play{
  width: 1200px;
  height: 820px;
  
  /* background-color: blue; */
}
.play1{
  width: 380px;
  height: 400px;
  margin-bottom: 20px;
  margin-right: 20px;
  /* background-color: pink; */
  float: left;
  border-radius: 15px;
  box-shadow:0.09px 0.09px 5px rgb(222, 219, 219);
  overflow: hidden;
}
.play-img{
  width: 380px;
  height: 280px;
}
.play-word strong{
  color: rgb(114, 114, 184);
}
.play-word p{
  color: rgb(102, 100, 100);
}


/* 游记部分样式 */
.yj1{
    width: 1200px;
    height: 200px;
    clear: both;
}
/* 游记部分样式 */
.yj1{
    width: 1200px;
    height: 200px;
    clear: both;
}
/* 游记tab部分 */
.yj-tab h3{
    display: inline;
}
.yj-tab-word{
    color: gray;
    font-size: 5px;
    margin-left: 5px;
}
.yj-tab{
  height: 30px;
    margin-bottom: 20px
}
.toutaio{
    float: right;
}

.yj-img{
    width: 300px;
    height: 200px;
    border-radius: 15px;
    float: left;
    margin-right: 8px;
}
.yj-usericon{
    border-radius: 50px;
    width: 30px;
    height: 30px;
}
.user {
    height: 50px;
    /* background-color: aquamarine; */
}
.user p{
    display: inline;
    position: relative;
    font-size: 9px;
    top: -10px;
    right: -5px;
}
.icon{
    margin-top: 75px;
}
.yj1 span{
    color: gray;
    font-size: 10px;
    position: relative;
    top: -10px;
}
</style>