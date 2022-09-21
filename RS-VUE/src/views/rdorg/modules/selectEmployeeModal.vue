<template>
  <a-modal
    :width="1000"
    :visible="visible"
    :title="title"
    :footer="null"
    @cancel="visible = false"
    :maskClosable="false"
    destroyOnClose
  >
    <a-form layout="inline">
      <a-form-item label="姓名">
        <a-input v-model="queryParam.ename" placeholder="请输入姓名" style="width:200px;" />
      </a-form-item>
      <a-form-item label="工号">
        <a-input v-model="queryParam.enumber" placeholder="请输入工号" style="width:200px;" />
      </a-form-item>
      <a-form-item label="部门">
        <a-input v-model="queryParam.deptName" placeholder="请输入部门" style="width:200px;" />
      </a-form-item>
      <a-form-item label="职位">
        <a-input v-model="queryParam.position" placeholder="请输入职位" style="width:200px;" />
      </a-form-item>
      <a-form-item label="职称">
        <a-input
          v-model="queryParam.title"
          placeholder="请输入职称"
          autosize="true"
          style="width:200px;"
        />
      </a-form-item>
      <a-form-item label="学历">
        <a-select
          v-model="queryParam.eduLevel"
          :allowClear="true"
          placeholder="请选择学历"
          style="width:200px;"
        >
          <a-select-option value="-1">请选择</a-select-option>
          <a-select-option value="0">无</a-select-option>
          <a-select-option value="7">初中</a-select-option>
          <a-select-option value="1">高中</a-select-option>
          <a-select-option value="2">中专</a-select-option>
          <a-select-option value="3">大专</a-select-option>
          <a-select-option value="4">本科</a-select-option>
          <a-select-option value="5">硕士</a-select-option>
          <a-select-option value="6">博士</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label="身份证号">
        <a-input v-model="queryParam.idNumber" placeholder="请输入身份证号" style="width:174px;" />
      </a-form-item>
      <a-form-item label="专业">
        <a-input v-model="queryParam.specialities" placeholder="请输入专业" style="width:200px;" />
      </a-form-item>
      <a-form-item label="备注">
        <a-input v-model="queryParam.remark" placeholder="请输入备注" style="width:200px;" />
      </a-form-item>
      <!-- <a-form-item label="研发部门">
        <select-down
          ref="rdDeptSelet"
          @fullPath="rdDeptSelected"
          treeType="rdDept"
          placeholder="请选择研发部门"
          style="width:200px;"
        />
      </a-form-item> -->
      <a-form-item>
        <a-button type="primary" @click="refreshData">查询</a-button>
      </a-form-item>
    </a-form>
    <div>
      <ystable
        ref="table"
        highlight-hover-row
        show-overflow
        resizable
        auto-resize
        :queryUrl="queryUrl"
        :params="getParams()"
        @checkbox-change="selectChange"
        @checkbox-all="selectChange"
        :seq-config="{startIndex: 1}"
        :toolbar="tableToolbar"
      >
        <template v-slot:buttons>
        </template>
        <vxe-table-column type="checkbox" width="40" fixed="left" />
        <vxe-table-column title="工号" field="enumber" width="80" remoteSort align="left"></vxe-table-column>
        <vxe-table-column title="姓名" field="ename" width="100" remoteSort align="left"></vxe-table-column>
        <vxe-table-column title="职位" field="position" width="100" remoteSort align="left"></vxe-table-column>
        <vxe-table-column title="部门" field="deptName" width="100" remoteSort align="left"></vxe-table-column>
        <vxe-table-column title="研发部门" field="rdDeptName" width="100" align="left"></vxe-table-column>
        <vxe-table-column title="性别" field="gender" width="80" remoteSort align="center">
          <template v-slot="{ row }">
            <span>{{ getGender(row.gender) }}</span>
          </template>
        </vxe-table-column>
        <vxe-table-column title="职称" field="title" width="100" align="left"></vxe-table-column>
        <vxe-table-column title="身份证号" field="idNumber" width="160" remoteSort align="left"></vxe-table-column>
        <vxe-table-column title="出生日期" field="birthday" width="100" remoteSort align="center">
          <template v-slot="{ row }">{{ row.birthday | formatData }}</template>
        </vxe-table-column>
        <vxe-table-column title="学历" field="eduLevel" width="80" remoteSort align="center">
          <template v-slot="{ row }">
            <span>{{ getEduLevel(row.eduLevel) }}</span>
          </template>
        </vxe-table-column>
        <vxe-table-column title="专业" field="specialities" width="100" remoteSort align="left"></vxe-table-column>
        <vxe-table-column title="入职日期" field="edate" width="100" remoteSort align="center">
          <template v-slot="{ row }">{{ row.edate | formatData }}</template>
        </vxe-table-column>
        <vxe-table-column title="离职日期" field="leaveDate" width="100" remoteSort align="center">
          <template v-slot="{ row }">{{ row.leaveDate | formatData }}</template>
        </vxe-table-column>
        <vxe-table-column title="备注" field="remark" :min-width="60" align="center"></vxe-table-column>
      </ystable>
      <div style="margin-top: 12px ; display:flex; flexDirection:row-reverse;">
        <a-button type="primary" @click="handleSubmit" :loading="addLoading">添加</a-button>
      </div>
    </div>
  </a-modal>
</template>
<script>
import yearMiXin from '@/utils/yearMixin'
import ystable from '@/components/Table/ystable'
import moment from 'moment'
import SelectDown from '@/components/SelectDown'
const eduLevelArr = ['无', '高中', '中专', '大专', '本科', '硕士', '博士', '初中']
export default {
  name: 'SelectEmployeeModal',
  components: {
    ystable,
    SelectDown
  },
  mixins: [yearMiXin],
  props: {
    saveUrl: {
      type: String,
      default: '/rdEmployee/addRdEmployeeData'
    },
    queryUrl: {
      type: String,
      default: '/employee/selectEmployeeList'
    }
  },
  data () {
    return {
      tableToolbar: {
        refresh: true,
        zoom: true,
        custom: true
      },
      form: this.$form.createForm(this),
      title: '',
      pagination: [],
      options: {},
      visible: false,
      loadFirst: false,
      year: undefined,
      entityList: [],
      selectedRowKeys: [],
      queryParam: { edate: '-1' },
      deptId: undefined,
      propsMap: [],
      enumbers: [],
      addLoading: false
    }
  },
  mounted () {
    this.handleSubmit = this.$debounce(this.handleSubmit)
  },
  filters: {
    formatData (value) {
      if (!value) { return '' }
      return moment(value).format('YYYY-MM-DD')
    }
  },
  methods: {
    getParams () {
      if (this.queryParam.edate === Number.undefined || this.queryParam.edate === '-1') {
        var startMonth = moment([this.year, 0, 1, 0, 0, 0, 0])
        var endMonth = moment([this.year, 11, 31, 0, 0, 0, 0])
        this.queryParam.startMonth = startMonth
        this.queryParam.endMonth = endMonth
      } else {
        this.queryParam.startMonth = moment([this.year, this.queryParam.edate, 1, 0, 0, 0, 0])
        this.queryParam.endMonth = moment([this.year + 1, this.queryParam.edate, 1, 0, 0, 0, 0])
      }
      this.queryParam.year = this.year
      return this.queryParam
    },
    add (year, title = '添加研发人员') {
      this.title = title
      this.visible = true
      this.entityList = []
      this.form.resetFields()
      this.flg = -1
      this.year = year
      this.selectedRowKeys = []
      this.deptId = null
      this.queryParam = {}
      this.$nextTick(() => {
        if (this.loadFirst) {
          this.$refs.table.refresh(true)
        }
        this.loadFirst = true
      })
    },
    moment,
    onSelectChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.entityList = selectedRows
    },
    getGender (index) {
      if (index === 1) {
        return '女'
      }
      if (index === 2) {
        return '男'
      }
    },
    disabledDate (current) {
      return !current || current > moment().endOf('day')
    },
    getEduLevel (index) {
      return eduLevelArr[index]
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.addLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          if (this.flg === -1) {
            values.year = this.year
            if (this.entityList.length === undefined || this.entityList.length < 1) {
              this.$message.info('请选择数据')
              this.addLoading = false
              return
            }
            values.entityList = this.entityList // 研发花名册 添加用到的参数
            values.enumbers = this.enumbers // 评审委员会花名册 添加用到的参数
            this.$http.post(this.saveUrl, values)
              .then(res => {
                if (res.success && res.data) {
                  this.visible = false
                  this.$emit('ok', true)
                  this.$message.success('添加成功')
                } else {
                  this.$message.error(res.errorMessage ? res.errorMessage : '添加失败')
                }
              }).finally(res => {
                this.addLoading = false
              })
          }
        } else {
          this.addLoading = false
        }
      })
    },
    refreshData () {
      this.$refs.table.refresh(true)
    },
    getCheckboxProps (record) {
      return {
        props: this.propsMap[record.id]
      }
    },
    selectChange ({ records }) {
      this.entityList = records
      this.selectedRowKeys = records.map(item => {
        return item.id
      })
      this.enumbers = records.map(item => {
        return item.enumber
      })
    },
    rdDeptSelected (rdDeptPath) {
      this.queryParam.rdDeptPath = rdDeptPath
    }
  }
}
</script>
