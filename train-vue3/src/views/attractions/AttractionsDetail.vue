<template>
    <div class="main">
      <div class="bread">
      <el-breadcrumb :separator-icon="ArrowRight">
        <el-breadcrumb-item :to="{ path: '/' }">旅游攻略社区</el-breadcrumb-item>
        <el-breadcrumb-item> 目的地</el-breadcrumb-item>
        <el-breadcrumb-item> {{attractionDetail.attractionsName}}</el-breadcrumb-item>
      </el-breadcrumb>
      </div>
      <div class="page">
        <div class="at">
          <div class="swiperTh1">
            <!-- 轮播图 -->
            <div class="swiper-big">
              <swiper
                      :loop="true"
                      :thumbs="{ swiper: thumbsSwiper }"
                      :modules="modules"
                      >
                <!-- 轮播图绑定fileIdList进行轮播 -->
                <swiper-slide v-for="fileId in attractionDetail.fileIdList" :key="fileId">
                  <img :src="`${serviceIp}/picture/${fileId}`"  class="imgsw" >
                </swiper-slide>
              </swiper>
            </div>
            <div class="swiper-little">
              <swiper  @swiper="setThumbsSwiper"  
                        slides-per-view="4"                
                        watchSlidesProgress="false"
                        spaceBetween="3" 
                        :modules="modules"
                        class="mySwiper"
                        :direction="'vertical'"
                      >
                <swiper-slide v-for="fileId in attractionDetail.fileIdList" :key="fileId" class="swslide">
                    <img :src="`${serviceIp}/picture/${fileId}`" class="imgsw"/>
                </swiper-slide> 
              </swiper>
             </div>
          </div>
          <div class="at-word">
            <h1>{{attractionDetail.attractionsName}}</h1>
            <p><strong style="color: blue;">{{attractionDetail.appraiseScore}}</strong>/5分<a href="" style="margin-left: 20px; color: blue;">2000条点评</a></p>
            <div class="at-tags">
              <el-icon><Orange /></el-icon>
              <span >{{ attractionDetail.tags }}</span>
            </div>
            <p><strong>地址</strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{ attractionDetail.address }}</p>
            <p><strong>官方电话</strong>&nbsp;&nbsp;&nbsp;&nbsp;{{attractionDetail.phoneNumber}}</p>
            <p><strong>公告信息</strong>&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;">健康安全须知|入园购票须知|五一抢购</span></p>
          </div>
        </div>
        <div class="other">
          <div class="word">
            <div style="width:1200px;" v-html="attractionDetail.detail" >
            </div>
          </div>
          <!-- 评论区模块 -->
          <div class="comment">
            <div class="comment-tab">
              <h2 ><strong>用户点评(20000)</strong></h2>
              <p><strong style="color: rgb(36, 161, 224);font-size: 30px;">4.7</strong>/5分</p>
              <el-button  @click="handle()"><el-icon><EditPen /></el-icon>写点评</el-button>
            </div>      
            <el-divider></el-divider>
            <div class="comment-main">
              <div class="commentOne" v-for="commentArea in commentList" :key="commentArea.commentId">
                <viewUser :userId="commentArea.userId" />
                <div class="commentWord">
                  <p v-html=" commentArea.content"></p>
                    <el-divider></el-divider>
                </div>              
              </div>
            </div>
          </div>
        </div> 
        </div>
    </div>
</template>

<script setup>
import { EditPen } from '@element-plus/icons-vue'
import { Swiper, SwiperSlide } from 'swiper/vue' // 引入swiper样式（按需导入）
import 'swiper/css';

import { FreeMode, Navigation, Thumbs } from 'swiper'
import { ref } from 'vue'
//引入跳转API
import {useRouter,useRoute} from 'vue-router'
//2.引入接口名称
import {getAttractionsDetailById,getComment,getUser} from '@/api/city/index.js'
// import UserBox from '@/components/UserBox/index.vue' 
import viewUser from '@/components/viewUser/index.vue' 
 
const input = ref('');
const router = useRouter();
const route = useRoute();
const serviceIp = import.meta.env.VITE_APP_BASE_API

//接口内容
const attractionDetail = ref({fileIdList: []})  //attractionsName、price、address
const attractionsEassy = ref([])
const englishName = ref("")

const commentList =ref([])  //里面有评论的userId，

// 轮播图
const thumbsSwiper = ref(null);
const modules = [Thumbs];
const setThumbsSwiper = (swiper) => {
    thumbsSwiper.value = swiper;
};

//获取组件传过来的attractionsId
// console.log(route.params.attractionsId);

function handle(){
  router.push({
    path:`/atrrationsComment`,
    query:{
    attractionsId: attractionDetail.value.attractionsId,
    attractionsName: attractionDetail.value.attractionsName,
    address:attractionDetail.value.address,
    fileId :attractionDetail.value.fileIdList[0]
    }
  })
}

// 4.定义使用接口函数;获取景点详细信息
function getAttractionsDetailList(){
  getAttractionsDetailById(route.params.attractionsId).then((res)=>{
      attractionDetail.value = res.data.attractionsDetail;
      attractionsEassy.value.push(res.data.essay)
      englishName.value = res.data.englishName
  })
console.log(attractionDetail);
}
getAttractionsDetailList()

function getCommentList(){
  getComment(1,route.params.attractionsId,0,5).then((res)=>{
    for(let i = 0; i < res.data.data.length; i++) {
      commentList.value.push(res.data.data[i])
    }
  })
}
getCommentList()


</script>

<style>
/* 设计整体格式 */
.main{
  width: 1240px;
  height: auto;
  display: inline-block;
  margin: 20px 100px;
  background-color: whitesmoke;
}
.bread{
  width: 1200px;
  height: 30px;
  margin-left: 20px;
  margin-top:20px;
}
.page{
  width:1200px;
  height: auto;
  margin-left: 20px;
}
.at{
  width: 1200px;
  height: auto;
  margin-bottom: 30px;
}
.other{
  width: 1200px;
  height: auto;
  float: left;
}
.word{
  width: 1200px;
  background: white;
  height: auto;
  float: left;
  margin-bottom: 20px;
}
.comment{
  background: white;
  width: 1200px;
  height: auto;
  float: left;
}
/* 景点轮播图信息样式 */
.swiperTh1{
  width: 700px;
  height: 400px;
  margin: 10px;
  position: relative;
  float: left;
}
.swiper-big{
  width: 520px;
  height: 400px;
  float: left;
  margin-right: 10px;
  margin-top: 10px;

}
.swiper-little{
  width: 150px;
  height: 400px;
  position: relative;
  float: left;
}
.mySwiper {
    width:100%;
    height: 100%;
    margin-top: 10px;
}
.swslide{
  height: 400px;
  width: 140px;
}
.imgsw{
  height: 100%;
  width: 100%;
}
/* 轮播时变化透明度的变化 */
.mySwiper .swiper-slide-thumb-active {
    border: pink 2px solid;
}

/* 景点评分介绍 */
.at-word{
  float: left;
  width:480px;
  height: 400px;
  margin-top: 20px;
}
.at-tags{
  background-color: rgb(245, 224, 200);
  color: #673114;
  width: 100%;
  height: 25px;
  text-align: center;
  border: rgb(255, 162, 0) 1px dashed;
}
/* 评论区tab */

.comment-tab {
  height: 120px;
}
.comment-tab button{
  width: 100px;
  height: 30px;
  position: relative;
  right: -950px;
  top: -80px;
  background-color:white;
  border: gray 1px solid;
}
.comment-main{
  width: 1200px;
}
.commentOne{
  width: 1200px;
  display: flex;
}
.commentWord{
  width: 1090px;
  margin-left: 10px;
  margin-right: 10px;
 }

.commentWord {
  width: 1090px;
  display: flex;
  flex-wrap: wrap;
}
.commentWord img{
  width: 150px;
  height: 150px;
  margin-right: 10px;
  margin-bottom: 10px;
} 
.commentUser img{
  width: 50px;
  height: 50px;
}
</style>