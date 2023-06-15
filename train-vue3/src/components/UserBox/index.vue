<template>
  <div class="user-box">
    <div class="user-avatar">
        <img class="img" :src="data.user.avatar">
    </div>
    <span class="user-nick-name">{{ data.user.nickName }}</span>
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

<style scoped>
.user-box {
    position: relative;
    top:0;
    left:0;
    display: flex;
    flex-direction: row;
    flex-wrap: nowrap;
    align-items: center;
    width: 100%;
    height: 100%;
    padding: 10%;
    margin: 0;
}

.user-avatar {
    position: relative;
    width: 0;
    height: 100%;
    padding-top: 30%;
    padding-left: 30%;
    border-radius: 50%;
    overflow: hidden;
}

.img {
    position: absolute;
    top: 0;
    right: 0;
    left: 0;
    bottom: 0;
    width: 100%;
    height: 100%;
}

.user-nick-name {
    position: absolute;
    top: 20%;
    left: 30%;
    font-weight: 510;
    margin-left: 13%;
    font-size: 1vw;
}
</style>