<template>
    <div style="width:1200px;margin:0 auto;">
        <div class="content">
          <div class="mask">
            <img v-if="!(coverPicId === '' || coverPicId === undefined)" :src="`${serviceIp}/picture/${coverPicId}`" class="picBig">
            <div class="maskWord">
              <p style="margin:0px">{{ oneEssay.date }}</p>
              <h1><strong>{{oneEssay.summary}}</strong></h1>
           </div>
           <div class="user">
            <!-- <viewUser :userId="oneEssay.userId" style="display:inline-block;"/> -->
           </div>
          </div>
        </div>
        <div>
          <p v-html="oneEssay.content"></p>
        </div>
    </div>
</template>

<script setup>
import {useRoute,} from 'vue-router'
import viewUser from '@/components/viewUser/index.vue'
import {getoneEssay,getPicture} from '@/api/city/index.js'
import { ref } from 'vue';

const route = useRoute( );
const essayId = route.params.essayId;
const oneEssay =ref({})
const coverPicId = ref('');
const serviceIp = import.meta.env.VITE_APP_BASE_API

//获取游记
function getoneEssayById(){
  getoneEssay(essayId).then((res)=>{
   oneEssay.value=res.data;
   coverPicId.value = res.data.coverPictureId;
   console.log(oneEssay);
  })
}
getoneEssayById()

</script>

<style lang="scss">
.content{
  width: 1200px ;
}

//内容设置
.mask{
  width: 1000px;
  margin: 0 auto;
  height: 400px;
}
.picBig{
  width: 100%;
  height: 100%;
}
.maskWord{
  position: relative;
  top: -150px;
  right: -30px;
  color: white;
}
</style>