<template>
  <div>
    <div class="table-page-search-wrapper">
      <span style="padding-bottom:10px;">
        <a-button
          type="primary"
          v-if="$auth('company:info:equity:add')"
          @click="$refs.modal.add(capitalContributionTitle)"
        >添加</a-button>
      </span>
    </div>
    <br />
    <a-table rowKey="id" ref="table" :dataSource="tableData" :pagination="false">
      <a-table-column title="主要股东名称" data-index="shareholder" align="center" />
      <a-table-column
        :title="capitalContributionTitle"
        data-index="capitalContribution"
        align="right"
      />
      <a-table-column title="出资方式" data-index="contributionType" align="center" />
      <a-table-column title="所占比例(%)" data-index="proportion" align="center" />
      <a-table-column title="操作" data-index="key" align="center" class="word-wrap">
        <template slot-scope="text,record">
          <span v-if="$auth('company:info:equity:edit')">
            <a @click="$refs.modal.edit(record,capitalContributionTitle)">编辑</a>
          </span>
          <span v-if="$auth('company:info:equity:del')">
            <a-divider type="vertical" v-if="$auth('company:info:equity:edit')" />
            <a-popconfirm title="是否确定删除?" @confirm="handleDel(record)">
              <a>删除</a>
            </a-popconfirm>
          </span>
        </template>
      </a-table-column>
    </a-table>
    <ownership-modal ref="modal" @ok="handleOk"></ownership-modal>
  </div>
</template>

<script>
import OwnershipModal from './modules/OwnershipModal'
export default {
  name: 'InfoOwnership',
  props: {
    capitalContributionTitle: {
      type: String,
      default: '出资额(万人民币)'
    }
  },
  components: {
    OwnershipModal
  },
  created () {
    this.getData()
    const self = this
    this.$getDictionary(5).then(res => {
      // console.log(res)
      for (let index = 0; index < res.length; index++) {
        const element = res[index]
        // self.capitalUnitData.set(element.key, element.value)
        self.$set(self.capitalUnitData, element.key, element.value)
      }
    })
  },
  data () {
    return {
      tableData: [],
      capitalUnitData: {}
    }
  },
  methods: {
    handleOk (flag) {
      if (flag) {
        this.$message.success('添加成功')
      } else {
        this.$message.success('更新成功')
      }
      this.getData()
    },
    handleDel (record) {
      this.$http.post('/ownership/del', { id: record.id }).then(res => {
        this.$message.success('删除成功')
      }).finally(res => {
        this.getData()
      })
    },
    getData () {
      if (!this.$auth('company:info:equity:view')) {
        this.tableData = []
        this.$notification.error({ message: '无此权限，请跟管理员联系！' })
        return
      }
      this.$http.get('/ownership/queryOwnershipList')
        .then(res => {
          this.tableData = res.data
          return this.tableData
        })
    }
  }
}
</script>
