<!--
 * @Author: ldx
 * @Date: 2021-01-23 09:11:24
 * @LastEditTime: 2022-02-25 13:38:01
 * @LastEditors: zdf
 * @Description:
 * @FilePath: \MS-VUE\src\views\customer\modules\AuditProgress\modules\FileItem.vue
-->
<template>
  <div>
    <div
      class="row_wrap"
      :title="currentItem.isTitle ? stageStr(currentItem, currentItem.stageType) : currentItem.docFileName"
      :class="{ 'active': currentObj.id === currentItem.id && !currentItem.isTitle }"
    >
      <a-icon style="margin-right: 2px; align-self: center;" v-if="currentItem.expand !== undefined" :type="currentItem.expand ? 'caret-down' : 'caret-right'" @click="() => { currentItem.expand = !currentItem.expand }"/>
      <span v-else>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
      <template v-if="currentItem.isTitle">
        <a-checkbox
          :indeterminate="checkBoxObj[currentItem.id] ? checkBoxObj[currentItem.id].isIndeterminate: undefined"
          :checked="checkBoxObj[currentItem.id] ? checkBoxObj[currentItem.id].checked : false"
          @change="onCheckBoxChange"
          :disabled="checkBoxObj[currentItem.id] ? checkBoxObj[currentItem.id].isDisabled : false"
        />
      </template>
      <template v-else>
        <a-checkbox
          :checked="checkBoxObj[currentItem.id] ? checkBoxObj[currentItem.id].checked : false"
          @change="onCheckBoxChange"
          :disabled="checkBoxObj[currentItem.id] ? checkBoxObj[currentItem.id].isDisabled : true && !(currentItem.hasPermission)"
        />
      </template>
      <div class="item_wrap" :class="{'text_indent': !currentItem.isTitle}" @click="clickRow">
        <span :class="{ 'stage': currentItem.isTitle }">
          <slot name="title"></slot>
        </span>
      </div>

    </div>
    <template v-if="currentItem.isTitle">
      <file-item
        v-for="(item) in currentItem.projectDocList"
        :key="item.id"
        :currentItem="item"
        :brothers.sync="currentItem.projectDocList"
        :currentObj.sync="currentObj"
        :checkBoxObj.sync="checkBoxObj"
        v-show="currentItem.expand"
      >
        <template #title>
          <template v-if="item.done">
            <a-badge
              :numberStyle="{ width:'6px', height: '6px' }"
              style="text-indent:0;"
              :dot="true"
              title="通过"
              :offset="[-2,4]"
              color="#008000"
            >
              <span v-if="item.done">&nbsp;</span>
            </a-badge>
          </template>
          <template v-else>
            <a-badge
              v-if="item.hasPermission"
              style="text-indent:0;"
              :numberStyle="{ width:'6px', height: '6px' }"
              :dot="true"
              title="当前审核人"
              :offset="[-2,4]"
            >
              <span v-if="item.hasPermission">&nbsp;</span>
            </a-badge>
            <template v-else>
              <a-badge
                v-if="item.auditStatus === 0"
                :numberStyle="{ width:'6px', height: '6px' }"
                style="text-indent:0;"
                :dot="true"
                title="进行中"
                :offset="[-2,4]"
                color="#1890ff"
              >
                <span v-if="item.auditStatus === 0">&nbsp;</span>
              </a-badge>
            </template>
          </template>
          <span :class="{'indent-title':!(item.hasPermission || item.done)}">{{ item.no }}</span>
          {{ item.isTitle ? stageStr(item,item.stageType) : item.docFileName }}
        </template>
      </file-item>
    </template>
  </div>

</template>

<script>
import { statusMap, statusColor } from '@/utils/processDoc/auditStatus'
export default {
  name: 'FileItem',
  props: {
    currentItem: {
      type: Object,
      default: () => { return {} }
    },
    currentObj: {
      type: Object,
      default: () => { return {} }
    },
    brothers: { // 兄弟集合
      type: Array,
      default: () => { return [] }
    },
    checkBoxObj: {
      type: Object,
      default: () => { return {} }
    }
  },
  data () {
    return {
      statusMap,
      statusColor,
      currentNo: -1
    }
  },
  methods: {
    clickRow () {
      if (!this.currentItem.isTitle) {
        this.currentObj.id = this.currentItem.id
        this.currentObj.item = this.currentItem
        this.currentObj.auditStatus = this.currentItem.auditStatus
        this.currentObj.logGetParams = { docFileId: this.currentItem.id }
        this.$emit('update:currentObj', this.currentObj)
      }
    },
    // 处理阶段带日期
    stageStr (currentItem, title) {
      let str = `${title} (${currentItem.beginDate ? currentItem.beginDate : ' '}~${currentItem.endDate ? currentItem.endDate : ' '})`
      str = str.replace(/-/gi, '/')
      str = str.replace(/~/gi, '-')
      return str
    },
    // 复选框改变事件
    onCheckBoxChange (e) {
      const checkBox = this.checkBoxObj[this.currentItem.id]
      if (checkBox.isStage) { // 阶段-checkbox
        checkBox['checked'] = e.target.checked
        checkBox['isIndeterminate'] = !e.target.checked ? e.target.checked : undefined
        if (checkBox.currentObj.projectDocList) {
          checkBox.currentObj.projectDocList.forEach((item) => {
            this.checkBoxObj[item.id]['checked'] = e.target.checked
          })
        }
      } else { // 文档-checkbox
        checkBox['checked'] = e.target.checked
        let parentIsIndeterminate = false
        const allCheckeds = []
        for (const item of this.brothers) {
          if (this.checkBoxObj[item.id]) {
            const tempCheckBox = this.checkBoxObj[item.id]
            allCheckeds.push(tempCheckBox.checked)
            if (!tempCheckBox.checked) { // 一个不是选中是
              parentIsIndeterminate = true
            }
          }
        }
        if (!allCheckeds.includes(true)) { // 全部没选中
          this.checkBoxObj[checkBox.stageId]['checked'] = false
          this.checkBoxObj[checkBox.stageId]['isIndeterminate'] = undefined
        } else {
          this.checkBoxObj[checkBox.stageId]['checked'] = !parentIsIndeterminate
          this.checkBoxObj[checkBox.stageId]['isIndeterminate'] = parentIsIndeterminate
        }
      }
      this.handleAllCheckBoxStatus()
      // 处理全选按钮的状态显示
      this.$emit('update:checkBoxObj', this.checkBoxObj)
    },
    /**
     * @description: 处理全选按钮的状态显示
     * @param {*}
     * @return {*}
     */
    handleAllCheckBoxStatus () {
      const stageAry = []
      for (const key in this.checkBoxObj) {
        if (Object.hasOwnProperty.call(this.checkBoxObj, key)) {
          const item = this.checkBoxObj[key]
          if (Number(item.stageId) > 0 && item.isStage && !item.isDisabled) {
            stageAry.push(item)
          }
        }
      }
      const isAllIndeterminate = stageAry.some(item => {
        return item.isIndeterminate || item.checked
      })
      const isAllChecked = stageAry.every(item => {
        return item.checked
      })
      // const isAllNoChecked = stageAry.every(item => {
      //   return !item.checked
      // })
      if (isAllIndeterminate) {
        this.checkBoxObj['all'].isIndeterminate = isAllIndeterminate
        this.checkBoxObj['all'].checked = false
      } else {
        this.checkBoxObj['all'].isIndeterminate = undefined
        this.checkBoxObj['all'].checked = false
      }
      // if (isAllNoChecked) {
      //   this.checkBoxObj['all'].isIndeterminate = undefined
      //   this.checkBoxObj['all'].checked = false
      // }
      if (isAllChecked) {
        this.checkBoxObj['all'].isIndeterminate = undefined
        this.checkBoxObj['all'].checked = true
      }
    }
  }
}
</script>

<style lang="less" scoped>
@import '../css/FileItem.less';
.indent-title{
  padding-left: 3px;
}
</style>
