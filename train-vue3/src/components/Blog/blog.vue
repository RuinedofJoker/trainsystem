<template>
  <div>
    <div>
      <QuillEditor
        v-model:content="content"
        :options="editorOption"
        contentType="html"
      />
    </div>
  </div>
</template>
  
  <script setup>

import { QuillEditor, Quill } from "@vueup/vue-quill"; //调用编辑器
import { container, ImageExtend, QuillWatch } from "quill-image-extend-module";
import quillTool from "@/utils/quillTool";
import { getToken } from "@/utils/auth";
Quill.register(quillTool, true);
Quill.register("modules/ImageExtend", ImageExtend);

import "quill/dist/quill.core.css";
import "quill/dist/quill.snow.css";
import "quill/dist/quill.bubble.css";
import { ref , defineEmits , watch } from "vue"

const serviceIp = import.meta.env.VITE_APP_BASE_API;

const emit = defineEmits(["updateModel"]);
const content = ref("");
// 工具栏配置
const toolbarOptions = [
  ["bold", "italic", "underline", "strike"], // 加粗 斜体 下划线 删除线
  ["blockquote", "code-block"], // 引用
  [{ list: "ordered" }, { list: "bullet" }], // 有序、无序列表
  [{ script: "sub" }, { script: "super" }], // 上标/下标
  [{ indent: "-1" }, { indent: "+1" }], // 缩进
  [{ direction: "rtl" }], // 文本方向
  [{ size: ["small", false, "large", "huge"] }], // 字体大小
  [{ header: [1, 2, 3, 4, 5, 6, false] }], // 标题
  [{ color: [] }, { background: [] }], // 字体颜色、字体背景颜色
  [{ font: [] }], // 字体种类
  [{ align: [] }], // 对齐方式
  ["clean"], // 清除文本格式
  [ "image"], // 链接、图片、视频
];

const editorOption = ref({
  theme: "snow",
  placeholder: "旅行中有哪些亮点？哪些发现？给迷茫的游友门指条明路吧~",
  modules: {
    ImageExtend: {
      name: "file", // 参数名
      action: serviceIp + "/comment/picture", // 服务器地址，如果为空则采用base64插入图片
      headers: (xhr) => {
        // 设置请求头参数（选填）
        xhr.setRequestHeader("Authorization", "Bearer " + getToken());
      },
      response: (res) => {
        //响应
        return `${serviceIp}/picture/${res.data.pictureId}`; //返回的是图片的地址
      },
      size: 8, // 图片不能超过8M
      sizeError: () => {
        this.$message.error("粘贴图片大小不能超过8MB!");
      },
    },
    toolbar: {
      container: toolbarOptions,
      handlers: {
        image: function (value) {
          QuillWatch.emit(this.quill.id);
        },
      },
    },
  },
});

watch(content, (newValue, oldValue) => {
    emit('updateModel', content.value)
})
</script>
  
  <style>
/* vue-quill设计 */
.ql-container {
  height: 300px;
  line-height: normal;
  width: auto;
}

span.ql-size {
  max-width: 80px !important;
}

.ql-tooltip[data-mode="link"]::before {
  content: "请输入链接地址:";
}

.ql-tooltip.ql-editing a.ql-action::after {
  border-right: 0px;
  content: "保存";
  padding-right: 0px;
}

.ql-tooltip[data-mode="video"] {
  left: 0 !important;
}

.ql-tooltip[data-mode="video"]::before {
  content: "请输入视频地址:";
}

.ql-picker.ql-size .ql-picker-label::before,
.ql-picker.ql-size .ql-picker-item::before {
  content: "14px";
}

.ql-picker.ql-size .ql-picker-label[data-value="small"]::before,
.ql-picker.ql-size .ql-picker-item[data-value="small"]::before {
  content: "10px";
}

.ql-picker.ql-size .ql-picker-label[data-value="large"]::before,
.ql-picker.ql-size .ql-picker-item[data-value="large"]::before {
  content: "18px";
}

.ql-picker.ql-size .ql-picker-label[data-value="huge"]::before,
.ql-picker.ql-size .ql-picker-item[data-value="huge"]::before {
  content: "32px";
}

.ql-picker.ql-header .ql-picker-label::before,
.ql-picker.ql-header .ql-picker-item::before {
  content: "文本";
}

.ql-picker.ql-header .ql-picker-label[data-value="1"]::before,
.ql-picker.ql-header .ql-picker-item[data-value="1"]::before {
  content: "标题1";
}

.ql-picker.ql-header .ql-picker-label[data-value="2"]::before,
.ql-picker.ql-header .ql-picker-item[data-value="2"]::before {
  content: "标题2";
}

.ql-picker.ql-header .ql-picker-label[data-value="3"]::before,
.ql-picker.ql-header .ql-picker-item[data-value="3"]::before {
  content: "标题3";
}

.ql-picker.ql-header .ql-picker-label[data-value="4"]::before,
.ql-picker.ql-header .ql-picker-item[data-value="4"]::before {
  content: "标题4";
}

.ql-picker.ql-header .ql-picker-label[data-value="5"]::before,
.ql-picker.ql-header .ql-picker-item[data-value="5"]::before {
  content: "标题5";
}

.ql-picker.ql-header .ql-picker-label[data-value="6"]::before,
.ql-picker.ql-header .ql-picker-item[data-value="6"]::before {
  content: "标题6";
}

.ql-picker.ql-font .ql-picker-label::before,
.ql-picker.ql-font .ql-picker-item::before {
  content: "标准字体";
}

.ql-picker.ql-font .ql-picker-label[data-value="serif"]::before,
.ql-picker.ql-font .ql-picker-item[data-value="serif"]::before {
  content: "衬线字体";
}

.ql-picker.ql-font .ql-picker-label[data-value="monospace"]::before,
.ql-picker.ql-font .ql-picker-item[data-value="monospace"]::before {
  content: "等宽字体";
}
</style>
  