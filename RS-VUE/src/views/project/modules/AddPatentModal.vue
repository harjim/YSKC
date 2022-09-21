<template>
  <a-modal
    :width="800"
    :visible="visible"
    :title="title"
    :maskClosable="false"
    @ok="handleSubmit"
    @cancel="visible = false"
    :afterClose="afterClose"
    :confirmLoading="confirmLoading"
  >
    <a-spin :spinning="spinning" tip="请稍后...">
      <a-form @submit="handleSubmit" :form="form">
        <a-row>
          <a-col :span="12">
            <a-form-item label="专利号/申请号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                placeholder="请输入专利号/申请号"
                style="width: 65%"
                v-decorator="['patentNo', {rules:[{required: true, message: '请输入专利号/申请号'}]}]"
                @blur="o=>checkPatentNo(o)"
                :disabled="isDisabled"
              /><a-button style="width: 35%" @click="getPatentData" v-if="isMsUser" >获取数据</a-button>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="专利名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                placeholder="请输入专利名称"
                v-decorator="['patentName', {rules:[{required: true, message: '请输入专利名称'}]}]"
                :disabled="isDisabled"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="专利类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-select v-decorator="['mainType', {rules:[{required: true, message: '请输入专利类型'}]}]" placeholder="请选择专利类型">
                <a-select-option value="发明专利">发明专利</a-select-option>
                <a-select-option value="实用新型">实用新型</a-select-option>
                <a-select-option value="外观设计">外观设计</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="发明人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                placeholder="请输入发明人"
                v-decorator="['inventor']"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="申请日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-date-picker
                style="width:100%"
                format="YYYY-MM-DD"
                placeholder="请选择申请日期"
                v-decorator="['applyDateTime', {rules:[{required: true, message: '请选择申请日期'}]}]"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="授权日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-date-picker
                style="width:100%"
                format="YYYY-MM-DD"
                placeholder="请选择授权日期"
                v-decorator="['authDate', {rules:[{required: true, message: '请选择授权日期'}]}]"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="权利要求数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number
                :max="$store.state.maxOrder"
                :min="0"
                :precision="0"
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
                placeholder="请输入使用次数"
                v-decorator="['usedCnt', {rules:[{required: true, message: '请输入使用次数'}]}]"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-form-item
            label="摘要"
            :labelCol="{span: 4}"
            :wrapperCol="{span: 19}"
            :help="()=>{const r = form.getFieldValue('summary'); return `(${!r? 0 : r.length > 1000 ? 1000 : r.length}/1000)`}">
            <a-textarea
              placeholder="请输入摘要"
              v-decorator="['summary', {rules:[{required: true, message: '请输入摘要'}]}]"
              :maxLength="1000"
              :rows="3">
            </a-textarea>
          </a-form-item>
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
    </a-spin>
  </a-modal>
</template>

<script>
import moment from 'moment'
import SelectProject from '@/components/SelectProject'
import { isMsUser } from '@/utils/processDoc/auditStatus'
import { addHandler, readPatent } from '@/utils/util'
const source = 'new'
export default {
  components: {
    SelectProject
  },
  mounted () {
    addHandler(source, d => {
      if (!d.hasError) {
        this.$initForm(this.form, { ...d.result, usedCnt: this.form.getFieldValue('usedCnt') }, ['authDate', 'applyDateTime'])
      } else {
        this.$message.error(d.msg)
      }
    })
  },
  computed: {
    isMsUser () {
      return isMsUser()
    }
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
      confirmLoading: false,
      year: 0,
      visible: false,
      form: this.$form.createForm(this),
      submitState: false,
      // projectList: [],
      isDisabled: false,
      spinning: false,
      id: 0,
      patentWindow: undefined
    }
  },
  methods: {
    getPatentData () {
      this.form.validateFields(['patentNo'], (errors) => {
        if (!errors) {
          readPatent(source, 'detail', { patentNo: this.form.getFieldValue('patentNo') })
        }
      })
    },
    checkPatentNo (o) {
      return this.$http.get('/patentDetail/checkPatentSole', { params: { patentNo: o.target.value } })
        .then(res => {
          if (!res.data) {
            this.form.setFields({ 'patentNo': { value: o.target.value, errors: [new Error('该项目已存在相同专利号!')] } })
            this.submitState = true
          } else {
            this.form.setFields({ 'patentNo': { value: o.target.value } })
            this.submitState = false
          }
          return res.data
        })
    },
    moment,
    add (year) {
      this.title = '添加专利'
      this.form.resetFields()
      this.id = 0
      this.year = year
      this.isDisabled = false
      this.visible = true
      this.confirmLoading = false
    },
    edit (record, year) {
      this.year = year
      this.isDisabled = true
      this.title = `编辑专利`
      this.form.resetFields()
      this.visible = true
      this.id = record.id
      this.loadBigText(record)
    },
    loadBigText (record) {
      this.spinning = true
      this.$http.get('/patentDetail/getSpecification', { params: { id: record.id } }).then(res => {
        if (res.success && res.data) {
          record.claimContent = res.data.claimContent
          record.specification = res.data.specification
        } else {
          this.$message.error(res.errorMessage || '获取权利要求内容/说明书内容失败')
        }
        this.$nextTick(() => {
          this.$initForm(this.form, record, ['authDate', 'applyDateTime'])
          // if (record.applyDateTime) {
          //   this.form.setFieldsValue({
          //     'applyDateTime': moment(record.applyDateTime, 'YYYY-MM-DD HH:mm:ss')
          //   })
          // }
        })
      }).finally(() => {
        this.spinning = false
      })
    },
    handleSubmit () {
      if (this.submitState) {
        return
      }
      const { form: { validateFields } } = this
      this.visible = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          if (this.id === 0) {
            this.$http.post('/patentDetail/addPatent', values)
              .then(res => {
                if (res.success && res.data) {
                  this.visible = false
                  this.$emit('ok', true)
                } else {
                  this.$message.error(res.errorMessage ? res.errorMessage : '添加失败')
                }
              }).finally(res => {
                this.confirmLoading = false
              })
          } else {
            values.id = this.id
            this.$http.post('/patentDetail/editPatent', values)
              .then(res => {
                if (res.success && res.data) {
                  this.visible = false
                  this.$emit('ok', false)
                } else {
                  this.$message.error(res.errorMessage ? res.errorMessage : '更新失败')
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
    // loadProject () {
    //   return this.$http.get('/project/queryProject', { params: { year: this.year } })
    //     .then(res => {
    //       if (res.data != null && res.data.length > 0) {
    //         this.projectList = res.data
    //         return this.projectList
    //       }
    //     })
    // },
    afterClose () {
      this.confirmLoading = false
      this.year = 0
      this.visible = false
      this.submitState = false
      // this.projectList = []
      this.isDisabled = false
      this.id = 0
    }
  }
}
</script>

<style scoped>
.ant-form-item-children .ant-input-number {
  width: 100%;
}
</style>
