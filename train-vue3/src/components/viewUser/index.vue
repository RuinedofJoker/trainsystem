<template>
     <div class="commentUser">
        <img :src="data.user.avatar">
        <p>{{ data.user.nickName }}</p>
    </div>
</template>

<script setup>
import {getUserInfo} from '@/api/user/user.js'
import {defineProps, reactive} from 'vue'

const props = defineProps({
    userId: Number
})

const data = reactive({
    user: {
        id: props.userId,
        name: "",
        nickName: "",
        sex: 0,
        avatar: "",
        email: "",
        phonenumber: ""
    }
})

getUserInfo(data.user.id).then(res => {
    data.user.id = res.data.userId
    data.user.name = res.data.name
    data.user.nickName = res.data.nickName
    data.user.sex = res.data.sex
    data.user.avatar = import.meta.env.VITE_APP_BASE_API + res.data.avatar
    data.user.email = res.data.email
    data.user.phonenumber = res.data.phonenumber
})
</script>

<style >
.commentUser{
  width: 80px;
  /* background-color: #aaf5bb; */
  margin-left: 20px;
}
.commentUser p{
  overflow: hidden;
  text-overflow:ellipsis; 
  white-space: nowrap;
  margin-top: 10px;
}
.commentUser img{
  width: 60px;
  height: 60px;
  border-radius: 50px;
}
</style>