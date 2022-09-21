<!--
 * @Author: ldx
 * @Date: 2021-03-03 11:28:46
 * @LastEditTime: 2021-04-26 17:07:52
 * @LastEditors: ldx
 * @Description: 审核tab基本布局
 * @FilePath: \MS-VUE\src\views\customer\modules\AuditProgress\modules\TabLayout.vue
-->
<template>
  <a-spin
    id="spin"
    :spinning="spinning"
    :tip="spinTip"
    style="height: 100%;width: 100%;"
  >
    <div
      class="center_wrap"
      ref="wrap"
      id="wrap"
    >
      <div
        class="up"
        ref="up"
        id="up"
        style="overflow: auto;"
      >
        <slot name="up"></slot>
      </div>
      <div
        class="midden"
        ref="resize"
        id="resize"
      >
      </div>
      <div
        class="down"
        ref="down"
        id="down"
      >
        <slot name="down"></slot>
      </div>
    </div>
  </a-spin>
</template>

<script>
export default {
  name: 'TabLayout',
  props: {
    spinTip: {
      type: String,
      default: '加载中...'
    },
    spinning: {
      type: Boolean,
      default: false
    }
  },
  mounted () {
    this.reSize()
  },
  data () {
    return {}
  },
  methods: {
    // 拖拽改变高度
    reSize () {
      const resize = this.$refs.resize
      const up = this.$refs.up
      const wrap = this.$refs.wrap
      resize.onmousedown = function (e) {
        const startY = e.clientY
        resize.top = resize.offsetTop
        document.onmousemove = function (e) {
          const endY = e.clientY
          let moveLen = resize.top + (endY - startY)
          const maxT = wrap.clientHeight - resize.offsetHeight
          if (moveLen < 180) moveLen = 180
          if (moveLen > maxT - 180) moveLen = maxT - 180
          resize.style.top = moveLen
          up.style.height = moveLen + 'px'
        }
        document.onmouseup = function (evt) {
          document.onmousemove = null
          document.onmouseup = null
          resize.releaseCapture && resize.releaseCapture()
        }
        resize.setCapture && resize.setCapture()
        return false
      }
    }
  }
}
</script>

<style
  lang="less"
  scoped
>
#spin /deep/ .ant-spin-container {
  height: 100%;
}
</style>
