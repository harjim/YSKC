<template>
  <a-modal
    :title="title"
    style="top: 20px;"
    :width="800"
    v-model="visible"
    @ok="handleSubmit"
    :maskClosable="false"
    :afterClose="afterClose"
  >
    <a-form @submit="handleSubmit" :form="form">
      <a-row :gutter="24">
        <a-col :span="12">
          <a-form-item label="专利号" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input
              v-decorator="['patentNo', {rules:[{required: true, message: '请输入专利号'}]}]"
              @blur="checkpatentNo"
              placeholder="请输入专利号"
              :disabled="isCreat"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="专利名称">
            <a-input v-decorator="['patentName', {rules:[{required: true, message: '请输入专利名称'}]}]" :disabled="isCreat" placeholder="请输入专利名称" />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="12">
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="专利类型">
            <a-select showSearch v-decorator="['mainType', {rules:[{required: true, message: '请选择专利类型'}]}]" placeholder="请选择专利类型">
              <a-select-option value="发明专利">发明专利</a-select-option>
              <a-select-option value="实用新型">实用新型</a-select-option>
              <a-select-option value="外观设计">外观设计</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item
            label="发明人"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
          >
            <a-input v-decorator="['inventor']" :disabled="isCreat" placeholder="请输入发明人" />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="申请日期">
            <a-date-picker
              :disabled="isCreat"
              style="width: 210px"
              format="YYYY-MM-DD"
              v-decorator="['applyDateTime', {rules:[{required: !isCreat, message: '请选择申请日期'}]}]"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="授权日期">
            <a-date-picker
              style="width: 210px"
              format="YYYY-MM-DD"
              v-decorator="['authDate', {rules:[{required: true, message: '请选择授权日期'}]}]"
            />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="12">
          <a-form-item label="权利要求数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input-number
              :max="$store.state.maxOrder"
              :min="0"
              :precision="0"
              style="width:100%"
              placeholder="请输入权利要求数量"
              v-decorator="['claimNum', {rules:[{required: true, message: '请输入权利要求数量'}]}]"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="使用次数" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input-number
              :max="$store.state.maxOrder"
              :min="0"
              :precision="0"
              style="width:100%"
              placeholder="请输入使用次数"
              v-decorator="['usedCnt', {rules:[{required: true, message: '请输入使用次数'}]}]"
            />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row>
        <a-form-item
          label="权利要求内容"
          :labelCol="{span: 4}"
          :wrapperCol="{span: 19}"
          :help="()=>{const r = form.getFieldValue('claimContent'); return `(${!r? 0 : r.length > 3000 ? 3000 : r.length}/3000)`}">
          <a-textarea
            placeholder="请输入权利要求内容"
            v-decorator="['claimContent', {rules:[{required: true, message: '请输入权利要求内容'}]}]"
            :maxLength="3000"
            :rows="5">
          </a-textarea>
        </a-form-item>
      </a-row>
      <a-row>
        <a-form-item
          label="说明书内容"
          :labelCol="{span: 4}"
          :wrapperCol="{span: 19}"
          :help="()=>{const r = form.getFieldValue('specification'); return `(${!r? 0 : r.length > 2000 ? 2000 : r.length}/2000)`}">
          <a-textarea
            placeholder="请输入说明书内容"
            v-decorator="['specification', {rules:[{required: false, message: '请输入说明书内容'}]}]"
            :maxLength="2000"
            :rows="5">
          </a-textarea>
        </a-form-item>
      </a-row>
    </a-form>
  </a-modal>
</template>

<script>
import moment from 'moment'
export default {
  components: {
  },
  data () {
    return {
      title: '',
      labelCol: {
        xs: { span: 24 },
        sm: { span: 8 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 14 }
      },
      visible: false,
      isCreat: false,
      confirmLoading: false,
      id: 0,
      form: this.$form.createForm(this),
      isAddChangeStatus: false,
      isEditChangeStatus: false,
      projectId: -1,
      companyId: -1
    }
  },
  methods: {
    moment,
    // 专利列表界面=》添加专利方法
    add () {
      this.isCreat = false
      this.title = '添加专利'
      this.visible = true
      this.id = -1
      this.form.resetFields()
    },

    // 申请列表界面=》添加专利方法
    addAndchengeStatus (record) {
      this.isCreat = false
      this.title = `关联专利[${record.companyName}]`
      this.visible = true
      this.id = record.id
      this.projectId = record.projectId
      this.companyId = record.companyId
      this.form.resetFields()
      let setFieldsValueObj = {}
      if (!record.patentNo) {
        this.isAddChangeStatus = true
        setFieldsValueObj = { patentName: record.patentName }
      } else {
        this.isEditChangeStatus = true
        setFieldsValueObj = {
          patentNo: record.patentNo,
          patentName: record.patentName,
          mainType: record.mainType === null ? undefined : record.mainType,
          applyDateTime: !record.applyDateTime || this.moment(record.applyDateTime),
          authDate: !record.authDate || this.moment(record.authDate),
          inventor: record.inventor
        }
      }
      this.$nextTick(() => {
        this.form.setFieldsValue(setFieldsValueObj)
      })
    },
    // 专利列表界面=》编辑专利方法
    edit (record) {
      this.isCreat = true
      this.title = `修改[${record.patentNo}]类型`
      this.visible = true
      this.id = record.id
      this.loadBigText(record)
    },
    loadBigText (record) {
      this.spinning = true
      this.$http.get('/rsPatent/getSpecification', { params: { id: record.id } }).then(res => {
        if (res.success && res.data) {
          record.claimContent = res.data.claimContent
          record.specification = res.data.specification
        } else {
          this.$message.error(res.errorMessage || '获取权利要求内容/说明书内容失败')
        }
        this.$nextTick(() => {
          this.$initForm(this.form, record, ['applyDateTime', 'authDate'])
        })
      }).finally(() => {
        this.spinning = false
      })
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.visible = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          if (this.isAddChangeStatus) {
            values.status = '已申请'
            values.id = this.id
            values.projectId = this.projectId
            values.companyId = this.companyId
            this.$http.post('/rsPatentPlan/updateStatus', values).then(res => {
              if (res.success && res.data) {
                this.visible = false
                this.confirmLoading = false
                this.$emit('ok', true)
              } else {
                this.$message.info(res.errorMessage)
              }
            })
            return
          }
          if (this.isEditChangeStatus) {
            values.id = this.id
            values.newPatentNo = values.patentNo
            this.$http.post('/rsPatentPlan/updatePatentNo', values).then(res => {
              if (res.success && res.data) {
                this.visible = false
                this.confirmLoading = false
                this.$emit('ok', true)
              } else {
                this.$message.info(res.errorMessage)
              }
            })
            return
          }
          if (this.id === -1) {
            this.$http.post('/rsPatent/addPatent', values)
              .then(res => {
                if (res.success && res.data) {
                  this.visible = false
                  this.confirmLoading = false
                  this.$emit('ok', true)
                } else {
                  this.$message.info(res.errorMessage)
                }
              }).finally(res => {
                this.confirmLoading = false
              })
          } else {
            if (values.mainType === null) {
              this.$message.error('必须选择专利类型')
              this.confirmLoading = false
              return
            }
            values.id = this.id
            this.$http.post('/rsPatent/update', values)
              .then(res => {
                if (res.success && res.data) {
                  this.visible = false
                  this.confirmLoading = false
                  this.$emit('ok', false)
                } else {
                  this.$message.info(res.errorMessage)
                }
              }).finally(res => {
                this.confirmLoading = false
              })
          }
        } else {
          this.confirmLoading = false
        }
      })
    },
    checkpatentNo () {
      const patentNo = this.form.getFieldValue('patentNo')
      if (patentNo) {
        this.$http.get('/rsPatent/checkPatentNo', { params: { patentNo: patentNo } })
          .then(res => {
            if (!res.data) {
              this.checked = false
              this.form.setFields({ 'patentNo': { value: patentNo, errors: [new Error('该专利号已存在')] } })
            } else {
              this.checked = true
              this.form.setFields({ 'patentNo': { value: patentNo } })
            }
          })
      }
    },
    afterClose () {
      this.form.resetFields()
      this.title = ''
      this.projectId = -1
      this.companyId = -1
      this.isAddChangeStatus = false
      this.isEditChangeStatus = false
    }
  }
}
</script>

<style scoped>
.ant-form-item-children .ant-calendar-picker {
  width: 100%;
}
