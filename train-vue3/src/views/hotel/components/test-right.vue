<template>

  <div class="big">
    <div class="images">
      <swiper
          :spaceBetween="0"
          :centeredSlides="true"
          :autoplay="{
      delay: 3000,
      disableOnInteraction: false,
    }"
          :pagination="{
          clickable: true,
    }"
          :navigation="true"
          :modules="modules"
          class="mySwiper"
      >
        <!--        北京-->
        <swiper-slide>
          <img src="../imgs/right/h1.jpg" alt="">
          <div class="small">
            <div class="small-content" v-for="item in hotelArr" :key="item.hotelId" @click="jump(item.hotelId,item.hotelName,item.userId)">
              <div class="smallImg"><img :src="item.hotelPicture" alt=""></div>
              <div class="text">
                <span class="h-name">{{ item.hotelName }}</span>
                <span class="h-p">￥{{ item.price }}起</span>
              </div>
            </div>

          </div>
        </swiper-slide>

        <!--上海-->
        <swiper-slide>
          <img src="../imgs/right/h2.jpg" alt="">
          <div class="small">
            <div class="small-content" v-for="item in shanghai" :key="item.hotelId" @click="jump(item.hotelId,item.hotelName,item.userId)">
              <div class="smallImg"><img :src="item.hotelPicture" alt=""></div>
              <div class="text">
                <span class="h-name">{{ item.hotelName }}</span>
                <span class="h-p">￥{{ item.price }}起</span>
              </div>
            </div>
          </div>
        </swiper-slide>
        <!--广州-->
        <swiper-slide>
          <img src="../imgs/right/h3.jpg" alt="">
          <div class="small">
            <div class="small-content" v-for="item in guangzhou" :key="item.hotelId" @click="jump(item.hotelId,item.hotelName,item.userId)">
              <div class="smallImg"><img :src="item.hotelPicture" alt=""></div>
              <div class="text">
                <span class="h-name">{{ item.hotelName }}</span>
                <span class="h-p">￥{{ item.price }}起</span>
              </div>
            </div>
          </div>
        </swiper-slide>

        <!--        深圳-->
        <swiper-slide>
          <img src="../imgs/right/h4.jpg" alt="">
          <div class="small">
            <div class="small-content" v-for="item in shenzhen" :key="item.hotelId" @click="jump(item.hotelId,item.hotelName,item.userId)">
              <div class="smallImg"><img :src="item.hotelPicture" alt=""></div>
              <div class="text">
                <span class="h-name">{{ item.hotelName }}</span>
                <span class="h-p">￥{{ item.price }}起</span>
              </div>
            </div>

          </div>
        </swiper-slide>

      </swiper>
    </div>


  </div>

</template>

<script>
import {Swiper, SwiperSlide} from 'swiper/vue';
import 'swiper/swiper.min.css'
import './style.css'
import {Autoplay, Navigation, Pagination} from "swiper";
import {getHotRecommend} from "../../../api/hotel/getRecommend";
import {useRouter} from 'vue-router';
import {getRecommend} from "../../../api/hotel/getRecommend";
import {ref, onMounted} from 'vue'
import {getAllHotel} from "../../../api/hotel/allHotel";
import {getProvince} from "../../../api/hotel/province";

export default {
  components: {
    Swiper,
    SwiperSlide,
    Autoplay,
    Pagination,
    Navigation
  },
  setup() {
    let imgs = [
      'h1.jpg',
      'h2.jpg',
      'h3.jpg',
      'h4.jpg'
    ]
    const onSwiper = (swiper) => {

    };
    const onSlideChange = () => {

    };
    //点击卡片跳转酒店详情页
    const router = useRouter()
    function jump(hotelId,hotelName,userId) {
      router.push({
        path: `/index/hotel_detail/${hotelId}/${hotelName}/${userId}`,
        params: {
          hotelId: hotelId,
          hotelName: hotelName,
          userId:userId,
        }
      })
    }

    onSlideChange()
    onSwiper()
    //酒店小盒子数据存放
    let hotelArr = ref([])
    let shanghai = ref([])
    let guangzhou = ref([])
    let shenzhen = ref([])

//获取推荐酒店
    function getSmallHotel() {
      getRecommend().then((res) => {
        hotelArr.value = res.data.data;
      })
    }

    function getShangHai() {
      getHotRecommend(0).then((res) => {
        shanghai.value = res.data.data;
      })
    }

    function getGuangZhou() {
      getHotRecommend(3).then((res) => {
        guangzhou.value = res.data.data;
      })
    }

    function getShenZhen() {
      getHotRecommend(7).then((res) => {
        shenzhen.value = res.data.data;
      })
    }


    getSmallHotel()
    getShangHai()
    getGuangZhou()
    getShenZhen()

    return {
      hotelArr,
      shanghai,
      guangzhou,
      shenzhen,
      onSwiper,
      onSlideChange,
      modules: [Autoplay, Pagination, Navigation],
      imgs,
      jump,
    };
// onMounted(() => {
//   getSmallHotel()
// })
  },
}
</script>


<style scoped>

.big {
  height: 656px;
  border-radius: 15px;
  overflow: hidden;
  padding: 0;
  /*border: #13ce66 1px solid;*/
  position: relative;
}

.images {
  width: 426px;
  height: 656px;
  border-radius: 15px;
  margin: 0;
  z-index: 1;
  position: absolute;
  bottom: 0px;
}

.small {
  /*background-color: rgba(255,255,255,0.5);*/
  z-index: 2;
  /*background: red;*/
  /*border: red 1px solid;*/
  width: 200px;
  height: auto;
  float: right;
  position: absolute;
  top: 20px;
  right: 10px;
}

.small-content {
  width: 200px;
  height: 50px;
  line-height: 50px;
  background-color: rgba(255, 255, 255, 0.2);
  border-radius: 8px;
  margin-bottom: 10px;
  cursor: pointer;
}

.smallImg {
  width: 50px;
  height: 50px;
  line-height: 50px;
  padding-top: 2.5px;
  padding-left: 3px;
  float: left;
}

.smallImg img {
  width: 44px;
  height: 44px;
  border-radius: 5px;

}

.text {
  /*border: #13ce66 1px solid;*/
  font-size: 14px;
  height: 50px;
  width: 145px;
  line-height: 50px;
  color: white;
  font-weight: bolder;
  float: left;
  display: inline-block;
}

.h-name {
  /*border: #1890ff 1px solid;*/
  font-size: 11px;
  display: block;
  width: 90px;
  height: 50px;
  float: left;
  overflow: hidden;
  text-overflow: ellipsis;
}

.h-p {
  float: right;
  /*border: hotpink 1px solid;*/
  font-size: 11px;
}

</style>
