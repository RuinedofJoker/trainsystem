<template>
    <div id="msg-page" ref="msgPageRef">
        <Navbar id="user-navbar"></Navbar>
        <div class="msg-content">
            <aside class="msg-aside-box">
                <div class="msg-aside">
                    <div id="aside-head" class="aside-font"><el-icon>
                            <Promotion />
                        </el-icon>&nbsp; 消息中心</div>
                    <div class="aside-item"><a class="aside-font"><span class="dot">• </span> 我的消息</a></div>
                    <div class="aside-item"><a class="aside-font"><span class="dot">• </span> 回复我的</a></div>
                    <div class="aside-item"><a class="aside-font"><span class="dot">• </span> @ 我的</a></div>
                    <div class="aside-item"><a class="aside-font"><span class="dot">• </span> 收到的赞</a></div>
                </div>
            </aside>
            <article class="content-list-box">
                <ConcernMsgContent></ConcernMsgContent>
            </article>
        </div>
    </div>
</template>

<script setup>
import { Navbar } from '@/layout/components'
import { onMounted, ref, reactive, onUnmounted } from 'vue'
import ConcernMsgContent from '@/components/ConcernMsgContent/index'
import useUserStore from '@/store/modules/user.js'
// 进入页面自动刷新
if (location.href.indexOf("#reloaded") == -1) {
    location.href = location.href + "#reloaded";
    location.reload();
}

const userStore = useUserStore()
const websocket = inject('websocket')
const ws = websocket('websocket/concern/message')

const msgPageRef = ref(null)

onMounted(() => {
    let innerHeight = window.innerHeight
    msgPageRef.value.style.height = innerHeight + 'px'
})

onUnmounted(() => {
    ws.close()
})
</script>

<style scoped>
#user-navbar {
    position: absolute;
    width: 100%;
    top: 0;
    height: 9.5%;
    margin: 0;
}

#msg-page {
    background-image: url(@/assets/images/msg-background.jpg);
    width: 100%;
    position: relative;
    margin: 0px;
    top: 0;
    left: 0;
    box-sizing: border-box;
}

.msg-content {
    background-color: rgba(244, 245, 247, 0.4);
    display: flex;
    align-items: flex-start;
    position: absolute;
    width: 78%;
    height: 90.5%;
    left: 11%;
    top: 9.5%;
}

.msg-aside-box {
    background-color: rgba(244, 245, 247, 0.6);
    margin-right: 10px;
    height: 100%;
    width: 15%;
}

.msg-aside {
    display: flex;
    flex-direction: column;
}

.dot {
    font-size: 2vw;
}

#aside-head {
    list-style: none;
    color: #000000;
    height: 62px;
    padding-top: 25px;
    align-self: center;
}

.aside-item {
    height: 40px;
    display: flex;
    align-items: center;
    margin-left: 5%;
    margin-bottom: 5px;
    text-align: center;
}

.aside-item>a:hover {
    color: rgb(47, 174, 227);
}

.aside-item-active {
    color: rgb(47, 174, 227);
}

.aside-font {
    display: inline-block;
    color: rgb(107, 117, 123);
    font-weight: bolder;
    margin-left: 10%;
    line-height: 12px;
}

.content-list-box {
    position: absolute;
    right: 10px;
    background-color: rgba(244, 245, 247, 0.6);
    width: 83%;
    margin-left: 0px;
    display: flex;
    margin-top: 10px;
    border-style: solid;
    border-color: #ffffff;
    border-width: 2px;
    border-radius: 5px;
    height: 97%;
}
</style>