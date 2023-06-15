<template>

  <div class="big">
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
      <swiper-slide><img src="../imgs/ad/t1.jpg" alt=""></swiper-slide>
      <swiper-slide><img src="../imgs/ad/t2.jpg" alt=""></swiper-slide>
      <swiper-slide><img src="../imgs/ad/t3.jpg" alt=""></swiper-slide>
      <swiper-slide><img src="../imgs/ad/t4.jpg" alt=""></swiper-slide>

    </swiper>

  </div>

</template>

<script>
import {Swiper, SwiperSlide} from 'swiper/vue';
import 'swiper/swiper.min.css'
import './style.css'
import {Autoplay, Navigation, Pagination} from "swiper";

export default {
  components: {
    Swiper,
    SwiperSlide,
    Autoplay,
    Pagination,
    Navigation
  },
  setup() {

    const onSwiper = (swiper) => {
      console.log(swiper);
    };
    const onSlideChange = () => {
      console.log('slide change');
    };
    return {
      onSwiper,
      onSlideChange,
      modules: [Autoplay, Pagination, Navigation],
    };
  }

}
</script>


<style scoped>

.big{
  height: 100%;
  border-radius: 15px;
  overflow: hidden;
  /*border: #13ce66 1px solid;*/
}

</style>
