<!--
 * @Author: ldx
 * @Date: 2020-08-06 10:59:39
 * @LastEditTime: 2022-09-19 14:07:12
 * @LastEditors: error: git config user.name && git config user.email & please set dead value or install git
 * @Description: 技改备案管理
 * @FilePath: \MS-VUE\src\views\project\Register.vue
-->

<template>
  <a-card :bordered="false" id="card" v-if="$auth('project:register:search')" >
    <a-form :form="form" layout="inline">
      <a-form-item label="客户名称">
        <a-input style="width:160px;" placeholder="请输入客户名称" v-model="queryParams.companyName" />
      </a-form-item>
      <a-form-item label="项目名称">
        <a-input style="width:160px;" v-model="queryParams.productName" placeholder="请输入项目名称" />
      </a-form-item>
      <a-form-item label="年份">
        <year-select style="width:160px;" v-model="queryParams.year" placeholder="请选择年份" />
      </a-form-item>
      <a-form-item>
        <a-button type="primary" @click="refreshTable(true)">查询</a-button>
      </a-form-item>
    </a-form>
    <ystable
      ref="table"
      queryUrl="/beian/getList"
      :params="getParams()"
      highlight-current-row
      highlight-hover-row
      show-overflow
      resizable
      auto-resize
      @completed="({data:{header}})=> headerData = header"
      :toolbar="{ refresh:true, zoom: true, custom: true, }" >
      <template #buttons>
        <span v-if="$auth('project:register:export')" style="padding-right: 10px">
          <a-button type="primary" @click="exportDataList()">导出</a-button>
        </span>
        <b>
          有效备案证数量:
          <a>{{ headerData.beianNum ? headerData.beianNum : '-' }}</a>
        </b>
        <b class="pleft">
          完工80%备案证数量:
          <a>{{ headerData.complateNum ? headerData.complateNum : '-' }}</a>
        </b>
        <b class="pleft">
          备案金额合计(万元):
          <a>{{ headerData && headerData.totalBudget ? headerData.totalBudget: '-' }}</a>
        </b>
        <b class="pleft">
          已投金额合计(万元):
          <a>{{ headerData && headerData.totalAmount ? headerData.totalAmount: '-' }}</a>
        </b>
      </template>
      <vxe-table-column title="序号" type="seq" width="60" align="center" header-align="center"></vxe-table-column>
      <vxe-table-column title="公司名称" field="companyName" width="200" align="left" header-align="center">
        <template #default="{ row }">
          <a
            @click="requestCustomer(row)"
            title="点击进入创新标准化管理系统"
            v-if="$auth('customer:my:jumpPage')"
          >{{ row.companyName }}</a>
          <span v-else>{{ row.companyName }}</span>
        </template>
      </vxe-table-column>
      <vxe-table-column title="申报项目名称" field="productName" width="200" align="left" header-align="center"></vxe-table-column>
      <vxe-table-column title="项目名称" field="pname" width="200" align="left" header-align="center"></vxe-table-column>
      <vxe-table-column title="年份" field="year" width="100" align="center" header-align="center"></vxe-table-column>
      <vxe-table-column title="备案总金额" field="totalBudget" width="100" align="right" header-align="center"></vxe-table-column>
      <vxe-table-column title="投资总金额" field="totalAmount" width="100" align="right" header-align="center"></vxe-table-column>
      <vxe-table-column title="设备费" field="equipment" width="100" align="right" header-align="center"></vxe-table-column>
      <vxe-table-column title="已完成设备费" field="equipmentCost" width="120" align="right" header-align="center"></vxe-table-column>
      <vxe-table-column title="建设费" field="construction" width="100" align="right" header-align="center"></vxe-table-column>
      <vxe-table-column title="已完成建设费" field="constructionCost" width="120" align="right" header-align="center"></vxe-table-column>
      <vxe-table-column title="铺地流动资金" field="initWorkCapital" width="120" align="right" header-align="center"></vxe-table-column>
      <vxe-table-column title="已完成铺地流动资金" field="initWorkCapitalCost" width="150" align="right" header-align="center"></vxe-table-column>
      <vxe-table-column title="完成率" field="completeRate" width="100" align="right" header-align="center"></vxe-table-column>
    </ystable>
  </a-card>
</template>

<script>
import ystable from '@/components/Table/ystable'
import { YearSelect } from '@/components/Selects'
export default {
  name: 'TechnicalRegister',
  components: {
    ystable,
    YearSelect
  },
  data () {
    return {
      projects: {},
      formStyle: {
        marginBottom: '0'
      },
      headerData: {},
      form: this.$form.createForm(this),
      countData: {},
      queryParams: {
        fullPath: undefined
      },
      tableData: [],
      editRow: undefined,
      projectRow: undefined
    }
  },
  methods: {
    complete (data) {
      // const projects = {}
      // data.forEach(a => {
      //   projects[a.id] = a
      // })
      // this.projects = projects
      if (data && data.length) {
        const companyIds = []
        data.forEach(item => {
          companyIds.push(item.companyId)
        })
        // this.getCountData()
      }
    },
    requestCustomer (record) {
      this.$companyJump(this, { id: record.customerId, companyId: record.companyId })
    },
    getParams () {
      const params = {
        companyName: this.queryParams.companyName,
        year: this.queryParams.year,
        fullPath: this.queryParams.fullPath,
        owerId: this.queryParams.owerId,
        productName: this.queryParams.productName
      }
      if (this.queryParams.tIds) { params.tIds = this.queryParams.tIds.map(a => a.id) }
      if (this.queryParams.owerId) params.owerId = this.queryParams.owerId.id
      return params
    },
    /**
     * @description: 显示添加或修改备案信息
     * @param {object,boolean}
     * @return {null}
     */
    onAddRegisterModal (record, isAdd = true) {
      if (isAdd) {
        this.projectRow = record
        this.$refs.addregisterModal.add(record, isAdd)
      } else {
        this.editRow = record
        this.$refs.addregisterModal.edit(record, isAdd)
      }
    },
    exportDataList () {
      this.spin = true
      this.$exportData('/beian/export', this.getParams(), `备案列表.xlsx`, this.$message).then(res => {
        this.spin = false
      })
    },
    /**
     * @description: 处理技术人员显示
     * @param {object}
     * @return {string}
     */
    processTechs (record) {
      if (record.techs) {
        let techNames = ''
        record.techs.forEach((item, index) => {
          techNames += item.realName + ','
        })
        return techNames.substring(0, techNames.length - 1)
      } else if (record.techName) {
        return record.techName
      } else {
        return ''
      }
    },
    /**
     * @description: 删除备案信息
     * @param {object}
     * @return {null}
     */
    handleDelete (record) {
      this.$http.post('/projectRegister/del', { id: record.id }).then((res) => {
        if (res.data && res.success) {
          this.$message.success('操作成功')
          this.loadContentMethod(record.projectId)
        } else {
          this.$message.error(res.errorMessage)
        }
      }).catch((error) => {
        this.$message.error(error.message)
      })
    },
    /**
     * @description: 刷新表格
     * @param {boolean,Object,boolean}
     * @return {null}
     */
    refreshTable (isRefresh, record) {
      if (!record) {
        this.$refs.table.refresh(isRefresh)
      } else {
        this.loadContentMethod(record.projectId)
      }
    },
    /**
     * @description:  下载文件
     * @param {string|number, number 1=>下载备案扫描证; 2=>下载备案文件, string}
     * @return {null}
     */
    downloadFile (id, sign = 1, fileName, filePaths) {
      let filePath = ''
      filePaths.split(',').forEach(item => {
        if (item.includes(fileName)) {
          filePath = item
        }
      })
      this.$exportData('/projectRegister/download', { sign, registerId: id, filePath, fileName }, fileName, this.$message)
    },
    /**
     * @description: 获取文件名称
     * @param {string}
     * @return {Array}
     */
    getFileNames (fileStr) {
      if (!fileStr) return []
      const files = fileStr.split(',')
      const fileNames = []
      files.forEach(item => {
        const temps = item.split('/')
        const fileName = temps[temps.length - 1]
        fileNames.push(fileName)
      })
      return fileNames
    },
    /**
     * @description:  懒加载子表数据
     * @param {type}
     * @return {type}
     */
    loadContentMethod (id) {
      return this.$http.get('/projectRegister/getList', { params: { projectId: id } }).then((res) => {
        if (res.data && res.success) {
          this.projects[id].projectRegisters = res.data
          this.projects[id].registerNum = res.data.length ? res.data.length : ''
          this.projects[id].amount = res.data.length ? res.data[0].amount : ''
        } else {
          this.$message.error(res.errorMessage)
        }
      }).catch((error) => {
        this.$message.error(error.message)
      })
    },
    getCountData (companyIds = [1, 456, 412]) {
      this.$http.get('/beian/countData', { params: { companyIds } }).then((res) => {
        if (res.data && res.success) {
          this.countData = res.data
        } else {
          this.$message.error(res.errorMessage)
          this.countData = {}
        }
      }).catch((error) => {
        this.$message.error(error.message)
      }).finally((res) => {
      })
    }
  }
}
</script>

<style lang="less" scoped>
#card /deep/ .headerbg {
  background-color: #fff;
}
.pleft {
  padding-left: 20px;
}
</style>
