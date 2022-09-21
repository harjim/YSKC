<template>
  <a-modal
    :width="600"
    :visible="visible"
    title="配置明细"
    :footer="null"
    @cancel="closeModal"
    :maskClosable="false"
  >
    <div style="margin-bottom: 10px;">
      <a-button type="primary" @click="$refs.configColumnModify.addCol(fieldConfig,type)">添加</a-button>
    </div>
    <div>
      <a-table
        bordered
        :dataSource="fieldConfig.config"
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
            <a type="primary" @click="$refs.configColumnModify.editCol(fieldConfig,record,type)">编辑</a>
            <a-divider type="vertical" />
            <a-popconfirm title="是否确定删除?" @confirm="del(record)">
              <a>删除</a>
            </a-popconfirm>
          </template>
        </a-table-column>
      </a-table>
      <div style="margin-top:46px;">
        <!-- <a-alert style="margin:10px 0;" :message="description" type="warning" banner/> -->
      </div>
      <div style="text-align:right;"><a-button @click="closeModal">关闭</a-button></div>
    </div>
    <config-column-modify-modal ref="configColumnModify" @ok="refresh(true)" />
  </a-modal>
</template>

<script>
import ConfigColumnModifyModal from './ConfigColumnModifyModal'
export default {
  components: {
    ConfigColumnModifyModal
  },
  data () {
    return {
      visible: false,
      fieldConfig: {},
      loading: false,
      edit: false,
      type: 2
    }
  },
  methods: {
    refresh (edit) {
      if (edit) {
        this.edit = edit
      }
      this.loading = true
      return this.$http.get('/fieldConfig/getTypeConfig', { params: { type: this.type } })
        .then(res => {
          this.fieldConfig = {}
          var configArr = []
          if (res.success && res.data) {
            this.fieldConfig = res.data
            if (typeof (this.fieldConfig.config) === 'string') {
              configArr = JSON.parse(this.fieldConfig.config)
            }
          }
          this.fieldConfig.config = configArr
          this.loading = false
        })
    },
    showModal (type) {
      this.type = type
      this.edit = false
      this.refresh()
      this.visible = true
    },
    del (record) {
      this.loading = true
      var tempConfig = []
      this.fieldConfig.config.forEach(c => {
        if (c.name !== record.name && c.alias !== record.alias) {
          tempConfig.push(c)
        }
      })
      this.fieldConfig.config = tempConfig
      const params = { id: this.fieldConfig.id, number: this.fieldConfig.number, type: this.type }
      params.config = JSON.stringify(this.fieldConfig.config)
      this.$http.post('/fieldConfig/editFieldConfigCol', params)
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
      if (this.edit) {
        this.$emit('ok', this.edit)
      }
    }
  }
}
</script>

<style>

</style>
