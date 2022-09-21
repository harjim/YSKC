<template>
  <div class="box">
    <vxe-grid
      ref="xTable"
      resizable
      stripe
      keep-source
      show-footer
      :data="customList"
      :edit-rules="validRules"
      :max-height="244"
      size="small"
      :edit-config="edit && { trigger: 'manual', mode: 'row', autoClear: false, showIcon: false }"
      :footer-method="footerMethod"
    >
      <vxe-table-column type="seq" width="60" fixed="left" title="序号"/>
      <slot></slot>
      <vxe-table-column title="操作" width="60" align="center" v-if="edit">
        <template v-slot="{ row }">
          <template>
            <a-popconfirm
              title="是否删除该记录?"
              :autoAdjustOverflow="false"
              placement="topRight"
              @confirm="delTableRow(row)"
            >
              <a>删除</a>
            </a-popconfirm>
          </template>
        </template>
      </vxe-table-column>
    </vxe-grid>
    <a-button
      v-if="edit"
      block
      type="dashed"
      icon="plus"
      class="box__btn"
      @click="insertEvent">添加
    </a-button>
  </div>
</template>

<script>
export default {
  name: 'ServiceEditTable',
  components: {},
  props: {
    customList: {
      type: Array,
      default: () => []
    },
    validRules: {
      type: Object,
      default: () => ({})
    },
    edit: {
      type: Boolean,
      default: true
    },
    footerMethod: {
      type: Function,
      default: null
    }
  },
  data () {
    return {}
  },
  computed: {},
  watch: {},
  methods: {
    async insertEvent () {
      const record = {
        itemType: undefined
      }
      await this.$refs.xTable.insertAt(record, -1)
    },
    delTableRow (row) {
      this.$refs.xTable.remove(row)
    },
    validAllEvent () {
      const { fullData } = this.$refs.xTable.getTableData()
      if (fullData.length === 0) {
        this.$message.error('请先添加服务客户记录')
        return [false]
      }
      for (let i = 0; i < fullData.length; i++) {
        const fullDatum = fullData[i]
        for (const validRulesKey in this.validRules) {
          if (!fullDatum[validRulesKey]) {
            this.$message.error(`服务事项第${i + 1}行${this.validRules[validRulesKey][0].message}`)
            return [false]
          }
        }
      }
      return [true, fullData]
    }
  }
}
</script>

<style lang="less" scoped>
.box {
  &__btn {
    margin-top: 12px;
  }
}

& /deep/ textarea.ant-input {
  margin-bottom: 0;
}
</style>
