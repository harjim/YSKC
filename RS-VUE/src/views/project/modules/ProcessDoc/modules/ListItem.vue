<!--
 * @Author: ldx
 * @Date: 2020-12-28 16:54:27
 * @LastEditTime: 2022-03-14 11:08:00
 * @LastEditors: lzh
 * @Description:
 * @FilePath: \RS-VUE\src\views\project\modules\ProcessDoc\modules\ListItem.vue
-->
<template>
  <div>
    <div ref="rowWrap" class="row-wrap" :class="{'item-selected': currentItem.id === active.currentId}" >
      <a-icon style="margin-right: 2px;" v-if="currentItem.expand !== undefined" :type="currentItem.expand ? 'caret-down' : 'caret-right'" @click="() => { currentItem.expand = !currentItem.expand }"/>
      <span v-else>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
      <!-- 文档 -->
      <template v-if="isFile">
        <template v-if="!isRSUser">
          <a-badge
            v-if="Number(currentItem.status) !== 5"
            :numberStyle="{ width:'6px', height: '6px' }"
            :dot="true"
            :offset="[12,4]"
            :title="statusMap[currentItem.status]"
            :color="statusColor[currentItem.status]">
            <a-checkbox
              @change="onCheckBoxChange"
              :checked="checkBoxObj[currentItem.id].checked"
            >
            </a-checkbox>
          </a-badge>
          <a-checkbox
            v-else
            :checked="checkBoxObj[currentItem.id].checked"
            @change="onCheckBoxChange"
          ></a-checkbox>
        </template>
        <template v-else>
          <a-checkbox
            :checked="checkBoxObj[currentItem.id].checked"
            @change="onCheckBoxChange"
          ></a-checkbox>
        </template>
      </template>
      <!-- 阶段 -->
      <template v-else>
        <a-checkbox
          :indeterminate="checkBoxObj[currentItem.id].isIndeterminate"
          :checked="checkBoxObj[currentItem.id].checked"
          @change="onCheckBoxChange"></a-checkbox>
      </template>
      <!-- </template> -->
      <div class="item-wrap" @click="itemClick" :class="{'isFile': isFile}">
        <!-- 序号部分 -->
        <div
          v-if="isFile"
          :class="{'edit-height': isEdit, 'text-indent': isFile,'item-index-box': !isFile}"
        >
          {{ parentSeq > -1 ? `${parentSeq +1}.${seq+1}.`: `${seq+1}.` }}
        </div>
        <div
          v-else
          :class="{'edit-height': isEdit, 'text-indent': isFile,'item-index-box': !isFile}"
          style="font-weight: 600; font-size: 15px;"
        >
          {{ parentSeq > -1 ? `${parentSeq +1}.${seq+1}.`: `${seq+1}.` }}
        </div>
        <!-- 文件名部分 -->
        <div
          v-if="isFile"
          class="item-title-box hidden-text "
          :title="title "
        >
          <template v-if="!isEdit ">
            <template v-if="!isRSUser && isEditStatus(currentItem.status) && !isFinance(currentItem.owner)">
              <span :class="{ needEdit:currentItem.needEdit || !currentItem.finished }">{{ inputData }}</span>
            </template>
            <template v-else>
              <span>{{ inputData }}</span>
            </template>
          </template>
          <template v-else >
            <a-input
              v-focus
              @focus="$event.currentTarget.select()"
              style="width: 100%;"
              v-model="inputData"
              :placeholder="title" />
          </template>
        </div>
        <div
          v-else
          class="item-title-box hidden-text"
          style="font-size: 15px; font-weight: 600;"
          :title="stageStr(title)">
          <template>
            {{ title }} <span style="font-size: 12px; font-weight: 400;">{{ stageDate() }}</span>
          </template>
        </div>
        <!-- 操作图标部分 -->
        <div
          class="item-icon-box"
          :class="{'edit-height': isEdit, 'show-item-box': !isFile}"
        >
          <template v-if="$auth('project:doc:add')">
            <a-icon v-if="!isFile" @click.stop="handleAdd" title="添加过程材料" type="plus" />
          </template>
          <a-icon v-if="isEdit" @click.stop="handleCancel" title="取消" type="close" />
          <a-icon v-if="isEdit" @click.stop="handleSubmit" title="提交" type="check" />
          <template
            v-if="$auth('project:doc:edit') && ( isRSUser|| isEditStatus(currentItem.status))">
            <a-icon v-if="isFile && !isEdit" @click.stop="handleEdit" title="编辑文件名" type="edit" />
          </template>
          <template
            v-if=" $auth('project:doc:del') && (isRSUser || isEditStatus(currentItem.status)) && !isFinance(currentItem.owner)">
            <a-icon v-if="isFile && !isEdit" @click.stop="handleDel" title="删除" type="delete" />
          </template>
          <template
            v-if="$auth('project:doc:edit') && ( isRSUser || isEditStatus(currentItem.status))">
            <a-icon v-if="(seq > 0 && isFile) && !isEdit " @click.stop="handleUp" title="向上移" type="arrow-up" />
            <a-icon v-if="(seq+1 !== stage.projectDocList.length && isFile) && !isEdit " @click.stop="handleDown" title="向下移" type="arrow-down" />
          </template>
        </div>
      </div>
    </div>
    <template v-if="currentItem.projectDocList && currentItem.projectDocList.length" >
      <list-item
        v-for="(file,index) in currentItem.projectDocList"
        :id="file.id"
        :key="file.id"
        :parentSeq="seq"
        :seq="index"
        :isFile="true"
        :title.sync="file.docFileName"
        :stage="currentItem"
        :currentItem="file"
        :active.sync="active"
        :brothers.sync="currentItem.projectDocList"
        :checkBoxObj.sync="checkBoxObj"
        v-on="$listeners"
        v-show="currentItem.expand"
      >
      </list-item>
    </template>
  </div>
</template>

<script>
import { mapState, mapActions, mapGetters } from 'vuex'
import { isEditStatus, isExportStatus, getStatusName, statusMap, statusColor, isFinance } from '@/utils/processDoc/auditStatus'

export default {
  name: 'ListItem',
  props: {
    isFile: { // 是否文档判断是否缩进
      type: Boolean,
      required: true
    },
    title: { // 标题
      type: String,
      required: true
    },
    parentSeq: { // 父级序号
      type: Number,
      default: -1
    },
    seq: { // 序号
      type: [Number, String],
      required: true
    },
    stage: {
      type: Object,
      default: () => { return {} }
    },
    currentItem: {
      type: Object,
      required: true
    },
    brothers: { // 兄弟集合
      type: Array,
      required: true
    },
    checkBoxObj: {
      type: Object,
      required: true
    },
    active: {
      type: Object,
      required: true
    }
  },
  mounted () {
    this.isRSUser = !this.userInfo.userSource
  },
  computed: {
    ...mapState(['doc']),
    ...mapGetters(['userInfo'])
  },
  directives: {
    focus: {
    // 指令的定义
      inserted: function (el) {
        el.focus()
      }
    }
  },
  watch: {
    'currentItem.docFileName': {
      handler: function (newValue) {
        this.inputData = newValue
      }
    },
    'active.editId': {
      handler: function (newValue) {
        if (this.isEdit && this.currentItem.id !== newValue) {
          this.isEdit = false
          this.inputData = this.backInputData
        }
      }
    }

  },
  data () {
    return {
      statusMap,
      statusColor,
      isRSUser: true, // 是否RS用户登录
      isEdit: false,
      inputData: this.title,
      backInputData: undefined,
      checkObj: {},
      halfCheck: false,
      isIndeterminate: false,
      stageCheck: false
    }
  },
  methods: {
    isEditStatus,
    isExportStatus,
    getStatusName,
    isFinance,
    ...mapActions(['setFile']),
    // 点击item事件
    itemClick (event) {
      if (!this.isFile) { return }
      this.active.currentId = this.currentItem.id
      this.active.file = this.currentItem
      this.active.stage = this.stage
      this.active.editId = this.currentItem.id
    },
    // 处理删除
    handleDel () {
      const me = this
      const docName = this.inputData
      this.$confirm({
        title: `你确定要删除【${docName}】?`,
        okType: 'danger',
        onOk () {
          me.requestDel()
        },
        onCancel () {}
      })
    },
    // 处理取消
    handleCancel () {
      if (this.isEdit) {
        // 处理取消
        this.isEdit = false
        this.inputData = this.backInputData
      }
    },
    // 请求删除过程文件
    requestDel () {
      const postData = {
        id: this.currentItem.id,
        docFileId: this.currentItem.docFileId,
        stage: this.currentItem.stageKey === '9999' ? null : this.currentItem.stageKey, // 兼容旧数据
        projectId: this.active.project.id
      }
      this.$http.post('/projectDocFile/delete', postData).then((res) => {
        if (res.data && res.success) {
          this.$message.success('删除成功')
          this.$listeners.showDoc(this.active, false)
        } else {
          this.$message.error(res.errorMessage)
        }
      }).catch((error) => {
        this.$message.error(error.message)
      })
    },
    // 处理编辑
    handleEdit () {
      this.isEdit = true
      this.backInputData = this.inputData
      this.active.editId = this.currentItem.id
    },
    // 处理提交
    handleSubmit () {
      if (this.isEdit) {
        if (!this.inputData.trim().length) {
          this.$message.info('过程文件名称不能为空！')
          return
        }
        if (this.inputData === this.backInputData) {
          this.isEdit = false
          return
        }
        const postObj = {
          id: this.currentItem.id,
          docFileName: this.inputData,
          stage: this.currentItem.stage === '9999' ? null : this.currentItem.stage,
          projectId: this.active.project.id,
          docFileId: this.currentItem.docFileId
        }
        this.$http.post('/projectDocFile/editDocFileName', postObj)
          .then(res => {
            if (res.success && res.data) {
              this.isEdit = false
              this.$message.success('操作成功')
              this.currentItem.docFileName = this.inputData
              this.setFile(this.currentItem)
              this.$listeners.updateFileName(postObj.docFileName)
            } else {
              this.$message.success('操作失败')
              this.inputData = this.backInputData
            }
          }).catch((error) => {
            this.message.error('请求接口出错/projectDocFile/editDocFileName' + error.message)
          })
      }
    },
    // 处理添加
    handleAdd () {
      // TODO 这样获取有点问题，有时间优化（通过方法一层一层传递function 调用）
      this.$listeners.openDocFileModal(this.currentItem)
    },
    // 处理向下
    handleDown () {
      let nextItem
      let postSeq
      const currentSeq = this.currentItem.seq
      const brothers = this.stage.projectDocList
      if (brothers.length) {
        if (this.seq <= brothers.length - 1) {
          nextItem = brothers[this.seq + 1]
          postSeq = nextItem.seq
        }
        if (!postSeq && !nextItem) { return }
        const postObj = {
          id: this.currentItem.id,
          seq: postSeq,
          stage: this.currentItem.stage === '9999' ? null : this.currentItem.stage,
          projectId: this.active.project.id,
          docFileId: this.currentItem.docFileId
        }
        this.$http.post('/projectDocFile/editDocFileName', postObj)
          .then(res => {
            if (res.success && res.data) {
              const index = this.seq
              brothers[index] = brothers.splice(index + 1, 1, brothers[index])[0]
              brothers[index].seq = currentSeq
              this.currentItem.seq = postSeq
            }
          }).catch((error) => {
            this.$message.error('操作失败')
            console.error('请求接口出错/projectDocFile/editDocFileName' + error.message)
          })
      }
    },
    // 处理向上
    handleUp () {
      let parentItem
      let postSeq
      const currentSeq = this.currentItem.seq
      const brothers = this.stage.projectDocList
      if (brothers.length) {
        if (this.seq >= 1) {
          parentItem = brothers[this.seq - 1]
          postSeq = parentItem.seq
        }
        if (!postSeq && !parentItem) { return }
        const postObj = {
          id: this.currentItem.id,
          seq: postSeq,
          stage: this.currentItem.stage === '9999' ? null : this.currentItem.stage,
          projectId: this.active.project.id,
          docFileId: this.currentItem.docFileId
        }
        this.$http.post('/projectDocFile/editDocFileName', postObj)
          .then(res => {
            if (res.success && res.data) {
              const index = this.seq
              brothers[index] = brothers.splice(index - 1, 1, brothers[index])[0]
              brothers[index].seq = currentSeq
              this.currentItem.seq = postSeq
            }
          }).catch((error) => {
            this.$message.error('操作失败')
            console.error('请求接口出错/projectDocFile/editDocFileName' + error.message)
          })
      }
    },
    // TODO 2021-04-29 重点处理一下
    // 复选框改变事件
    onCheckBoxChange (e) {
      const activeCheckBox = this.checkBoxObj[this.currentItem.id]
      // const statusAry = [1, 2, 3, 5]
      const brothers = this.stage.projectDocList
      if (activeCheckBox.isStage) { // 阶段
        activeCheckBox['checked'] = e.target.checked
        activeCheckBox['isIndeterminate'] = !e.target.checked ? e.target.checked : undefined
        brothers.forEach((item) => {
          this.checkBoxObj[item.id]['checked'] = e.target.checked
        })
      } else { // 文档
        activeCheckBox['checked'] = e.target.checked
        const checkboxBrothers = []
        for (const item of brothers) {
          checkboxBrothers.push(this.checkBoxObj[item.id])
        }
        const isAllChecked = checkboxBrothers.every(item => {
          return item.checked
        })
        const isNoChecked = checkboxBrothers.every(item => {
          return !item.checked
        })
        if (isAllChecked) {
          this.checkBoxObj[this.stage.id]['isIndeterminate'] = undefined
        } else {
          if (isNoChecked) {
            this.checkBoxObj[this.stage.id]['isIndeterminate'] = false
          } else {
            this.checkBoxObj[this.stage.id]['isIndeterminate'] = !isAllChecked
          }
        }
        this.checkBoxObj[this.stage.id]['checked'] = isAllChecked
      }
    },
    // 处理阶段带日期
    stageStr (title) {
      const beginDate = this.stage.beginDate || ''
      const endDate = this.stage.endDate || ''
      let str = `(${title} ${beginDate}~${endDate})`
      str = str.replace(/-/gi, '/')
      str = str.replace(/~/gi, '-')
      return str
    },
    stageDate () {
      const beginDate = this.stage.beginDate || ''
      const endDate = this.stage.endDate || ''
      let str = `(${beginDate}~${endDate})`
      str = str.replace(/-/gi, '/')
      str = str.replace(/~/gi, '-')
      return str
    }
  }
}
</script>

<style lang="less" scoped>
.hidden-text {
  overflow:hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.text-indent {
  text-indent: 1em;
}
.row-wrap {
  display: flex;
  flex-direction: row;
  align-items: center;
  &:hover {
    background-color:#e6f7ff ;
  }
}
.item-wrap {
    width: 100%;
    display: flex;
    flex: 1;
    align-items: center;
    flex-direction: row;
    cursor: pointer;
    font-size: 14px;
    line-height: 16px;
    .item-index-box {
      font-size: 14px;
      line-height: 16px;
      padding: 5px 0 5px 5px;
    }
    .edit-height {
      line-height: 32px;
    }
    .item-title-box {
      font-size: 14px;
      line-height: 16px;
      padding: 5px;
      // flex: 1;
    }
    .item-icon-box {
      padding: 5px 0;
      display:none;
      white-space: nowrap;
      i {
        color:#444;
        margin-right:5px;
        white-space: nowrap;
        &:hover {
          color: #1890ff;
        }
      }
    }
    .show-item-box {
      display:inline-block !important ;
    }
    &:hover {
      .item-icon-box {
        display:inline-block;
        white-space: nowrap;
      }
    }
  }

.item-selected {
  background-color: #e6f7ff;
  color: #1890ff;
}
.needEdit {
  color: #0000CD;
}
</style>
