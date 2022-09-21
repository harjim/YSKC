<!--
    * @Author:
    * @Date: 2022-09-06 18:32:30
    * @Description:
-->
<template>
  <a-form
    layout="inline"
    ref="dailyForm"
    :form="form"
    :label-col="labelCol"
    :wrapper-col="wrapperCol"
    :class="canEdit ? 'showEdit': 'onlyShow'"
  >
    <a-form-item label="客户名称">
      <search-select
        url="/customer/getCustomerOwner"
        searchField="customerName"
        sTitle="companyName"
        placeholder="客户名称"
        @change="changeCustomer"
        v-decorator="['customer', {rules: [{ required: canEdit, message: '请输入客户名称' }]}]"
        sKey="customerId"
        :readonly="!canEdit"
      />
    </a-form-item>
    <a-form-item label="业务员">
      <search-select
        style="width: 240px"
        url="/user/userForSelect"
        searchField="realName"
        sTitle="realName"
        :multiple="false"
        placeholder="请输入业务员"
        @change="changeOwner"
        v-decorator="['owner', {rules: [{ required: canEdit, message: '请输入业务员' }]}]"
        :readonly="!canEdit"
      />
    </a-form-item>
    <a-form-item label="所属部门">
      <dept-select
        v-if="canEdit"
        style="width: 240px;"
        placeholder="请选择所属部门"
        v-decorator="['deptId']"
        :onlyShow="!canEdit" />
      <span v-else>{{ initData.deptName }}</span>
    </a-form-item>
    <a-form-item label="事项">
      <a-select
        v-if="canEdit"
        placeholder="请选择事项"
        @change="itemTypeChange"
        v-decorator="['itemType', {rules: [{ required: true, message: '请选择事项' }]}]"
      >
        <a-select-option v-for="(value,key) in reasons" :key="value" :value="key">{{ value }}</a-select-option>
      </a-select>
      <span v-else>{{ reasons[initData.itemType] }}</span>
    </a-form-item>
    <a-form-item label="日期">
      <a-date-picker
        v-if="canEdit"
        valueFormat="YYYY-MM-DD hh:mm:ss"
        inputReadOnly
        v-decorator="['workDate', {rules: [{ required: true, message: '请选择日期' }]}]"
      ></a-date-picker>
      <span v-else>{{ initData.workDate }}</span>
    </a-form-item>
    <a-form-item label="内容">
      <a-textarea
        v-if="canEdit"
        :autoSize="{minRows: 4, maxRows: 4}"
        placeholder="请输入内容"
        v-decorator="['content', {rules: [{ required: true, message: '请输入内容' }, {max: 1000, message: '内容最大长度为1000'}]}]"
      />
      <span v-else>{{ initData.content }}</span>
    </a-form-item>
    <a-form-item label="附件">
      <a-upload
        v-if="canEdit"
        :file-list="fileList"
        :before-upload="beforeUpload"
        v-model="fileList"
        @preview="downloadFile"
        @change="removeFile"
      >
        <a-button icon="upload">上传附件</a-button>
      </a-upload>
      <template v-else>
        <template v-if="initData.filePath && initData.filePath.length">
          <span v-for="(filePath, index) in initData.filePath" :key="index" style="display: inline-block; margin-right: 20px;">
            <a @click="downloadFile(filePath)">
              {{ getFileName(filePath) }}</a>
            <a-tooltip style="cursor:pointer" placement="top" @click="preview(filePath)">
              <template slot="title">
                <span>预览</span>
              </template>
              <a-icon type="eye" style="margin-left:5px" />
            </a-tooltip>
          </span>
        </template>
        <template v-else>-</template>
      </template>
    </a-form-item>
    <preview-modal ref="previewModal" />
  </a-form>
</template>

<script>
import DeptSelect from '@/components/Selects/DeptSelect'
import SearchSelect from '@/components/Selects/SearchSelect'
import { isEditStatus } from '@/utils/processDoc/auditStatus'
import PreviewModal from '@/components/PreviewModal'
export default {
  data () {
    const labelCol = {
      xs: { span: 24 },
      sm: { span: 5 }
    }
    const wrapperCol = {
      xs: { span: 24 },
      sm: { span: 16 }
    }
    return {
      labelCol,
      wrapperCol,
      fileList: [],
      initData: {},
      owner: null,
      customer: null,
      form: this.$form.createForm(this),
      canEdit: true
    }
  },
  components: {
    DeptSelect,
    SearchSelect,
    PreviewModal
  },
  props: {
    reasons: {
      type: Object,
      default: () => {}
    }
  },
  methods: {
    /** 初始化表单
     * @value owner { id: key, realName: value}
     * @value customer { customerId: key, companyName: value}
     */
    fromInit (row) {
      this.initData.id = row.id
      this.initData.itemType = row.itemType
      this.initData.workDate = row.workDate
      this.initData.content = row.content
      this.initData.filepath = row.filepath
      this.initData.deptName = row.deptName

      const owner = { id: row.ownerId, realName: row.owner }
      const customer = { customerId: row.customerId, companyName: row.companyName }
      this.$initForm(this.form, { ...row, owner, customer }, ['workDate'])

      this.canEdit = isEditStatus(row.status) && this.$auth('innovation:finaDaily:edit')

      // 初始化导入组件 fileList
      if (row.filepath && row.filepath.trim() !== '') {
        if (this.canEdit) {
          this.fileList = row.filepath.split(',').map(fileUrl => {
            const fileName = this.getFileName(fileUrl)
            return {
              uid: fileName,
              name: fileName,
              status: 'done',
              url: fileUrl
            }
          })
        } else {
          this.initData.filePath = row.filepath.split(',')
        }
      }
    },
    /** 预览文件 */
    preview (filePath) {
      if (filePath === '') {
        this.$message.info('请先上传文件')
        return
      }
      const fileName = this.getFileName(filePath)
      this.$refs.previewModal.show(filePath, fileName)
    },
    /** 获取文件名 */
    getFileName (filePath) {
      return filePath ? filePath.substring(filePath.lastIndexOf('/') + 1).replace(/\d+/, '') : ''
    },
    /** 上传文件 */
    beforeUpload (file, key) {
      // 是否超过限制大小
      if (!this.$checkFileSize(file, this.$message)) {
        return false
      }
      // 是否重复上传
      const files = this.fileList.map((item) => item.name)
      if (files.includes(file.name)) {
        this.$message.error('文件名重复')
        return false
      }

      const param = new FormData()
      param.append('file', file)
      param.append('dir', '/finaDaily/')
      const config = {
        // 添加请求头
        headers: { 'Content-Type': 'multipart/form-data' }
      }
      this.$http.post('/document/upload', param, config).then(({ data, success, errorMessage }) => {
        if (success) {
          this.fileList.push(
            {
              uid: data[0].fileName,
              name: data[0].fileName,
              status: 'done',
              url: data[0].filePath
            }
          )
          // this.$refs.form.clearValidate('filepath')
        } else {
          this.$message.error(errorMessage)
        }
      })
      return false
    },
    /** 下载文件 */
    downloadFile (file) {
      const fileName = this.getFileName(file.url)
      this.$exportData('/document/downloadFile', { fileName, filePath: file.url }, fileName, this.$message)
    },
    /** 移除文件 */
    removeFile (file) {
      this.fileList = file.fileList
    },
    /** 公司名称修改 */
    changeCustomer (value) {
      if (!value) return
      let owner
      const deptId = value.deptId
      // 获取对应的业务员id和部门
      if (value.ownerId && value.ownerId !== '') {
        owner = { id: 1, deptId: value.deptId, deptName: value.deptName, realName: value.ownerName, userName: value.ownerId }
      }
      if (owner && deptId) {
        this.form.setFieldsValue({ owner, deptId })
      }
    },
    /** 业务员改变 */
    changeOwner (value, option) {
      this.form.ownerId = value.id
      this.form.setFieldsValue({ deptId: value.deptId })
    },
    /** 事项改变 */
    itemTypeChange (value, option) {
      this.initData.itemTypeValue = option.data.key
    },
    /** 表单校验 */
    validate () {
      let error
      this.form.validateFields((e) => { error = e })
      return error
    },
    /** 提交表单 */
    async handleSubmit (url) {
      const values = this.form.getFieldsValue()
      const customerId = values.customer.customerId
      const companyName = values.customer.companyName
      const ownerId = values.owner.id
      const formData = { ...this.initData, ...values, customerId, companyName, ownerId }
      delete formData.customer
      delete formData.owner
      delete formData.filePath
      delete formData.deptName
      formData.filepath = this.filePathToString()
      this.$http.post(url, formData).then(res => {
        if (res.success) {
          this.$message.success('操作成功')
          // 关闭表单
          this.$emit('handleOk')
        } else {
          this.$message.error(res.errorMessage || '操作失败')
        }
      }).catch(res => {
        this.$message.error(res.errorMessage || '系统异常，请联系管理员')
      })
    },
    /** 文件列表转字符串存数据库 文件之间以 ',' 分割 */
    filePathToString () {
      if (this.fileList.length > 0) {
        return this.fileList.map(item => item.url).join(',')
      } else {
        return null
      }
    }
  }
}
</script>

<style lang="less">
.ant-form-inline{
  // 错误提示样式
  .ant-form-explain {
    position: absolute;
  }
  .ant-form-item-with-help {
    margin-bottom: 0 !important;
  }

}
</style>
