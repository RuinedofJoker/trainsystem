import { createApp } from 'vue'

import Cookies from 'js-cookie'

import ElementPlus from 'element-plus'
import locale from 'element-plus/lib/locale/lang/zh-cn' // 中文语言

import '@/assets/styles/index.scss' // global css

import App from './App'
import store from './store'
import router from './router'
import directive from './directive' // directive

// 注册指令
import plugins from './plugins' // plugins
import { download } from '@/utils/request'

// svg图标
import 'virtual:svg-icons-register'
import SvgIcon from '@/components/SvgIcon'
import elementIcons from '@/components/SvgIcon/svgicon'

//swiper轮播图
import { register } from 'swiper/element/bundle';
register();


// // 分页组件
// import Pagination from '@/components/Pagination'
// // 自定义表格工具组件
// import RightToolbar from '@/components/RightToolbar'
// // 富文本组件
// import Editor from "@/components/Editor"
// // 文件上传组件
// import FileUpload from "@/components/FileUpload"
// // 图片上传组件
// import ImageUpload from "@/components/ImageUpload"
// // 图片预览组件
// import ImagePreview from "@/components/ImagePreview"
// // 自定义树选择组件
// import TreeSelect from '@/components/TreeSelect'
// // 字典标签组件
// import DictTag from '@/components/DictTag'
import websocket from '@/utils/websocket.js'
//文本编辑器
import { QuillEditor } from '@vueup/vue-quill'
import '@vueup/vue-quill/dist/vue-quill.snow.css';

const app = createApp(App)

app.provide('websocket', websocket)

app.use(router)
app.use(store)
app.use(plugins)
app.use(elementIcons)
app.component('svg-icon', SvgIcon)
app.component('QuillEditor',QuillEditor)

directive(app)

// 使用element-plus 并且设置全局的大小
app.use(ElementPlus, {
  locale: locale,
  // 支持 large、default、small
  size: Cookies.get('size') || 'default'
})

app.mount('#app')
