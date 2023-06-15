<template>
    <div class="page">
        <div class="spot">
            <div class="aside">
                <!-- 绑定一个方法，悬停显示组件 -->
                <div @mouseenter="onEnter"  @mouseleave="onLeave"  class="spotBtn"> 
                    <p>国内（含港澳台）<el-icon class="el-icon--right"><ArrowRight /></el-icon></p>
                </div>
                <el-divider />
            </div>
                <div class="hover_wrapper" @mouseenter="onEnter" @mouseleave="onLeave" v-show="showHover==true" >
                    <!-- 6.遍历请求过来结果的数组；绑定数据 -->
                    <!-- 父组件动态绑定属性，给子组件传递数据 -->
                    <ProvinceSpot v-for="item in provinceList" :key="item.provinceId" :provinceName="item.provinceName"/>
                </div>
            <div class="main"  v-if="showHover==false">
                <div >
                    <div class="big">
                        <img src="../../assets/images/myImage/风景6.png" class="img1">
                    </div>
                    <div style="width:800px;height: 202px;margin-top:10px;overflow:hidden;">
                        <el-carousel arrow="always" interval="50000">
                            <el-carousel-item v-for="essays in essayList">
                                <div style="width:800px;height: 205px; display: flex;">
                                    <div class="swiper-item"  v-for="essay in essays" :key="essay.essayId" @click="jumpYouji(essay.essayId)">
                                       <img :src="`${serviceIp}/picture/${essay.coverPictureId}`" class="swiper-img">
                                            <div class="word">{{essay.summary}}</div>
                                            <div class="swiperWord">
                                                <viewUser :userId="essay.userId"/>
                                            </div>
                                    </div>
                                </div>
                            </el-carousel-item>
                        </el-carousel>
                    </div>
                </div>
            </div>
        </div>
        <div class="gg">
            <div class="gg-tab">
                <h3>智慧旅游旗舰店</h3>
                <div class="tabs">
                    <!-- 对按钮事件监听，:class 点击之后给div绑定新的class -->                   
                        <el-button @click="change(index)" v-for="(item,index) in dataList" :key="index">
                            {{ item.option }}
                        </el-button>
                    <a href="/" style="margin-left:12px ;"><el-button>更多<el-icon><CaretRight /></el-icon></el-button></a>
                </div>
            </div>
            <div class="gg-content">
                <!-- 根据点击的按钮显示不同的div -->
                <div class="east" v-show="number==0">
                    <div class="the1">
                        <div class="gg-img">
                            <a href=""><img src="../../assets/images/myImage/嘉兴.jpg"  class="gg-pc"></a>
                        </div>
                        <div class="gg-word">
                            <div class="gg-icon"><img src="../../assets/images/myImage/嘉兴icon.png" ></div>                   
                            <h3><Strong>心游嘉兴体验馆</Strong></h3>
                            <span>红船启梦，心游嘉兴：江南水乡、历史文化名城，以“潮、河、湖、海”为特色，正全力打造运河国际旅游休闲城市</span>                           
                        </div>
                    </div>
                    <div class="the1">
                        <div class="gg-img">
                            <a href=""><img src="../../assets/images/myImage/文成旅游.png" class="gg-pc"></a>
                        </div>
                        <div class="gg-word">
                            <div class="gg-icon"><img src="../../assets/images/myImage/文成icon.png" ></div>
                            <h3><Strong>文成旅游旗舰店</Strong></h3>
                            <span>伯温故里，天然文成</span>
                        </div>
                    </div>
                </div>
                <div class="south" v-show="number==1">
                    <div class="the1">
                        <div class="gg-img">
                            <a href=""><img src="../../assets/images/myImage/湛江.png"  class="gg-pc"></a>
                        </div>
                        <div class="gg-word">
                            <div class="gg-icon"><img src="../../assets/images/myImage/湛江icon.png" ></div>
                                <h3><Strong>湛江官方旗舰店</Strong></h3>
                                <span>湛江官方旗舰店</span>
                        </div>
                    </div>
                    <div class="the1">
                        <div class="gg-img">
                            <a href=""><img src="../../assets/images/myImage/桂林.png" class="gg-pc"></a>
                        </div>
                        <div class="gg-word">
                            <div class="gg-icon"><img src="../../assets/images/myImage/桂林icon.png" ></div>
                                <h3><Strong>桂林旅游旗舰店</Strong></h3>
                                <span>桂林，中国首批国家历史文化名城、中国优秀旅游城市，境内的山水风光举世闻名，千百年来享有“桂林山水甲天下”的美誉。</span>
                        </div>
                    </div>
                </div>
                <div class="east_south" v-show="number==2">
                    <div class="the1">
                        <div class="gg-img">
                            <a href=""><img src="../../assets/images/myImage/西藏.png"  class="gg-pc"></a>
                        </div>
                        <div class="gg-word">
                            <div class="gg-icon"><img src="../../assets/images/myImage/西藏icon.png" ></div>">
                            <h3><Strong>西藏旅游旗舰店</Strong></h3>
                            <span>西藏，重要的世界旅游目的地，这里是“人间圣地·天上西藏”</span>
                        </div>
                    </div>
                    <div class="the1">
                        <div class="gg-img">
                            <a href=""><img src="../../assets/images/myImage/四川.png" class="gg-pc"></a>
                        </div>
                        <div class="gg-word">
                            <div class="gg-icon"><img src="../../assets/images/myImage/四川icon.png" ></div>
                            <h3><Strong>四川省旅游旗舰店</Strong></h3>
                            <span>天府三九大，安逸走四川</span>
                        </div>
                    </div>
                    <div class="the1">
                        <div class="gg-img">
                            <a href=""><img src="../../assets/images/myImage/腾冲.png" class="gg-pc"></a>
                        </div>
                        <div class="gg-word">
                            <div class="gg-icon"><img src="../../assets/images/myImage/腾冲icon.png" ></div>
                            <h3><Strong>腾冲文旅官方旗舰店</Strong></h3>
                            <span>世界腾冲，天下和顺</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="yj">
            <div class="yj-tab">
                <h3>推荐游记</h3>
                <span class="yj-tab-word">什么是优质游记？申请必看</span>
                <el-button @click="jumpWrite" style="margin-left: 800px;">发布游记</el-button>
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
</template>7

<script setup>
import { getProvince,getEssay } from '@/api/city/index.js'
//2.导入接口的方法
import ProvinceSpot from '@/components/ProvinceSpot/index.vue'
import { reactive, ref } from 'vue';
import router from '../../router';
import viewUser from '@/components/viewUser/index.vue' 

const serviceIp = import.meta.env.VITE_APP_BASE_API
let showHover = ref(false);
let number = ref(0);
let dataList = reactive([ {option:'华东'},
                 {option:'华南'},
                 {option:'西南'}])

const provinceList = ref([]) //4.定义接收数据的数组，里面有provinceId和priovinceName
//根据provinceName获取所有city
const essayList = ref([]);
const essayItems = ref([]);

//3.封装一个请求接口的方法
function getProvinceList(){
    getProvince().then((res)=>{
        // console.log(res);                             //遍历打印返回的结果
        for(let i = 0; i < res.data.length; i++){        //将返回的结果放入定义的数组
            provinceList.value.push(res.data[i])
        }
    })
}
getProvinceList()  //5.调用方法

function getEssayList(){
    getEssay(10).then((res)=>{
        for(let i = 0,j = 0; i < res.data.length; i++,j++) {
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
getEssayList()

function onEnter(){
    showHover.value=true
}
function onLeave(){
    showHover.value=false
}
function change(index){
    console.log(index);
    number.value = index;//将下标绑定给number，根据number显示对应的div
}
function jumpYouji(essayId){
    router.push({
        path:`/attractionsYouji/${essayId}`,
        params:{
            essayId :essayId
        }
    })
}
function jumpWrite(){
    router.push({
        path:`/attractionsYoujiWrite`
    })
}

</script>

<style >
/* 设置整体大小 */
.page{
    margin-left: 30px;
}
.spot{
    width: 1200px;
    height: 700px;
    margin-top: 20px;
}
.aside{
    width: 390px;
    height: 700px;
    background-color: rgb(239, 243, 246);
    float: left;
}
.hover_wrapper{
   width: 810px;
   height: 700px;
   float: left; 
   display: flex;
   flex-direction: column;
   overflow: scroll;
}
.main{
    width: 800px;
    height: 700px;
    margin-left: 10px;
    float: left;
}
.gg{
    width: 1200px;
    height: 400px;
}
.yj{
    width: 1200px;
    display: flex;
    flex-direction: column;
}


/* 设置地点样式 */
/* 侧边栏 */
.spotBtn{
    height: 50px;
    line-height: 40px;
}
.el-icon--right{
    float:right;
    margin-top: 25px;
}
/* 主要内容 */
.img1{
    width:800px;
    height: 485px; 
    border-radius:15px;

}
/* 三张图片显示在一行 */
.swiper-item {
    width: 258px;
    height: 200px;
    /* float: left; */
    margin-left :8px; 
    border-radius:15px;
    overflow: hidden;
    text-overflow:ellipsis; white-space: nowrap;
}
/*给图片设置大小 */
.swiper-img{
    width: 100%;
    height: 100%;
   
}
/* 鼠标悬停时放大 */
.swiper-item:hover .swiper-img{
    transform:scale(1.1);
}
/* 设置文字悬浮在图片上 */
.word{
    width:240px;
    height: 30px;
   /* border: red 2px solid; */
    position: relative;
    z-index: 1;
    top:-65px;
    right:-10px;
    color: #ffffff;
    font-size: 20px;
    overflow: hidden;
    text-overflow:ellipsis; 
    white-space: nowrap;
}
/* 设置用户头像悬浮在图片上 */
.swiperWord{
    height: 30px;
    position: relative;
    top: -70px;
    right: 10px;
}
.swiperWord img{
    width: 30px;
    height: 30px;
    border: #ffffff 1px solid;
}

.swiperWord p{
    color: #ffffff;
    font-size: 5px;
    position: relative;
    top: -39px;
    right: -35px;
}
/* 广告部分样式设置 */
.gg{
    margin-bottom: 50px;
}
.tabs{
    float: right;
    margin-top: 10px;
    /* background-color: aquamarine; */
}
.gg-tab h3{
    float: left;
}
.gg-tab{
    margin-top: 40px;
    margin-bottom: 25px;
    width:1200px;
    height: 60px;

}
/* 广告内容设置： */
.the1{
    width:300px;
    margin-right: 10px;
    height: 300px;
    float: left;
    box-shadow: 10px 10px 5px -4px rgba(168, 187, 220, 0.8);

}
.gg-img{
    width: 300px;
    height: 198px;
    overflow: hidden;
    margin-bottom: 2px;;
}
.gg-pc{
    width: 100%;
    height: 100%;
    border-radius: 15px
}
.gg-word{
    width: 300px;
    height: 100px;
    background-color: rgb(253, 254, 255);
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}
.gg-word span{
    font-size: 15px;
    color: gray;
}
.gg-icon{
    border-radius: 50px;
    border: 1px solid gray;
    width: 50px;
    height: 50px;
    float: left;
    margin-top: 20px;
    margin-right: 5px;
}
.gg-icon img {
    border-radius: 50px;
    width: 100%;
    height: 100%;
}
.gg-pc:hover{
    transform: scale(1.1);
}


/* 游记部分样式 */
.yj1{
    width: 1200px;
    height: 200px;
    clear: both;
}
/* 游记tab部分 */
.yj h3{
    display: inline;
}
.yj-tab-word{
    color: gray;
    font-size: 5px;
    margin-left: 5px;
}
.yj-tab{
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

.user {
    position: relative;
    right: 20px;
    top: -10px;
    /* background-color: aquamarine; */
}
.user img{
    width: 30px;
    height: 30px;
}
.user p{
    display: inline;
    position: relative;
    font-size: 9px;
    top: -20px;
    right: -35px;
}

.yj1 span{
    color: gray;
    font-size: 10px;
    position: relative;
    top: -20px;
}

</style>