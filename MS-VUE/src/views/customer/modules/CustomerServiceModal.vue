<template>
  <a-modal
    style="top: 0px;"
    :width="1000"
    :visible="visible"
    :title="title"
    :maskClosable="false"
    @ok="handleSubmit"
    @cancel="visible = false"
    :afterClose="onModalafterClose"
    :zIndex="998"
    :bodyStyle="{ maxHeight:'84vh', overflow: 'auto' }"
  >
    <a-form @submit="handleSubmit" :form="form">
      <a-row :gutter="24">
        <a-col :span="18">
          <a-form-item label="客户名称" :labelCol="{sm : {span: 4} }" :wrapperCol="{sm : {span: 20} }">
            <search-select
              style="width: 100%"
              url="/customer/getCustomerSelect"
              searchField="customerName"
              placeholder="请输入客户名称"
              v-decorator="['customerId', {rules:[{required: true, message: '请输入客户名称'}]}]"
              :disabled="isdisabled"
            />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="12">
          <a-form-item
            label="服务方式"
            :labelCol="labelCol"
            :wrapperCol="{
              xs: { span: 24 },
              sm: { span: 16 }
            }"
          >
            <a-radio-group
              name="radioGroup"
              :default-value="0"
              v-model="serviceType"
              @change="onChange"
              :disabled="isdisabled"
            >
              <a-radio :value="index" v-for="(item,index) in serviceTypes" :key="index">{{ item }}</a-radio>
            </a-radio-group>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="12">
          <a-form-item label="主服务人姓名" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <search-select
              url="/user/userForSelect"
              searchField="realName"
              sTitle="realName"
              placeholder="主服务人员"
              v-decorator="['mainStaffName', {rules:[{required: true, message: '请输入主服务人员姓名'}]}]"
              @change="v => onComuptedTotalStaffCount(v, 'mainStaff')"
              :disabled="isdisabled"
            />
            <!-- <span v-else>{{ project.owerName }}</span> -->
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="其他服务人员" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <search-select
              url="/user/userForSelect"
              searchField="realName"
              sTitle="realName"
              :multiple="true"
              placeholder="请选择其他服务人员"
              v-decorator="['staffIds']"
              @change="v => onComuptedTotalStaffCount(v, 'staffs')"
              :disabled="isdisabled"
            />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="12">
          <a-form-item label="服务总人数" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input-number
              placeholder="请输入服务总人数"
              v-decorator="['totalStaff']"
              :min="0"
              :disabled="isdisabled"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="服务总工时" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input-number
              placeholder="请输入服务总工时"
              v-decorator="['totalTime']"
              :min="0"
              :disabled="isdisabled"
            />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="12">
          <a-form-item label="客户参与人员" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input
              placeholder="请输入客户参与主要人员"
              v-decorator="['customerParticipan']"
              :disabled="isdisabled"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="服务的具体部门" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input
              placeholder="请输入本次服务的具体部门/车间"
              v-decorator="['serviceDept']"
              :disabled="isdisabled"
            />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="12">
          <a-form-item
            label="开始时间"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            :disabled="isdisabled"
          >
            <a-date-picker
              style="width:100%;"
              format="YYYY-MM-DD HH:mm"
              showTime
              placeholder="请选择服务开始时间"
              v-decorator="['startDate', {rules:[{required: true, message: '请选择服务开始时间'}]}]"
              :disabled="isdisabled"
              @change="startDateChange"
              :disabledDate="handleDisabledDate"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="结束时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-date-picker
              style="width:100%;"
              format="YYYY-MM-DD HH:mm"
              showTime
              placeholder="请选择服务结束时间"
              v-decorator="['endDate', {rules:[{required: true, message: '请选择服务结束时间'}]}]"
              :disabled="isdisabled || endDataDisabeld"
              @change="endDateChange"
              :disabledDate="handleEndDisabledDate"
              :disabledTime="handleEndDisabledTime"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="天数" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input-number v-decorator="['days']" placeholder="请选择服务时间" disabled></a-input-number>
          </a-form-item>
        </a-col>
      </a-row>

      <a-form-item label="上次存在的问题解决情况" class="coutomsformItem">
        <a-textarea
          placeholder="请输入上次存在的问题解决情况"
          :rows="4"
          v-decorator="['lastProblem']"
          :disabled="isdisabled"
          :auto-size="{ minRows: 2, maxRows: 6 }"
        />
      </a-form-item>
      <a-form-item label="本次服务遇到的问题及解决方案" class="coutomsformItem">
        <a-textarea
          placeholder="请输入本次服务遇到的问题及解决方案"
          :rows="4"
          v-decorator="['nowProblem']"
          :disabled="isdisabled"
          :auto-size="{ minRows: 2, maxRows: 6 }"
        />
      </a-form-item>

      <a-form-item label="客户对本次服务的建议" class="coutomsformItem">
        <a-textarea
          placeholder="请输入客户对本次服务的建议"
          :rows="4"
          v-decorator="['customerAdvice']"
          :disabled="isdisabled"
          :auto-size="{ minRows: 2, maxRows: 6 }"
        />
      </a-form-item>
      <a-row :gutter="24">
        <a-col :span="24">
          <a-form-item
            label="附件"
            :labelCol="{ sm: { span: 2 } }"
            :wrapperCol="{ sm: { span: 20 } }"
          >
            <a-upload
              :disabled="isdisabled"
              :fileList="fileList"
              :multiple="true"
              @change="UploadHandleChange"
              :beforeUpload="beforeUpload"
              :showUploadList="{showPreviewIcon: true, showDownloadIcon:true, showRemoveIcon: status > 0 && status !==2 ? false : true}"
              v-decorator="[ 'upload', { rules:[ { required: true, type:'array', transform: transformUpload, message: '请上传附件' } ] } ]"
              @preview="onPreview"
              @download="downloadfile"
            >
              <a-button>
                <a-icon type="upload" />上传附件
              </a-button>
            </a-upload>
          </a-form-item>
        </a-col>
      </a-row>
      <a-form-item label="审核意见" class="coutomsformItem" v-if="status >= 1">
        <a-textarea
          placeholder="请输入审核意见"
          :rows="4"
          v-decorator="['auditOpinion']"
          :disabled=" status >=2 || !($auth('customer:customerServices:pass') || $auth('customer:customerServices:refuse'))"
          :auto-size="{ minRows: 2, maxRows: 6 }"
        />
      </a-form-item>
    </a-form>
    <template slot="footer">
      <a-button
        type="default"
        @click="activeModalButton('save')"
        v-if="(status===0 || status===2 || status===undefined) && $auth('customer:customerServices:draft')"
      >保存草稿</a-button>
      <a-button
        type="primary"
        @click="activeModalButton('submitStatus')"
        v-if="(status===0 || status===2 || status===undefined) && $auth('customer:customerServices:commit')"
      >提交</a-button>
      <a-button
        type="primary"
        @click="activeModalButton('pass')"
        v-if="status===1 && $auth('customer:customerServices:pass')"
      >通过</a-button>
      <a-button
        type="danger"
        @click="activeModalButton('reject')"
        v-if="status===1 && $auth('customer:customerServices:refuse')"
      >不通过</a-button>
      <a-button type="default" @click="activeModalButton('close')">取消</a-button>
    </template>
  </a-modal>
</template>

<script>
import { SearchSelect } from '@/components/Selects'
import { cloneDeep } from 'lodash'
import moment from 'moment'
export default {
  components: {
    SearchSelect
  },
  props: {
    preview: {
      type: Function,
      required: true
    }
  },
  data () {
    return {
      title: '',
      visible: false,
      serviceType: 0,
      totalStaffCount: 0,
      customer: undefined,
      startDate: null,
      startTime: null,
      endDate: null,
      id: 0,
      status,
      fileNames: [],
      files: [],
      percent: 0,
      fileList: [],
      changeStatu: 0,
      serviceTypes: ['现场对接', '电话沟通', '其他'],
      endDataDisabeld: true,
      form: this.$form.createForm(this),
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 14 }
      },
      queryParms: {
        companyName: undefined,
        tIds: []
      }
    }
  },
  computed: {
    isdisabled () {
      let result = false
      if (this.status >= 1 && this.status !== 2) {
        result = true
      }
      return result
    }
  },
  methods: {
    moment,
    /**
     * @description: 转化上传的验证值的回调函数
     * @param {*} 当前的值
     * @return {*} 验证的内容
     */
    transformUpload (value) {
      if (value && value.fileList && value.fileList.length) {
        return value.fileList
      }
      return null
    },
    downloadfile (file) {
      this.$exportData('/serviceLog/download', { id: this.id, fileName: file.name, filePath: file.url }, file.name, this.$message)
    },
    // 监听结束时间
    endDateChange (date) {
      if (date === null || date === undefined) {
        this.form.setFieldsValue({ endDate: undefined })
        this.calcDays()
        return
      }
      const startDate = this.form.getFieldValue('startDate')
      const bkDate = cloneDeep(date)
      const currentDate = moment()
      const update = { endDate: moment(bkDate) }
      if (moment(startDate).valueOf() >= moment(date).valueOf()) {
        update['endDate'] = moment(startDate)
      }
      if (currentDate <= date) {
        update['endDate'] = currentDate
      }
      this.$nextTick(() => {
        this.form.setFieldsValue(update)
      })
      this.calcDays()
    },
    // 监听开始时间
    startDateChange (date) {
      this.$nextTick(() => {
        this.form.setFieldsValue({ endDate: null })
      })
      if (date != null) {
        this.form.setFieldsValue({ startDate: moment(date) })
        this.endDataDisabeld = false
      }
      this.calcDays()
    },
    // 计算天数
    calcDays () {
      this.$nextTick(() => {
        let { startDate, endDate } = this.form.getFieldsValue(['startDate', 'endDate'])
        let days
        if (startDate && endDate) {
          startDate = moment(startDate.format('YYYY-MM-DD'))
          endDate = moment(endDate.format('YYYY-MM-DD'))
          days = endDate.diff(startDate, 'day') + 1
        }
        this.form.setFieldsValue({ days: days })
      })
    },
    range (start, end) {
      const result = []
      for (let i = start; i < end; i++) {
        result.push(i)
      }
      return result
    },
    handleDisabledTime (date) { },
    // 禁止时间
    handleEndDisabledTime (date) {
      const bkDate = cloneDeep(date)
      const startDate = moment(this.form.getFieldValue('startDate'))
      const bkstartDate = cloneDeep(this.form.getFieldValue('startDate'))
      if (moment(bkDate).startOf('day').valueOf() === startDate.startOf('day').valueOf()) {
        if (moment(bkDate).hour() === bkstartDate.hour()) {
          return {
            disabledHours: () => this.range(0, 24).splice(0, bkDate.get('hour')),
            disabledMinutes: () => this.range(bkstartDate.get('minute'), 60)
          }
        } else {
          return {
            disabledHours: () => this.range(0, 24).splice(0, bkstartDate.get('hour'))
          }
        }
      }
      if (moment(bkDate).startOf('day').valueOf() === moment().startOf('day').valueOf()) {
        return {
          disabledHours: () => this.range(moment().get('hour'), 24),
          disabledMinutes: () => this.range(moment().get('minute'), 60)
        }
      }
    },
    // 开始时间=》限制日期选择
    handleDisabledDate (current) {
      return current > moment()
    },
    // 结束时间=》限制日期选择
    handleEndDisabledDate (current) {
      const startDate = moment(this.form.getFieldValue('startDate'))
      const bkStart = cloneDeep(startDate)
      const bkCurrent = cloneDeep(current)
      if (startDate) {
        // return bkCurrent <= startDate || bkCurrent.startOf('day') > moment()
        return bkCurrent.isBefore(bkStart.startOf('day'), 'minute ') || bkCurrent.startOf('day') > moment()
      } else {
        return current
      }
    },
    /**
     * @description: 上传前事件监听
     * @param {*} file
     * @param {*} key
     * @return {*} boolean
     */
    beforeUpload (file, key) {
      if (!this.$checkFileSize(file, this.$message)) {
        return
      }
      const param = new FormData()
      param.append('file', file)
      param.append('dir', '/serviceLog/')
      const config = {
        // 添加请求头
        headers: { 'Content-Type': 'multipart/form-data' }
      }
      this.$http.post('/serviceLog/upload', param, config).then(res => {
        if (res.success) {
          this.fileList.push({
            uid: res.data.fileName,
            name: res.data.fileName,
            status: 'done',
            url: res.data.filePath
          })
        }
      }).catch(res => {
      })
      return false
    },
    /**
     * @description: 上传改变事件监听
     * @param {*} file
     * @return {*} null
     */
    UploadHandleChange (file) {
      if (file.file.status === 'removed') {
        this.fileList = file.fileList
      }
    },
    /**
     * @description:  执行提交，通过判断传入参数，执行对应按钮
     * @param {*} mark 执行标识
     * @return {*} null
     */
    activeModalButton (mark) {
      const { form: { validateFields } } = this
      if (mark === 'close') {
        this.$nextTick(() => {
          this.form.resetFields()
          this.visible = false
          this.onModalafterClose()
        })
      }
      this.visible = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          const auditOpinion = this.form.getFieldsValue(['auditOpinion'])
          values.customerId = values.customerId.id
          values.serviceType = this.serviceType
          values.mainStaffId = values.mainStaffName.id
          values.id = this.id
          const ids = []
          const paths = []
          if (this.fileList.length !== 0) {
            this.fileList.forEach(item => {
              paths.push(item.url)
            })
            if (paths.length > 0) {
              values.filePaths = paths
            }
          } else {
            values.filePaths = []
          }
          if (values.staffIds) {
            values.staffIds.forEach(item => {
              ids.push(item.id)
            })
          }
          values.staffIds = ids.join(',')
          if (mark === 'save' && this.id === 0) {
            values.changeStatus = 0
            this.handleSubmit('/serviceLog/addServiceLog', values, '添加')
          }
          if (mark === 'save' && this.id !== 0) {
            values.changeStatus = 0
            this.handleSubmit('/serviceLog/editServiceLog', values, '保存')
          }
          if (mark === 'submitStatus') {
            values.changeStatus = 1
            if (this.id === 0) {
              this.handleSubmit('/serviceLog/addServiceLog', values, '提交')
            } else {
              this.handleSubmit('/serviceLog/editServiceLog', values, '提交')
            }
          }
          if (mark === 'reject') {
            values = {
              id: this.id,
              changeStatus: 2,
              auditOpinion: auditOpinion.auditOpinion
            }
            this.handleSubmit('/serviceLog/editServiceLog', values, '不通过')
          }
          if (mark === 'pass') {
            values = {
              id: this.id,
              changeStatus: 3,
              auditOpinion: auditOpinion.auditOpinion
            }
            this.handleSubmit('/serviceLog/editServiceLog', values, '通过')
          }
          this.visible = false
          this.onModalafterClose()
        }
      })
    },
    // 添加按钮初始化数据
    add () {
      this.customer = undefined
      this.title = '添加服务记录'
      this.visible = true
      this.status = undefined
    },
    // 详情按钮回显初始化数据
    edit (record) {
      this.id = record.id
      this.activeType = 'edit'
      this.title = '编辑服务记录-' + record.customerName
      this.visible = true
      this.status = record.status
      this.percent = 0
      this.serviceType = record.serviceType * 1
      this.endDataDisabeld = false
      const newStaffIds = []
      if (record.otherUsersModels.length > 0) {
        record.otherUsersModels.forEach((item) => {
          const temp = { id: item.id, realName: item.realName }
          newStaffIds.push(temp)
        })
      }
      this.totalStaffCount = newStaffIds.length + 1
      if (record.filePaths) {
        const temp = []
        record.filePaths.forEach(item => {
          const name = item.substring(item.lastIndexOf('/') + 14, item.length)
          temp.push({
            uid: name,
            name,
            status: 'done',
            url: item
          })
        })
        this.fileList = temp
      }
      this.$nextTick(() => {
        const data = {
          customerId: { id: record.customerId, title: record.customerName },
          mainStaffName: { id: record.mainUserId, realName: record.mainStaffName },
          staffIds: newStaffIds,
          totalStaff: this.totalStaffCount,
          totalTime: record.totalTime,
          customerParticipan: record.customerParticipan,
          serviceDept: record.serviceDept,
          customerAdvice: record.customerAdvice,
          nowProblem: record.nowProblem,
          lastProblem: record.lastProblem,
          startDate: moment(record.startDate),
          endDate: moment(record.endDate),
          upload: { fileList: this.fileList },
          days: record.days
        }
        if (this.status > 1) {
          data.auditOpinion = record.auditOpinion
        }
        this.form.setFieldsValue(data)
      })
    },
    // 执行按钮提交
    handleSubmit (url, values, message) {
      this.$http.post(url, values)
        .then(res => {
          if (res.success && res.data) {
            this.visible = false
            this.$emit('ok', true)
            this.form.resetFields()
            this.$message.success(`${message}成功`)
          } else {
            this.$message.error(res.errorMessage ? res.errorMessage : `${message}失败`)
          }
        }).finally(res => {
          this.confirmLoading = false
        })
    },
    // modal关闭后初始化数据
    onModalafterClose () {
      this.form.resetFields()
      this.serviceType = 0
      this.totalStaffCount = 0
      this.status = 0
      this.id = 0
      this.percent = 0
      this.fileList = []
      this.endDataDisabeld = true
    },
    // 监听服务方式改变
    onChange (e) {
      this.serviceType = e.target.value
    },
    // 计算服务总人数

    onComuptedTotalStaffCount (v, mark) {
      if (mark === 'mainStaff') {
        if (v) {
          const mainstaff = this.form.getFieldsValue(['mainStaffName'])
          if (!mainstaff.mainStaffName) { this.totalStaffCount++ }
        } else {
          this.totalStaffCount--
        }
      }
      if (mark === 'staffs') {
        if (Array.isArray(v)) {
          const mainstaff = this.form.getFieldsValue(['mainStaffName'])
          if (mainstaff.mainStaffName) {
            this.totalStaffCount = v.length + 1
          } else {
            this.totalStaffCount = v.length
          }
        }
      }
      this.form.setFieldsValue({ totalStaff: this.totalStaffCount })
    },
    onPreview (file) {
      // console.log('onPreview', file)
      this.preview(file.url, file.name)
    }
  }
}
</script>

<style lang="less" scoped>
.ant-form-item-children .ant-input-number {
  width: 100%;
}
.coutomsformItem {
  padding-left: 24px;
  padding-right: 24px;
}
</style>
