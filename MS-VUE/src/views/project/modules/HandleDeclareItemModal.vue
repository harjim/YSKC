<!--
 * @Author: ldx
 * @Date: 2020-10-13 09:39:36
 * @LastEditTime: 2021-07-13 18:30:12
 * @LastEditors: ldx
 * @Description:
 * @FilePath: \MS-VUE\src\views\project\modules\HandleDeclareItemModal.vue
-->
<template>
  <a-modal
    :title="title"
    :width="800"
    :visible="isVisible"
    :afterClose="afterClose"
    :maskClosable="false"
    :bodyStyle="{ height: '60vh', overflowY: 'auto' }"
    @cancel="isVisible = false"
    @ok="handleSubmit"
  >
    <a-spin :spinning="spinning">
      <a-form :form="form">
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item
              label="项目名称"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
            >
              <a-input
                placeholder="请输入项目名称"
                v-decorator="['productName',{rules: [{ required: true, whitespace: true, message: '请输入项目名称' }] }]"
              ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item
              label="年份"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
            >
              <!-- <year-select
                v-decorator="['year', {rules:[{required: true, message: '请选择年份'}]}]"
                placeholder="请选择年份"
              /> -->
              <a-select
                placeholder="请选择年份"
                :allowClear="true"
                v-decorator="['year', {rules:[{required: true, message: '请选择年份'}]}]"
              >
                <a-select-option v-for="y in years" :key="y">{{ y }}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item
              label="地区"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
            >
              <a-cascader
                :changeOnSelect="true"
                placeholder="请选择地区"
                :fieldNames="{ label: 'value', value: 'key', children: 'children' }"
                :options="addressCode"
                v-decorator="['addressCode', {rules:[{required: true, message: '请选择地区'}]}]"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item
              label="项目类型"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
            >
              <a-select
                :options="typeLists"
                placeholder="请选择项目类型"
                v-decorator="['pType',{rules: [{ required: true, message: '请选择项目类型' }] }]"
              ></a-select>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item
              label="项目级别"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
            >
              <a-select
                :options="leveLists"
                placeholder="请选择项目级别"
                v-decorator="['pLevel',{rules: [{ required: true, message: '请选择项目级别' }] }]"
              ></a-select>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item
              label="所属机构"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
            >
              <a-input
                placeholder="请输入所属机构"
                v-decorator="['govName',{rules: [{ required: false, message: '请输入所属机构' }] }]"
              ></a-input>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="24">
            <a-form-item
              label="办公地址"
              :labelCol="{sm:4}"
              :wrapperCol="{sm: 20}"
            >
              <a-input
                placeholder="请输入办公地址"
                v-decorator="['address',{rules: [{ required: false, message: '请输入办公地址' }] }]"
              ></a-input>
            </a-form-item>
          </a-col>

        </a-row>
      </a-form>
      <a-row :gutter="24">
        <a-col :span="24">
          <vxe-grid
            ref="yst"
            auto-resize
            :resizable="true"
            id="HandleDeclareItemModal"
            highlight-hover-row
            show-overflow
            show-header-overflow
            border
            size="small"
            :data="tableData"
          >
            <vxe-table-column
              title="序号"
              :width="60"
              type="seq"
              align="center"
            ></vxe-table-column>
            <vxe-table-column
              title="方向*"
              field="direction"
              align="center"
              :width="150"
            >
              <template #header>
                <span style="color:red;">*</span>方向
              </template>
              <template v-slot="{row}">
                <a-input
                  placeholder="请输入方向"
                  v-model="row['direction']"
                ></a-input>
              </template>
            </vxe-table-column>
            <vxe-table-column
              title="截止日期*"
              field="expiryDate"
              align="center"
              :width="150"
            >
              <template #header>
                <span style="color:red;">*</span>截止日期
              </template>
              <template v-slot="{row}">
                <a-date-picker
                  placeholder="截止日期"
                  v-model="row['expiryDate']"
                ></a-date-picker>
              </template>
            </vxe-table-column>
            <vxe-table-column
              title="申请指南地址"
              field="appGuideUrl"
              align="center"
              :width="150"
            >
              <template v-slot="{row}">
                <a-input
                  placeholder="请输入申请指南地址"
                  v-model="row['appGuideUrl']"
                ></a-input>
              </template>
            </vxe-table-column>
            <vxe-table-column
              title="联系人"
              field="linkName"
              align="center"
              :width="150"
            >
              <template v-slot="{row}">
                <a-input
                  placeholder="请输入联系人"
                  v-model="row['linkName']"
                ></a-input>
              </template>
            </vxe-table-column>
            <vxe-table-column
              title="联系电话"
              field="linkTel"
              align="center"
              :width="150"
            >
              <template v-slot="{row}">
                <a-input
                  placeholder="请输入联系电话"
                  v-model="row['linkTel']"
                ></a-input>
              </template>
            </vxe-table-column>
            <vxe-table-column
              title="主方向"
              field="mainDirection"
              align="center"
              :width="150"
            >
              <template v-slot="{row}">
                <a-input
                  placeholder="请输入主方向"
                  v-model="row['mainDirection']"
                ></a-input>
              </template>
            </vxe-table-column>
            <vxe-table-column
              title="通知文号"
              field="noticeNo"
              align="center"
              :width="150"
            >
              <template v-slot="{row}">
                <a-input
                  placeholder="请输入通知文号"
                  v-model="row['noticeNo']"
                ></a-input>
              </template>
            </vxe-table-column>
            <vxe-table-column
              title="通知地址"
              field="noticeUrl"
              align="center"
              :width="150"
            >
              <template v-slot="{row}">
                <a-input
                  placeholder="请输入通知地址"
                  v-model="row['noticeUrl']"
                ></a-input>
              </template>
            </vxe-table-column>
            <vxe-table-column
              title="政策文件"
              field="files"
              align="left"
              :width="150"
            >
              <template v-slot="{row}">
                <a-upload
                  v-if="row.files.length <= 0 && $auth('project:declareProject:upload')"
                  :multiple="true"
                  :showUploadList="false"
                  :beforeUpload="(file,files)=>{uploadFile(row,file,files)}"
                >
                  <a>上传</a>
                </a-upload>
                <span
                  v-else
                  v-for="(file,index) in row.files"
                  :key="index"
                  style="margin-right:10px;"
                >
                  <a
                    title="点击下载"
                    @click="download(row,file)"
                    v-if="$auth('project:declareProject:download')"
                  >
                    {{ file.fileName }}
                  </a>
                  <span v-else>
                    {{ file.fileName }}
                  </span>
                  <a-popconfirm
                    title="是否确定删除?"
                    @confirm="delFile(row,file)"
                    style="color:red;margin-left:5px"
                  >
                    <a-tooltip placement="top">
                      <template slot="title">
                        <span>删除</span>
                      </template>
                      <a-icon type="close" />
                    </a-tooltip>
                  </a-popconfirm>
                </span>
              </template>
            </vxe-table-column>
            <vxe-table-column
              title="公示名单地址"
              field="publicItemUrl"
              align="center"
              :width="150"
            >
              <template v-slot="{row}">
                <a-input
                  placeholder="请输入公示名单地址"
                  v-model="row['publicItemUrl']"
                ></a-input>
              </template>
            </vxe-table-column>
            <vxe-table-column
              title="操作"
              align="center"
              width="100"
            >
              <template v-slot="{ row ,rowIndex}">
                <!-- <a @click="() => {}">复制当前行</a> -->
                <a-popconfirm
                  title="是否确定删除?"
                  @confirm="handleDel(row,rowIndex)"
                >
                  <a>删除</a>
                </a-popconfirm>
              </template>
            </vxe-table-column>
          </vxe-grid>
          <a-button
            type="dashed"
            style="width: 100%;font-weight: bolder"
            @click="addDirection()"
          >+</a-button>
        <!-- <div class="customAddBtn" @click="addDirection()">+</div> -->
        </a-col>
      </a-row>
    </a-spin>
  </a-modal>
</template>
<script>
import { YearSelect } from '@/components/Selects'
import { cloneDeep } from 'lodash'
import ysTable from '@/components/Table/ystable'
import moment from 'moment'
const startYear = (new Date()).getFullYear() - 4
const endYear = (new Date()).getFullYear() + 6
const years = []
for (let y = startYear; y < endYear; y++) {
  years.unshift(y)
}
export default {
  name: 'HandleDeclareItemModal',
  components: {
    YearSelect,
    ysTable
  },
  created () {
    this.$getDictionary(1).then(d => {
      this.addressCode = d
    })
    this.$getDictionary(10).then(res => {
      for (const type of res) {
        this.typeLists.push({ value: type.key, label: type.value })
      }
    })
    this.$getDictionary(11).then(res => {
      for (const type of res) {
        this.leveLists.push({ value: type.key, label: type.value })
      }
    })
  },
  data () {
    return {
      addressCode: [], // 地址编号
      isVisible: false,
      form: this.$form.createForm(this),
      labelCol: {
        xs: { span: 8 },
        sm: { span: 8 }
      },
      wrapperCol: {
        xs: { span: 16 },
        sm: { span: 16 }
      },
      // typeLists: [{ value: 0, label: '经信类' }],
      typeLists: [],
      leveLists: [],
      tableData: [],
      isEdit: false,
      id: undefined,
      row: undefined,
      title: '',
      directionModels: [],
      spinning: true,
      years
    }
  },
  methods: {
    moment,
    open (isEdit, row) {
      this.spinning = true
      this.afterClose()
      this.title = isEdit ? '编辑申报项目' : '添加申报项目'
      this.form.resetFields()
      this.isVisible = true
      if (isEdit) {
        this.isEdit = isEdit
        this.id = row.id
        this.row = row
        if (row.directionModels) {
          this.directionModels = cloneDeep(row.directionModels)
          this.directionModels.forEach((item) => {
            item['expiryDate'] = moment(item.expiryDate)
            this.tableData.push(item)
            this.$set(item, 'files', this.getFilesAry(item['policyPath']))
          })
        } else {
          this.$set(row, 'directionModels', this.directionModels)
        }
        const fieldObj = {
          productName: row.productName,
          addressCode: row.addressCode.split('/'),
          year: row.year,
          pLevel: row.pLevel,
          pType: row.pType,
          govName: row.govName,
          address: row.address
        }
        this.$nextTick(() => {
          this.form.setFieldsValue(fieldObj)
          this.spinning = false
        })
      }
      this.spinning = false
    },
    afterClose () {
      this.isVisible = false
      this.isEdit = false
      this.id = undefined
      this.tableData = []
      this.title = ''
      this.directionModels = []
    },
    handleSubmit () {
      const verifyObj = this.verifyTableData()
      if (verifyObj && verifyObj.result) {
        this.$message.warning(verifyObj.message)
        this.spinning = false
        return
      }
      this.form.validateFields((error, values) => {
        if (!error) {
          this.spinning = true
          const verifyAry = []
          for (const item of this.tableData) {
            verifyAry.push(item.direction)
          }
          if (this.isRepeat(verifyAry)) {
            this.$message.warning('方向名存在重复。')
            return
          }
          if (this.id) {
            values.id = this.id
          }
          if (this.tableData.length) {
            const directionModels = []
            for (const row of this.tableData) {
              const tempObj = {}
              Object.assign(tempObj, row)
              tempObj['expiryDate'] = tempObj['expiryDate'].format('YYYY-MM-DD')
              directionModels.push(tempObj)
            }
            values.directionModels = directionModels
          }
          const addressCodeStr = values.addressCode.join('/')
          values.addressCode = addressCodeStr
          this.$http.post(this.isEdit ? '/rsProduct/update' : '/rsProduct/add', values).then((res) => {
            if (res.data && res.success) {
              if (this.isEdit) {
                values.directionModels = res.data
              }
              this.$emit('ok', this.isEdit, values)
              this.$message.success('操作成功')
              this.spinning = false
              this.isVisible = false
            } else {
              this.$message.error(res.errorMessage)
              this.spinning = false
            }
          }).catch((error) => {
            this.$message.error(error.message)
            this.spinning = false
          })
        }
      })
    },
    //  添加方向
    addDirection () {
      const row = {
        // index: this.tableData.length,
        direction: undefined, // 方向
        expiryDate: undefined, // 截止日期
        appGuideUrl: undefined, // 申请指南地址
        linkName: undefined, // 联系人
        linkTel: undefined, // 联系电话
        mainDirection: undefined, // 主方向
        noticeNo: undefined, // 通知文号
        noticeUrl: undefined, // 通知地址
        policyPath: '', // 政策文件路径
        publicItemUrl: undefined, // 公示名单地址
        files: [], // 文件集合,
        productId: this.row ? this.row.id : undefined
      }
      this.tableData.push(row)
    },
    handleDel (row, rowIndex) {
      this.spinning = true
      if (!row.id) {
        this.tableData.splice(rowIndex, 1)
        return
      }
      this.$http.post('/rsProduct/delDirection', { id: row.id }).then((res) => {
        if (res.data && res.success) {
          this.tableData.splice(rowIndex, 1)
          this.directionModels.forEach((item, i) => {
            if (item.id === row.id) {
              this.row.directionModels.splice(i, 1)
            }
          })
          this.spinning = false
          this.$message.success('操作成功')
        } else {
          this.$message.error(res.errorMessage)
          this.spinning = false
        }
      }).catch((error) => {
        this.$message.error(error.message)
        this.spinning = false
      })
    },
    // 验证表单数据
    verifyTableData () {
      let count = 0
      for (const item of this.tableData) {
        count++
        const verifyAry = ['direction', 'expiryDate']
        for (const value of verifyAry) {
          if (!item[value]) {
            return {
              result: true,
              message: `第${count}行: 标题带*的，要必填！`
            }
          }
        }
      }
    },
    // 验证一个数组是否有重复
    isRepeat (arr) {
      const hash = {}
      for (const i in arr) {
        if (hash[arr[i]]) {
          return true
        }
        hash[arr[i]] = true
      }
      return false
    },
    // 上传文件
    uploadFile (row, file, files) {
      this.spinning = true
      // 给Param添加需要上传的文件
      const param = new FormData()
      param.append('dir', '/HandleDeclareItemModal/')
      param.append('file', file)
      // 设置请求头
      const config = { headers: { 'Content-Type': 'multipart/form-data' } }
      this.$http.post('/rsProduct/upload', param, config).then((res) => {
        if (res.data && res.success) {
          const fileMaps = res.data
          for (const fileMap of fileMaps) {
            const file = { fileName: fileMap.fileName, filePath: fileMap.filePath }
            row.files.push(file)
          }
          row.policyPath = ''
          for (const file of row.files) {
            row.policyPath += `,${file.filePath}`
          }
          row.policyPath = row.policyPath.substring(1)
          this.spinning = false
          this.$message.success(file.name + '上传成功')
        } else {
          this.$message.error(res.errorMessage)
          this.spinning = false
        }
      }).catch((error) => {
        this.$message.error(error.message)
        this.spinning = false
      })
    },
    // 下载文件
    download (record, file) {
      this.spinning = true
      this.$exportData('/rsProduct/download', { filePath: file.filePath, fileName: file.fileName }, file.fileName, this.$message)
      this.spinning = false
    },
    // 删除文件
    delFile (record, file) {
      this.spinning = true
      const files = record.files
      let index = -1
      files.forEach((item, i) => {
        if (item.filePath === file.filePath) {
          index = i
        }
      })
      files.splice(index, 1)
      record.policyPath = ''
      for (const file of files) {
        record.policyPath += file.filePath
      }
      this.spinning = false
    },
    // 通过路径名获取名称
    getFilesAry (path) {
      // /HandleDeclareItemModal/1603071219475test2.png,/HandleDeclareItemModal/1603071219487test1.png
      const files = []
      if (path) {
        const ary = path.split(',')
        for (const filePath of ary) {
          const index = filePath.lastIndexOf('/')
          const fileName = filePath.substring(index + 14)
          const file = { fileName: fileName, filePath: filePath }
          files.push(file)
        }
      }
      return files
    }
  }
}
</script>
<style lang='less' scoped>
.customAddBtn {
  border-bottom-left-radius: 8px;
  border-bottom-right-radius: 8px;
  color: #fff;
  font-size: 18px;
  font-weight: bolder;
  text-align: center;
  background-color: #1890ff;
}
.customAddBtn:hover {
  background-color: #40a9ff;
  cursor: pointer;
}
</style>
