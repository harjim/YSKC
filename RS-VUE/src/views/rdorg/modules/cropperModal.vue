<!--
 * @Author: your name
 * @Date: 2022-03-25 15:42:57
 * @LastEditors: lzh
 * @LastEditTime: 2022-04-07 09:20:52
 * @Description: In User Settings Edit
 * @FilePath: \RS-VUE\src\views\rdorg\modules\cropperModal.vue
-->
<template>
  <div class="cropper-modal" v-if="visible">
    <vue-cropper
      class="cropper"
      ref="cropper"
      :img="option.img"
      :output-size="option.size"
      :output-type="option.outputType"
      :info-true="option.infoTrue"
      :full="option.full"
      :can-move="option.canMove"
      :can-move-box="option.canMoveBox"
      :fixed-box="option.fixedBox"
      :original="option.original"
      :auto-crop="option.autoCrop"
      :auto-crop-width="option.autoCropWidth"
      :auto-crop-height="option.autoCropHeight"
      :center-box="option.centerBox"
      :high="option.high"
      :mode="option.mode"
      :canScale="option.canScale"
    >
    </vue-cropper>
    <div class="actions" ref="actions">
      <span style="padding-right: 4px;">截图控制台</span>
      <a-button size="small" @click="onCancel">取消</a-button>
      <a-button style="margin-left: 8px" size="small" type="primary" @click="onOK" :loading="confirmloading">确定</a-button>
    </div>
  </div>
</template>

<script>
import { VueCropper } from 'vue-cropper'
import yearMiXin from '@/utils/yearMixin'
import domToImage from 'dom-to-image'

export default {
  components: {
    VueCropper
  },
  mixins: [yearMiXin],
  data () {
    return {
      visible: false,
      confirmloading: false,
      option: {
        // 裁剪图片的地址
        img: '',
        // 裁剪生成图片的质量
        size: 1,
        // 输出原图比例截图 props名full
        full: true,
        // 裁剪生成图片的格式
        outputType: 'png',
        // 上传图片是否可以移动
        canMove: false,
        // 固定截图框大小 不允许改变
        fixedBox: false,
        // 上传图片按照原始比例渲染
        original: false,
        // 截图框能否拖动
        canMoveBox: true,
        // 是否默认生成截图框
        autoCrop: false,
        // 只有自动截图开启 宽度高度才生效
        // 默认生成截图框宽度
        autoCropWidth: 0,
        // 默认生成截图框高度
        autoCropHeight: 0,
        // 截图框是否被限制在图片里面
        centerBox: false,
        // 是否按照设备的dpr 输出等比例图片
        high: false,
        // mode  图片默认渲染方式
        mode: 'contain',
        canScale: false
      }
    }
  },
  methods: {
    show () {
      domToImage.toBlob(document.querySelector('.tree'), {
        bgcolor: '#fff'
      }).then(res => {
        this.option.img = window.URL.createObjectURL(res)
        this.visible = true
        this.$emit('updateLoading')
        this.$nextTick(() => {
          this.$refs.cropper.startCrop()
        })
      })
    },
    onOK () {
      this.uploadOrgImg(true)
    },
    onCancel () {
      this.$refs.cropper.clearCrop()
      this.visible = false
    },
    uploadOrgImg (isShowMessage) {
      this.$refs.cropper.getCropBlob(blob => {
        const param = new FormData()
        param.append('file', blob)
        param.append('beginYear', this.currentYear)
        const config = {
          // 添加请求头
          headers: { 'Content-Type': 'multipart/form-data' }
        }
        this.confirmloading = true
        this.$http.post('/projectDocFileData/uploadOrgImg', param, config).then((res) => {
          if (res.data && res.success) {
            if (isShowMessage) {
              this.$message.success(`组织架构图片生成成功`)
              this.$refs.cropper.clearCrop()
              this.visible = false
            }
          } else {
            console.error(`uploadOrgImg functin error : ${res.errorMessage}`)
            this.$message.error(res.errorMessage)
          }
        }).catch((error) => {
          this.$message.error(error.message)
          console.error(`uploadOrgImg functin error : ${error.message}`)
        }).finally((res) => {
          this.confirmloading = false
          if (isShowMessage) {
            this.$emit('onBtnLoading', false)
          }
        })
      })
    }
  }
}
</script>

<style lang="less" scoped>
.cropper-modal {
  width: 100vw;
  height: 100vh;
  position: fixed;
  z-index: 100;
  top: 0;
  left: 0;
  .actions {
    background-color: #FFF;
    position: absolute;
    right: 0;
    top: 0;
    margin: 4px 0;
    padding: 4px;
    border-radius: 4px;
  }
}
</style>
