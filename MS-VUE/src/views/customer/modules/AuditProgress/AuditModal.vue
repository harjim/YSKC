<!--
 * @Author: ldx
 * @Date: 2020-11-26 10:13:57
 * @LastEditTime: 2021-06-30 15:15:40
 * @LastEditors: ldx
 * @Description:
 * @FilePath: \MS-VUE\src\views\customer\modules\AuditProgress\AuditModal.vue
-->
<template>
  <a-modal
    style="top: 0px;"
    :title="title"
    :width="800"
    :visible="isVisible"
    :afterClose="onClose"
    :maskClosable="false"
    :footer="null"
    :bodyStyle="{minHeight: '365px'}"
    @cancel="onClose"
  >
    <a-spin :spinning="spinning">
      <div v-if="isAuth" class="form-container">
        <a-form id="form" :form="form">
          <a-form-item label="审批意见">
            <a-textarea v-decorator="['suggestion', {rules:[{required: true, whitespace:true,min: 5, message: '请输入5个字以上审批意见'}]}]" placeholder="请输入审批意见" :rows="4" :extra="onComputeTextarea('remark',200)"></a-textarea>
          </a-form-item>
          <a-row>
            <a-col style="text-align: center;">
              <a-button type="primary" class="btn-space success" @click="audit(true)">通过</a-button>
              <a-button type="danger" class="btn-space reject" @click="audit(false)">驳回</a-button>
            </a-col>
          </a-row>
        </a-form>
      </div>
      <vxe-grid
        ref="table"
        highlight-hover-row
        highlight-current-row
        show-overflow="title"
        resizable
        auto-resize
        border="inner"
        size="small"
        :data="tableData"
      >
        <vxe-table-column type="seq" title="序号" width="60"></vxe-table-column>
        <vxe-table-column
          title="节点名称"
          field="nodeName"
          header-align="left"
          align="left"
        >
        </vxe-table-column>
        <vxe-table-column
          title="审批状态"
          field="pass"
          width="100"
          header-align="left"
          align="left"
        >
          <template v-slot="{row}">
            <span :style="{color: passColor[row.status]}">
              {{ passKM[row.status] }}
            </span>
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="审批人"
          field="auditor"
          width="100"
          header-align="left"
          align="left"
        >
          <template v-slot="{row}">
            {{ row.auditor ? row.auditor : '-' }}
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="审批意见"
          field="suggestion"
          header-align="left"
          align="left"
        >
        </vxe-table-column>
        <vxe-table-column
          title="审批时间"
          field="createTime"
          width="160"
          header-align="left"
          align="center"
        >
        </vxe-table-column>
      </vxe-grid>
    </a-spin>
  </a-modal>
</template>
<script>
import ystable from '@/components/Table/ystable'
export default {
  name: 'AuditModal',
  components: {
    ystable
  },
  props: {
    record: {
      type: Object,
      default: () => { return {} }
    }
  },
  data () {
    return {
      isVisible: false,
      spinning: false,
      form: this.$form.createForm(this),
      title: 'undefined',
      auditId: undefined,
      row: undefined,
      tableData: [],
      flowNodeMap: {},
      isAuth: false,
      passKM: { 1: '通过', 2: '驳回', 3: '激活' },
      passColor: { 1: '#5BB51D', 2: '#F70D1C', 3: '#F70D1C' }
      // projectId: undefined
    }
  },
  methods: {
    show (row, flowNodeMap, title, projectId) {
      this.row = row
      this.auditId = row.id
      this.flowNodeMap = flowNodeMap
      this.title = `${title}-${row.modeName}`
      this.getAuditLog(row.instanceId)
      this.form.resetFields()
    },
    onClose () {
      this.isVisible = false
      this.spinning = false
      this.auditId = undefined
      this.row = undefined
      this.isAuth = false
      this.tableData = []
      this.flowNodeMap = {}
    },
    // 提交审批意见
    onSubmit (status) {
      this.form.validateFields((error, values) => {
        if (!error) {
          this.spinning = true
          const paramObj = {
            id: this.row.id ? this.row.id : undefined,
            projectId: this.record.id,
            companyId: this.record.companyId,
            year: this.record.year,
            moduleId: this.row.mode,
            pass: status,
            nodeId: this.row.curNodeId,
            suggestion: values.suggestion,
            instanceId: this.row.instanceId
          }
          this.$http.post('/projectProgress/projectAudit', paramObj).then((res) => {
            if (res.data && res.success) {
              this.$message.success('操作成功')
              this.$emit('refreshProgress')
              this.isVisible = false
            } else {
              this.$message.error(res.errorMessage)
            }
          }).catch((error) => {
            this.$message.error(error.message)
          }).finally((res) => {
            this.spinning = false
          })
        }
      })
    },
    // 获取审批日志
    getAuditLog (instanceId) {
      this.spinning = true
      this.$http.get('/projectProgress/getAuditLog', { params: { instanceId: this.row.instanceId, curNodeId: this.row.curNodeId } }).then((res) => {
        if (res.data && res.success) {
          this.tableData = res.data.logs
          this.isAuth = res.data.nodePerms
          this.isVisible = true
          this.spinning = false
        } else {
          this.$message.error(res.errorMessage)
          this.spinning = false
        }
      }).catch((error) => {
        this.$message.error(error.message)
        this.spinning = false
      })
    },
    // 计算文本域的个数
    onComputeTextarea (fieldName, limitNumber = 200) {
      const content = this.form.getFieldValue(fieldName)
      const contentLenght = content ? content.length : 0
      if (contentLenght > limitNumber) {
        const obj = {}
        obj[fieldName] = { value: content.substring(0, limitNumber) }
        this.form.setFields(obj)
      }
      return `(${contentLenght}/${limitNumber})`
    }
  }
}
</script>
<style lang='less' scoped>
.btn-space {
  margin-right: 12px;
}
.form-container {
  margin-bottom: 12px;
}
.hover(@color:#fff) {
  color: #fff;
  box-shadow: #fff;
  border: 1px solid #fff;
  background: @color;
}
#form {
  .reject {
    &:hover {
      .hover(red);
    }
  }
  .success {
    &:hover {
      .hover(#1890ff);
    }
  }
}
</style>
