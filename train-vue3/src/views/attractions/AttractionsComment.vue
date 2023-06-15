<template>
    <div class="edit">
        <div class="editComment">
            <div class="tab">
              <img :src="`${serviceIp}/picture/${route.query.fileId}`" >
              <a href="/attractions/detail/景点id" target="_blank">
                <h3>{{route.query.attractionsName}}</h3>
              </a>
              <span>{{route.query.address}}</span>
            </div>
            <div class="editmain">
                <div class="commentmain">
                  <p style="">点评内容:</p>
                  <Blog style="display:inline-block;width:660px;margin-left: 20px;" @updateModel="getCommentContent"/>
                </div>
                <el-button  style="margin-top: 20px;margin-left: 340px;width: 70px;height: 30px;" 
                    @click="dialogCommentVisible = false;sendConmment()">
                    发表评论
        </el-button>
            </div>
        
        </div>
    </div>
</template>

<script setup>
import { reactive, ref } from "vue";
import Blog from '@/components/Blog/blog.vue'
import {useRouter,useRoute} from 'vue-router'
import {addComment} from '@/api/city/index.js'

const serviceIp = import.meta.env.VITE_APP_BASE_API
const route = useRoute();
const router = useRouter();
let content = "";
let dialogCommentVisible = ref(false)

let isShow = ref(true);


function getCommentContent(contentVal) {
  content = contentVal
}

//发表评论
function sendConmment(){
  const commentApi = {
    commentType: 1,
    articleId: route.query.attractionsId,
    content: content
  }
  //发表后跳转页面
  addComment(commentApi).then((res)=>{
    router.replace({
      path:`/attractions/detail/${ route.query.attractionsId}`
    })
  })
}

</script>


<style lang="scss">
.edit{
width: 1200px;
margin: 0 auto;
}
// 编辑页面设置
.editComment{
    width: 800px;
    height: auto;
    margin-left: 100px;
}
// 展示栏
.tab{
    height: 120px;
    background-color: rgb(244, 243, 243);
}
.tab img{
  width: 100px;
  height: 70px;
  margin: 20px;
  display: inline-block;
}
.tab h3{
  display: inline;
  position: relative;
  top: -70px;
  color: rgb(90, 173, 208);
}
.tab span{
  color: gray;
  font-size: 10px;
  position:relative;
  right: 150px;
  top: -40px;
}
.editmain{
    border:rgb(174, 173, 175) 1px solid;
    padding-left:10px;
}
.commentmain{
  margin-top: 30px;
  margin-left: 20px;
}
.ql-editor img{
  width:200px;
  height: 200px;
}
.ql-container{
  height: 600px;
}
</style>