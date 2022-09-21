<!--
 * @Author: ldx
 * @Date: 2021-02-06 10:01:50
 * @LastEditTime: 2021-09-08 11:24:23
 * @LastEditors: lzh
 * @Description: 过程文件批量审核modal
 * @FilePath: \MS-VUE\src\views\patent\modules\BatchAuditPatentModal.vue
-->
<template>
  <a-modal
    :zIndex="1001"
    :title="title"
    :width="650"
    :visible="isVisible"
    :afterClose="afterClose"
    :maskClosable="false"
    style="top: 0px;"
    :destroyOnClose="true"
    :getContainer="popupContainer"
    :confirmLoading="confirmLoading"
    :bodyStyle="{ height: '65vh', maxHeight: '65vh', overflow: 'auto' }"
    @cancel="isVisible = false"
  >
    <template #footer>
      <a-button type="default" @click="isVisible = false">取消</a-button>
    </template>
    <div id="tableBox" style="height:calc(100% - 200px);">
      <vxe-grid
        :data="tableData"
        height="auto"
        size="small"
        ref="table"
        highlight-current-row
        highlight-hover-row
        show-overflow="title"
        resizable
        auto-resize
        :columns="columns"
      >
        <template #auditSlot="{row}">
          <a-checkbox
            :checked="row.checked"
            :id="`${row.id}`"
            @change="(e) => checkboxChange(e,row)">
          </a-checkbox>
        </template>
      </vxe-grid>
    </div>
    <div class="form_wrap" style="height: 200px;">
      <a-form id="form" :form="form">
        <a-form-item label="审批意见" :extra="onComputeTextarea('suggestion',200)" style="margin-bottom: 0px;">
          <a-textarea
            v-decorator="['suggestion', {rules:[{required: true, whitespace:true,min: 4, message: '请输入5个字以上审批意见'}]}]"
            placeholder="请输入审批意见"
            :rows="4"
          />
        </a-form-item>
        <a-row>
          <a-col style="text-align: center;">
            <a-popconfirm
              placement="top"
              ok-text="确定"
              cancel-text="取消"
              @confirm="handleSubmit(false)"
              :getPopupContainer="getPopupContainer"
              :disabled="!btnDisabled">
              <template slot="title">
                <p>您确定要驳回审核吗?</p>
              </template>
              <a-button
                class="btn-space"
                :class="{reject:btnDisabled }"
                :loading="confirmLoading"
                :disabled="!btnDisabled"
              >驳回</a-button>
            </a-popconfirm>
            <a-popconfirm
              placement="top"
              ok-text="确定"
              cancel-text="取消"
              @confirm="handleSubmit(true)"
              :getPopupContainer="getPopupContainer"
              :disabled="!btnDisabled">
              <template slot="title">
                <p>您确定要通过审核吗?</p>
              </template>
              <a-button
                class="btn-space"
                :class="{success:btnDisabled }"
                :loading="confirmLoading"
                :disabled="!btnDisabled"
              >通过</a-button>
            </a-popconfirm>
          </a-col>
        </a-row>
      </a-form>
    </div>
  </a-modal>
</template>
<script>
import { isEditStatus } from '@/utils/processDoc/auditStatus'
import { popupContainer } from '@/views/customer/modules/AuditProgress/modules/js/screenFullMountDom'
import { projectAudits } from '@/api/patent/patent'
import { cloneDeep } from 'lodash'
export default {
  name: 'BatchAuditModal',
  data () {
    return {
      columns: [
        { title: '序号', type: 'seq', headerAlign: 'center', align: 'center', width: '60' },
        { title: '项目名称', field: 'pname', headerAlign: 'center', align: 'center', minWidth: '120' },
        { title: '申请名称', field: 'patentName', headerAlign: 'center', align: 'center', minWidth: '120' },
        { title: '是否审核', field: 'checked', headerAlign: 'center', align: 'center', width: '80', slots: { default: 'auditSlot' } }
      ],
      confirmLoading: false,
      form: this.$form.createForm(this),
      isVisible: false,
      title: '',
      tableData: [],
      patentIds: [],
      btnDisabled: false,
      projectId: undefined
    }
  },
  watch: {
    patentIds: {
      immediate: true,
      handler (val) {
        if (val.length) {
          this.btnDisabled = true
        } else {
          this.btnDisabled = false
        }
      }
    }
  },
  methods: {
    isEditStatus,
    popupContainer,
    getPopupContainer () { return popupContainer('tableBox') },
    show (chooseFiles, title = '批量审核专利') {
      this.isVisible = true
      this.title = title
      this.form.resetFields()
      this.tableData = cloneDeep(chooseFiles)
      this.tableData.map(item => {
        this.patentIds.push(item.id)
        this.$set(item, 'checked', true)
      })
    },
    afterClose () {
      this.patentIds = []
      this.projectId = undefined
      this.btnDisabled = false
      this.title = ''
      this.confirmLoading = false
    },
    handleSubmit (status) {
      this.form.validateFields((error, values) => {
        if (!error) {
          const params = {
            pass: status,
            suggestion: values.suggestion,
            patentIds: this.patentIds
          }
          this.confirmLoading = true
          projectAudits(params).then(data => {
            if (data) {
              this.$message.success('操作成功')
              this.$emit('refresh')
              this.isVisible = false
            }
          }).catch(error => {
            this.$message.error(error.message || '系统异常，请联系管理员！')
          }).finally(() => {
            this.confirmLoading = false
          })
        }
      })
    },
    checkboxChange (e, row) {
      row.checked = e.target.checked
      this.patentIds = []
      this.tableData.forEach(item => {
        if (item.checked) {
          this.patentIds.push(item.id)
        }
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
#tableBox /deep/ .vxe-table .c--title {
  width: 100% !important;
}
.btn-space {
  margin-right: 12px;
}
.hover(@color:#fff) {
  color: #fff;
  box-shadow: #fff;
  border: 1px solid #fff;
  background: @color;
}
.form_wrap {
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
