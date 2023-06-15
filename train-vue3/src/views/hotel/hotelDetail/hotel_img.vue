<template>
  <div class="big-box">
    <div class="images">

        <img :src="hotelImages.hotelPicture">
    </div>

  </div>
</template>

<script setup>
import {Swiper, SwiperSlide} from 'swiper/vue';
import 'swiper/swiper.min.css'
import '../components/style.css'
import {Autoplay, Navigation, Pagination} from "swiper";
import {useRouter} from 'vue-router'
import {useRoute} from 'vue-router'
import {ref} from 'vue'
import {getHotelDetail} from "../../../api/hotel/hotelDetail";

const route = useRoute();
const hotelId = route.params.hotelId;

//请求接口
const router = useRouter();
const hotelImages = ref({
  hotelPicture:''
})

function getHotelImages(hotelId) {
  getHotelDetail(hotelId).then((res) => {
    hotelImages.value = res.data;
    // console.log(hotelImages.value)
  })
}
getHotelDetail(hotelId)
getHotelImages(hotelId)
</script>

<style scoped>
.big-box {
  /*display: inline-block;*/
}

.images {
  width: 85%;
  height: 380px;
  float: left;
}
.images img{
  width: 100%;
  height: 380px;
}


</style>
