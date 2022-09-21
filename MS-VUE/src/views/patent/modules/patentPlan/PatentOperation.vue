<!--
 * @Author: zdf
 * @Date: 2021-08-23 08:25:01
 * @LastEditTime: 2021-08-31 08:36:31
 * @LastEditors: zdf
 * @Description: 专利操作
 * @FilePath: \MS-VUE\src\views\patent\modules\patentPlan\PatentOperation.vue
-->
<template>
  <a-card :title="title" style="width: 100%;height:100%">
    <a-spin :spinning="btnLoading" tip="请稍后...">
      <div class="audit_wrap">
        <div class="audit_content">
          <a-form :form="form">
            <template v-if="auditPermission && row.process === 0">
              <a-form-item >
                <a-textarea
                  :auto-size="{ minRows: 3, maxRows: 5 }"
                  v-decorator="['suggestion', {rules:[{required: true, whitespace:true,min: 5, message: '请输入5个字以上审批意见'}]}]"
                  placeholder="请输入审批意见"
                  :extra="onComputeTextarea('suggestion',200)">
                </a-textarea>
              </a-form-item>
              <div class="audit_btn">
                <a-popconfirm placement="top" ok-text="确定" cancel-text="取消" @confirm="handleSubmit(false)" :getPopupContainer="getPopupContainer">
                  <template slot="title">
                    <p>您确定要驳回审核吗?</p>
                  </template>
                  <a-button size="small" class="btn_space reject">驳回</a-button>
                </a-popconfirm>
                <a-popconfirm placement="top" ok-text="确定" cancel-text="取消" @confirm="handleSubmit(true)" :getPopupContainer="getPopupContainer">
                  <template slot="title">
                    <p>您确定要通过审核吗?</p>
                  </template>
                  <a-button size="small" class="btn_space success">通过</a-button>
                </a-popconfirm>
              </div>
            </template>
            <template v-else-if="canProcess && row.process !== 4">
              <a-form-item style="padding:0;">
                当前进度：<span>{{ processes[curProcess].label }}</span>
              </a-form-item>
              <div v-if="filterProcess"><span><a-alert style="margin:10px 0;" :message="`请上传${curProcess === 1 ? '初稿': curProcess === 2 ? '定稿' : curProcess === 3 ? '受理通知书' : '授权通知书、知识产权证书，并关联专利。'}`" type="warning" banner/></span></div>
              <template v-else>
                <a-form-item>
                  <a-textarea
                    :auto-size="{ minRows: 3, maxRows: 5 }"
                    v-decorator="['remark', {rules:[{required: false, whitespace:true,min: 5, message: '请输入5个字以上备注'}]}]"
                    placeholder="请输入备注"
                    :extra="onComputeTextarea('remark',200)">
                  </a-textarea>
                </a-form-item>
                <div class="audit_btn">
                  <a-popconfirm
                    placement="top"
                    :disabled="filterProcess"
                    ok-text="确定"
                    cancel-text="取消"
                    @confirm="handleProcess()"
                    :getPopupContainer="getPopupContainer">
                    <template slot="title">
                      <p>您确定要通过[{{ processes[curProcess].label }}]吗?</p>
                    </template>
                    <a-button
                      :disabled="filterProcess"
                      size="small"
                      class="btn_space success">通过</a-button>
                  </a-popconfirm>
                </div>
              </template>
            </template>
          </a-form>
        </div>
      </div>
    </a-spin>
  </a-card>
</template>

<script>
export default {
  props: {
    row: {
      type: Object,
      required: true
    },
    auditPermission: {
      type: Boolean,
      default: false
    },
    instanceId: {
      type: Number,
      default: undefined
    },
    canProcess: {
      type: Boolean,
      default: false
    },
    processes: {
      type: Array,
      default: () => []
    },
    curProcess: {
      type: Number,
      default: 0
    },
    files: {
      type: Object,
      required: true
    }
  },
  data () {
    return {
      btnLoading: false,
      title: '',
      form: this.$form.createForm(this),
      filterProcess: false
    }
  },
  created () {
    if (this.auditPermission) {
      this.title = '审批意见'
    } else {
      this.title = '进度'
    }
    this.loadFilterProcess()
  },
  watch: {
    row () {
      this.form.resetFields()
      this.loadFilterProcess()
    },
    curProcess () {
      this.loadFilterProcess()
    }
  },
  methods: {
    loadFilterProcess () {
      this.filterProcess = ((this.curProcess === 1 || this.curProcess === 2 || this.curProcess === 3) && (!this.files[this.curProcess] || !this.files[this.curProcess].length)) ||
       (this.curProcess === 4 && ((!this.files[5] || !this.files[5].length) || (!this.files[6] || !this.files[6].length) || !this.row.patentNo))
    },
    getPopupContainer (triggerNode) {
      return triggerNode.parentNode
    },
    handleSubmit (pass) {
      this.btnLoading = true
      this.form.validateFields((errors, values) => {
        if (errors) {
          this.btnLoading = false
          return
        }
        this.$http.post('/projectProgress/projectAudits', Object.assign(values, { instanceIds: [this.instanceId], pass })).then(res => {
          if (res.success && res.data) {
            setTimeout(() => {
              this.$emit('getInfo')
            }, 200)
            // 成功以后清除数据
            this.form.resetFields()
            this.btnLoading = false
          } else {
            this.$message.error(res.errorMessage || '审批失败')
            this.btnLoading = false
          }
        })
      })
    },
    handleProcess () {
      this.btnLoading = true
      this.form.validateFields((errors, values) => {
        if (errors) {
          this.btnLoading = false
          return
        }
        this.$http.post('/patentPlan/setProcess', Object.assign({ id: this.row.id, process: this.curProcess }, values)).then(res => {
          if (res.success && res.data) {
            this.$emit('getInfo')
            this.row.process = this.curProcess
            // 成功以后清除数据
            this.form.resetFields()
            this.$message.success('操作成功')
            this.loadFilterProcess()
          } else {
            this.$message.error(res.errorMessage || '操作失败')
          }
          this.btnLoading = false
        })
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

<style lang="less" scoped>
.ant-form-item {
  margin-bottom: 0px;
}
.audit_wrap {
  position: relative;
  .audit_btn {
      width:100%;
      text-align: center;
    .btn_space {
        display: inline-block;
        margin-right: 10px;
        }
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
}
.hover(@color:#fff) {
  color: #fff;
  box-shadow: #fff;
  border: 1px solid #fff;
  background: @color;
}
</style>
