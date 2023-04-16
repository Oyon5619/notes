<template>
  <div class="w-editor">
    <div ref="editor" class="textNeirong">
    </div>
  </div>
</template>
 
<script>
import E from 'wangeditor'
import { uploadFileApi } from '@/api/imgApi'  //上传图片和视频的地址，根据后端给的地址确定
 
export default {
  name: 'wEditor',
  data() {
    return {
      editor: null,
      info_: null,
    }
  },
  model: {
    prop: 'value',
    event: 'change'
  },
  props: {
    value: {
      type: String,
      default: ''
    },
    isClear: {
      type: Boolean,
      default: false
    }
  },
  watch: {
    isClear(val) {
      // 触发清除文本域内容
      if (val) {
        this.editor.txt.clear()
        this.info_ = null
      }
    },
    value: function (value) {
      if (value !== this.editor.txt.html()) {
        this.editor.txt.html(this.value)
      }
    }
  },
  created() {
 
  },
  mounted() {
    this.setEditor()
    this.editor.txt.html(this.value)
  },
  methods: {
    setEditor() {
      this.editor = new E(this.$refs.editor)

      this.editor.config.height = 500  //富文本编辑器的高度
      this.editor.config.uploadImgShowBase64 = false // base 64 存储图片
      this.editor.config.uploadImgServer = 'http://localhost:8080/file/upload'// 填写配置服务器端地址
      this.editor.config.uploadImgHeaders = {
        'Accept': 'application/json, text/plain, */*',
        'Access-Control-Allow-Origin': '*'
      } // 自定义 header
      this.editor.config.uploadFileName = 'imgFile' // 后端接受上传文件的参数名
      this.editor.config.uploadImgMaxSize = 4 * 1024 * 1024 // 将图片大小限制为 4M
      this.editor.config.uploadImgMaxLength = 6 // 限制一次最多上传 6 张图片
 
      this.editor.config.onchange = (html) => {
        this.info_ = html // 绑定当前逐渐地值
        this.$emit('change', this.info_) // 将内容同步到父组件中
      }
 
      this.editor.config.uploadImgHooks = {
        fail: (xhr, editor, result) => {
          // 插入图片失败回调
          console.log(xhr)
          console.log(result)
        },
        success: (xhr, editor, result) => {
          // 图片上传成功回调
          console.log(xhr)
          console.log(result)
        },
        timeout: (xhr, editor) => {
          // 网络超时的回调
          console.log(xhr)
 
        },
        error: (xhr, editor) => {
          // 图片上传错误的回调
          console.log(xhr)
        },
        customInsert: (insertImg, result, editor) => {
          console.log(result)
          var url = result.url
          insertImg(url)
        }
      }

      // 创建富文本编辑器
      this.editor.create()
    }
  }
}
</script>
 
<style>
.w-editor {
  width: 100%;
  margin: auto;
  position: relative;
}
</style>
 
 