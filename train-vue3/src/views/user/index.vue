<template>
  <div class="user-detail">
    <Navbar id="user-navbar"></Navbar>
    <header class="user-detail-header">
      <!-- 头盒子 -->
      <div class="user-info">
        <!-- 头像和昵称盒子 -->
        <div class="infoBox">
          <img :src="data.user.avatar" class="user-avatar" />
          <span class="user-nick-name">{{ data.user.nickName }}</span>
        </div>
        <!-- 关注按钮组 -->
        <div class="user-btns" v-if="!isLocalUser">
          <div class="user-follow">关注</div>
          <div class="user-message" @click="checkUserMsg">发消息</div>
        </div>
      </div>
    </header>

    <div class="main">
      <!-- 文章 -->
      <div class="main_left">
        <!-- 单个文章 -->
        <div class="article" v-for="item in data.user.article" :key="item.nikeName" @click="toArticleDetails">
          <!-- 第一行，头像+昵称+发布时间 -->
          <div class="article_writer_info">
            <div class="writer_avatar">
              <img :src="data.user.avatar" alt="">
            </div>
            <div class="writer_info_text">
              <div class="writer_nikeName">
                {{ data.user.nickName }}
              </div>
              <div class="article_releaseTime">
                {{ new Date(item.date).toLocaleString('zh-CN', { timeZone: 'Asia/Shanghai' }) }}
              </div>
            </div>
          </div>
          <!-- 文章主体部分 -->
          <div class="article_content">
            <div class="article_title">
              {{ item.essayTitle }}
            </div>
            <div class="articleBox">
              <div class="article_main">
                {{ item.summary }}
              </div>
              <div class="article_img">
                <img :src="serverApi + '/picture/' + item.coverPictureId" alt="">
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="main_right">
        <div class="followers">
          <div class="followers_header" v-if="isLocalUser">
            全部关注
          </div>
          <div class="followers_content" v-if="isLocalUser">

            <div class="followers_item" v-for="item in data.user.follower" :key="item.nickName">
              <img :src="item.avatar" alt="" class="follower_avatar">
              <div class="follower_nikeName">
                {{ item.nickName }}
              </div>
            </div>

          </div>
        </div>
      </div>
    </div>
  </div>
</template>
  
<script setup>
import { getUserInfo } from '@/api/user/user.js'
import { ref, reactive, onMounted, onUnmounted } from 'vue'
import { Navbar } from '@/layout/components'
import { useRouter, useRoute } from 'vue-router'
import { getConcernList } from '@/api/concern/concern.js'
import { getUserEssayList } from '@/api/essay/essay.js'
import useUserStore from '@/store/modules/user.js'

const router = useRouter()
const route = useRoute()
const serverApi = import.meta.env.VITE_APP_BASE_API

const userStore = useUserStore();

const currentUserId = route.params.userId

if(currentUserId === '' || currentUserId === undefined) {
  router.replace({
    path: '/login'
  })
}

//判断当前登录用户和当前查看用户是否是同一个
const isLocalUser = ref(false)
const data = reactive({
  user: {
    id: "",
    name: "",
    nickName: "",
    sex: 0,
    avatar: "",
    email: "",
    phonenumber: "",
    follower: [],
    article: []
  }
})

isLocalUser.value = (route.params.userId == userStore.id)

getUserInfo(route.params.userId).then(res => {
  data.user.id = res.data.userId
  data.user.name = res.data.name
  data.user.nickName = res.data.nickName
  data.user.sex = res.data.sex
  data.user.avatar = serverApi + res.data.avatar
  data.user.email = res.data.email
  data.user.phonenumber = res.data.phonenumber
})

//获取该用户游记列表
getUserEssayList(currentUserId).then(res => {
  data.user.article = res.data
})

//获取该用户关注列表
if(isLocalUser.value) {
  getConcernList().then(res => {
    data.user.follower = res.data
  })
}

onMounted(() => {
  window.addEventListener('scroll', () => {
    let scrollTop = document.documentElement.scrollTop
    if (scrollTop >= 260) {
      document.getElementById('user-navbar').classList.add('user-navbar-active')
      document.getElementById('user-navbar').classList.remove('user-navbar-unactive')
    } else {
      document.getElementById('user-navbar').classList.add('user-navbar-unactive')
      document.getElementById('user-navbar').classList.remove('user-navbar-active')
    }
  })
})

onUnmounted(() => {
  window.removeEventListener('scroll')
})

function checkUserMsg() {
  router.push({ path: '/concern/message' })
}

function toArticleDetails(){
  router.push({})
}
</script>

<style scoped>
.user-detail {
  background-color: #f4f5f7;
}

.user-navbar-unactive {
  position: relative;
  width: 100%;
}

.user-navbar-active {
  position: fixed;
  top: 0;
  left: 7.5%;
  right: 7.5%;
}

#user-sex {
  height: 10px;
  width: 10px;
}

.user-detail-header {
  position: relative;
  height: 190px;
  width: 85%;
  margin: auto;
  background: url('@/assets/images/user-background.jpg');
  background-repeat: no-repeat;
  background-size: cover;
}

.user-detail-content {
  margin-top: 20px;
  width: 85%;
  margin-left: 7.5%;
  display: flex;
}

.user-info {
  position: absolute;
  bottom: 20px;
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  text-align: center;
  /* border: 1px solid white; */
  padding: 0 40px;

}

.infoBox {
  display: flex;
  align-items: center;
}

.user-btns {
  display: flex;

}

.user-btns div {
  margin: 0 20px;
  border-radius: 5px;
  height: 35px;
  width: 100px;
  color: #fff;
  line-height: 35px;
  user-select: none;
}


.user-follow {
  background-color: #F25D8E;
  border: 2px solid #ffffff;

}

.user-follow:hover {
  background-color: #FF85AD;
}

.user-message {
  border: 2px solid gray;
  background: rgba(0, 0, 0, .45);
}

.user-message:hover {
  background: rgba(0, 0, 0, .60);
}

.email {
  position: absolute;
  left: 225px;
  bottom: 54px;
  color: #fff;
}

.user-avatar {
  border-radius: 35px;
  border-style: solid;
  border-width: 2px;
  border-color: cornsilk;
  width: 70px;
  height: 70px;
  margin: 10px;
}

.user-nick-name {
  margin-left: 30px;
  font-size: 20px;
  font-weight: 700;
  color: aliceblue;
}

.article-article-content {
  background-color: #ffffff;
  border-radius: 5px;
  width: 79%;
  margin-bottom: 10px;
}

.concern-list {
  background-color: #ffffff;
  border-radius: 5px;
  margin-left: 1%;
  width: 20%;
  margin-bottom: 10px;
  /* height: ; */
}

.main {
  margin-top: 20px;
  width: 85%;
  margin-left: 7.5%;
  display: flex;
  justify-content: space-between;
}

.main_left {
  width: 79%;
}

.main_left div {
  background: white;
}

.main_right {
  width: 20%;
}

.main_right div {
  background: white;
}

.followers {
  border-radius: 5px;
  box-shadow:
    0px 0px 1px rgba(0, 0, 0, 0.046),
    0px 0px 8px rgba(0, 0, 0, 0.09);

}

.followers_header {
  height: 30px;
  line-height: 30px;
  text-align: center;
  border-bottom: #a8a8a8 2px solid;
}

.followers_content {
  /* border: 1px solid black; */
  min-height: 500px;
}

.followers_content_null {
  min-height: 500px;
  display: flex;
  justify-content: center;
  align-items: center;
  color: rgb(90, 89, 89);
}

.followers_item {
  display: flex;
  align-items: center;
  height: 60px;
  width: 80%;
  margin-left: 40px;
}

.follower_avatar {
  width: 40px;
  height: 40px;
  border-radius: 50px;
  margin-right: 20px;
}

.article {
  /* border: 1px solid black; */
  border-radius: 5px;
  min-height: 300px;
  margin-bottom: 20px;
  box-shadow:
    0px 0px 1px rgba(0, 0, 0, 0.046),
    0px 0px 8px rgba(0, 0, 0, 0.09);

}

.article_writer_info {
  /* width: 100px; */
  height: 80px;
  /* border: 1px solid black; */
  display: flex;
  align-items: center;
  margin-left: 20px;

}

.writer_avatar img {
  width: 50px;
  height: 50px;
  border-radius: 50px;
}

.writer_info_text {
  margin-left: 10px;
}

.writer_nikeName {
  font-weight: 900;
  font-size: 18px;

}

.article_releaseTime {
  color: gray;
}

.article_content {
  margin: 0px 80px;
}

.article_title {
  font-weight: 900;
  font-size: 18px;
  width: 100%;  width: 100%;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 4;
  overflow: hidden;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 1;
  overflow: hidden;
}

.articleBox {
  display: flex;
  margin-top: 20px;
}

.article_main {
  max-height: 200px;

  line-height: 30px;
}

img {
  margin-left: 30px;
}

.article_img img {
  width: 100px;
  height: 100px;
}
</style>