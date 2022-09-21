<!--
 * @Author:
 * @Date: 2020-11-25 16:36:09
 * @LastEditTime: 2022-03-29 11:31:45
 * @LastEditors: lzh
 * @Description: 组织架构
 * @FilePath: \RS-VUE\src\views\rdorg\Arch.vue
-->
<template>
  <a-card style="height: 100%" :bodyStyle="{ height: '100%', overflow: 'auto'}">
    <a-tabs v-model="actvieKey" @change="callback" id="tabs">
      <template slot="tabBarExtraContent">
        <a-button
          v-if="actvieKey == '1' && $auth('rdorg:arch:rd')"
          type="primary"
          @click="onSaveImage"
          :loading="btnLoading"
        >
          生成组织架构图片
        </a-button>
        <a-button style="margin-left: 8px" v-if="actvieKey == '1' && $auth('rdorg:arch:rd')" type="primary" :loading="cropperBtnLoading" @click="handleCropper">截图</a-button>
      </template>
      <a-tab-pane :tab="`研发架构`" key="1" v-if="$auth('rdorg:arch:rd')">
        <architecture ref="architectureTab" @onBtnLoading="onBtnLoading"></architecture>
      </a-tab-pane>
      <a-tab-pane :tab="`技术中心简介`" key="2" v-if="$auth('rdorg:arch:techDesc')">
        <electrodeionization ref="electrodeionizationTab"></electrodeionization>
      </a-tab-pane>
    </a-tabs>
    <cropperModal ref="cropperModal" @updateLoading="cropperBtnLoading=false" />
  </a-card>
</template>

<script>
import Architecture from './Architecture'
import Electrodeionization from './Electrodeionization'
import cropperModal from './modules/cropperModal.vue'

export default {
  components: {
    Architecture,
    Electrodeionization,
    cropperModal
  },
  data () {
    return {
      title: '',
      id: 0,
      projectData: {},
      visible: false,
      actvieKey: undefined,
      btnLoading: false,
      cropperBtnLoading: false
    }
  },
  created () {
    if (this.$auth('rdorg:arch:rd')) {
      this.actvieKey = '1'
      return
    }
    if (this.$auth('rdorg:arch:techDesc')) {
      this.actvieKey = '2'
    }
  },
  methods: {
    callback (activeKey) { // 切换tab调用
      this.actvieKey = activeKey
    },
    onSaveImage () {
      if (this.$refs.architectureTab && this.$refs.architectureTab.onSaveImage) {
        this.btnLoading = true
        this.$refs.architectureTab.onSaveImage(true)
      }
    },
    onBtnLoading (status) {
      this.btnLoading = status
    },
    handleCropper () {
      this.cropperBtnLoading = true
      this.$refs.cropperModal.show()
    }
  }
}
</script>

<style lang="less" scoped>
//parentHeight
@FH: 100%;
#tabs /deep/ .ant-tabs-content {
  height: @FH;
}
#tabs /deep/ .ant-tabs-tabpane-active {
  height: calc(@FH - 60px);
  overflow: auto;
}
#tabs {
  height: @FH;
}
</style>
