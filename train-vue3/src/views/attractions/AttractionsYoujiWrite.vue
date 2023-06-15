<template>
    <div style="width:1000px;margin:0 auto">
        <div class="tab">        
            <el-button class="btnReturn" @click="jumpReturn"><el-icon><ArrowLeftBold /></el-icon></el-button>
            <h3>发布你的游记</h3>
            <el-alert title="发布成功" type="success" v-show="isPublish" />
        </div>
       <div class="blog" >
            <h3>游记标题:</h3>
            <el-input v-model="essay.essayTitle" class="inputTitle" placeholder="请输入游记标题"></el-input>
            <h3>  游记内容：</h3>
            <QuillEditor v-model:content="content" :options="editorOption" contentType="html" class="quill"/>
       </div>
     
       <div class="tags">
            <div class="tags-item">
                <span>游记地点<el-icon class="icon"><LocationInformation /></el-icon></span>
                <el-input v-model="checkedAttractions.attractionsName" 
                          @click="getAttractionsList"
                          @change="getAttractionsList" 
                          @blur="onLeave" 
                          style="width:600px;height:40px;margin-left: 60px;">
                </el-input>
            </div>
            <div v-if="isShow" style="margin-left: 160px;">
                <div v-for="attractions in attractionsList" 
                     :key="attractions.attractionsId"
                     @click="checkAttractions(attractions)"
                      @blur="onLeave" >
                     {{ attractions.attractionsName }}
                </div>
            </div>

            <div class="tags-item" style="margin-top: 30px;">
                <span>添加封面照片<el-icon class="icon"><PictureRounded /></el-icon></span>
                <input type="file" @change="getImg"  ref="imgDom" style="margin-left: 30px;"/>
                <br>
                <br>
                <img v-if="coverImgSrc != ''" :src="coverImgSrc" alt="Preview" class="coverImg">
            </div>

            <div class="tags-item">
                <span>文章摘要<el-icon class="icon"><Tickets /></el-icon></span>
                <el-input v-model="essay.summary" 
                          placeholder="摘要：会在推荐、列表等场景外露，帮助读者快读了解内容" 
                          class="inputSummary"
                          maxlength="256"
                          show-word-limit
                          type="textarea"></el-input>
            </div>
       </div>
       <div class="btn">
        <el-button @click="store">保存游记</el-button>
       <el-button @click="submit">提交游记</el-button>
     
       </div>
    </div>
</template>

<script setup>
import { reactive, ref, toRef } from 'vue'
import router from '../../router'
import { getToken } from '@/utils/auth'
import { addEssay,updateEssayContent,getAttractions,publishEssay,addPicture} from '@/api/city/index.js'
//编辑界面依赖的导入
import {QuillEditor, Quill } from '@vueup/vue-quill' //调用编辑器
import { container, ImageExtend, QuillWatch } from 'quill-image-extend-module'
import quillTool from '@/utils/quillTool'
Quill.register(quillTool, true)
Quill.register('modules/ImageExtend', ImageExtend)
import 'quill/dist/quill.core.css'
import 'quill/dist/quill.snow.css'
import 'quill/dist/quill.bubble.css'


const input = ref('')
const textarea = ref('')
const imgDom = ref(null); //获取img虚拟Dom
const coverImgSrc = ref("")
const essay = reactive({
    content: ""
})
const content = toRef(essay, "content")
const isShow = ref(false)

const attractionsList = ref([])
const checkedAttractions = reactive({attractionsName: ""})
const serviceIp = import.meta.env.VITE_APP_BASE_API


function checkAttractions(attractions) {
    essay.placeId = attractions.attractionsId
    checkedAttractions.attractionsName = attractions.attractionsName
}


const toolbarOptions = reactive([
      ['bold', 'italic', 'underline', 'strike'], // 加粗 斜体 下划线 删除线
      ["blockquote", "code-block"], // 引用
      [{ list: 'ordered' }, { list: 'bullet' }], // 有序、无序列表
      [{ script: "sub" }, { script: "super" }], // 上标/下标
      [{ indent: '-1' }, { indent: '+1' }], // 缩进
      [{ direction: 'rtl' }], // 文本方向
      [{ size: ['small', false, 'large', 'huge'] }], // 字体大小
      [{ header: [1, 2, 3, 4, 5, 6, false] }], // 标题
      [{ color: [] }, { background: [] }], // 字体颜色、字体背景颜色
      [{ align: [] }], // 对齐方式
      ['clean'], // 清除文本格式
      [ 'image'] // 链接、图片、视频
  ])

const editorOption = ref({
                  theme: 'snow',
                  placeholder: '旅行中有哪些亮点？哪些发现？给迷茫的游友门指条明路吧~',
                  modules: {
                      ImageExtend: {
                          name: 'file', // 参数名
                          action: serviceIp + '/attractions/essay/picture', // 服务器地址，如果为空则采用base64插入图片
                          headers: xhr => { // 设置请求头参数（选填）
                              xhr.setRequestHeader('Authorization', 'Bearer ' + getToken())
                          },
                          response: res => {  //响应
                              return `${serviceIp}/picture/${res.data.pictureId}`
                          },
                          size: 8, // 图片不能超过8M
                          sizeError: () => {
                              this.$message.error('粘贴图片大小不能超过8MB!')
                          }
                      },
                      toolbar: {
                          container: toolbarOptions,
                          handlers: {
                              image: function(value) {
                                  QuillWatch.emit(this.quill.id)
                              },
                          }
                      }
                  }
              })

//使用token判断是否登录
const token=getToken()
if(token === undefined || token === ""){
  router.push({
    path:'/login'
  })
}


//发表游记,启动页面进行
function postEssay(){
    addEssay().then((res)=>{
        //把res.data存到essay里面
        copyEssayField(res.data, essay)
    })
}
postEssay()
//把获取到的数据存到目标对象里面
function copyEssayField(src, target) {
    target.essayId = src.essayId;
    target.essayTitle = src.essayTitle;
    target.content = src.content;
    target.coverPictureId = src.coverPictureId;
    target.summary = src.summary;
    target.date = src.date;
    target.placeId = src.placeId;
    target.placeType = src.placeType;
    target.isPublished = src.isPublished;
}
function jumpReturn(){
    router.push({
        path:'/index/attractions',
    })
}

//获取图片
function getImg(event){
    //通过formData发送数据
    const formData = new FormData()
    formData.append("file", imgDom.value.files[0])
    //调接口发给后端
    addPicture(formData).then(res => {
        essay.coverPictureId = res.data.pictureId
        coverImgSrc.value = serviceIp + "/picture/" + res.data.pictureId
    })  
}
function store(){
     updateEssayContent(essay).then(res => {
        copyEssayFromRes(res)
         console.log(essay);
     })
}
const isPublish = ref(false)

//提交游记
function submit(){
    store();
    publishEssay(essay.essayId).then(res => {
        if(res.msg === "发布成功") {
            isPublish.value = true
            //跳路由
            router.replace({
                path:'/index/attractions',
            })
        }
    })

}

//节流；防抖
let timer
function getAttractionsList(){
    isShow.value = true;
    clearTimeout(timer)
    timer = setTimeout(function(){
        getAttractions(10,checkedAttractions.attractionsName).then((res)=>{
            attractionsList.value.splice(0, attractionsList.value.length)
            for(let i = 0; i < res.data.length; i++) {
                attractionsList.value.push(res.data[i])
            }
        })
    }, 300)
}
function onLeave(){
    //  isShow.value = false;
}
//把返回的数据存到essay里
const copyEssayFromRes = function(res) {
    essay.articleId = res.data.articleId;
    essay.content = res.data.content;
    essay.coverPictureId= res.data.coverPictureId;
    essay.date= res.data.date;
    essay.essayId= res.data.essayId;
    essay.essayTitle= res.data.essayTitle;
    essay.isEffective= res.data.isEffective;
    essay.isPublished= res.data.isPublished;
    essay.lookNum= res.data.lookNum;
    essay.placeId= res.data.placeId;
    essay.placeType= res.data.placeType;
    essay.summary= res.data.summary;
    essay.userId= res.data.userId;
}
</script>

<style >

.tab{
    width: 1000px;
    height: 80px;
}
.blog{
    width: 1000px;
    height: 300px;
}
.tags{
    height: auto;
    position: relative;
    top: 200px;
}
.btn{
    height: 100px;
    position: relative;
    top: 300px;
    right: -350px;
}
/* 头部样式 */
.btnReturn{
    display: inline-block;
    background-color: rgb(248, 248, 248);
    border: 0px;
}
.tab h3{
    display: inline-block;
    position: relative;
    right: -10px;
    top: 3px;
}
/* blog部分样式 */
.inputTitle{
    position: relative;
    width: 500px;
    top: -40px;
    right: -90px;
}
.quill img{
    width: 400px;
    height: 400px;
}
/* 标签部分样式 */
.tags-item{
    margin-top: 10px;
    height: auto;
}
.icon{
    position: relative;top:3px;
}
.inputSummary{
    width: 800px;
    position: relative;
    right: -60px;
    top: 35px;
}
.coverImg{
    width: 400px;
    height: 300px;
}

</style>