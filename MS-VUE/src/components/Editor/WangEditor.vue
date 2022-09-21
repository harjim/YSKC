<template>
  <div :class="prefixCls">
    <div ref="editor" class="editor-wrapper"></div>
  </div>
</template>

<script>
import WEditor from 'wangeditor'
export default {
  name: 'WangEditor',
  props: {
    prefixCls: {
      type: String,
      default: 'ant-editor-wang'
    },
    // eslint-disable-next-line
    value: {
      type: String
    },
    // 路径，默认放到temp目录下
    dir: {
      type: String,
      default: '/temp/'
    },
    placeholder: {
      type: String,
      default: '请输入内容'
    }
  },
  data () {
    return {
      editor: null,
      editorContent: null
    }
  },
  watch: {
    value (newVal) {
      // editorContent和新值相同,表示是通过change事件改变的,则不需要重新赋值编辑器. 重新赋值会带来编辑器的焦点问题
      if (this.editorContent !== newVal) {
        this.editorContent = newVal || ''
        this.editor.txt.html(this.editorContent)
      }
    }
  },
  mounted () {
    this.initEditor()
    if (this.value) {
      this.editor.txt.html(this.value)
    }
  },
  methods: {
    initEditor () {
      this.editor = new WEditor(this.$refs.editor)
      this.editor.config.customUploadImg = (resultFiles, insertImgFn) => {
        const param = new FormData()
        param.append('file', resultFiles[0])
        param.append('dir', this.dir)
        param.append('key', 'wangeditor') // 没有实质的用处接口要求传 2021-02-19 询问过
        const config = {
        // 添加请求头
          headers: { 'Content-Type': 'multipart/form-data' }
        }
        this.$http.post('/import/importImages', param, config).then(res => {
          if (res.success) {
            insertImgFn(res.data.filePath)
          }
        }).catch(res => { })
      }
      this.editor.config.zIndex = 11
      this.editor.config.uploadImgAccept = ['jpg', 'jpeg', 'png', 'gif', 'bmp']
      this.editor.config.onchange = (html) => {
        this.editorContent = html.replace(/<img([^>]*)>/gi, '<img$1 />')
          .replace(/<br([^>]*)>/gi, '<br/>')
        if (this.editorContent === '' && this.value === undefined) {
          return
        }
        this.$emit('change', this.editorContent)
        this.$emit('input', this.editorContent)
      }
      this.editor.config.showFullScreen = false
      this.editor.config.placeholder = this.placeholder
      this.editor.config.pasteFilterStyle = true
      this.editor.config.pasteTextHandle = function (content) {
      // content 即粘贴过来的内容（html 或 纯文本），可进行自定义处理然后返回
        if (content === '' && !content) return ''
        var str = content
        str = str.replace(/<xml>[\s\S]*?<\/xml>/ig, '')
        str = str.replace(/<style>[\s\S]*?<\/style>/ig, '')
        str = str.replace(/<\/?[^>]*>/g, '')
        str = str.replace(/[ | ]*\n/g, '\n')
        str = str.replace(/&nbsp;/ig, '')
        return str
      }
      this.editor.config.menus = [
        'image', 'head', // 标题
        'bold', // 粗体
        'fontSize', // 字号
        'fontName', // 字体
        'italic', // 斜体
        'underline', // 下划线
        'strikeThrough', // 删除线
        'foreColor', // 文字颜色
        'backColor', // 背景颜色
        'link', // 插入链接
        'list', // 列表
        'justify', // 对齐方式
        'table', // 表格
        'undo', // 撤销
        'redo' // 重复
      ]
      this.editor.create()
    }
  }
}
</script>

<style lang="less" scoped>
.ant-editor-wang {
  .editor-wrapper {
    text-align: left;
  }
  // & /deep/ .w-e-text-container {
	// 		// 文本框里面的层级调低
	// 		z-index: 11 !important;
	// 	}
  // & /deep/ .w-e-menu {
  //     z-index: 12 !important;
  // }
  // & /deep/ .w-e-toolbar {
	// 		// 给工具栏换行
  //     z-index: 11 !important;
	// 		flex-wrap: wrap;
	// }
}
</style>
