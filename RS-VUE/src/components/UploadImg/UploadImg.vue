<template>
  <div class="clearfix">
    <a-upload
      listType="picture-card"
      :fileList="fileList"
      @preview="handlePreview"
      @change="handleChange"
      :beforeUpload="(file)=>beforeUpload(file,rowKey)"
    >
      <!-- :beforeUpload="(file)=>beforeUpload(file,rowKey)" -->
      <!-- action="https://www.mocky.io/v2/5cc8019d300000980a055e76" -->
      <div v-if="fileList.length < maxLength">
        <a-icon type="plus" />
        <div class="ant-upload-text">上传图片</div>
      </div>
    </a-upload>
    <a-modal :visible="previewVisible" :footer="null" @cancel="handleCancel">
      <img alt="example" style="width: 100%" :src="previewImage" />
    </a-modal>
  </div>
</template>
<script>
function getBase64 (file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.readAsDataURL(file)
    reader.onload = () => resolve(reader.result)
    reader.onerror = error => reject(error)
  })
}
export default {
  props: {
    imgs: {
      type: Array,
      default: () => []
    },
    rowKey: {
      type: String,
      required: true
    },
    projectId: {
      type: Number,
      required: true
    },
    maxLength: {
      type: Number,
      default: 8
    }
  },
  data () {
    return {
      previewVisible: false,
      previewImage: '',
      fileList: []
    }
  },
  watch: {
    imgs (img) {
      this.loadImg(img)
    }
  },
  created () {
    this.loadImg(this.imgs)
  },
  methods: {
    handleCancel () {
      this.previewVisible = false
    },
    async handlePreview (file) {
      if (!file.url && !file.preview) {
        file.preview = await getBase64(file.originFileObj)
      }
      this.previewImage = file.url || file.preview
      this.previewVisible = true
    },
    handleChange (file) {
      if (file.file.status === 'removed') {
        this.fileList = file.fileList
        this.$emit('ok', this.fileList, this.rowKey)
      }
    },
    loadImg (img) {
      if (img) {
        this.fileList = img
        this.fileList.forEach(image => {
          if (image.url && image.url.indexOf('/static/images/project/') === -1) {
            image.url = '/static' + image.url
          }
        })
      } else {
        this.fileList = []
      }
    },
    uploadImgs (file, rowKey) {
      const param = new FormData()
      param.append('file', file)
      param.append('projectId', this.projectId)
      param.append('key', rowKey)
      const config = {
        // 添加请求头
        headers: { 'Content-Type': 'multipart/form-data' }
      }
      this.$http.post('/import/importImages', param, config).then(res => {
        if (res.success) {
          this.fileList = [...this.fileList, {
            uid: res.data.newFileName,
            name: res.data.newFileName,
            status: 'done',
            url: res.data.filePath
          }]
          this.loadImg(this.fileList)
          this.$emit('ok', this.fileList, rowKey)
        }
      }).catch(res => {
      })
    },
    beforeUpload (file, key) {
      this.uploadImgs(file, key)
      return false
    }
  }
}
</script>
<style>
/* you can make up upload button and sample style by using stylesheets */
.ant-upload-select-picture-card i {
  font-size: 32px;
  color: #999;
}

.ant-upload-select-picture-card .ant-upload-text {
  margin-top: 8px;
  color: #666;
}
</style>
