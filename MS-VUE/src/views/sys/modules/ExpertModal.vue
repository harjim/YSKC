<template>
  <a-modal
    title="操作"
    style="top: 20px;"
    :width="1200"
    v-model="visible"
    @ok="handleSubmit"
    :maskClosable="false"
  >
    <a-form @submit="handleSubmit" :form="form">
      <a-row :gutter="48">
        <a-col :md="8" :sm="24">
          <a-form-item label="头像照片" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-upload
              v-decorator="['photoUrl', {rules:[{required: true, message: '请上传头像照片'}]}]"
              name="avatar"
              listType="picture-card"
              class="avatar-uploader"
              :showUploadList="false"
              :action="action"
              :beforeUpload="beforeUpload"
              @change="uploadPhoto"
              :headers="headers"
            >
              <img v-if="userPhotoUrl" :src="userPhotoUrl" alt="avatar" :width="130" :height="60" />
              <div v-else>
                <a-icon :type="loading ? 'loading' : 'plus'" />
                <div class="ant-upload-text">Upload</div>
              </div>
            </a-upload>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="48">
        <a-col :md="8" :sm="24">
          <a-form-item label="编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['expertNumber', {rules:[{required: true, message: '请输入编号'}]}]" />
          </a-form-item>
        </a-col>
        <a-col :md="8" :sm="24">
          <a-form-item label="姓名" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['realName', {rules:[{required: true, message: '请输入姓名'}]}]" />
          </a-form-item>
        </a-col>
        <!-- <a-col :md="8" :sm="24">
          <a-form-item label="头像照片" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['photoUrl', {rules:[{required: true, message: '请输入头像照片'}]}]" />
          </a-form-item>
        </a-col>-->
      </a-row>
      <a-row :gutter="48">
        <a-col :md="8" :sm="24">
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="性别">
            <a-select
              showSearch
              v-decorator="['gender', {rules:[{required: true, message: '请输入性别'}]}]"
            >
              <a-select-option value="1">男</a-select-option>
              <a-select-option value="2">女</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :md="8" :sm="24">
          <a-form-item label="生日" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-date-picker
              format="YYYY-MM-DD"
              style="width: 240px"
              v-decorator="['birthday', {rules:[{required: true, message: '请选择生日'}]}]"
            />
          </a-form-item>
        </a-col>

        <a-col :md="8" :sm="24">
          <a-form-item label="电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input
              style="width: 235px"
              v-decorator="['phone',{rules: [{ required: true, message: '请输入手机号或者电话号码!', pattern: /^((0\d{3,4}-\d{7,8})|(1[3584]\d{9}))|(0\d{2,3}-?\d{7,8})|(\d{7,8})$/}]}]"
            />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="48">
        <a-col :md="8" :sm="24">
          <a-form-item label="身份证" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input
              style="width: 240px"
              v-decorator="['idNumber', {rules:[{required: true, message: '请输入身份证',pattern:/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/}]}]"
            />
          </a-form-item>
        </a-col>
        <a-col :md="8" :sm="24">
          <a-form-item label="邮箱" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input
              v-decorator="['email', {rules:[{required: true, message: '请输入邮箱',pattern:/(\S)+[@]{1}(\S)+[.]{1}(\w)+/}]} ]"
            />
          </a-form-item>
        </a-col>
        <a-col :md="8" :sm="24">
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="政治面貌">
            <a-select
              showSearch
              v-decorator="['policitalStatus', {rules:[{required: true, message: '请输入政治面貌'}]}]"
            >
              <a-select-option value="0">群众</a-select-option>
              <a-select-option value="1">共青团员</a-select-option>
              <a-select-option value="2">党员</a-select-option>
              <a-select-option value="3">其他党派</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="48">
        <a-col :md="8" :sm="24">
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="最高学历">
            <a-select
              showSearch
              v-decorator="['eduLevel',{rules:[{required: true, message: '请选择最高学历'}]}]"
            >
              <a-select-option value="0">无</a-select-option>
              <a-select-option value="1">高中及以下</a-select-option>
              <a-select-option value="2">中专</a-select-option>
              <a-select-option value="3">大专</a-select-option>
              <a-select-option value="4">本科</a-select-option>
              <a-select-option value="5">硕士</a-select-option>
              <a-select-option value="6">博士</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :md="8" :sm="24">
          <a-form-item label="邮编" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['postcode', {rules:[{required: true, message: '请输入邮编'}]} ]" />
          </a-form-item>
        </a-col>
        <a-col :md="8" :sm="24">
          <a-form-item label="通讯地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['address', {rules:[{required: true, message: '请输入通讯地址'}]} ]" />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="48">
        <a-col :md="8" :sm="24">
          <a-form-item label="所属行业" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <!-- <a-select
              v-decorator="['industryCode', {rules:[{required: true, message: '请选择所属行业'}]}]"
              style="width:300px;float:left"
              mode="multiple"
              :maxTagCount="3"
            >
              <a-select-option value="A">农、林、牧、渔业</a-select-option>
              <a-select-option value="B">采矿业</a-select-option>
              <a-select-option value="C">制造业</a-select-option>
              <a-select-option value="D">电力、热力、燃气及水的生产和供应业</a-select-option>
              <a-select-option value="E">建筑业</a-select-option>
              <a-select-option value="F">批发和零售业</a-select-option>
              <a-select-option value="G">交通运输、仓储和邮政业</a-select-option>
              <a-select-option value="H">住宿和餐饮业</a-select-option>
              <a-select-option value="I">信息传输、软件和信息技术服务业</a-select-option>
              <a-select-option value="J">金融业</a-select-option>
              <a-select-option value="K">房地产业</a-select-option>
              <a-select-option value="L">租赁和商务服务业</a-select-option>
              <a-select-option value="M">科学研究和技术服务业</a-select-option>
              <a-select-option value="N">水利、环境和公共设施管理业</a-select-option>
              <a-select-option value="O">居民服务、修理和其他服务业</a-select-option>
              <a-select-option value="P">教育</a-select-option>
              <a-select-option value="Q">卫生和社会工作</a-select-option>
              <a-select-option value="R">文化、体育和娱乐业</a-select-option>
              <a-select-option value="S">公共管理、社会保障和社会组织</a-select-option>
              <a-select-option value="T">国际组织</a-select-option>
            </a-select>-->
            <a-input
              v-decorator="['industryCode', {rules:[{required: true, message: '请输入所属行业'}]} ]"
            />
          </a-form-item>
        </a-col>
        <a-col :md="8" :sm="24">
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="专家级别">
            <a-select
              showSearch
              v-decorator="['level',{rules:[{required: true, message: '请选择专家级别'}]}]"
            >
              <a-select-option value="0">特聘专家</a-select-option>
              <a-select-option value="1">高级研究员</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="48">
        <a-col :md="8" :sm="24">
          <a-form-item label="发证日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-date-picker
              style="width: 240px"
              format="YYYY-MM-DD"
              v-decorator="['issueDate',{rules:[{required: true, message: '请选择发证日期'}]}]"
            />
          </a-form-item>
        </a-col>
        <a-col :md="8" :sm="24">
          <a-form-item label="有效期" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-date-picker
              style="width: 240px"
              format="YYYY-MM-DD"
              :disabledDate="disabledDate"
              @change="changeValiDate"
              v-decorator="['validDate',{rules:[{required: true, message: '请选择有效期'}]}]"
            />
          </a-form-item>
        </a-col>
      </a-row>

      <a-row :gutter="48">
        <a-col>
          <a-form-item
            label="工作经历"
            :labelCol="{ xs: { span: 24 },sm: { span: 2}}"
            :wrapperCol="{ xs: { span: 24 },sm: { span: 16}}"
            :width="300"
          >
            <a-textarea
              v-decorator="['workHistory',{rules:[{required: true, message: '请输入工作经历'}]}]"
              :rows="3"
            ></a-textarea>
          </a-form-item>
        </a-col>
      </a-row>

      <a-row :gutter="48">
        <a-col :span="48">
          <a-form-item
            label="社会活动"
            :labelCol="{ xs: { span: 24 },sm: { span: 2 }}"
            :wrapperCol="{ xs: { span: 24 },sm: { span: 16 }}"
          >
            <a-textarea
              v-decorator="['partHistory',{rules:[{required: true, message: '请输入社会活动'}]}]"
              :rows="3"
            ></a-textarea>
          </a-form-item>
        </a-col>
      </a-row>

      <a-row :gutter="48">
        <a-col :span="48">
          <a-form-item
            label="获得荣誉"
            :labelCol="{ xs: { span: 24 },sm: { span: 2 }}"
            :wrapperCol="{ xs: { span: 24 },sm: { span: 16 }}"
          >
            <a-textarea v-decorator="['honour']" :rows="3"></a-textarea>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>

<script>
import { getBaseUrl } from '@/utils/request'
import { ACCESS_TOKEN } from '@/store/mutation-types'
import moment from 'moment'
function getBase64 (img, callback) {
  const reader = new FileReader()
  reader.addEventListener('load', () => callback(reader.result))
  reader.readAsDataURL(img)
}
export default {
  name: 'DeptModal',
  data () {
    return {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      },
      visible: false,
      confirmLoading: false,
      id: 0,
      mdl: {},
      form: this.$form.createForm(this),
      permissions: [],
      autoExpandParent: true,
      maxExpertNumberValue: 0,
      userPhotoUrl: '',
      photoUrl: '',
      loading: false,
      headers: { 'Access-Token': '' },
      action: getBaseUrl() + '/expert/importPhotoUrl'
    }
  },
  created () {
    const token = this.$ls.get(ACCESS_TOKEN)
    this.headers['Access-Token'] = token
  },
  methods: {
    moment,
    add (record) {
      this.title = '新建专家信息'
      this.confirmLoading = false
      this.userPhotoUrl = null
      this.loadgetMaxExpertNumber()
      this.visible = true
      this.form.resetFields()
      this.id = -1
    },
    edit (record) {
      this.title = '修改专家信息'
      this.form.resetFields()
      this.visible = true
      this.id = record.id
      this.$nextTick(() => {
        this.$initForm(this.form, record, ['issueDate', 'birthday', 'validDate'])
        if (record.photoUrl && record.photoUrl.trim() !== '') {
          this.userPhotoUrl = record.photoUrl.trim()
        }
        this.form.setFieldsValue({ photoUrl: record.photoUrl })
      })
    },
    close () {
      this.$emit('close')
      this.visible = false
    },
    handleCancel () {
      this.close()
    },
    onChange (value) {
      this.value = value
    },
    disabledDate (current) {
      return current < this.form.getFieldValue('issueDate')
    },
    beforeUpload (file) {
      const isJPG = file.type === 'image/jpeg' || 'image/png'
      if (!isJPG) {
        this.$message.error('请上传jpg或png格式的图片!')
      }
      const isLt2M = file.size / 1024 / 1024 < 2
      if (!isLt2M) {
        this.$message.error('图片大小不能超过 2MB!')
      }
      return isJPG && isLt2M
    },
    uploadPhoto (info) {
      if (info.file.status === 'uploading') {
        this.loading = true
      }
      if (info.file.status === 'done') {
        this.photoUrl = info.file.response.data
        const { form: { setFieldsValue } } = this
        setFieldsValue({ photoUrl: this.photoUrl })
        getBase64(info.file.originFileObj, (imageUrl) => {
          this.userPhotoUrl = imageUrl
          this.loading = false
        })
      }
    },
    changeValiDate (date, dateStr) {
      this.validDate = dateStr
    },
    handleDateCheck (rule, value, callback) {
      const validDateValue = this.form.getFieldValue('validDate')
      if (validDateValue === null) {
        callback()
      }
      if (moment(value) < moment(validDateValue)) {
        callback(new Error('发证日期应该小于有效期'))
      }
      callback()
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.visible = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          if (this.photoUrl) {
            values.photoUrl = this.photoUrl
          }
          if (this.id === -1) {
            this.$http.post('/expert/addExpert', values)
              .then(res => {
                if (res.success && res.data) {
                  this.visible = false
                  this.confirmLoading = false
                  this.$emit('ok', true)
                }
              }).finally(res => {
                this.confirmLoading = false
              })
          } else {
            values.id = this.id
            this.$http.post('/expert/updateExpert', values)
              .finally(res => {
                this.confirmLoading = false
                this.visible = false
                this.$emit('ok', false)
              })
          }
        } else {
          this.confirmLoading = false
        }
      })
    },
    loadgetMaxExpertNumber () {
      this.$http.post('/expert/getMaxExpertNumber')
        .then(res => {
          this.form.setFields({ 'expertNumber': { value: res.data } })
        })
    }
  }
}
</script>

<style scoped>
</style>
