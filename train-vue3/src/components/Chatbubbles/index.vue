<template>
  <div class="msg-item" :class="props.msg.isLocalUser">
    <!-- <img v-if="props.msg.isLocalUser === 'chat-bubbles-left'"> -->
    <span v-if="props.msg.contentType === 0" class="chat-bubbles-font">{{ props.msg.content }}</span>
    <!-- <img v-if="props.msg.isLocalUser === 'chat-bubbles-right'"> -->
  </div>
</template>

<script setup>
import {defineProps, onMounted} from 'vue'
const props = defineProps({
  msg: Object
})
</script>

<style>
.msg-item {
  margin: 20px;
  border: none;
  width: fit-content;
  max-width: 80%;
}

.chat-bubbles-font {
  display: block;
  font-size: 16px;
  line-height: 40px;
  word-break: normal;
  white-space: pre-wrap;
  word-wrap: break-word;
  overflow: hidden;
}

.chat-bubbles-left {
  padding: 0 20px;
  border-radius: 0 30px 30px 30px;
  background-color:#ffffff;
  margin-left: 20px;
  align-self: flex-start;
}
.chat-bubbles-right {
  padding: 0 20px;
  border-radius: 30px 0 30px 30px;
  background-color:#80b9f2;
  margin-right: 40px;
  align-self: flex-end;
}
</style>