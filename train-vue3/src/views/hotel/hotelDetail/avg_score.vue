<template>
  <div class="big-score">
    <div class="left-text">

      <span style="margin-top: 90px;color: #366AB3">
        <el-icon size="20px"><Memo /></el-icon>
        用户点评
      </span>
      <span style="color: #366AB3"></span>
      <!--      <div class="special">4.7-->
      <!--        <el-icon>-->
      <!--          <StarFilled/>-->
      <!--        </el-icon>-->
      <!--        <el-icon>-->
      <!--          <StarFilled/>-->
      <!--        </el-icon>-->
      <!--        <el-icon>-->
      <!--          <StarFilled/>-->
      <!--        </el-icon>-->
      <!--        <el-icon>-->
      <!--          <StarFilled/>-->
      <!--        </el-icon>-->
      <!--      </div>-->
    </div>
    <div class="star">
      <div>

      </div>
    </div>
    <div class="comment">
      <span @click="dialogCommentVisible=true">
        <el-icon>
        <EditPen/>
      </el-icon>
      写评论</span>
    </div>

    <!--    弹出评论框-->
    <el-dialog v-model="dialogCommentVisible" width="30%" align-center>
      <el-input
          v-model="textarea1"
          type="textarea"
          :autosize="{ minRows: 4, maxRows: 8 }"
          placeholder="请在此处写下您的评论..."
          style="margin-bottom: 10px;">
      </el-input>

      <!-- 上传图片-->
      <div>
        <label class="upload-button" for="upload">上传图片</label>
        <input type="file" ref="imgFile" @change="checkImg" id="upload">
      </div>

      <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogCommentVisible = false">取消</el-button>
        <el-button type="primary" @click="dialogCommentVisible = false;send()">
          发送
        </el-button>
      </span>
      </template>
    </el-dialog>


  </div>
</template>

<script setup>
import {ref, reactive} from 'vue'
import {useRoute} from 'vue-router'
import {ElMessage} from 'element-plus'
import {addComment} from "../../../api/hotel/comment";
import {addPicture} from "../../../api/hotel/comment";

// import type {UploadFile} from 'element-plus'

let dialogCommentVisible = ref(false)
const textarea1 = ref('')
//获取路由酒店id
const route = useRoute()
let hotelId = route.params.hotelId
console.log("articleId:", hotelId)

//上传图片
const imgFile = ref(null)

function checkImg() {
  const imgFormData = new FormData()
  for (let i = 0; i < imgFile.value.files.length; i++) {
    imgFormData.append("file", imgFile.value.files[i])
  }
  //调用请求发送数据
  addPicture(imgFormData).then((res) => {
    textarea1.value =
        `<p style="margin: 0;
height: auto;
    word-wrap:break-word;
    word-break:break-all;
overflow: hidden;
">${textarea1.value}<p style="display: inline-block;"><img src="${import.meta.env.VITE_APP_BASE_API}/picture/${res.data.pictureId}"
 style="width: 120px;height: 120px;margin-right: 10px;"></img>`
    console.log(textarea1.value)
  })
}

//发送评论
function send() {
  ElMessage('发送成功')
  const data = {
    commentType: 4,
    articleId: hotelId,
    content: textarea1.value,
  }
  addCommentTo(data)
}

//向后端添加评论
const emit = defineEmits(['getCommentData'])

function addCommentTo(data) {
  console.log(data)
  addComment(data).then((res) => {
    emit('getCommentData', res.data)
    // console.log(res.data)
  })
}


</script>

<style scoped>
.big-score {
  width: 100%;
  height: 150px;
  padding: 20px;
}

.left-text {
  padding-left: 40px;
  float: left;
  width: 30%;
  font-weight: bolder;
  font-size: 18px;
  position: relative;
}

.left-text span {
  margin-right: 10px;
  display: inline-block;
}

.special {
  font-size: 35px;
  position: absolute;
  color: #366AB3;
  top: 40px;
  right: 10px;
}

.star {
  float: left;
  /*width: 100%;*/
}

.star div {
  padding-left: 80px;
  height: auto;
  float: left;
  width: 300px;
}

.comment {
  float: right;
  padding-right: 40px;
  padding-top: 80px;
  font-size: 18px;
  color: #ff8800;
}

.comment span {
  display: inline-block;
  width: 100px;
  height: 50px;
  line-height: 50px;
  /*background-color: #1890ff;*/
  z-index: 2;
}

.upload-button {
  padding: 6px 25px;
  background: #409eff;
  border-radius: 4px;
  color: white;
  cursor: pointer;
}

input {
  display: none;
}

</style>
