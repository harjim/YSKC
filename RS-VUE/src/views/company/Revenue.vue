<!--
 * @Author: your name
 * @Date: 2022-01-19 08:59:37
 * @LastEditors: lzh
 * @LastEditTime: 2022-01-25 16:58:10
 * @Description: In User Settings Edit
 * @FilePath: \RS-VUE\src\views\company\Revenue.vue
-->
<template>
  <a-card>
    <a-form layout="inline">
      <a-form-item label="年份">
        <year-select @change="v=>queryParams.year = v" style="width:200px;" placeholder="年份" />
      </a-form-item>
      <a-form-item>
        <a-button type="primary" @click="loadData" v-if="$auth('company:revenue:search')">查询</a-button>
      </a-form-item>
      <a-form-item>
      </a-form-item>
    </a-form>
    <vxe-grid ref="xGrid" v-bind="gridOptions">
      <template #toolbar_buttons>
        <a-button type="primary" @click="$refs.creatModal.add()" v-if="$auth('company:revenue:add')">添加</a-button>
      </template>
      <template #toolbar_tools>
        <span style="margin-right: 8px;">单位：万元</span>
      </template>
    </vxe-grid>
    <revenue-modal ref="creatModal" @ok="loadData"></revenue-modal>
  </a-card>
</template>

<script>
import { YearSelect } from '@/components'
import RevenueModal from './modules/RevenueModal'
import moment from 'moment'
export default {
  components: {
    RevenueModal,
    moment,
    YearSelect
  },
  data () {
    return {
      queryParams: {
        year: undefined
      },
      gridOptions: {
        resizable: true,
        highlightHoverRow: true,
        highlightCurrentRow: true,
        headerAlign: 'center',
        align: 'right',
        border: true,
        data: [],
        columns: [
          { field: 'year', title: '年份', width: 120, fixed: 'left', align: 'center' },
          { field: 'feb', title: '一月', width: 120 },
          { field: 'mar', title: '二月', width: 120 },
          { field: 'jan', title: '三月', width: 120 },
          { field: 'apr', title: '四月', width: 120 },
          { field: 'may', title: '五月', width: 120 },
          { field: 'jun', title: '六月', width: 120 },
          { field: 'jul', title: '七月', width: 120 },
          { field: 'aug', title: '八月', width: 120 },
          { field: 'sept', title: '九月', width: 120 },
          { field: 'oct', title: '十月', width: 120 },
          { field: 'nov', title: '十一月', width: 120 },
          { field: 'dec', title: '十二月', width: 120 },
          { field: 'total',
            title: '合计',
            width: 120,
            slots: {
              default: ({ row }, h) => {
                let total = 0
                for (const key in row) {
                  if (key !== 'year') {
                    total += typeof row[key] === 'number' ? row[key] : 0
                  }
                }
                return total
              }
            }
          },
          { field: 'action',
            title: '操作',
            align: 'center',
            width: 120,
            fixed: 'right',
            slots: {
              default: ({ row }, h) => {
                return [
                  this.$auth('company:revenue:edit') ? <a style="margin-right: 8px;" onclick={ () => this.$refs.creatModal.edit(row) }>编辑</a> : null,
                  this.$auth('company:revenue:del') ? <a-tooltip placement="top">
                    <a style="margin-left:3px;" onclick={ () => this.delRow(row.year) }>
                      删除
                    </a>
                  </a-tooltip> : null
                ]
              }
            }
          }
        ],
        toolbar: {
          refresh: {
            query: this.loadData
          },
          custom: true,
          zoom: true,
          slots: {
            buttons: 'toolbar_buttons',
            tools: 'toolbar_tools'
          }
        }
      }
    }
  },
  created () {
    this.loadData()
  },
  methods: {
    loadData () {
      this.$http.get('/revenue/getList', { params: this.queryParams }).then(res => {
        if (res.success && res.data) {
          this.gridOptions.data = res.data
        } else {
          this.$message.error('获取数据失败')
        }
      })
    },
    delRow (year) {
      const self = this
      this.$confirm({
        title: '是否确定删除?',
        onOk () {
          return self.$http.post('/revenue/del', { year: year })
            .then(res => {
              if (res.success && res.data) {
                self.$message.success('删除成功')
              } else {
                self.$message.error(res.errorMessage ? res.errorMessage : '删除失败')
              }
            }).finally(res => {
              self.loadData()
            })
        },
        onCancel () {
        }
      })
    }
  }
}
</script>
