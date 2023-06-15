<template>
    <div id="chat-emoji" ref="chatEmojiArea"><ChatEmoji @chooseEmoji="chooseEmoji"></ChatEmoji></div>
    <aside class="msg-list">
        <div class="list-head">关注列表</div>
        <div v-for="concernUser in data.concernList" :key="concernUser.userId" class="list-item" @mouseover="hoverUserBox($event, concernUser)" @click="changeCheckedUserId($event, concernUser)">
            <UserBox class="user-box" :user-id="concernUser.userId"></UserBox>
        </div>
    </aside>
    <section class="chat-box">
        <div class="chat-head"><span class="chat-head-username">{{ data.checkedUser.nickName }}</span></div>
        <div class="chat-history"><ChatHistory :user-id="data.checkedUser.userId"></ChatHistory></div>
        <div class="chat-send">
            <header class="chat-send-header">
                <button @click="checkEmoji" class="change-emoji-state"><el-icon size="25"><ChatDotRound /></el-icon></button>
                <el-upload
                  ref="uploadPictureRef"
                  class="upload-picture"
                  action="https://run.mocky.io/v3/9d059bf9-4660-45f2-925d-ce80ad6c4d15"
                  :auto-upload="false"
                >
                    <el-icon :size="25"><PictureFilled /></el-icon>
                </el-upload>
            </header>
            <section class="chat-send-section">
                <textarea id="chat-send-area" v-model="data.sendArea"></textarea>
            </section>
            <footer class="chat-send-footer">
                <el-button class="send-button" @click="sendMsg">发送</el-button>
            </footer>
        </div>
    </section>
</template>

<script setup>
import UserBox from '@/components/UserBox/index'
import { onMounted, ref, reactive, onUnmounted, computed } from 'vue'
import ChatEmoji from '@/components/ChatEmoji/index'
import ChatHistory from '@/components/ChatHistory/index'
import {getUserInfo} from '@/api/user/user.js'
import {getMessageList} from '@/api/concern/concern.js'
import useUserStore from '@/store/modules/user.js'

const userStore = useUserStore()
const websocket = inject('websocket')
const ws = websocket('websocket/concern/message')

let emojiVisibility = ref(false)
const chatEmojiArea = ref(null)

const data = reactive({
  concernList: [],
  checkedUser: {
        userId: -1,
        userName: "",
        nickName: "",
        sex: 0,
        avatar: "",
        email: "",
        phonenumber: ""
    },
  sendArea: '',
})

getMessageList().then(res => {
    for(let i = 0; i < res.data.length; i++){
        data.concernList.push(res.data[i])
    }
})

function changeCheckedUserId(e, user){
    data.checkedUser = user
}
function hoverUserBox(e, concernUser) {
    console.log(concernUser);
}

function sendMsg(){
    let msgContent = data.sendArea
    const msg = {}
    msg.code = 1
    msg.senderUserId = userStore.id
    msg.receiverUserId = data.checkedUser.userId
    msg.data = {}
    msg.data.content = msgContent
    msg.data.contentType = 0
    ws.send(JSON.stringify(msg))
    data.sendArea = ''
}

function checkEmoji(){
    if(!emojiVisibility.value){
        chatEmojiArea.value.style.visibility = 'visible'
    }else{
        chatEmojiArea.value.style.visibility = 'hidden'
    }
    emojiVisibility.value = !emojiVisibility.value
}
function chooseEmoji(e) {
    data.sendArea = data.sendArea + e
    checkEmoji()
}
</script>

<style scoped>
.msg-list {
    background-color: #ffffff;
    display: flex;
    flex-direction: column;
    width: 30%;
    height: 100%;
    border-right: 1px solid #e9eaec;
    padding: 0;
}

.list-head {
    height: 6%;
    font-size: 15px;
    border-bottom: 1px solid #e9eaec;
    color: rgb(107,117,123);
    padding-left: 30px;
}

.list-item {
    height: 80px;
    padding: 0;
    width: 100%;
    align-self: center;
}
.list-item-active {
    background-color: #e4e5e6;
}
.list-item:hover {
    background-color: #e4e5e6;
}
.list-item::-webkit-scrollbar{
    height: 0;
}

.chat-box {
    position: relative;
    background-color: #f4f5f7;
    width: 100%;
    display: flex;
    flex-direction: column;
}

#chat-emoji {
    position: fixed;
    top: 45%;
    left: 40%;
    right: 10%;
    visibility: hidden;
    z-index: 1;
}

.chat-head {
    width: 100%;
    height: 6%;
    font-size: 15px;
    border-bottom: 1px solid #e9eaec;
    color: rgb(107,117,123);
    text-align: center;
    align-items: center;
    line-height: 35px;
}

.chat-history {
    width: 100%;
    height: 68%;
    border-bottom: 1px solid #e9eaec;
}

.chat-send {
    width: 100%;
    height: 26%;
    position: absolute;
    top: 74%;
    padding: 0;
}

.chat-send-header {
    position: absolute;
    top: 0;
    margin: 0;
    padding: 5px;
    height: 20%;
    display: flex;
}

.change-emoji-state {
    border-style: none;
    background-color: #f4f5f7;
    margin: 8px;
}

.upload-picture {
    margin: 8px;
}

.chat-send-section {
    position: absolute;
    top: 23%;
    margin: 0;
    height: 60%;
    width: 100%;
}

#chat-send-area {
    position: absolute;
    margin: 0;
    height: 90%;
    width: 95%;
    border: none;
    resize: none;
    outline: none;
    background-color: #f4f5f7;
    margin-left: 2.5%;
    font-size: 18px;
}
#chat-send-area::-webkit-scrollbar {
    height: 0;
}

.chat-send-footer {
    position: absolute;
    left: 0;
    top: 80%;
    margin: 0;
    padding: 5px;
    height: 20%;
    width: 100%;
}

.send-button {
    position: absolute;
    left: 83%;
    bottom: 10px;
    width: 80px;
    height: 25px;
}
</style>