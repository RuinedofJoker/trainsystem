<template>
    <div class="area" style="height: auto;width:700px;display: flex;flex-wrap: wrap;">
        <!-- 7.使用cityList里面的数据 -->
        <div class="province" style="height: auto;width: 100px; display: inline-block; margin-left:10px" >{{ props.provinceName }}</div>
        <div class="spot" style=" height: auto;width: 500px;">
            <!-- 8.给城市绑定方法，钩子函数里面获取到当前点击的城市名称，跳转到对应的界面 -->
            <div v-for="item in cityList" :key="item.cityId" @click="jump(item.englishName,item.cityName)">{{ item.cityName }}</div>
        </div>
    </div>
</template>

<script setup>
//2，导入接口的方法
import {getCityByProvinceName} from '@/api/city/index.js'
import { ref } from 'vue';
import {useRouter} from 'vue-router'

const router = useRouter();

//3.子组件获取父组件参数
const props = defineProps({
provinceName:String
})

const cityList = ref([]); //6.定义数组，接收数据；里面有cityId、cityName、cityHotSpot,provinceId、englishName

//4.调获取城市的api
function getCity(){
getCityByProvinceName(props.provinceName).then((res)=>{ //根据provinceName获取所有cityList信息
    for(let i=0;i<res.data.length;i++){
        cityList.value.push(res.data[i]);
    }
    // console.log(cityList.value);
})
}
getCity();//5.调用方法

//e表示当前出发的事件对象；
//e.target是e事件的元素；div
//e.target.innerHTML获取道div里面的元素
function jump(englishName,cityName){
//  console.log(e.target.innerHTML);
router.push({ //这里是一个钩子函数
    path:`/index/attractions/place?englishName=${englishName}&cityName = ${cityName}`, //带了城市名称这个参数
    query:{
        englishName:englishName,
        cityName:cityName
    }
})
}
</script>

<style lang="scss" scoped>
// 大盒子设置弹性布局，小盒子一列一列显示

// 给地点设置flex布局，一行一行的显示所有市
.spot{
display: flex; //弹性盒子
flex-wrap: wrap;
}

.spot div{
// border:rgb(137, 36, 77) 1px solid;
margin-right: 20px;
width:100px;
height: 30px;
}
</style>