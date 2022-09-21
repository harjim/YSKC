<!--
 * @Author: ldx
 * @Date: 2021-07-08 11:13:40
 * @LastEditTime: 2022-02-10 15:19:50
 * @LastEditors: lzh
 * @Description: 需求表单
 * @FilePath: \MS-VUE\src\views\patent\modules\DemandFormModal.vue
-->
<template>
  <a-modal
    :title="title"
    :width="800"
    :visible="isVisible"
    :afterClose="afterClose"
    :maskClosable="false"
    @cancel="isVisible = false"
    :bodyStyle="{ padding: '12px',maxHeight: '70vh', overflow: 'auto' }"
  >
    <a-form :form="form">
      <a-row :gutter="24">
        <a-col :span="24">
          <a-form-item label="客户名称" :labelCol="{ sm:{span:3}}" :wrapperCol="{sm: {span: 21}}">
            <span v-if="isDisable"> {{ record.companyName ? record.companyName : '-' }}</span>
            <search-select
              v-else
              :disabled="Boolean(updatedId)"
              style="width: 100%"
              url="/customer/getCustomerSelect"
              searchField="customerName"
              placeholder="请输入客户名称"
              v-decorator="['customerId', {rules:[{required: true, message: '请输入客户名称'}]}]"
            />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="12">
          <a-form-item label="年份" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <span v-if="isDisable"> {{ record.year }}</span>
            <!-- <search-select
              v-else
              style="width: 282px;"
              url="/user/userForSelect"
              searchField="realName"
              sTitle="realName"
              placeholder="请输入业务员"
              v-decorator="['ownerId', {rules:[{required: true, message: '请输入业务员'}]}]"
            /> -->
            <year-select
              v-else
              :disabled="Boolean(updatedId)"
              style="width: 282px;"
              placeholder="请选择年份"
              v-decorator="['year', {rules:[{required: true, message: '请选择年份'}]}]"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="需求类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <span v-if="isDisable"> {{ record.type === 0 ? '购买' : '撰写' }}</span>
            <a-select
              v-else
              style="width: 282px;"
              placeholder="请选择需求类型"
              :disabled="Boolean(updatedId)"
              v-decorator="['type', {rules:[{required: true, message: '请选择需求类型'}]}]"
            >
              <a-select-option :value="1">撰写</a-select-option>
              <a-select-option :value="0">购买</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="12">
          <a-form-item label="业务人员" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <span v-if="isDisable"> {{ record.realName ? record.realName : '-' }}</span>
            <search-select
              v-else
              style="width: 282px;"
              url="/user/userForSelect"
              searchField="realName"
              sTitle="realName"
              placeholder="请输入业务员"
              v-decorator="['ownerId', {rules:[{required: true, message: '请输入业务员'}]}]"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="工程师" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <span v-if="isDisable"> {{ record.engineer || '-' }}</span>
            <search-select
              v-else
              style="width: 282px;"
              url="/user/userForSelect"
              searchField="realName"
              sTitle="realName"
              placeholder="请输入工程师"
              v-decorator="['engineerId', {rules:[{required: true, message: '请输入工程师'}]}]"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="发明专利" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <span v-if="isDisable"> {{ record.inventionNum ? record.inventionNum : '-' }} 个</span>
            <a-input-number
              v-else
              style="width: 282px;"
              placeholder="请输入个数"
              v-decorator="['inventionNum']"
              :min="0"
              :max="99" ></a-input-number>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="实用新型" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <span v-if="isDisable"> {{ record.utilityNum ? record.utilityNum : '-' }} 个</span>
            <a-input-number
              v-else
              style="width: 282px;"
              placeholder="请输入个数"
              v-decorator="['utilityNum']"
              :min="0"
              :max="99" ></a-input-number>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="外观设计" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <span v-if="isDisable"> {{ record.appearanceDesignNum ? record.appearanceDesignNum: '-' }} 个</span>
            <a-input-number
              v-else
              style="width: 282px;"
              placeholder="请输入个数"
              v-decorator="['appearanceDesignNum']"
              :min="0"
              :max="99" ></a-input-number>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="12">
          <a-form-item label="需求文档" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-upload
              :fileList="demandFiles"
              :multiple="false"
              @change="(file) => changeUpload(file,demandFiles)"
              :beforeUpload="(file,fileList,) => beforeUpload(file,fileList,demandFiles)"
              :showUploadList="{showPreviewIcon: true, showDownloadIcon:true, showRemoveIcon:true}"
              v-decorator="[ 'demandUpload', { rules:[ { required: false, type:'array', transform: transformUpload, message: '请上传附件' } ] } ]"
              @preview="onPreview"
              @download="onDownloadFile"
            >
              <a-button v-if="!isDisable" :disabled="isDisable">
                <a-icon type="upload" />上传附件
              </a-button>
            </a-upload>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="发明人信息" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-upload
              :fileList="inventorInfoFiles"
              :multiple="false"
              @change="(file) => changeUpload(file,inventorInfoFiles)"
              :beforeUpload="(file,fileList) => beforeUpload(file,fileList,inventorInfoFiles)"
              :showUploadList="{showPreviewIcon: true, showDownloadIcon:true, showRemoveIcon: true}"
              v-decorator="[ 'inventorUpload', { rules:[ { required: false, type:'array', transform: transformUpload, message: '请上传附件' } ] } ]"
              @preview="onPreview"
              @download="onDownloadFile"
            >
              <a-button v-if="!isDisable" :disabled="isDisable">
                <a-icon type="upload" />上传附件
              </a-button>
            </a-upload>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="24">
          <a-form-item label="备注" :labelCol="{ sm:{span:3}}" :wrapperCol="{sm: {span: 21}}">
            <span v-if="isDisable"> {{ record.remark ? record.remark : '-' }}</span>
            <a-textarea
              v-else
              :rows="3"
              placeholder="请输入备注"
              v-decorator="['remark',{ rules: [ { required: false } ] }]"
            ></a-textarea>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
    <template #footer >
      <div style="height: 32px;">
        <a-button type="primary" style="margin-left: 10px;" @click="onComfirm" v-if="isShowComfirm && auth.confirm" :disabled="confirmLoading">确认</a-button>
        <a-button type="primary" style="margin-left: 10px;" @click="onSubmit" v-if="isShowSubmit && auth.submit" :disabled="confirmLoading">提交</a-button>
        <a-button type="primary" style="margin-left: 10px;" @click="onSave" v-if="!isDisable && (auth.editDemand || auth.addDemand)" :disabled="confirmLoading">保存</a-button>
      </div>
    </template>
  </a-modal>
</template>
<script>
import { SearchSelect, YearSelect } from '@/components/Selects'
import { upload, saveDemand, submitDemand } from '@/api/patent/demandList'

import { mapGetters } from 'vuex'
export default {
  name: 'DemandFormModal',
  components: {
    SearchSelect,
    YearSelect
  },
  props: {
    status: {
      type: Number || String,
      required: true
    },
    previewCB: {
      type: Function,
      required: true
    },
    auth: {
      type: Object,
      required: true
    }
  },
  computed: {
    ...mapGetters(['userInfo']),
    isDisable () {
      if (this.record) {
        return [1, 2, 3].includes(this.record.status * 1)
      } else {
        return false
      }
    },
    isShowComfirm () {
      if (this.record) {
        return [1].includes(this.record.status * 1)
      } else {
        return false
      }
    },
    isShowSubmit () {
      if (this.record && this.isEdit) {
        return [0].includes(this.record.status * 1)
      } else {
        return false
      }
    }
  },
  data () {
    return {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 18 }
      },
      isVisible: false,
      title: '',
      form: this.$form.createForm(this),
      demandFiles: [],
      inventorInfoFiles: [],
      record: undefined,
      updatedId: undefined,
      isEdit: false,
      confirmLoading: false
    }
  },
  methods: {
    show (title) {
      this.isVisible = true
      this.title = title
      const formData = {
        ownerId: {
          deptId: null,
          id: this.userInfo.id,
          realName: this.userInfo.realName,
          userName: this.userInfo.realName
        }
      }
      this.$nextTick(() => {
        this.form.setFieldsValue(formData)
      })
    },
    edit (record, title) {
      this.isVisible = true
      this.title = title
      this.isEdit = true
      this.record = record
      this.updatedId = this.record.id
      this.demandFiles = this.transFormStrToAry(record.filePath)
      this.inventorInfoFiles = this.transFormStrToAry(record.inventorInfo)
      if (this.isDisable) { return }
      const formData = {
        remark: record.remark,
        customerId: {
          id: record.customerId,
          title: record.companyName
        },
        ownerId: {
          deptId: null,
          id: record.ownerId,
          realName: record.realName,
          userName: record.realName
        },
        year: record.year,
        type: record.type,
        inventionNum: record.inventionNum,
        utilityNum: record.utilityNum,
        appearanceDesignNum: record.appearanceDesignNum,
        inventorUpload: { fileList: this.demandFiles },
        demandUpload: { fileList: this.inventorInfoFiles }
      }
      if (record.engineerId) {
        formData.engineerId = {
          id: record.engineerId,
          realName: record.engineer
        }
      }
      this.$nextTick(() => {
        this.form.setFieldsValue(formData)
      })
    },
    afterClose () {
      this.isVisible = false
      this.record = undefined
      this.isEdit = false
      this.form.resetFields()
      this.demandFiles = []
      this.inventorInfoFiles = []
      this.updatedId = undefined
      this.confirmLoading = false
    },
    onSave () {
      this.form.validateFields((errors, values) => {
        if (errors) { return }
        const postParams = {
          id: this.updatedId,
          remark: undefined,
          inventorInfo: this.transFormFilePath(this.inventorInfoFiles),
          filePath: this.transFormFilePath(this.demandFiles),
          status: 0,
          customerId: undefined,
          ownerId: undefined
        }
        Object.assign(postParams, values)
        postParams.customerId = values.customerId.id
        postParams.ownerId = values.ownerId.id
        postParams.engineerId = values.engineerId.id
        this.confirmLoading = true
        saveDemand(postParams).then(data => {
          if (data) {
            this.$emit('success')
            if (!this.isEdit) {
              this.isEdit = true
              const temp = {}
              Object.assign(temp, values)
              temp.status = 0
              temp.id = data
              temp.companyName = values.customerId.title
              temp.realName = values.ownerId.realName
              this.$set(this, 'record', temp)
              this.updatedId = data
            }
            this.isVisible = false
            this.$message.success('操作成功！')
          }
        }).catch(error => {
          this.$message.error(error.message || '系统异常，请联系管理员！')
        }).finally(() => {
          this.confirmLoading = false
        })
      })
    },
    onPreview ({ url: path, name }) {
      this.previewCB({ path, name })
    },
    onDownloadFile (file) {
      if (file.status === 'done') {
        this.$exportData('/patentBuying/download', { filePath: file.url }, file.name, this.$message)
      } else if (file.status === 'uploading') {
        this.$message.error('未保存不可以下载！')
      }
    },
    changeUpload (file, files) {
      if (file.file.status === 'removed') {
        const index = files.findIndex(item => item.uid === file.file.uid)
        files.splice(index, 1)
      }
    },
    beforeUpload (file, fileList, files) {
      if (!this.$checkFileSize(file, this.$message)) {
        return
      }
      const param = new FormData()
      param.append('file', file)
      param.append('dir', '/demand/')
      upload(param).then(data => {
        if (data) {
          files.push({
            uid: data.filePath,
            name: data.fileName,
            status: 'done',
            url: data.filePath
          })
        }
      }).catch(error => {
        this.$message.error(error.message || '系统异常，请联系管理员！')
      })
      return false
    },
    transformUpload () {},
    transFormFilePath (fileList) {
      if (!fileList) { return undefined }
      const tempPath = fileList.map(item => item.url)
      return tempPath.join()
    },
    transFormStrToAry (filePaths) {
      if (!filePaths) return []
      const files = []
      filePaths.split(',').forEach(path => {
        const name = path.substring(path.lastIndexOf('/') + 14)
        files.push({
          uid: path,
          name,
          status: 'done',
          url: path
        })
      })
      return files
    },
    onSubmit () {
      this.$confirm({
        title: '您确定要改为【提交】状态',
        onOk: () => {
          this.handlerSubmitDemand({ id: this.record.id, status: 1 })
        }
      })
    },
    onComfirm () {
      this.$confirm({
        title: '您确定要改为【确认】状态？',
        onOk: () => {
          this.handlerSubmitDemand({ id: this.record.id, status: 2 })
        }
      })
    },
    handlerSubmitDemand (param) {
      this.confirmLoading = true
      submitDemand(param).then(data => {
        if (data) {
          this.record.status = param.status
          this.$emit('success')
          this.isVisible = false
          this.$message.success('操作成功！')
        }
      }).catch(error => {
        this.$message.error(error.message || '系统异常，请联系管理员！')
      }).finally(() => {
        this.confirmLoading = false
      })
    }
  }
}
</script>
<style lang='less' scoped>
.btn-plus {
  font-size: 12px;
  width: 20px !important;
  min-width: 20px !important;
  height: 20px !important;
  text-align:center;
}
</style>
