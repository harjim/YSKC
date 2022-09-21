<!--
 * @Author: your name
 * @Date: 2022-03-03 08:28:34
 * @LastEditors: error: git config user.name && git config user.email & please set dead value or install git
 * @LastEditTime: 2022-09-05 13:51:19
 * @Description: 项目签名汇总
 * @FilePath: \RS-VUE\src\views\project\SignatureSummary.vue
-->
<template>
  <a-card style="height: 100%" :bodyStyle="{ height: '100%', overflow: 'auto'}" v-if="control.search">
    <a-form layout="inline">
      <a-form-item label="项目">
        <a-tree-select
          v-model="rdTitles"
          style="width: 350px;"
          :dropdownStyle="{ maxHeight: '420px', maxWidth: '350px' }"
          tree-default-expand-all
          tree-checkable
          :show-checked-strategy="SHOW_PARENT"
          :maxTagCount="3"
          placeholder="请选择项目"
        >
          <a-tree-select-node
            title="所有项目"
            :value="-1"
            key="-1"
            v-if="projects && projects.length"
          >
            <a-tree-select-node
              v-for="p in projects"
              :key="p.id"
              :value="p.rdTitle"
              :title="`${p.rdTitle}-${p.pname}`"
            />
          </a-tree-select-node>
        </a-tree-select>
      </a-form-item>
      <a-form-item><a-button type="primary" @click="query(true)">查询</a-button></a-form-item>
    </a-form>
    <ystable
      rowId="id"
      ref="table"
      highlight-hover-row
      show-overflow
      show-header-overflow
      :toolbar="{zoom:true,custom:true,refresh:true}"
      queryUrl="/signatureSummary/getList"
      :params="queryParams"
      align="center"
    >
      <template v-slot:buttons>
        <a-button style="margin-right: 10px;" v-if="control.add" type="primary" @click="showAdd">添加</a-button>
        <a-button style="margin-right: 10px;" v-if="control.import" type="primary" @click="openUploadModal">导入</a-button>
        <a-button v-if="control.export" type="primary" @click="onExport">导出</a-button>
      </template>
      <vxe-table-column type="seq" title="序号" :width="80" />
      <vxe-table-column title="RD" field="rdTitle" remoteSort />
      <vxe-table-column title="项目名称" field="pname" remoteSort />
      <vxe-table-column title="编制" field="toCompile" />
      <vxe-table-column title="审核" field="auditor" />
      <vxe-table-column title="批准" field="approval" />
      <vxe-table-column title="操作" field="edit" >
        <template #default="{ row }">
          <a @click="$refs.addModal.edit(row)" v-if="control.edit">编辑</a>
        </template>
      </vxe-table-column>
    </ystable>
    <uploadModal
      :sampleData="sampleData"
      :showProgress="true"
      :paramData="params"
      paramKey="tableField"
      title="导入项目签名汇总"
      templateName="项目签名汇总模板"
      ref="uploadModal"
      action="/doc/signatureSummary/importSignature"
      @onSuccess="success"
      @error="error"
    />
    <add-signature-modal :year="currentYear" ref="addModal" @ok="search"/>
  </a-card>
</template>

<script>
import ystable from '@/components/Table/ystable'
import { SelectProject, UploadModal } from '@/components'
import yearMiXin from '@/utils/yearMixin'
import { TreeSelect } from 'ant-design-vue'
import { mapGetters } from 'vuex'
import AddSignatureModal from './modules/AddSignatureModal'

const SHOW_PARENT = TreeSelect.SHOW_PARENT

export default {
  props: {
  },
  mixins: [yearMiXin],
  name: 'SignatureSummary',
  components: {
    ystable,
    SelectProject,
    UploadModal,
    AddSignatureModal
  },
  data () {
    return {
      SHOW_PARENT,
      queryParams: {},
      rdTitles: [],
      projects: [],
      control: {
        search: this.$auth('project:signatureSummary:search'),
        export: this.$auth('project:signatureSummary:export'),
        import: this.$auth('project:signatureSummary:import'),
        add: this.$auth('project:signatureSummary:add'),
        edit: this.$auth('project:signatureSummary:edit')
      },
      tableField: {
        tableId: 'importSignatureSummaryTable',
        fieldTitleObject: {
          pname: { title: '项目名称', required: true, defaultTitle: '项目名称', importField: true },
          rdTitle: { title: '项目RD', required: true, defaultTitle: '项目RD', importField: true },
          toCompile: { title: '编制', required: false, defaultTitle: '编制', importField: true },
          auditor: { title: '审核', required: false, defaultTitle: '审核', importField: true },
          approval: { title: '批准', required: false, defaultTitle: '批准', importField: true }
        }
      },
      sampleData: [{
        pname: '格式：xxxx',
        rdTitle: '格式：2021RD01',
        toCompile: '格式：xxx',
        auditor: '格式：xxx',
        approval: '格式：xxx'
      }],
      params: {}
    }
  },
  computed: {
    ...mapGetters(['userInfo'])
  },
  created () {
    this.queryParams.year = this.currentYear
  },
  watch: {
    currentYear (value) {
      this.select()
    }
  },
  mounted () {
    this.params.year = this.currentYear
    this.select()
  },
  methods: {
    query (refresh) {
      this.loadQueryParam()
      this.$refs['table'].refresh(refresh)
    },
    openUploadModal () {
      this.params.year = this.currentYear
      this.$refs.uploadModal.show(this.tableField)
    },
    loadQueryParam () {
      let rdTitles = []
      if (this.rdTitles.indexOf(-1) >= 0) {
        rdTitles = this.projects.map(a => a.rdTitle)
      } else {
        rdTitles = this.rdTitles
      }
      this.queryParams.rdTitles = rdTitles
      this.queryParams.year = this.currentYear
    },
    success (fileName, resData) {
      console.log(resData)
      if (resData === '') {
        this.$message.success(fileName + '导入成功')
      } else {
        this.$message.error(fileName + '导入失败')
      }
      this.query(true)
    },
    search () {
      if (!this.currentYear) {
        return
      }
      this.query(true)
    },
    select () {
      this.$http.get('/project/getSelectList', { params: { year: this.currentYear, sign: 2 } })
        .then(res => {
          if (res.data != null && res.data.length > 0) {
            this.projects = res.data
            this.rdTitles = []
          } else {
            this.projects = []
            this.rdTitles = []
          }
        })
    },
    onExport () {
      this.$exportData(
        '/signatureSummary/export',
        this.queryParams,
        `${this.userInfo.companyName}-${this.queryParams.year}-项目签名汇总表.xls`,
        this.$message
      )
    },
    error (fileName, msg) {
      this.$message.error(`${fileName}导入失败${msg ? ':' + msg : ''}`)
    },
    showAdd () {
      this.$refs.addModal.add()
    }
  }
}
</script>
