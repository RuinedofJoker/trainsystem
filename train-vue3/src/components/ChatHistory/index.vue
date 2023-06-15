<template>
  <div class="chat-history-area">
    <el-scrollbar height="100%" ref="msgScroll" class="msg-scroll" noresize="false">
        <div ref="innerRef" id="inner-ref">
          <Chatbubbles v-for="historyMsg in historyMsgList" :key="historyMsg.messageId" :msg="historyMsg"></Chatbubbles>
        </div>
    </el-scrollbar>
  </div>
</template>

<script setup>
import {getUserInfo} from '@/api/user/user.js'
import {getHistoryMessage} from '@/api/concern/concern.js'
import {defineProps, ref, reactive, onMounted, computed, watch, onUpdated, onBeforeMount} from 'vue'
import Chatbubbles from '@/components/Chatbubbles/index.vue'

const msgScroll = ref(null)
const innerRef = ref(null)

const websocket = inject('websocket')
const ws = websocket('websocket/concern/message')

const props = defineProps({
    userId: Number
})

const historyMsgList = reactive([])

ws.onmessage = (res) => {
  new Promise((resolve, reject) => {
    const resContent = JSON.parse(res.data)
    const resData = resContent.data
    if(resContent.code === 200 || resContent.code === 1){
      if(resData.contentType === 0){
        const curHistoryMsg = {}
        curHistoryMsg.messageId = resData.messageId
        curHistoryMsg.contentType = resData.contentType
        curHistoryMsg.content = resData.content
        curHistoryMsg.date = new Date(resData.date)
        if(resData.isLocalUser === 1){
          curHistoryMsg.isLocalUser = 'chat-bubbles-right'
        }else{
          curHistoryMsg.isLocalUser = 'chat-bubbles-left'
        }
        historyMsgList.push(curHistoryMsg)
      }
    }
    resolve()
  }).then(() => {
    msgScroll.value.setScrollTop(innerRef.value.clientHeight)
  })
}

watch(props, (newVal,oldVal) => {
  historyMsgList.splice(0, historyMsgList.length)
  if(props.userId != -1){
        getHistoryMessage(props.userId).then((res) => {
        new Promise((resolve, reject) => {
          for(let i = 0;i < res.data.length; i++){
            if(res.data[i].contentType === 0){
              const curHistoryMsg = {}
              curHistoryMsg.messageId = res.data[i].messageId
              curHistoryMsg.contentType = res.data[i].contentType
              curHistoryMsg.content = res.data[i].content
              curHistoryMsg.date = new Date(res.data[i].date)
              if(res.data[i].isLocalUser === 1){
                curHistoryMsg.isLocalUser = 'chat-bubbles-right'
              }else{
                curHistoryMsg.isLocalUser = 'chat-bubbles-left'
              }
              historyMsgList.push(curHistoryMsg)
            }
          }
          resolve()
        }).then(() => {
          msgScroll.value.setScrollTop(innerRef.value.clientHeight)
        })
      })
    }
},{immediate:true})

onMounted(() => {
  innerRef.value.style.maxWidth = innerRef.value.clientWidth+'px'
})
</script>

<style scoped>
.chat-history-area {
    height: 100%;
    width: 100%;
    position: relative;
}

#inner-ref {
  display: flex;
  flex-direction: column;
}
</style>