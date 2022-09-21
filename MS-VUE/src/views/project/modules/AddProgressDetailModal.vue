<template>
  <a-modal
    :visible="isVisible"
    :afterClose="onAfterClose"
    :maskClosable="false"
    :width="900"
    :title="title"
    @cancel="isVisible = false"
    @ok="onHandleSubmit"
  >
    <a-form :form="form">
      <a-row :gutter="24">
        <a-col :span="12">
          <a-form-item label="项目阶段" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-select
              :disabled="stageDisabled"
              placeholder="请选择项目阶段"
              v-decorator="['stageKey' , {rules:[{required: true, message: '请选择项目阶段'}]}]" >
              <a-select-option :value="item.key" v-for="item in stages" :key="item.key" :disabled="disableIndex === -1 ? item.order !== 1 : item.order !== disableIndex && item.order !== (disableIndex + 1)">{{ item.value }}</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="项目状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-select
              placeholder="请选择项目状态"
              v-decorator="['status', {rules:[{required: true, message: '请选择项目状态'}]}]">
              <a-select-option :value="item.key" v-for="item in statuss" :key="item.key" >{{ item.value }}</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="12">
          <a-form-item label="技术人员" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <search-select
              url="/user/userForSelect"
              searchField="realName"
              sTitle="realName"
              placeholder="技改工程师"
              v-decorator="['techName', {rules:[{required: true, message: '请选择技术人员'}]}]"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="项目标签" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-select
              placeholder="请选择项目标签"
              v-decorator="['labelKey', {rules:[{required: true, message: '请选择项目标签'}]}]">
              <a-select-option :value="item.key" v-for="item in labels" :key="item.key">{{ item.value }}</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="12">
          <a-form-item label="预投入金额（万）" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input-number
              style="width: 100%"
              :min="0"
              :max="999999999999999999"
              :precision="2"
              placeholder="请输入金额"
              v-decorator="['budget', {rules:[{required: true, message: '请输入金额'}]}]"
            ></a-input-number>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="完成日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-date-picker style="width: 100%;" v-decorator="['completedDate']"></a-date-picker>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="24">
          <a-form-item label="进度情况" :labelCol="{span: 4}" :wrapperCol="{span: 20}" :extra="onComputeTextarea('process',200)">
            <a-textarea
              placeholder="请输入进度情况"
              :rows="3"
              v-decorator="['process', {rules:[{required: true, whitespace: true, message: '请输入进度情况'}]}]"
            ></a-textarea>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="24">
          <a-form-item label="问题反馈" :labelCol="{span: 4}" :wrapperCol="{span: 20}" :extra="onComputeTextarea('feedback',200)">
            <a-textarea
              placeholder="请输入项目问题反馈"
              :rows="3"
              v-decorator="['feedback', {rules:[{required: true, whitespace: true, message: '请输入问题反馈'}]}]"
            ></a-textarea>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="24">
          <a-form-item label="备注" :labelCol="{span: 4}" :wrapperCol="{span: 20}" :extra="onComputeTextarea('remark',200)">
            <a-textarea
              placeholder="请输入备注"
              :rows="2"
              v-decorator="['remark', {rules:[{ whitespace: true, message: '请输入备注'}]}]"
            ></a-textarea>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>

<script>
import { SearchSelect } from '@/components/Selects'
import moment from 'moment'
export default {
  name: 'AddProgressDetailModal',
  components: {
    SearchSelect
  },
  data () {
    return {
      isVisible: false,
      isAdd: false,
      title: '',
      projectId: -1,
      form: this.$form.createForm(this),
      labelCol: {
        xs: { span: 8 },
        sm: { span: 8 }
      },
      wrapperCol: {
        xs: { span: 16 },
        sm: { span: 16 }
      },
      id: -1,
      stages: [],
      labels: [],
      statuss: [],
      disableIndex: -1,
      stageDisabled: false,
      record: {}
    }
  },
  methods: {
    moment,
    show (record, stages, labels, statuss, stagesObj) {
      this.form.resetFields()
      this.getStageList(record.productId).then((res) => {
        if (res) {
          this.stages = this.stages.map(item => {
            if (stagesObj[item.stageKey]) {
              item['key'] = item.stageKey
              item['value'] = stagesObj[item.stageKey]
            }
            if (item.stageKey === record.stageKey) {
              this.disableIndex = item.order
            }
            return item
          })
          this.record = record
          this.projectId = record.id
          this.labels = labels
          this.statuss = statuss
          this.title = '更新进度'
          this.isAdd = true
          this.isVisible = true
          this.$nextTick(() => {
            this.form.setFieldsValue({
              techName: record.techs === null ? '' : { 'id': record.techs[0].memberId, 'realName': record.techs[0].realName, 'userName': record.techs[0].realName }
            })
          })
        }
      })
    },
    edit (record, stages, labels, statuss) {
      this.record = record
      this.stages = stages
      this.labels = labels
      this.statuss = statuss
      this.form.resetFields()
      this.stageDisabled = true
      this.isVisible = true
      this.isAdd = false
      this.title = '编辑进度'
      this.projectId = record.projectId
      this.id = record.id
      this.$nextTick(() => {
        this.form.setFieldsValue({
          stageKey: record.stageKey === null ? '' : record.stageKey,
          status: record.status === null ? '' : record.status,
          labelKey: record.labelKey === null ? '' : record.labelKey,
          techName: record.realName === null ? '' : { 'id': record.techId, 'realName': record.techName, 'userName': record.techName },
          process: record.process === null ? '' : record.process,
          feedback: record.feedback === null ? '' : record.feedback,
          remark: record.remark === null ? '' : record.remark,
          budget: record.budget === null ? '' : record.budget,
          completedDate: record.completedDate === null ? null : this.moment(record.completedDate)
        })
      })
    },
    // modal 关闭之后的方法
    onAfterClose () {
      this.form.resetFields()
      this.isAdd = false
      this.isVisible = false
      this.projectId = -1
      this.id = -1
      this.stages = []
      this.labels = []
      this.statuss = []
      this.disableIndex = -1
      this.stageDisabled = false
      this.record = {}
    },
    // 提交事件
    onHandleSubmit () {
      this.form.validateFields((errors, values) => {
        if (!errors) {
          values.techObj = values.techName
          values.techName = values.techObj.realName
          values.techId = values.techObj.id
          values.projectId = this.projectId
          if (this.id > 0) {
            values.id = this.id
          }
          const url = this.isAdd ? '/projectTechLog/addTechLog' : '/projectTechLog/updateTechLog'
          this.$http.post(url, values).then((res) => {
            if (res.data && res.success) {
              this.$message.success(this.isAdd ? '添加成功' : '更新成功')
              this.isVisible = false
              if (this.isAdd) {
                this.$emit('ok', true)
              } else {
                this.$emit('refreshData', this.record, values)
              }
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
        }
      })
    },
    // 获取可以选择的项目技改阶段列表
    getStageList (productId) {
      return this.$http.get('/productTechStage/queryProjectStage', { params: { productId } }).then((res) => {
        if (res.data && res.success) {
          this.stages = res.data
          let result = true
          if (res.data.length <= 0) {
            this.$message.warning('请配置好阶段，再进行操作')
            result = false
          }
          return new Promise((resolve) => {
            resolve(result)
          })
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

<style>
</style>
