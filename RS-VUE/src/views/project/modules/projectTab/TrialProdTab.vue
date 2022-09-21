<template>
  <a-card :bordered="false">
    <a-form layout="inline" :form="form">
      <span>
        <a-button type="primary" @click="$refs.trialProdModal.add(projectData)">添加</a-button>
      </span>
    </a-form>
    <br />
    <div>
      <s-table
        ref="table"
        size="default"
        rowKey="id"
        :data="getData"
        :alert="options.alert"
        showPagination="auto"
      >
        <a-table-column title="试制日期" data-index="trialDate" align="left" />
        <a-table-column title="计划PO量" data-index="planPO" align="left" />
        <a-table-column title="实际PO量" data-index="actualPO" align="left" />
        <!-- <a-table-column title="工艺线" data-index="workshopName" align="left" /> -->
        <a-table-column title="试产机台号" data-index="pos" align="left" />
        <a-table-column title="试产班组" data-index="trialGroup" align="left" />
        <a-table-column title="主材消耗" data-index="mainMaterial" align="left" />
        <a-table-column title="辅材消耗" data-index="auxMaterial" align="left" />
        <a-table-column title="燃料消耗" data-index="fuel" align="left" />
        <a-table-column title="动力消耗" data-index="power" align="left" />
        <a-table-column title="气体" data-index="gas" align="left" />
        <a-table-column title="预计备品备件(万元)" data-index="spare" align="left" />
        <a-table-column title="操作" data-index="key" align="center" :width="230">
          <template slot-scope="text,record">
            <a @click="$refs.trialProdModal.edit(record,projectData)">编辑</a>
            <a-divider type="vertical" />
            <a-popconfirm title="是否确定删除?" @confirm="del(record)">
              <a>删除</a>
            </a-popconfirm>
          </template>
        </a-table-column>
      </s-table>
    </div>
    <trial-prod-modal ref="trialProdModal" @ok="handleOk" @error="handleError"/>
  </a-card>
</template>

<script>
import { STable, Ellipsis } from '@/components'
import TrialProdModal from './tabModules/TrialProdModal'

export default {
  name: 'TrialProdTab',
  components: {
    STable,
    TrialProdModal,
    Ellipsis
  },
  props: {
    projectData: {
      type: Object,
      required: true
    }
  },
  data () {
    return {
      visible: false,
      title: '',
      projectId: '',
      options: [],
      form: this.$form.createForm(this),
      getData: parameter => {
        return this.$http.get('/trialProd/queryTrial', { params: Object.assign(parameter, { projectId: this.projectId }) })
          .then(res => {
            return res.data
          })
      }
    }
  },
  watch: {
    projectData (item) {
      this.projectId = item.id
      this.$nextTick(() => {
        this.$refs.table.refresh(true)
      })
    }
  },
  created () {
    this.projectId = this.projectData.id
    this.$nextTick(() => {
      this.$refs.table.refresh(true)
    })
  },
  methods: {
    handleSubmit () {
      this.visible = false
    },
    handleOk () {
      this.$refs.table.refresh(true)
    },
    handleError (errorMassege) {
      this.$message.error(errorMassege)
    },
    del (record) {
      this.$http.post('/trialProd/delTrial', record).then(res => {
        this.$message.success('删除成功')
      }).finally(res => {
        this.$emit('change')
        this.$refs.table.refresh(true)
      })
    }
  }
}
</script>

<style>
</style>
