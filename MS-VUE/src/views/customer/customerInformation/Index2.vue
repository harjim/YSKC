<!--
 * @Author: ldx
 * @Date: 2020-12-04 11:41:25
 * @LastEditTime: 2020-12-04 14:33:04
 * @LastEditors: ldx
 * @Description:
 * @FilePath: \MS-VUE\src\views\customer\customerInformation\index.vue
-->
<template>
  <a-modal
    :title="title"
    style="top: 20px;"
    :width="800"
    v-model="visible"
    @ok="handleSubmit"
    :maskClosable="false"
  >
    <a-anchor :affix="true" :getContainer="() => this.$refs.modalWrapper">
      <a-anchor-link href="#components-anchor-demo-basic" title="Basic demo" />
      <a-anchor-link href="#components-anchor-demo-static" title="Static demo" />
      <a-anchor-link
        href="#components-anchor-demo-basic"
        title="Basic demo with Target"
        target="_blank"
      />
      <a-anchor-link href="#API" title="API">
        <a-anchor-link href="#Anchor-Props" title="Anchor Props" />
        <a-anchor-link href="#Link-Props" title="Link Props" />
      </a-anchor-link>
    </a-anchor>
    <div ref="modalWrapper" style="height: 500px;overflow:auto">
      <p v-for="i in 100" :key="i">
        {{ i }}
        <a v-if="i==1" id="components-anchor-demo-basic">components-anchor-demo-static</a>
        <a v-if="i==50" id="components-anchor-demo-static">components-anchor-demo-static</a>
      </p>
    </div>
  </a-modal>
</template>

<script>
import moment from 'moment'

export default {
  name: 'Index',
  components: {
  },
  computed: {
    isHightfun: function () {
      return this.isHight
    },
    isHasfun: function () {
      return this.isHas
    }
  },
  data () {
    return {
      title: '12123',
      visible: false
    }
  },

  methods: {
    moment,
    show () {
      this.visible = true
    },
    handleSubmit () {}
  }
}
</script>
<style lang="less" scoped>
</style>
