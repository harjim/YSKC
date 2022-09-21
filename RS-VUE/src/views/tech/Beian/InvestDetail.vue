<!--
 * @Author: ldx
 * @Date: 2021-03-18 09:05:06
 * @LastEditTime: 2021-10-27 08:21:24
 * @LastEditors: lzh
 * @Description: 投资清单
 * @FilePath: \RS-VUE\src\views\tech\Beian\InvestDetail.vue
-->
<template>
  <a-card class="container" :bodyStyle="{ height: '100%', padding: '12px', overflow: 'auto'}">
    <template v-if="$auth('tech:beian:investments:search')">
      <a-spin id="spin" :spinning="spinning" tip="加载中...">
        <ystable
          ref="table"
          id="invoices"
          queryUrl="/inverstment/getList"
          :params="queryParams"
          border
          resizable
          auto-resize
          highlight-hover-row
          show-overflow="title"
          show-header-overflow
          highlight-current-row
          size="small"
          :toolbar="{ refresh: true, zoom: true, custom: true }"
        >
          <template #buttons>
            <a-button size="small" type="primary" @click="showDrawer" v-if="$auth('tech:beian:investments:add')">添加</a-button>
          </template>
          <vxe-table-column field="order" title="序号" width="80" align="center" header-align="center"></vxe-table-column>
          <vxe-table-column title="设备名称" field="ename" min-width="120" align="left" header-align="center">
            <template #default="{row}">
              <a @click="onEdit(row)" v-if="$auth('tech:beian:investments:edit')">{{ row.ename }}</a>
              <template v-else> {{ row.ename }}</template>
            </template>
          </vxe-table-column>
          <vxe-table-column title="支出类别" field="type" min-width="120" align="left" header-align="center">
            <template #default="{row}">
              {{ setType(row.type) }}
            </template>
          </vxe-table-column>
          <vxe-table-column title="入账日期" field="entryDate" width="120" align="center" header-align="center">
            <template #default="{row}">
              {{ row.entryDate ? moment(row.entryDate).format("YYYY-MM-DD") : '-' }}
            </template>
          </vxe-table-column>
          <vxe-table-column title="数量" field="countQuantity" width="60" align="right" header-align="center"></vxe-table-column>
          <vxe-table-column title="不含税金额（元）" field="countAmount" width="130" align="right" header-align="center"></vxe-table-column>
          <vxe-table-column title="转账金额（元）" field="countBank" width="130" align="right" header-align="center"></vxe-table-column>
          <vxe-table-column
            title="操作"
            width="90"
            align="center"
            header-align="center"
            fixed="right"
            v-if="$auth('tech:beian:investments:del')">
            <template #default="{row}">
              <a-popconfirm title="您确定要删除？" placement="topLeft" @confirm="onDel(row)" v-if="$auth('tech:beian:investments:del')">
                <a>删除</a>
              </a-popconfirm>
            </template>
          </vxe-table-column>
        </ystable>
        <drawer ref="drawer" v-bind="$attrs" :record="record" @refresh="refresh" :disburseType="disburseType"></drawer>
      </a-spin>
    </template>
  </a-card>
</template>

<script>
import { ystable } from '@/components/'
import Drawer from './modules/Drawer'
import moment from 'moment'
import { delInvestment } from '@/api/tech/BeiAnGuanLi/Invest'
export default {
  name: 'InvestDetail',
  components: {
    Drawer,
    ystable
  },
  props: {
    record: {
      type: Object,
      default: () => { return {} }
    }
  },
  mounted () {
    this.queryParams.beianId = this.record.id
    this.$getDictionary(16).then(data => {
      this.disburseType = data
      this.setDisburseMap(this.disburseType, this.disburseMap)
    })
  },
  data () {
    return {
      map: { 1: '设备及技术投资', 2: '土地、公用工程及其他投资', 3: '铺底流动资金' },
      spinning: false,
      queryParams: {},
      disburseType: [],
      disburseMap: {}
    }
  },
  methods: {
    moment,
    showDrawer () {
      this.$refs.drawer.show('添加资产')
    },
    onEdit (record) {
      this.$refs.drawer.edit(`编辑资产【${record.ename}】`, { ...record })
    },
    refresh () {
      this.spinning = true
      this.$refs.table.refresh(true)
      this.spinning = false
    },
    onDel (record) {
      this.spinning = true
      delInvestment([{ id: record.id }]).then((response) => {
        if (response.data && response.success) {
          this.refresh()
          this.$message.success('操作成功')
        } else {
          this.$message.error(`${response.errorCode}: ${response.errorMessage}`)
          console.log(`onDel function error ${response.errorCode}: ${response.errorMessage}`)
        }
      }).catch((error) => {
        this.$message.error(error.message)
        console.log(`onDel function error`, error.message)
      }).finally(() => {
        this.spinning = false
      })
    },
    setDisburseMap (disburseTypes, map) {
      disburseTypes.forEach((item) => {
        map[item.key] = item.value
        if (item.children && item.children.length) {
          this.setDisburseMap(item.children, map)
        }
      })
    },
    setType (typeStr) {
      let types = []
      if (Array.isArray(typeStr)) {
        types = typeStr
      } else {
        types = typeStr.split(',')
      }
      let returnStr = ''
      types.forEach(item => {
        returnStr += this.disburseMap[item] + '/'
      })
      return returnStr.substring(0, returnStr.length - 1)
    }
  }
}
</script>
<style lang="less" scoped>
.container {
  height: 100%;
  #spin {
    height: 100%;
    width: 100%;
    & /deep/ .ant-spin-nested-loading {
      height: 100%;
    }
    & /deep/ .ant-spin-container {
      height:  100%;
    }
  }
}
</style>
