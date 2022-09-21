<template>
  <a-modal
    :width="600"
    :visible="visible"
    :title="`配置${type === 0 ? '薪资明细' : '五险一金'}`"
    :footer="null"
    @cancel="closeModal"
    :maskClosable="false"
  >
    <div style="margin-bottom: 10px;">
      <a-button type="primary" @click="$refs.salaryConfigModify.addCol(salaryConfig,type)">添加</a-button>
    </div>
    <div>
      <a-table
        bordered
        :dataSource="salaryConfig.config"
        :pagination="false"
        size="small"
        style="height:301px;"
        :scroll="{ y: 300 }"
        :loading="loading"
        rowKey="name"
      >
        <a-table-column title="名称" :width="300" data-index="alias" key="alias" />
        <a-table-column title="操作" :width="200" align="center">
          <template slot-scope="text,record">
            <a type="primary" @click="$refs.salaryConfigModify.editCol(salaryConfig,record,type)">编辑</a>
            <a-divider type="vertical" />
            <a-popconfirm title="是否确定删除?" @confirm="del(record)">
              <a>删除</a>
            </a-popconfirm>
          </template>
        </a-table-column>
      </a-table>
      <div style="margin-top:46px;">
        <a-alert style="margin:10px 0;" :message="description" type="warning" banner/>
      </div>
      <div style="text-align:right;"><a-button @click="closeModal">关闭</a-button></div>
    </div>
    <salary-config-modify-modal ref="salaryConfigModify" @ok="refresh(true)" />
  </a-modal>
</template>

<script>
import SalaryConfigModifyModal from './SalaryConfigModifyModal'
export default {
  components: {
    SalaryConfigModifyModal
  },
  data () {
    return {
      visible: false,
      salaryConfig: {},
      loading: false,
      type: 0,
      edit: false,
      description: ''
    }
  },
  methods: {
    refresh (edit) {
      this.edit = edit
      this.loading = true
      return this.$http.get('/fieldConfig/getTypeConfig', { params: { type: this.type } })
        .then(res => {
          this.salaryConfig = {}
          var configArr = []
          if (res.success && res.data) {
            this.salaryConfig = res.data
            if (typeof (this.salaryConfig.config) === 'string') {
              configArr = JSON.parse(this.salaryConfig.config)
            }
          }
          this.salaryConfig.config = configArr
          this.loading = false
        })
    },
    showModal (type) {
      if (type === 0) {
        this.description = '配置薪资明细后，应发工资＝薪资明细项总和。'
      } else {
        this.description = '配置五险一金后，五险一金＝五险一金明细项总和。'
      }
      this.type = type
      this.visible = true
      this.refresh(false)
    },
    del (record) {
      this.loading = true
      var tempConfig = []
      this.salaryConfig.config.forEach(c => {
        if (c.name !== record.name && c.alias !== record.alias) {
          tempConfig.push(c)
        }
      })
      this.salaryConfig.config = tempConfig
      const params = { id: this.salaryConfig.id, number: this.salaryConfig.number, type: this.type }
      params.config = JSON.stringify(this.salaryConfig.config)
      this.$http.post('/fieldConfig/addFieldConfigCol', params)
        .then(res => {
          if (res.success && res.data) {
            this.$message.success('删除成功')
            this.refresh(true)
          } else {
            this.$message.error(res.errorMessage ? res.errorMessage : '删除成功')
          }
        }).finally(() => {
          this.loading = false
        })
    },
    closeModal () {
      this.visible = false
      this.$emit('ok', this.edit)
    }
  }
}
</script>

<style>
</style>
