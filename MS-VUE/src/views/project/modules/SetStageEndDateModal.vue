<template>
  <a-modal
    :title="title"
    :width="500"
    :visible="isVisible"
    :afterClose="afterClose"
    :maskClosable="false"
    @cancel="isVisible = false"
    @ok="handleSubmit">
    <vxe-grid
      id="/productTechStage/getStageList"
      ref="table"
      border="full"
      :data="stages"
      max-height="100%"
      highlight-hover-row
      highlight-current-row
      show-overflow
      resizable
      auto-resize
      :customConfig="{storage: { visible: true, resizable: true } }"
    >
      <vxe-table-column
        title="序号"
        type="seq"
        width="80"
        header-align="center"
        align="center"
      ></vxe-table-column>
      <vxe-table-column
        title="阶段"
        field="stageName"
        min-width="120"
        header-align="center"
        align="left"
      ></vxe-table-column>
      <vxe-table-column
        title="截止日期"
        field="deadline"
        min-width="150"
        header-align="center"
        align="left"
      >
        <template v-slot=" { row }">
          <span v-if="$auth('project:tech:setTime')">
            <a-date-picker
              :value=" row.deadline === null ? null : moment(row.deadline)"
              :allowClear="true"
              @change="(currentDate,dataStr) => changeDate(currentDate,dataStr,row)"></a-date-picker>
          </span>
          <span v-else>
            {{ row.deadline }}
          </span>
        </template>
      </vxe-table-column>
    </vxe-grid>
  </a-modal>
</template>

<script>
import moment from 'moment'
export default {
  name: 'SetStageEndDateMoadal',
  data () {
    return {
      isVisible: false,
      form: this.$form.createForm(this),
      lists: ['未开始', '审核', '审批'],
      title: '',
      stageObj: {},
      stages: [],
      record: {},
      labelCol: {
        sm: { span: 8 }
      },
      wrapperCol: {
        sm: { span: 10 }
      }
    }
  },
  mounted () {
    this.$getDictionary(7).then((res) => {
      res.forEach((item, index) => {
        this.stageObj[item.key] = item.value
        this.stageObj[item.value] = item
      })
    })
  },
  methods: {
    moment,
    show (record) {
      this.record = record
      this.title = `设置[${record.productName}]阶段截止日期`
      const showObj = {} // 回填对象
      this.$http.get('/productTechStage/getStageList', { params: { projectId: record.id, productId: record.productId } }).then((res) => {
        if (res.data && res.success) {
          const data = res.data
          this.stages = data
          data.map((item, index) => {
            if (this.stageObj[item.stageKey]) {
              item['stageName'] = this.stageObj[item.stageKey]
              item['obj'] = this.stageObj[this.stageObj[item.stageKey]]
              if (item.deadline) { // 有日期，处理回填内容
                showObj[item.stageKey] = this.moment(item.deadline)
              }
            }
          })
        } else {
          this.$message.error(res.errorMessage)
        }
      }).catch((error) => {
        this.$message.error(error)
      })
      this.isVisible = true
    },
    afterClose () {
      this.isVisible = false
      this.title = ''
      this.stages = []
      this.record = {}
    },
    handleSubmit () {
      const submitAry = this.stages.map(item => {
        item['projectId'] = this.record.id
        delete item.stageName
        delete item.obj
        return item
      }).filter(item => {
        return item.projectStageId || item.deadline
      })
      this.$http.post('/productTechStage/editDeadline', submitAry).then((res) => {
        if (res.data && res.success) {
          this.isVisible = false
          this.$message.success('操作成功')
          this.$emit('ok', false)
        } else {
          this.$message.error(res.errorMessage)
        }
      }).catch((error) => {
        if (error.message.includes('403')) {
          this.$message.error('无此权限，请跟管理员联系！')
        } else {
          this.$message.error('操作失败：' + error.message)
        }
      })
    },
    changeDate (currentDate, dateStr, row) {
      this.stages = this.stages.map(item => {
        if (item.stageName === row.stageName) {
          if (!currentDate) {
            item.deadline = null
          } else {
            item.deadline = dateStr
          }
        }
        return item
      })
    }
  }
}
</script>

<style lang="less" scoped>

</style>
