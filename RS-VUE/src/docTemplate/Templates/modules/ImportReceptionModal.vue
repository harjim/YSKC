<!--
 * @Author: ldx
 * @Date: 2020-12-12 09:27:35
 * @LastEditTime: 2021-02-03 17:29:01
 * @LastEditors: ldx
 * @Description:
 * @FilePath: \RS-VUE\src\docTemplate\Templates\modules\ImportReceptionModal.vue
-->
<template>
  <a-modal
    :zIndex="99999"
    :width="400"
    :visible="visible"
    title="导入试验试制通知单接收部门"
    @ok="handleSubmit"
    @cancel="visible = false"
    :maskClosable="false"
    :getContainer="getContainer"
    :confirmLoading="confirmLoading">
    <template v-if="dataArr.length">
      <a-radio-group v-model="chooseValue">
        <a-radio v-for="(item,index) in dataArr" :key="index" :value="index">
          {{ `${stageMap[item.stageKey]}-${item.docFileName}` }}
        </a-radio>
      </a-radio-group>
    </template>
    <template v-else>
      当前不存在其他试验试制通知单
    </template>
  </a-modal>
</template>
<script>
import { popupContainer, domName } from '@/docTemplate/Templates/js/screenFullMountDom'
const stageMap = {
  '100': '规划阶段',
  '200': '研究阶段',
  '300': '设计开发',
  '400': '设计验证',
  '500': '实验验证',
  '600': '试验证',
  '700': '试制',
  '800': '项目验收',
  '900': '项目收尾' }
export default {
  data () {
    return {
      stageMap,
      domName,
      visible: false,
      confirmLoading: false,
      chooseValue: undefined,
      projectId: undefined,
      id: undefined,
      dataArr: []

    }
  },
  methods: {
    open (id, projectId) {
      this.id = id
      this.projectId = projectId
      this.loadData()
      this.chooseValue = undefined
      this.visible = true
    },
    getContainer () {
      return popupContainer(this.domName)
    },
    handleSubmit () {
      this.confirmLoading = true
      if (this.chooseValue || this.chooseValue === 0) {
        const temp = JSON.parse(this.dataArr[this.chooseValue].fileData)
        if (temp && temp.receiveList && temp.receiveList.length) {
          this.$emit('ok', temp.receiveList)
        }
      }
      this.confirmLoading = false
      this.visible = false
    },
    loadData () {
      this.$http.get('/projectDocFile/getReception', { params: { projectId: this.projectId, id: this.id } })
        .then(res => {
          if (res.success && res.data) {
            this.dataArr = res.data
          }
        })
    }
  }
}
</script>
