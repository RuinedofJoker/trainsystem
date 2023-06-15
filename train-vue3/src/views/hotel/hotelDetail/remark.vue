<template>
  <div id="big-remark">
    <div class="user-remark" v-for="item in commentList" :key="item.articleId">
      <!--    用户昵称区-->
      <div class="user">
        <div>
          <span><img :src="service + userList.avatar" alt=""></span>
          <span class="user-name"><span style="font-size: 13px;"></span>{{ item.userId }}</span>
        </div>
        <div class="check-in">
          <!--                    <span><el-icon><House/></el-icon>曙光·舒适大床房</span>-->
          <!--                    <span><el-icon><Calendar/></el-icon>于2023年3月入住</span>-->
        </div>
      </div>
      <div class="remark-content">
        <div style="color: #1890ff"></div> <!--评分-->
        <!--        <div>{{ item.content }}</div>-->
        <div v-html="item.content"></div>
      </div>
    </div>

    <div class="user-remark"></div>

  </div>
</template>

<script setup>
import {useRoute} from 'vue-router'
import {ref, defineProps, watch} from 'vue'
import {getComment} from "../../../api/hotel/comment";
import {getUser} from "../../../api/hotel/user";

//ES6模块语法，import.meta.env.VITE_APP_BASE_API 是一个环境变量，它的值被赋给了 service 变量。以便于在应用程序中使用该变量值。
const service = import.meta.env.VITE_APP_BASE_API
//获取路由传值
const route = useRoute()
const {hotelId, hotelName, userId} = route.params
console.log(route.params)

//接收用户刚评论的内容 从父组件得到
const props = defineProps({
  comment: Object
})

//获取后端评论
const commentList = ref([])

function getCommentList() {
  getComment(hotelId).then((res) => {
    commentList.value = res.data.data;
    console.log("评论数据", commentList)
  })
}

getCommentList()

//获取用户信息
const userList = ref([])

function getUserList() {
  getUser(userId).then((res) => {
    userList.value = res.data;
    console.log("userList:", userList.value)
  })
}
getUserList()

//监听评论刷新，评论数据刷新时，将其推送到commentList 并再次调用获取后端评论方法，实现实时刷新
watch(props.comment, (newVal, oldVal) => {
  commentList.value.push(newVal)

  getCommentList()
})

</script>

<style scoped>
#big-remark {
  display: inline-block;
  background: white;
  padding: 15px;
  width: 100%;
  height: auto;
}

.user-remark {
  border-top: lightgray 1px solid;
  display: inline-block;
  width: 100%;
  height: auto;
  padding: 10px 0;
  margin-bottom: 10px;
}

.user {
  width: 20%;
  float: left;
}

.user img {
  float: left;
  width: 60px;
  height: 60px;
  border-radius: 50%;
}

.user-name {
  margin-left: 10px;
  font-size: 16px;
  float: left;
  color: #464848;
  font-weight: bolder;
}

.check-in {
  display: inline-block;
  padding-top: 5px;
  height: 50px;
  color: gray;
  float: left;
}

.check-in span {
  display: inline-block;
}

.remark-content {
  width: 80%;
  height: auto;
  /*border: hotpink 1px solid;*/
  float: left;
  font-size: 15px;
}

.remark-content img {
  width: 100px;
  height: 100px;
  border: #1890ff 2px solid;
}

.score {
  font-size: 20px;
  color: #1890ff;
  font-weight: bolder;
}

.remark-img {
  display: inline-block;
  width: 100%;
  /*height: 120px;*/
  line-height: 120px;
  /*border: #1890ff 1px solid;*/
  margin-top: 10px;
}

.remark-img img {
  display: inline-block;
  float: left;
  width: 100px;
  height: 100px;
  /*border: #1890ff 2px solid;*/
  margin-right: 10px;
}

.remark-img img:hover {
  border: #287DFA 2px solid;
}

</style>
