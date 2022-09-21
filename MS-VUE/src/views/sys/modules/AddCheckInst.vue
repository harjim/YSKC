<template>
  <a-modal
    :title="`${edit ? '编辑' : '添加'}查新机构`"
    :width="1000"
    destroyOnClose
    :maskClosable="false"
    :visible="visible"
    :confirmLoading="loading"
    @cancel="close"
    @ok="handleSubmit"
    :okButtonProps="{props: { disabled: btnDisabled }}"
  >
    <a-form-model :model="form" ref="form" :rules="rules" :labelCol="{ span: 6 }">
      <a-row>
        <a-col :span="12">
          <a-form-model-item required label="机构名称" prop="instName">
            <a-input style="width: 300px;" v-model="form.instName" placeholder="请输入机构名称"/>
          </a-form-model-item>
        </a-col>
        <a-col :span="12">
          <a-form-model-item required label="账户名" prop="accountName">
            <a-input style="width: 300px;" v-model="form.accountName" placeholder="请输入账户名"/>
          </a-form-model-item>
        </a-col>
      </a-row>

      <a-row>
        <a-col :span="12">
          <a-form-model-item required label="开户行" prop="accountBank">
            <a-input style="width: 300px;" v-model="form.accountBank" placeholder="请输入开户行"/>
          </a-form-model-item>
        </a-col>
        <a-col :span="12">
          <a-form-model-item required label="账号" prop="accountNo">
            <a-input style="width: 300px;" v-model="form.accountNo" placeholder="请输入账号"/>
          </a-form-model-item>
        </a-col>
      </a-row>

      <a-row>
        <a-col :span="24">
          <a-form-model-item label="收费标准" :labelCol="{ span: 3 }">
            <a-table
              bordered
              :dataSource="form.list"
              :columns="columns"
              :pagination="false"
              :rowKey="r => r.id || new Date().getTime()"
            >
              <template slot="daysTitle">
                <span>完成时间(工作日)</span>
                <a-tooltip
                  style="cursor:pointer"
                  placement="top"
                >
                  <template slot="title">完成时间需大于上一条记录的完成时间</template>
                  <a-icon style="margin-left: 4px; color: rgba(0, 0, 0, .6)" type="info-circle"/>
                </a-tooltip>
              </template>
              <template slot="days" slot-scope="text, record, index">
                <a-input-number
                  v-if="record.editable"
                  style="width: 100%;"
                  :min="minCheck(index)"
                  :value="text"
                  @change="v => handleChange(v, index, 'days')"
                />
                <span v-else>{{ text }}</span>
              </template>
              <template slot="amount" slot-scope="text, record, index">
                <a-input-number
                  v-if="record.editable"
                  style="width: 100%;"
                  :min="0"
                  :step="0.01"
                  :value="text"
                  @change="v => handleChange(v, index, 'amount')"
                />
                <span v-else>{{ text }}</span>
              </template>
              <template slot="checkType" slot-scope="text, record, index">
                <a-select
                  v-if="record.editable"
                  :default-value="text"
                  @change="v => handleChange(v, index, 'checkType')">
                  <a-select-option :value="0">国内查新</a-select-option>
                  <a-select-option :value="1">国内外查新</a-select-option>
                  <a-select-option :value="2">国外查新</a-select-option>
                </a-select>
                <span v-else>{{ text === 0 ? '国内查新' : (text === 1 ? '国内外查新' : '国外查新') }}</span>
              </template>
              <template slot="operation" slot-scope="text, record, index">
                <template v-if="record.editable">
                  <a style="margin-right: 6px;" @click="save(index)">保存</a>
                  <a-popconfirm title="是否确认取消？" @confirm="cancel(index)">
                    <a>取消</a>
                  </a-popconfirm>
                </template>
                <a v-else @click="editRow(index)">编辑</a>
              </template>
              <template slot="footer">
                <a-button type="dashed" icon="plus" block :disabled="btnDisabled" @click="addTableRow">添加</a-button>
              </template>
            </a-table>
          </a-form-model-item>
        </a-col>
      </a-row>

      <div style="margin-top: 16px;">
        <a-row>
          <a-col :span="12">
            <a-form-model-item required label="快递费" prop="postage">
              <a-input-number
                style="width: 300px;"
                :min="0"
                v-model="form.postage"
                placeholder="请输入快递费"/>
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="付款备注">
              <a-input style="width: 300px;" v-model="form.payRemark" placeholder="请输入付款备注"/>
            </a-form-model-item>
          </a-col>
        </a-row>

        <a-row>
          <a-col :span="24">
            <a-form-model-item label="其他备注" :labelCol="{ span: 3 }">
              <a-textarea
                style="width: 778px;"
                v-model="form.remark"
                placeholder="请输入"
                :auto-size="{ minRows: 2, maxRows: 6 }"/>
            </a-form-model-item>
          </a-col>
        </a-row>

        <a-row>
          <a-col :span="12">
            <a-form-model-item label="联系人">
              <a-input style="width: 300px;" v-model="form.linkMan" placeholder="请输入联系人"/>
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="联系电话">
              <a-input style="width: 300px;" v-model="form.linkTel" placeholder="请输入联系电话"/>
            </a-form-model-item>
          </a-col>
        </a-row>

        <a-row>
          <a-col :span="24">
            <a-form-model-item label="附件" :labelCol="{ span: 3 }" :wrapperCol="{ span: 20 }">
              <a-upload
                :fileList="fileList"
                :multiple="true"
                :showUploadList="{showPreviewIcon: true, showDownloadIcon:true, showRemoveIcon: true}"
                :before-upload="beforeUpload"
                @change="uploadHandleChange"
                @preview="onPreview"
                @download="downloadFile"
              >
                <a-button>
                  <a-icon type="upload"/>
                  上传附件
                </a-button>
              </a-upload>
            </a-form-model-item>
          </a-col>
        </a-row>
      </div>
    </a-form-model>
  </a-modal>
</template>

<script>
import { message } from 'ant-design-vue'

const initListItem = {
  days: undefined,
  amount: undefined,
  checkType: undefined,
  editable: true,
  isNew: true
}

export default {
  name: 'AddCheckInst',
  props: {
    preview: {
      type: Function,
      required: true
    }
  },
  data () {
    this.cacheTable = []
    return {
      columns: [
        { title: '序号', key: 'num', customRender: (t, r, i) => `${i + 1}`, width: 80, align: 'center' },
        {
          dataIndex: 'days',
          key: 'days',
          width: 160,
          align: 'center',
          scopedSlots: { title: 'daysTitle', customRender: 'days' }
        },
        {
          title: '金额(元)',
          key: 'amount',
          dataIndex: 'amount',
          width: 200,
          align: 'right',
          scopedSlots: { customRender: 'amount' }
        },
        {
          title: '查新范围',
          key: 'checkType',
          dataIndex: 'checkType',
          width: 240,
          scopedSlots: { customRender: 'checkType' }
        },
        {
          title: '操作',
          key: 'operation',
          width: 100,
          align: 'center',
          scopedSlots: { customRender: 'operation' }
        }
      ],
      visible: false,
      edit: false,
      loading: false,
      form: {},
      fileList: [],
      rules: {
        instName: [{ required: true, message: '请输入机构名称' }],
        accountName: [{ required: true, message: '请输入账户名' }],
        accountBank: [{ required: true, message: '请输入开户行' }],
        accountNo: [{ required: true, message: '请输入账号' }],
        postage: [{ required: true, message: '请输入快递费' }]
      }
    }
  },
  computed: {
    btnDisabled () {
      if (!this.form.list) return false
      const { list } = this.form
      for (let i = 0; i < list.length; i++) {
        if (list[i].editable) return true
      }
      return false
    }
  },
  methods: {
    open (data) {
      if (data) {
        this.cacheTable = data.list ? data.list.map(item => ({ ...item })) : []
        this.form = { ...data, list: this.cacheTable.map(item => ({ ...item })) }
        this.edit = true
        const files = data.filePath ? data.filePath.split(',') : []
        this.fileList = files.length ? files.map(i => ({
          uid: i.substring(i.lastIndexOf('/') + 1, i.length),
          name: i.substring(i.lastIndexOf('/') + 1, i.length),
          status: 'done',
          url: i
        })) : null
      } else {
        this.form = {}
        this.cacheTable = []
        this.edit = false
      }
      this.visible = true
    },
    close () {
      this.visible = false
      this.form = {}
      this.fileList = []
    },
    handleChange (v, i, k) {
      this.form.list[i][k] = v
    },
    minCheck (i) {
      if (i === 0) return 1
      const { list } = this.form
      return list[i - 1].days + 1 || 1
    },
    editRow (i) {
      const list = this.form.list.map(item => ({ ...item }))
      list[i].editable = true
      this.form.list = list
    },
    cancel (i) {
      const list = this.form.list
      if (list[i].isNew) {
        list.splice(i, 1)
      } else {
        list[i] = { ...this.cacheTable[i] }
      }
      this.form.list = [...list]
    },
    save (i) {
      this.form.list[i].editable = false
      this.cacheTable[i] = { ...this.form.list[i] }
    },
    addTableRow () {
      let list
      if (this.form.list) {
        list = [...this.form.list, { ...initListItem }]
      } else {
        list = [{ ...initListItem }]
      }
      this.form = {
        ...this.form,
        list
      }
    },
    beforeUpload (file, key) {
      if (!this.$checkFileSize(file, this.$message)) {
        return
      }
      const param = new FormData()
      param.append('file', file)
      param.append('dir', '/checkInst/')
      const config = {
        headers: { 'Content-Type': 'multipart/form-data' }
      }
      this.$http.post('/document/upload', param, config).then(({ success, data }) => {
        if (success) {
          this.fileList.push({
            uid: data[0].fileName,
            name: data[0].fileName,
            status: 'done',
            url: data[0].filePath
          })
        }
      })
      return false
    },
    uploadHandleChange (file) {
      if (file.file.status === 'removed') {
        this.fileList = file.fileList
      }
    },
    onPreview (file) {
      this.preview(file.url, file.name)
    },
    downloadFile (file) {
      this.$exportData('/document/downloadFile', {
        fileName: file.name,
        filePath: file.url
      }, file.name, this.$message)
    },
    handleSubmit () {
      this.$refs.form.validate(valid => {
        if (valid) {
          this.loading = true
          const filePath = this.fileList.map(item => item.url).join(',')
          const path = this.edit ? '/checkInst/updateCheckInst' : '/checkInst/addCheckInst'
          this.$http.post(path, { ...this.form, filePath }).then(({ success, errorMessage }) => {
            if (success) {
              message.success(this.edit ? '编辑成功' : '添加成功')
              this.close()
              this.$emit('refresh', true)
            } else {
              message.error(errorMessage || '添加失败')
            }
          }).finally(() => {
            this.loading = false
          })
        }
      })
    }
  }
}
</script>

<style lang="less" scoped>
.grid {
  display: grid;
  grid-template-columns: repeat(2, 50%);
  grid-gap: 10px;
}
</style>
